import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class creates xml, read it, modify it.
 * Doesn't require using window.
 *
 * @author Vad Nik
 * @version 1.0 dated Feb 21, 2018
 */
public class XmlProcessing {
    private static final String XML_NAME = NotesMain.userPath + "\\Data.xml";
    private static Node rootN;
    private static Document documentD;
    private static final String NOTE_TAG = "note";
    private static final String SERVICE_TAG = "service";
    private static final String NAME_TAG = "name";
    private static final String TEXT_TAG = "text";
    private static final String XML_DEF_TEXT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><notes><service id=\"0\">0</service><service id=\"1\">true</service></notes>";
    private static final String DATE_TAG = "date";

    /**
     * Class constructor creates xml file,
     * describes base xml management tools.
     */
    XmlProcessing() {
        File file = new File(XML_NAME);
        if (!file.exists()) {
            try(FileWriter fileWriter = new FileWriter(XML_NAME)) {
                fileWriter.write(XML_DEF_TEXT);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(XML_NAME);
            rootN = document.getDocumentElement();
            documentD = document;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method that is used to read xml file.
     *
     * @return hash map {@code readXml} that contains special params.
     */
    static HashMap readXml() {
        HashMap<String, String> readXml = new HashMap<>();
        NodeList notes = rootN.getChildNodes();
        int s = 0;
        for (int i = 0; i < notes.getLength(); i++) {
            Node note = notes.item(i);
            if (note.getNodeType() != Node.TEXT_NODE) {
                NodeList notesProps = note.getChildNodes();

                for (int j = 0; j < notesProps.getLength(); j++) {
                    Node noteProp = notesProps.item(j);

                    if (note.getNodeName().equals(SERVICE_TAG) && note.getAttributes().item(0).getTextContent().equals("0"))
                        s = Integer.parseInt(note.getTextContent());

                    if (noteProp.getNodeType() != Node.TEXT_NODE) {
                        readXml.put(String.valueOf(s),
                                note.getChildNodes().item(0).getTextContent() + ":" +
                                        noteProp.getChildNodes().item(0).getTextContent() + "~");
                    }
                }
                if (!note.getTextContent().equals("0"))
                    s++;
            }
        }
        return readXml;
    }

    /**
     * Checks whether program can run.
     *
     * @return true if program can run, otherwise returns false.
     */
    static boolean canRun() {
        String s = null;
        NodeList nodes = rootN.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeName().equals(SERVICE_TAG) && node.getAttributes().item(0).getTextContent().equals("1"))
                s = node.getTextContent();
        }

        return Boolean.valueOf(s);
    }

    /**
     * Sets the canRun flag {@code canRun()} to false.
     */
    static void setRunFlagToFalse() {
        if (canRun()) {
            NodeList nodes = rootN.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeName().equals(SERVICE_TAG) && node.getAttributes().item(0).getTextContent().equals("1")) {
                    node.setTextContent("false");
                }
            }

            try {
                writeDocument();
            } catch (TransformerConfigurationException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Sets the canRun flag {@code canRun()} to true.
     */
    static void setRunFlagToTrue() {
        if (!canRun()) {
            NodeList nodes = rootN.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeName().equals(SERVICE_TAG) && node.getAttributes().item(0).getTextContent().equals("1")) {
                    node.setTextContent("true");
                }
            }

            try {
                writeDocument();
            } catch (TransformerConfigurationException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Method counts the number for the param to be correctly placed into hash map correctly.
     *
     * @return value produced by the method.
     */
    private static String countDoublePlus() {
        String s = null;
        NodeList nodeList = rootN.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeName().equals(SERVICE_TAG) && node.getAttributes().item(0).getTextContent().equals("0"))
                s = node.getTextContent();
        }

        if (s != null) {
            int g = Integer.parseInt(s);
            g++;
            s = String.valueOf(g);
        }
        return s;
    }

    /**
     * Method gets value to be counted.
     *
     * @return value of service tag {@code SERVICE_TAG} in the xml file.
     */
    static int getCount() {
        int s = 0;
        NodeList nodeList = rootN.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeName().equals(SERVICE_TAG) && node.getAttributes().item(0).getTextContent().equals("0"))
                s = Integer.parseInt(node.getTextContent());
        }
        return s;
    }

    /**
     * Resets the counter.
     */
    static void resetCount() {
        rootN.getChildNodes().item(0).setTextContent(String.valueOf(0));
        try {
            writeDocument();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a note.
     *
     * @param item id of the note that needs to be deleted.
     */
    static void deleteNote(int item) {
        Node del = documentD.getDocumentElement().getElementsByTagName(NOTE_TAG).item(item);
        rootN.removeChild(del);
        try {
            writeDocument();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Edits notes.
     *
     * @param name name of the note.
     * @param text text of the note.
     * @param item id of the note that needs to be edited.
     */
    static void editNote(String name, String text, int item) {
        Node node = documentD.getDocumentElement().getElementsByTagName(NOTE_TAG).item(item);
        rootN.removeChild(node);
        createNote(name, text);
        try {
            writeDocument();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a note.
     *
     * @param nameS name of the note.
     * @param textS text of the note.
     */
    static void createNote(String nameS, String textS) {
        Element note = documentD.createElement(NOTE_TAG);

        Element name = documentD.createElement(NAME_TAG);
        name.setTextContent(nameS);

        Element text = documentD.createElement(TEXT_TAG);
        text.setTextContent(textS);

        note.appendChild(name);
        note.appendChild(text);

        rootN.appendChild(note);
        rootN.getChildNodes().item(0).setTextContent(countDoublePlus());

        try {
            writeDocument();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Writes modifications into the xml file.
     *
     * @throws TransformerConfigurationException just not to handle this exception.
     */
    private static void writeDocument() throws TransformerConfigurationException {
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(documentD);
        try(FileOutputStream fs = new FileOutputStream(XML_NAME)) {
            StreamResult result = new StreamResult(fs);
            tr.transform(source, result);
            fs.close();
        } catch (IOException | TransformerException ex) {
            ex.printStackTrace();
        }

    }
}