/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author kalves
 */
public class Configuracao {

    private String idCliente;
    private String pathArquivos;
    private String urlLista;
    private boolean removerArquivos;
    private Integer taxaAtualizacao;

    public Configuracao() {
        try {
            Properties props = new Properties();
            FileInputStream file = new FileInputStream("src/main/resources/configuracao.properties");
            props.load(file);
            
            idCliente = props.getProperty("config.id_cliente");
            pathArquivos = props.getProperty("config.path_arquivos");
            urlLista = props.getProperty("config.url_lista");
            removerArquivos = props.getProperty("config.remover_arquivos").equals("true");
            String taxaAtualizacaoAuxiliar = props.getProperty("config.taxa_atualizacao");
            taxaAtualizacao = Integer.parseInt(taxaAtualizacaoAuxiliar);
        } catch (IOException ex) {
            System.out.println("Não foi possível ler as configurações iniciais.");
            System.exit(0);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            System.out.println("Taxa de atualização informada é inválida");
            System.exit(0);
        }
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getPathArquivos() {
        return pathArquivos;
    }

    public String getUrlLista() {
        return urlLista;
    }

    public boolean isRemoverArquivos() {
        return removerArquivos;
    }

    public Integer getTaxaAtualizacao() {
        return taxaAtualizacao;
    }

}
