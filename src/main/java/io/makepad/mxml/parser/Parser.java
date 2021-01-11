package io.makepad.mxml.parser;

import io.makepad.mxml.exceptions.FileFormatException;
import io.makepad.mxml.exceptions.MXMLParserException;
import io.makepad.mxml.resolver.MXMLEntityResolver;
import io.makepad.mxml.utils.Filename;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parser {
  private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
  public Document document;
  /*
  * Class constructor
  * @param filePath the path of the MusicXML file
  */
  public Parser(String filePath) throws MXMLParserException, FileFormatException {
    try {
      String extension = Filename.getExtension(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setIgnoringComments(true);
      DocumentBuilder builder = factory.newDocumentBuilder();
      builder.setEntityResolver(new MXMLEntityResolver());
      File file = new File(filePath);
      InputStream inputStream = new FileInputStream(file);
      Map<String,InputSource> Files = new HashMap<String,InputSource>();
      XPathFactory xPathFactory = XPathFactory.newInstance();
      switch (extension) {
        case "mxl":
          LOGGER.info("Compressed document");
          ZipInputStream zipInputStream = new ZipInputStream(inputStream);
          ZipEntry zipEntry = null;
          String zipEntryName = null;
          while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            InputSource currentInputSource=getInputSourceFromZipInputStream(zipInputStream);
            Files.put(zipEntry.getName(),currentInputSource);
            if ("META-INF/container.xml".equals(zipEntry.getName())) {
              Document container =
                  builder.parse(currentInputSource);
              XPath xpath = xPathFactory.newXPath();
              zipEntryName =
                  (String) xpath.evaluate("container/rootfiles/rootfile/@full-path",
                      container, XPathConstants.STRING);
            } else if (zipEntry.getName().equals(zipEntryName))
              document = builder.parse(currentInputSource);
            zipInputStream.closeEntry();
          }
          if (document==null && !(zipEntryName==null)) {
            document = builder.parse(Files.get(zipEntryName));
          }
          break;
        case "mxml":
          LOGGER.info("Non compressed document");
          this.document = builder.parse(file);
          if (!this.checkDocument()) {
            MXMLParserException e = new MXMLParserException("The document is not a valid music xml file");
            LOGGER.throwing(Parser.class.getName(), "constructor", e);
            throw e;
          }
          break;
        default:
          FileFormatException e = new FileFormatException(String.format("%s format is not supported", extension));
          LOGGER.throwing(Parser.class.getName(), "constructor", e);
          throw e;
      }



    } catch (ParserConfigurationException e) {
      LOGGER.throwing(Parser.class.getName(), "constructor", e);
      e.printStackTrace();
    } catch (IOException e) {
      LOGGER.throwing(Parser.class.getName(), "constructor", e);
      e.printStackTrace();
    } catch (SAXException e) {
      LOGGER.throwing(Parser.class.getName(), "constructor", e);
      e.printStackTrace();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
  }

  private boolean checkDocument() {
      return this.document.getDocumentElement().getTagName().equals("score-partwise");
  }

  private InputSource getInputSourceFromZipInputStream(
      ZipInputStream zipInputStream
  ) throws IOException {
    BufferedReader
        reader = new BufferedReader(new InputStreamReader(zipInputStream));
    StringBuilder stringBuilder = new StringBuilder();
    String string = null;
    while ((string = reader.readLine()) != null)
      stringBuilder.append(string + "\n");
    return new InputSource(new StringReader(stringBuilder.toString()));
  }


}
