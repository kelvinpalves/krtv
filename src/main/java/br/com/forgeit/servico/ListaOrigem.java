/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.servico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kalves
 */
public class ListaOrigem {
    
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ListaOrigem.class);

    private final String urlLista;
    private HttpURLConnection urlConn;

    public ListaOrigem(String url) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYhhmmss");
        String urlListaCache = url + '?' + sdf.format(new Date());
        this.urlLista = urlListaCache;
    }

    public void conexaoOK() throws IOException {
        try {
            System.out.println(this.urlLista);
            URL url = new URL(this.urlLista);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            urlConn.connect();
            
            System.out.println(urlConn.getResponseCode());
            
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println(urlConn.getResponseCode());
                throw new IOException();
            }
        } catch (IOException e) {
            throw e;
        }
    }
    
    private String lerRetornoUrl() throws IOException {
        StringBuilder sb;
        
        try {
            BufferedReader r  = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8")));

            sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line.startsWith("#") ? line : "CHAVEAUX" + line);
            }
        } catch (IOException ex) {
            throw ex;
        }
        
        return sb.toString();
    }
    
    public List<ListaOrigemDTO> ler() throws Exception {
        String retorno = lerRetornoUrl().trim();
        
        String[] linhas = retorno.split("#");
        
        List<ListaOrigemDTO> origemDtoLista = new ArrayList<>();
        
        int max = 4;
        int aux = 0;
        
        for (String local : linhas) {
            
            if (local.trim().length() == 0) {
                continue;
            }
            
            // PARA TESTES USAR ISSO
//            aux++;
//            if (aux >= max) {
//                continue;
//            }
            // PARA TESTES USAR ISSO
            
            String auxiliar[] = local.split("CHAVEAUX");
            
            if (auxiliar.length == 2) {
                ListaOrigemDTO dto = ListaOrigemDTO.builder()
                        .id(1)
                        .descricao(auxiliar[0])
                        .url(auxiliar[1])
                        .build();

                origemDtoLista.add(dto);
            } else {
                logger.error("linha invalida " + local);
            }
        }
        
        return origemDtoLista;
    }
    

}
