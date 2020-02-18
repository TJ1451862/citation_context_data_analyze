package cn.edu.whu.irlab.citation_context_data_analyze.util;


import org.apache.commons.io.FileUtils;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author gcr19
 * @date 2019-10-12 15:19
 * @desc
 **/
public class WriteUtil {

    private final static Logger logger = LoggerFactory.getLogger(WriteUtil.class);

    public static void writeXml(Element element,String outputPath) throws IOException {
        XMLOutputter outputter=new XMLOutputter();
        outputter.setFormat(outputter.getFormat().setEncoding("UTF-8"));
        outputter.output(element,new FileOutputStream(outputPath));
    }

    public static void writeStr(String docPath, String content) {
        File file = new File(docPath);
        try {
            FileUtils.writeStringToFile(file, content, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
