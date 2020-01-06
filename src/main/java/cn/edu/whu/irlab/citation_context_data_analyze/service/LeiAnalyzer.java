package cn.edu.whu.irlab.citation_context_data_analyze.service;

import cn.edu.whu.irlab.citation_context_data_analyze.exception.LeiAnalyzerException;
import cn.edu.whu.irlab.citation_context_data_analyze.model.Record;
import cn.edu.whu.irlab.citation_context_data_analyze.model.RefLabelElement;
import cn.edu.whu.irlab.citation_context_data_analyze.model.SentenceElement;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.PatternSyntaxException;

/**
 * @author gcr19
 * @version 1.0
 * @date 2019/12/20 15:43
 * @desc 雷声伟数据的解析器
 **/
@Service
public class LeiAnalyzer {

    private final static Logger logger = LoggerFactory.getLogger(LeiAnalyzer.class);

    @Autowired
    private GrobidService grobidService;

    private Element article;
    private Map<Integer, SentenceElement> sentenceElementMap;
    private Map<Integer, Map<Integer, SentenceElement>> paragraphElementMap;
    private Map<Integer, RefLabelElement> refLabelElementMap;

    public void init(Element article) throws LeiAnalyzerException {
        this.article = article;
        sentenceElementMap = new HashMap<>();
        paragraphElementMap = new HashMap<>();
        refLabelElementMap = new HashMap<>();
        collectParagraphs(article);
    }

    public void collectParagraphs(Element element) throws LeiAnalyzerException {
        if (element.getName().equals("p")) {
            try {
                if (!element.getParentElement().getName().equals("references"))
                    paragraphElementMap.put(element.getAttribute("number").getIntValue(), collectSentencesAndRefLabels(element));
            } catch (DataConversionException e) {
                logger.error(e.getMessage());
            }
        } else {
            for (Element e :
                    element.getChildren()) {
                collectParagraphs(e);
            }
        }
    }

    public Map<Integer, SentenceElement> collectSentencesAndRefLabels(Element paragraph) throws LeiAnalyzerException {
        Map<Integer, SentenceElement> sentenceElementMap = new HashMap<>();
        for (Element e :
                paragraph.getChildren()) {
            if (!e.getName().equals("s")) continue;
            SentenceElement s = new SentenceElement();
            try {
                String[] s_number = e.getAttributeValue("number").split(",");
                s.setNum(Integer.parseInt(s_number[0]));
                s.setCType(e.getAttributeValue("c_type"));
                s.setMarkType(e.getAttributeValue("mark_type"));
                s.setContent(e.getValue());
                s.setpNum(e.getAttribute("p").getIntValue());
                if (e.getChildren().size() != 0 || e.getChildren() != null) {
                    s.setHasRefLabel(true);
                    for (Element ef :
                            e.getChildren()) {
                        RefLabelElement r = new RefLabelElement();
                        r.setNum(ef.getAttribute("number").getIntValue());
                        r.setsNum(s.getNum());
                        r.setpNum(s.getpNum());
                        if (ef.getAttribute("ref_num") != null) {
                            r.setRefNum(ef.getAttribute("ref_num").getIntValue());
                        } else {
                            r.setRefNum(-1);
                        }
                        r.setContent(ef.getValue());
                        try {
                            String r_content = r.getContent().replaceAll("\\(", "&bracp;").
                                    replaceAll("\\)", "&brace;");
                            String s_content = s.getContent().replaceAll("\\(", "&bracp;").
                                    replaceAll("\\)", "&brace;");
                            r.setAtSentenceContent(s_content.replaceAll(r_content, "[#]").
                                    replaceAll("&bracp;", "\\(").replaceAll("&brace;", "\\)"));
                        } catch (PatternSyntaxException p) {
                            throw new LeiAnalyzerException("PatternSyntaxException :" + p.getMessage() + " in sentence: " + r.getsNum() + " reference: " + r.getNum());
                        }

                        Attribute contextAttr = ef.getAttribute("context");
                        if (contextAttr != null && !contextAttr.getValue().equals("")) {
                            String[] context = ef.getAttributeValue("context").split(",");
                            List<Integer> contextList = new ArrayList<>();
                            for (int i = 0; i < context.length; i++) {
                                contextList.add(Integer.parseInt(context[i]));
                            }
                            r.setContextNum(contextList);
                        } else {
                            r.setContextNum(null);
                        }
                        refLabelElementMap.put(r.getNum(), r);
                    }
                } else {
                    s.setHasRefLabel(false);
                }
            } catch (DataConversionException ex) {
                logger.error(ex.getMessage());
            }
            sentenceElementMap.put(s.getNum(), s);
            this.sentenceElementMap.put(s.getNum(), s);
        }
        return sentenceElementMap;
    }

    public void parseReference() {
        Element references = article.getChild("body").getChild("references");
        for (Element ref :
                references.getChildren()) {
            ref.setName("ref");
            String stringRef=ref.getValue();
            grobidService.processCitation(stringRef);
        }
    }

    public List<Record> extractRecord() throws LeiAnalyzerException {
        List<Record> records = new ArrayList<>();
        int groupId = 0;
        for (Map.Entry<Integer, RefLabelElement> entry :
                refLabelElementMap.entrySet()) {
            groupId++;
            RefLabelElement refLabelElement = entry.getValue();
            Integer paragraphId = refLabelElement.getpNum();
            List<Integer> contextList = refLabelElement.getContextNum();

            Map<Integer, SentenceElement> sentenceElementMap = paragraphElementMap.get(paragraphId);
            if (sentenceElementMap == null)
                throw new LeiAnalyzerException("paragraph: " + paragraphId + "为空");
            for (Map.Entry<Integer, SentenceElement> entry1 :
                    sentenceElementMap.entrySet()) {
                SentenceElement sentenceElement = entry1.getValue();
                Record record = new Record();
                record.setDistance(refLabelElement.getsNum() - sentenceElement.getNum());
                record.setIsCitationContext(record.getDistance() == 0 ? 1 : isContext(contextList, sentenceElement.getNum()));
                record.setNeighborSentence(sentenceElement.getContent());
                record.setSentenceHasLabel(refLabelElement.getAtSentenceContent());
                record.setGroupId(groupId);
                records.add(record);
            }
        }
        return records;
    }

    private int isContext(List<Integer> contextList, Integer integer) {
        if (contextList == null) {
            return 0;
        }

        if (contextList.contains(integer))
            return 1;
        else
            return 0;
    }


}
