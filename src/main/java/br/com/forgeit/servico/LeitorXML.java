/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.servico;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author kalves
 */
public class LeitorXML {
    
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LeitorXML.class);

    public List<String> baixarArquivos(String xml, String path) throws Exception {
        List<String> retorno = new ArrayList<>();
        
        File fXmlFile = new File(xml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();
        
        NodeList channels = doc.getElementsByTagName("item");
        
        boolean precisaSalvar = false;

        for (int temp = 0; temp < channels.getLength(); temp++) {
            Node nNode = channels.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                
                String imagem = eElement.getElementsByTagName("photo").item(0).getTextContent();

                retorno.add(imagem);
                eElement.getElementsByTagName("photo").item(0).setTextContent(path + lerNomeByURL(imagem));
                precisaSalvar = true;
            }
        }
        
        if (precisaSalvar) {
            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                
                FileOutputStream fileOutputStream = new FileOutputStream(xml);
                StreamResult streamResult = new StreamResult(fileOutputStream);
                
                tr.transform(new DOMSource(doc), streamResult);
                
                fileOutputStream.close();
            } catch (Exception ex) {
                logger.error("Erro ler ou salvar xml", ex);
            }
        }
        
        return retorno;
    }
    
    private String lerNomeByURL(String url) {
        return url.split("/")[url.split("/").length - 1];
    }

}
