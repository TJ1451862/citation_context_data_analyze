package cn.edu.whu.irlab.citation_context_data_analyze.service;

import cn.edu.whu.irlab.citation_context_data_analyze.exception.LeiAnalyzerException;
import cn.edu.whu.irlab.citation_context_data_analyze.util.TypeConverter;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author gcr19
 * @version 1.0
 * @date 2020/1/6 15:59
 * @desc 雷声伟数据预处理器
 **/
@Service
public class LeiProcessor {

    private final static Logger logger = LoggerFactory.getLogger(LeiProcessor.class);

    @Autowired
    private GrobidService grobidService;


    private Element article;

    public void  init(Element article) {
        this.article = article;
    }

    public void parseReferences() {
        Element references = article.getChild("body").getChild("references");
        for (Element ref :
                references.getChildren()) {
            try {
                parseCitation(ref);
            } catch (LeiAnalyzerException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void parseCitation(Element ref) throws LeiAnalyzerException {
        if (!ref.getParentElement().getName().equals("references")) {
            throw new LeiAnalyzerException("Not reference element, the element name is: " + ref.getName());
        }
        String stringRef = grobidService.parseCitation(ref.getValue());
        Element biblStruct = null;
        try {
            biblStruct = TypeConverter.str2xml(stringRef);
        } catch (JDOMException | IOException e) {
            logger.error(e.getMessage());
        }
        ref.removeContent();

        if (biblStruct != null) {
            ref.setContent(biblStruct.removeContent());
        }
        ref.setName("ref");
    }

    public Element getArticle() {
        return article;
    }
}
