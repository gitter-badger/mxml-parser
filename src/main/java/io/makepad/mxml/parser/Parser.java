package io.makepad.mxml.parser;
import io.makepad.mxml.resolver.MXMLEntityResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

public class Parser {
    public Document document;
    public Parser(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       // factory.setValidating(true);
        //factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new MXMLEntityResolver());
        File file = new File(filePath);
        // TODO: Check for the score-partwise tag to be sure that it is a MusicXML file
        this.document = builder.parse(file);
    }
}
