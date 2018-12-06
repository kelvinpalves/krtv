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

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Configuracao.class);
    private static String OS = System.getProperty("os.name").toLowerCase();

    public Configuracao() {
        try {
            Properties props = new Properties();
            
            FileInputStream file = null;
            
            logger.info(OS);
            
            if (isWindows()) {
                file = new FileInputStream("C:\\krtv\\configuracao.properties");
            } else {
                file = new FileInputStream("/home/kelvin/configuracao.properties");
            }
            
            props.load(file);

            idCliente = props.getProperty("config.id_cliente").trim();
            pathArquivos = props.getProperty("config.path_arquivos").trim();
            urlLista = props.getProperty("config.url_lista").trim();
            removerArquivos = props.getProperty("config.remover_arquivos").trim().equals("true");
            String taxaAtualizacaoAuxiliar = props.getProperty("config.taxa_atualizacao").trim();
            taxaAtualizacao = Integer.parseInt(taxaAtualizacaoAuxiliar);
        } catch (IOException ex) {
            logger.error("Não foi possível ler as configurações iniciais.", ex);
            System.exit(0);
        } catch (NumberFormatException ex) {
            logger.error("Não foi possível ler as configurações iniciais.", ex);
            System.exit(0);
        }
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
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
