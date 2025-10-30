package Repository.XML;

import Model.Departamento;
import org.w3c.dom.*;
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
import java.io.IOException;
import java.util.ArrayList;

public class XMLDepartamentoRepository{

    public void algo() throws ParserConfigurationException, TransformerException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element raiz = doc.createElement("departamentos");
        doc.appendChild(raiz);

        ArrayList<Departamento> departamentos = new ArrayList<>();

        for (Departamento d : departamentos) {
            Element departamento = doc.createElement("departamento");
            Attr id = doc.createAttribute("id");
            Element nombre = doc.createElement("nombre");
            Element capacidad = doc.createElement("capacidadMaxima");

            id.setValue(String.valueOf(d.getId()));
            nombre.setTextContent(d.getNombre());
            capacidad.setTextContent(String.valueOf(d.getCapacidadMaxima()));

            departamento.appendChild(nombre);
            departamento.appendChild(capacidad);
            departamento.setAttributeNode(id);

            raiz.appendChild(departamento);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("departamentos.xml"));
        transformer.transform(source, result);

        ArrayList<Departamento> departamentosLeidos = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document docLectura = dBuilder.parse(new File("departamentos.xml"));
        docLectura.getDocumentElement().normalize();

        NodeList nList = docLectura.getElementsByTagName("departamento");

        for (int i = 0; i < nList.getLength(); i++) {
            Node unNodoHijo = nList.item(i);
            Element e = (Element) unNodoHijo;

            int id = Integer.parseInt(e.getAttribute("id"));
            String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
            int capacidad = Integer.parseInt(e.getElementsByTagName("capacidadMaxima").item(0).getTextContent());

            departamentosLeidos.add(new Departamento(id, nombre, capacidad));
        }
    }
}



