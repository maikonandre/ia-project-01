package controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vo.SalaVO;
import java.util.Arrays;

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
                    newSala.setX(Integer.parseInt(eElement.getAttribute("x")));
                    newSala.setY(Integer.parseInt(eElement.getAttribute("y")));
                    newSala.setPos(Integer.parseInt(eElement.getAttribute("pos")));
                    newSala.setRegion(eElement.getAttribute("region"));
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
    
    public int CalcularDiferencaManhattan(SalaVO sala1, SalaVO sala2){
        int calcX = sala2.getX() - sala1.getX();
        int calcY = sala2.getY() - sala1.getY();
        
        calcX = calcX < 0 ? (calcX * -1) : calcX;
        calcY = calcY < 0 ? (calcY * -1) : calcY;
        
        return calcX + calcY;
    }
    
    public String CalcularRotaSimulated(SalaVO salaInicial, SalaVO salaFinal) {
        
        String[] caminho = new String[100];
        caminho[0] = salaInicial.getId();
        
        try{
            int loop = 1;
            
            boolean encontrou = true;
            
            while(!salaInicial.getId().equals(salaFinal.getId())) {
                
                if(loop == 100) {
                    caminho = null;
                    break;
                }
                
                String[] vizinhos = salaInicial.getVizinhos();
                
                if(encontrou == false){
                    Arrays.sort(vizinhos);
                }
                
                int loopVizinhos = 0;
                
                for(String v : vizinhos){
                    loopVizinhos += 1;
                    
                    boolean passou = false;
                    
                    if(v == null) break;
                    
                    for(String c : caminho){
                        
                        if(c == null) break;
                        
                        if(c.equals(v) && encontrou == true){
                            passou = true;
                            break;
                        }
                    }
                    
                    if(!passou) {
                        caminho[loop] = v;
                        
                        loop += 1;
                        
                        salaInicial = this.FindById(v);
                        
                        encontrou = true;
                        
                        break;
                    }
                    else{
                        if(loopVizinhos == vizinhos.length) {
                            encontrou = false;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        
        String rota = "";
        
        if(caminho != null) {
            if(caminho.length > 0) {
                for(String c : caminho){
                    if(c == null) break;
                    rota += c + ", ";
                }

                rota = rota.substring(0, rota.length() - 2);

            }else{
                rota = "Não foi possível determinar uma rota!";
            }
        }
        else{
            rota = "Não foi possível determinar uma rota!";
        }
        
        return rota;
    }
    
    public String CalcularRotaSimulatedManhattan(SalaVO salaInicial, SalaVO salaFinal) {
        
        String[] caminho = new String[100];
        
        caminho[0] = salaInicial.getId();
        
        try{        
            int loop = 1;

            while(CalcularDiferencaManhattan(salaInicial, salaFinal) != 0){
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
                            int diferencaTeste = this.CalcularDiferencaManhattan(salaTeste, salaFinal);

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
            System.out.println("Erro: " + ex.getMessage());
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
    
    public String CalcularRotaHillClimbing(SalaVO salaInicial, SalaVO salaFinal) {
        
        String caminho = salaInicial.getId() + ", ";
        int maxMoves = 100;
        SalaVO salaAtual = salaInicial;
        String ultVizinho = "";
        int forcePrimeiroVizinho = 0;
        
        for (int i = 0; i < maxMoves; i++) {           
            
            SalaVO melhorOpcao = salaAtual;
            
            for (String vizinho : salaAtual.getVizinhos()) {
                
                SalaVO salaIterator = FindById(vizinho);
                
                if (forcePrimeiroVizinho < 1) {
                    melhorOpcao = salaIterator;
                    ultVizinho = vizinho;
                }
                else {
                    
                    int passosMelhorOp = (melhorOpcao.getPos() < salaFinal.getPos()) ? (salaFinal.getPos() - melhorOpcao.getPos()) : (melhorOpcao.getPos() - salaFinal.getPos());
                    int passosIterator = (salaIterator.getPos() < salaFinal.getPos()) ? (salaFinal.getPos() - salaIterator.getPos()) : (salaIterator.getPos() - salaFinal.getPos());

                    if (salaIterator.getRegion().equals(salaFinal.getRegion())) {

                        if (passosIterator < passosMelhorOp) {
                            melhorOpcao = salaIterator;
                            ultVizinho = vizinho;
                        }

                    }
                    else {

                        if (!melhorOpcao.getRegion().equals(salaFinal.getRegion())) {

                            if (passosIterator < passosMelhorOp) {
                                melhorOpcao = salaIterator;
                                ultVizinho = vizinho;
                            }
                        }
                    }
                }
                
                forcePrimeiroVizinho++;
                
            }
            
            salaAtual = melhorOpcao;
            forcePrimeiroVizinho = 0;
            
            caminho += ultVizinho + ", ";
            
            if (salaFinal.getId().equals(salaAtual.getId())) {
                break;
            }
        }
        
        caminho = caminho.substring(0, caminho.length()-2);
        
        return caminho;
    }
}
