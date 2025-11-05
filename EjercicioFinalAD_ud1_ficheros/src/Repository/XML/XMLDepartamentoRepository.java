package Repository.XML;

import Model.Departamento;
import Repository.DepartamentoRepository;
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
import java.util.List;

public class XMLDepartamentoRepository implements DepartamentoRepository {

    final String nombreArchivo;

    public XMLDepartamentoRepository(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void guardar(Departamento departamento) throws IOException {
        List<Departamento> departamentosAGuardar = new ArrayList<>();
        try {
            departamentosAGuardar = findAll();
        } catch (IOException e) {
        }

        departamentosAGuardar.add(departamento);

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element raiz = doc.createElement("departamentos");
            doc.appendChild(raiz);

            for (Departamento d : departamentosAGuardar) {
                Element dptoElement = doc.createElement("departamento");
                Attr id = doc.createAttribute("id");
                Element nombre = doc.createElement("nombre");
                Element capacidad = doc.createElement("capacidadMaxima");

                id.setValue(String.valueOf(d.getId()));
                nombre.setTextContent(d.getNombre());
                capacidad.setTextContent(String.valueOf(d.getCapacidadMaxima()));

                dptoElement.appendChild(nombre);
                dptoElement.appendChild(capacidad);
                dptoElement.setAttributeNode(id);

                raiz.appendChild(dptoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreArchivo));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new IOException("Error al guardar el departamento en XML: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Departamento> findAll() throws IOException {
        List<Departamento> departamentosLeidos = new ArrayList<>();
        File xmlFile = new File(nombreArchivo);

        if (!xmlFile.exists()) {
            return departamentosLeidos;
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document docLectura = dBuilder.parse(xmlFile);
            docLectura.getDocumentElement().normalize();

            NodeList nList = docLectura.getElementsByTagName("departamento");

            for (int i = 0; i < nList.getLength(); i++) {
                Node unNodoHijo = nList.item(i);
                if (unNodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) unNodoHijo;

                    int id = Integer.parseInt(e.getAttribute("id"));
                    String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                    int capacidad = Integer.parseInt(e.getElementsByTagName("capacidadMaxima").item(0).getTextContent());

                    departamentosLeidos.add(new Departamento(id, nombre, capacidad));
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error al leer departamentos desde XML: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new IOException("Error al parsear datos numéricos desde XML: " + e.getMessage(), e);
        }
        return departamentosLeidos;
    }
}



