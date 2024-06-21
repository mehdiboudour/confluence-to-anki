import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
@Disabled
class DomManipulationTest {
    //https://www.baeldung.com/java-xerces-dom-parsing
    //https://stackoverflow.com/questions/8484921/how-to-convert-string-to-dom-document-object-in-java
    //https://www.grepper.com/answers/262656/java+read+file+to+string?ucard=1
    //https://stackoverflow.com/questions/10356258/how-do-i-convert-a-org-w3c-dom-document-object-to-a-string

    private static final String SAMPLE_DOCUMENT = "src/test/resources/test.html";

    private String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    @Test
    void browsingConfluencePageBody() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        String confluencePageBodyExample = this.readFile(SAMPLE_DOCUMENT);
        InputSource is = new InputSource();
        confluencePageBodyExample = confluencePageBodyExample.replace((char) 92, ' ');
        is.setCharacterStream(new StringReader(confluencePageBodyExample));
        Document document = builder.parse(is);
        document.getDocumentElement().normalize();
        //Very first ul
        Node root = document.getFirstChild();
        NodeList children = root.getChildNodes();
        Node current;
        for(int i=0; i<5; i++) {
            current = children.item(i);
            System.out.println(i+" = "+current.getNodeName());
            if(current.getNodeType() == Node.ELEMENT_NODE) {
                if(current.getNodeName().equals("li")) {
                    //This means it is a question
                    System.out.printf("Question : %s%n", current.getTextContent());
                }
                if(current.getNodeName().equals("ul")) {
                   //This means it is an answer or a set of answers.
                    // Either Basic or Cloze
                    NodeList items = current.getChildNodes();
                    if(items.getLength() == 1) {
                        System.out.println("Answer : " + current.getFirstChild().getTextContent());
                    } else {
                        for(int j=0; j<items.getLength(); j++)
                            System.out.println("* "+items.item(j).getTextContent());
                    }
                    System.out.println();
                }
            }
        }
        Assertions.assertTrue(Boolean.TRUE, "Test passed.");
    }

    @Test
    void creatingCleanFormattedClozeTexts() throws ParserConfigurationException, TransformerException {
        final String CLOZE_ITEM_FORMAT = "{{c%d::%s}}";
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document clozeitems = builder.newDocument();
        Element root = clozeitems.createElement("ul");
        clozeitems.appendChild(root);
        Element item1 = clozeitems.createElement("li");
        root.appendChild(item1);
        item1.setTextContent(CLOZE_ITEM_FORMAT.formatted(1, "First item."));
        Element item2 = clozeitems.createElement("li");
        root.appendChild(item2);
        item2.setTextContent(CLOZE_ITEM_FORMAT.formatted(2, "Second item."));
        Element item3 = clozeitems.createElement("li");
        root.appendChild(item3);
        item3.setTextContent(CLOZE_ITEM_FORMAT.formatted(3, "Third itm."));
        DOMSource domSource = new DOMSource(clozeitems);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(domSource, result);
        System.out.println(writer);
        Assertions.assertTrue(Boolean.TRUE, "Test OK.");
    }
}
