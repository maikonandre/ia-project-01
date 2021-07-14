package controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vo.SalaVO;

/**
 *
 * @author maikon
 */
public class SalaController {

    public SalaController() {
    }
    
    public SalaVO[] CarregarSalas() {
        SalaVO[] salas = new SalaVO[21];
        
        try {
            
            File xmlFile = new File("./src/model/salas.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            NodeList nList = doc.getElementsByTagName("sala");
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    
                    SalaVO newSala = new SalaVO();
                    newSala.setId(eElement.getAttribute("id"));
                    String listVizinhos[] = eElement.getElementsByTagName("vizinhos").item(0).getTextContent().split(";");
                    newSala.setVizinhos(listVizinhos);
                    
                    salas[i] = newSala;
                }
            }
        }
        catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
        
        return salas;
    }
}
