package io.makepad.mxml.parser;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParserTest {
    private static final Logger LOGGER = Logger.getLogger(ParserTest.class.getName());
    private Document parsedDocument;
    private boolean isDocumentValid;

    public ParserTest(String filePath, boolean isDocumentValid) {
        LOGGER.info(String.format("File path %s", filePath));
        LOGGER.info(String.format("isDocumentValid %s", isDocumentValid));
        this.parsedDocument = new Parser(filePath).document;
        LOGGER.info(String.format("Parsed document uri %s", this.parsedDocument.getDocumentURI()));
        LOGGER.info(String.format("Document element tag name %s", this.parsedDocument.getDocumentElement().getTagName()));
        this.isDocumentValid = isDocumentValid;
    }

    @Parameterized.Parameters(name = "Run {index}: filePath:{0}, isValid:{1}")
    public static Iterable<Object[]> data() {
        String validPrefix = "src/test/resources/valid";
        String invalidPrefix = "src/test/resources/invalid";
        String[] validFiles = new File(validPrefix).list();
        String[] invalidFiles = new File(invalidPrefix).list();
        ArrayList<Object[]> filesList = new ArrayList<Object[]>();
        for (String validFilePath: validFiles) {
            filesList.add(new Object[]{new File(validPrefix + "/" + validFilePath).getAbsolutePath(), true});
        }
        for (String invalidFilePath: invalidFiles) {
            filesList.add(new Object[]{new File(invalidPrefix + "/" + invalidFilePath).getAbsolutePath(), false});
        }
        return filesList;
    }

    @Test
    public void testValidity() {
        LOGGER.info(String.format("Parsed document tag name %s", this.parsedDocument.getDocumentElement().getTagName()));
        LOGGER.info(String.format("Test will return %b", this.parsedDocument.getDocumentElement().getTagName().equals("score-partwise")));
        LOGGER.info(String.format("isDocument valid %b", this.isDocumentValid));
        assertEquals(this.parsedDocument.getDocumentElement().getTagName().equals("score-partwise"), this.isDocumentValid);
    }
}
