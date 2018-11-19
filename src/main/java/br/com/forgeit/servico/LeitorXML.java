/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.servico;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author kalves
 */
public class LeitorXML {

    public List<String> baixarArquivos(String xml) throws Exception {
        List<String> retorno = new ArrayList<>();
        
        File fXmlFile = new File(xml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        NodeList channels = doc.getElementsByTagName("item");

        for (int temp = 0; temp < channels.getLength(); temp++) {
            Node nNode = channels.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                retorno.add(eElement.getElementsByTagName("photo").item(0).getTextContent());
            }
        }
        
        return retorno;
    }

}
