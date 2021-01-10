package io.makepad.mxml.parser;

import io.makepad.mxml.resolver.MXMLEntityResolver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Parser {
  private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
  public Document document;
  /*
  * Class constructor
  * @param filePath the path of the MusicXML file
  */
  public Parser(String filePath) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setIgnoringComments(true);
      DocumentBuilder builder = factory.newDocumentBuilder();
      builder.setEntityResolver(new MXMLEntityResolver());
      File file = new File(filePath);
      System.out.println("New file created");
      this.document = builder.parse(file);
    } catch (ParserConfigurationException e) {
      LOGGER.throwing(Parser.class.getName(), "constructor", e);
      e.printStackTrace();
    } catch (IOException e) {
      LOGGER.throwing(Parser.class.getName(), "construcor", e);
      e.printStackTrace();
    } catch (SAXException e) {
      LOGGER.throwing(Parser.class.getName(), "construcor", e);
      e.printStackTrace();
    }
  }

  private boolean checkDocument() {
      return this.document.getDocumentElement().getTagName().equals("score-partwise");
  }
}
