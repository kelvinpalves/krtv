/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.servico;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author kalves
 */
public class Download {
    
    private final String pathArquivos;
    
    public Download(String pathArquivos) {
        this.pathArquivos = pathArquivos;
    }
    
    public String salvar(String arquivo) throws Exception {
        URL url = new URL(arquivo);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        
        InputStream in = urlConn.getInputStream();
        
        Files.copy(in, Paths.get(pathArquivos + FilenameUtils.getName(url.getPath())), StandardCopyOption.REPLACE_EXISTING);
        
        return pathArquivos + FilenameUtils.getName(url.getPath());
    }
    
}
