package cn.edu.whu.irlab.citation_context_data_analyze.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author gcr19
 * @date 2019-08-18 09:57
 * @desc 类型转换工具
 **/
public class TypeConverter {

    public static Element str2xml(String string) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Element root = null;
        Document doc = saxBuilder.build(new StringReader(string));
        root = doc.getRootElement();
        return root;
    }


    public static String element2string(Element element){
        XMLOutputter outputter=new XMLOutputter();
        return outputter.outputElementContentString(element);
    }
}
