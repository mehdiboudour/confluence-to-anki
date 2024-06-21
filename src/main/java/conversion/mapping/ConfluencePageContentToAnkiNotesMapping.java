package conversion.mapping;

import anki.model.Basic;
import anki.model.Cloze;
import anki.model.Note;
import anki.model.resources.NotesBatch;
import confluence.model.resources.LabeledPage;
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

public class ConfluencePageContentToAnkiNotesMapping {

    private static final String CLOZE_ITEM_FORMAT = "{{c%d::%s}}";
    private static final String TAGS_HIERARCHY_SEPARATOR = "::";
    private static final String UNORDERED_LIST_TAG_NAME = "ul";
    private static final String LIST_ITEM_TAG_NAME = "li";
    private static final String CLOZE_HEADER_FORMAT = "<div>%s</div>";
    private static final String VALUE_OF_OMIT_XML_DECLARATION = "yes";

    public NotesBatch mapFrom(LabeledPage confluencePage) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String tags = this.formNoteTagsFromPageLabels(confluencePage);
        Node root = this.extractTheRootNodeOf(confluencePage);

        NodeList children = root.getChildNodes(); //source elements to be browsed
        NotesBatch notes = new NotesBatch(); //batch of notes to fill
        Node currentNode; //the current node while browsing
        String firstField = ""; //first field of the current anki note
        String secondField; //second field of the current anki note
        for(int i=0; i<children.getLength(); i++) {
            currentNode = children.item(i);

            //The node is a first field
            if(currentNode.getNodeName().equals(LIST_ITEM_TAG_NAME)) {
                firstField = currentNode.getTextContent();
            }

            //The node is a second field
            if(currentNode.getNodeName().equals(UNORDERED_LIST_TAG_NAME)) {
                NodeList itemsOfUl = currentNode.getChildNodes();
                Note note;

                if(itemsOfUl.getLength() == 1) { //It's a basic note
                    secondField = currentNode.getFirstChild().getTextContent();
                    note = new Basic(firstField, secondField);
                } else { //It's a cloze note
                    firstField = CLOZE_HEADER_FORMAT.formatted(firstField);
                    secondField = this.formatClozeItemsFromUlNodeList(itemsOfUl);
                    note = new Cloze(firstField+secondField, "");
                }
                note.setTags(tags);
                notes.add(note);
            }
            //Any other tag type will be ignored
        }
        return notes;
    }

    private Node extractTheRootNodeOf(LabeledPage page) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        //Replace all the '\' characters to avoid parsing errors
        String body = page.getBody().replace((char) 92, ' ');
        is.setCharacterStream(new StringReader(body));
        Document document = builder.parse(is);
        document.getDocumentElement().normalize();
        return document.getFirstChild();
    }

    private String formNoteTagsFromPageLabels(LabeledPage page) {
        StringBuilder tags = new StringBuilder();
        int index = 0;
        do {
            tags.append(page.getLabels().get(index));
            index++;
            if(index < page.getLabels().size())
                tags.append(TAGS_HIERARCHY_SEPARATOR);
        } while (index < page.getLabels().size());

        return tags.toString();
    }

    private String formatClozeItemsFromUlNodeList(NodeList sourceItems) throws ParserConfigurationException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document clozeitems = builder.newDocument();
        Element ul = clozeitems.createElement(UNORDERED_LIST_TAG_NAME);
        clozeitems.appendChild(ul);
        for (int i=0; i<sourceItems.getLength(); i++) {
            Element item = clozeitems.createElement(LIST_ITEM_TAG_NAME);
            item.setTextContent(CLOZE_ITEM_FORMAT.formatted(i+1, sourceItems.item(i).getTextContent()));
            ul.appendChild(item);
        }

        return this.printDomDocumentToString(clozeitems);
    }

    private String printDomDocumentToString(Document document) throws TransformerException {
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, VALUE_OF_OMIT_XML_DECLARATION);
        transformer.transform(domSource, result);
        return writer.toString();
    }
}
