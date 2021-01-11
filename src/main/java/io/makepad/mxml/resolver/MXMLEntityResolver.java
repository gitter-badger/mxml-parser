package io.makepad.mxml.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MXMLEntityResolver implements org.xml.sax.EntityResolver {
  private static final Logger LOGGER = Logger.getLogger(MXMLEntityResolver.class.getName());
  private static final String dtdFolder = "/mxml/dtd/";
  private static final Map<String, String> PUBLIC_ID_MAP =
      Map.ofEntries(
          Map.entry("-//Recordare//DTD MusicXML 0.6b Partwise//EN", dtdFolder + "1.0/partwise.dtd"),
          Map.entry("-//Recordare//DTD MusicXML 0.7b Partwise//EN", dtdFolder + "1.0/partwise.dtd"),
          Map.entry("-//Recordare//DTD MusicXML 1.0 Partwise//EN", dtdFolder + "1.0/partwise.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.0 Attributes//EN",
              dtdFolder + "1.0/attributes.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.0 Barline//EN", dtdFolder + "1.0/barline.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.0 Direction//EN", dtdFolder + "1.0/direction.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.0 Common//EN", dtdFolder + "1.0/common.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.0 Identity//EN", dtdFolder + "1.0/identity.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.0 Link//EN", dtdFolder + "1.0/link.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.0 Note//EN", dtdFolder + "1.0/note.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.0 Score//EN", dtdFolder + "1.0/score.dtd"),
          Map.entry("-//Recordare//DTD MusicXML 1.1 Partwise//EN", dtdFolder + "1.1/partwise.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.1 Attributes//EN",
              dtdFolder + "1.1/attributes.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.1 Barline//EN", dtdFolder + "1.1/barline.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.1 Common//EN", dtdFolder + "1.1/common.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.1 Direction//EN", dtdFolder + "1.1/direction.dtd"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 1.1 Identity//EN", dtdFolder + "1.1/identity.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.1 Layout//EN", dtdFolder + "1.1/layout.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.1 Link//EN", dtdFolder + "1.1/link.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.1 Note//EN", dtdFolder + "1.1/note.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 1.1 Score//EN", dtdFolder + "1.1/score.dtd"),
          Map.entry("-//Recordare//DTD MusicXML 2.0 Partwise//EN", dtdFolder + "2.0/partwise.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 2.0 Common//EN", dtdFolder + "2.0/common.mod"),
          Map.entry(
              "ISO 8879:1986//ENTITIES Added Latin 1//EN//XML", dtdFolder + "2.0/isolat1.ent"),
          Map.entry(
              "ISO 8879:1986//ENTITIES Added Latin 2//EN//XML", dtdFolder + "2.0/isolat2.ent"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 2.0 Layout//EN", dtdFolder + "2.0/layout.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 2.0 Identity//EN", dtdFolder + "2.0/identity.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 2.0 Attributes//EN",
              dtdFolder + "2.0/attributes.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 2.0 Link//EN", dtdFolder + "2.0/link.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 2.0 Note//EN", dtdFolder + "2.0/note.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 2.0 Barline//EN", dtdFolder + "2.0/barline.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 2.0 Direction//EN", dtdFolder + "2.0/direction.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 2.0 Score//EN", dtdFolder + "2.0/score.mod"),
          Map.entry("-//Recordare//DTD MusicXML 3.0 Partwise//EN", dtdFolder + "3.0/partwise.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.0 Common//EN", dtdFolder + "3.0/common.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.0 Layout//EN", dtdFolder + "3.0/layout.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.0 Identity//EN", dtdFolder + "3.0/identity.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.0 Attributes//EN",
              dtdFolder + "3.0/attributes.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.0 Link//EN", dtdFolder + "3.0/link.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.0 Note//EN", dtdFolder + "3.0/note.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.0 Barline//EN", dtdFolder + "3.0/barline.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.0 Direction//EN",
              dtdFolder + "3.0/direction.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.0 Score//EN", dtdFolder + "3.0/score.mod"),
          Map.entry("-//Recordare//DTD MusicXML 3.1 Partwise//EN", dtdFolder + "3.1/partwise.dtd"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.1 Common//EN", dtdFolder + "3.1/common.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.1 Layout//EN", dtdFolder + "3.1/layout.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.1 Identity//EN", dtdFolder + "3.1/identity.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.1 Attributes//EN",
              dtdFolder + "3.1/attributes.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.1 Link//EN", dtdFolder + "3.1/link.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.1 Note//EN", dtdFolder + "3.1/note.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.1 Barline//EN", dtdFolder + "3.1/barline.mod"),
          Map.entry(
              "-//Recordare//ELEMENTS MusicXML 3.1 Direction//EN", dtdFolder + "3.1/direction.mod"),
          Map.entry("-//Recordare//ELEMENTS MusicXML 3.1 Score//EN", dtdFolder + "3.1/score.mod"));

  /*
   * Function resolves the entity of the XML file which will be parsed
   * @param publicID the public id of the entity
   * @param systemID the system id of the entity
   */
  public final InputSource resolveEntity(String publicID, String systemID)
      throws SAXException, IOException {
    LOGGER.info("Resolve entity function called");
    LOGGER.info(String.format("Public ID %s", publicID));
    LOGGER.info(String.format("System ID %s", systemID));
    String fileName = PUBLIC_ID_MAP.get(publicID);
    LOGGER.info(String.format("File name %s", fileName));
    if (fileName != null) {
      LOGGER.fine("File name is not null");
      InputStream inputStream = getClass().getResourceAsStream(fileName);
      if (inputStream != null) {
        LOGGER.fine("Created input stream is not null");
        return new InputSource(inputStream);
      }
      LOGGER.warning("Created input stream is null");
    }
    LOGGER.warning("File name is null");
    return null;
  }
}
