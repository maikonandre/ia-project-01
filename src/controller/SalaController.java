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
            
            System.out.println("Root do elemento: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("sala");
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    
                    SalaVO newSala = new SalaVO();
                    newSala.setId(eElement.getAttribute("id"));
                    newSala.setX(Integer.parseInt(eElement.getAttribute("x")));
                    newSala.setY(Integer.parseInt(eElement.getAttribute("y")));
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
    
    public SalaVO FindById(String id) {
        
        SalaVO[] salas = this.CarregarSalas();
        SalaVO sala = new SalaVO();
        
        try{            
            for (SalaVO s : salas) {
                if (s.getId() == null ? id == null : s.getId().equals(id)) {
                    sala = s;
                    break;
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        
        return sala;
    }
    
    public int CalcularDiferenca(SalaVO sala1, SalaVO sala2){
        int calcX = sala2.getX() - sala1.getX();
        int calcY = sala2.getY() - sala1.getY();
        
        calcX = calcX < 0 ? (calcX * -1) : calcX;
        calcY = calcY < 0 ? (calcY * -1) : calcY;
        
        return calcX + calcY;
    }
    
    public String CalcularRota(SalaVO salaInicial, SalaVO salaFinal) {
        
        String[] caminho = new String[100];
        
        caminho[0] = salaInicial.getId();
        
        try{        
            int loop = 1;

            while(CalcularDiferenca(salaInicial, salaFinal) != 0){
                caminho[loop] = salaInicial.getId();

                String[] vizinhos = salaInicial.getVizinhos();
                String[] provaveisCaminhos = new String[5];
                
                int loop2 = 0;

                for(String s : vizinhos){
                    boolean passou = false;

                    for(String s1 : caminho){
                        if(s1 != null){
                            if(s1.equals(s)){
                                passou = true;
                            }
                        }
                        else{
                            break;
                        }
                    }

                    if(!passou){
                        provaveisCaminhos[loop2] = s;
                        
                        loop2 += 1;
                    }
                }
                
                if(provaveisCaminhos.length == 0) {
                        Exception ex = new Exception("Nenhum caminho possível foi encontrado!");
                        throw ex;
                    }
                    else{
                        String caminhoMenor = "";
                        int caminhoMenorValor = 100;
                        
                        for(String s2 : provaveisCaminhos){
                            
                            if(s2 != null){
                                SalaVO salaTeste = this.FindById(s2);
                                int diferencaTeste = this.CalcularDiferenca(salaTeste, salaFinal);

                                if(diferencaTeste < caminhoMenorValor){
                                    caminhoMenorValor = diferencaTeste;
                                    caminhoMenor = salaTeste.getId();
                                }
                            }
                            else{
                                break;
                            }
                        }
                        
                        caminho[loop] = caminhoMenor;
                        
                        salaInicial = this.FindById(caminhoMenor);
                        
                        loop += 1;
                    }
            }   
        }     
        catch (Exception ex){
            
        }
        
        String rota = "";
        
        if(caminho.length > 0){        
            for(String s : caminho){
                if(s != null){
                    rota+= s + ", ";
                }
                else{
                    break;
                }
            }

            rota = rota.substring(0, rota.length() - 2);
        }
        else{
            rota = "Não foi possível encontrar um caminho!";
        }
        
        return rota;
    }
}
