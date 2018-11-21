/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.main;

import br.com.forgeit.config.Configuracao;
import br.com.forgeit.servico.Download;
import br.com.forgeit.servico.LeitorXML;
import br.com.forgeit.servico.ListaOrigem;
import br.com.forgeit.servico.ListaOrigemDTO;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kalves
 */
public class Run {
    public static void main (String [] args) {
        
        try {
            
            Configuracao configuracao = new Configuracao();
            
            File pathArquivos = new File(configuracao.getPathArquivos());
            Files.createDirectories(pathArquivos.toPath());
            
            ListaOrigem listaOrigem = new ListaOrigem(configuracao.getUrlLista());
            listaOrigem.conexaoOK();
            
            System.out.println("Conexão OK");
            
            List<ListaOrigemDTO> lista = listaOrigem.ler();
            
            Download download = new Download(configuracao.getPathArquivos());
            
            List<String> listaArquivosXML = new ArrayList<>();
            
            for (ListaOrigemDTO dto : lista) {
                try {
                    String arquivo = download.salvar(dto.getUrl());
                    System.out.println("SUCESSO ao efetuar o download do arquivo, " + dto.getUrl());
                    listaArquivosXML.add(arquivo);
                } catch(Exception ex) {
                    ex.printStackTrace();
                    System.out.println("ERRO ao efetuar o download do arquivo, " + dto.getUrl());
                }
            }
            
            LeitorXML leitorXML = new LeitorXML();
            List<String> imagens = new ArrayList<>();
            
            for (String arquivo : listaArquivosXML) {
                try {
                    imagens.addAll(leitorXML.baixarArquivos(arquivo));
                } catch (Exception ex) {
                    System.out.println("Erro ao ler imagens no xml");
                }
            }
            
            for (String imagem : imagens) {
                download.salvar(imagem);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
