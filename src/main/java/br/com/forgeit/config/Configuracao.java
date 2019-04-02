/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author kalves
 */
public final class Configuracao {

    private String idCliente;
    private String pathArquivos;
    private String urlLista;
    private boolean removerArquivos;
    private Integer taxaAtualizacao;

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Configuracao.class);
    private static String OS = System.getProperty("os.name").toLowerCase();

    public Configuracao() throws Exception {
        try {
            Properties props = getConfiguracao();

            String pathInicial = props.getProperty("config.path_inicial").trim();

            definirVariaveis(pathInicial);

            urlLista = props.getProperty("config.url_lista").trim() + idCliente + ".txt";

            String taxaAtualizacaoAuxiliar = props.getProperty("config.taxa_atualizacao").trim();
            taxaAtualizacao = Integer.parseInt(taxaAtualizacaoAuxiliar);
            taxaAtualizacao = 10;

            logger.info(urlLista);
            logger.info(idCliente);
            logger.info(pathArquivos);

        } catch (Exception ex) {
            logger.error("Não foi possível ler as configurações iniciais.", ex);
            throw ex;
        }
    }

    public Properties getConfiguracao() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        FileInputStream file = null;

        if (isWindows()) {
            file = new FileInputStream("C:\\krtv\\configuracao.properties");
        } else {
            file = new FileInputStream("/home/kelvin/configuracao.properties");
        }

        props.load(file);

        return props;
    }

    public void definirVariaveis(String pathInicial) throws Exception {
        String usuario = System.getProperty("user.home");
        
        File file = new File(usuario + "/AppData/Roaming");
        
        System.out.println("Path parcial: " + file.getAbsolutePath());
        
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        String parcial = file.getAbsolutePath();

        for (String diretorio : directories) {
            if (diretorio.startsWith("SignagePlayer.")) {
                parcial += File.separator + diretorio;
            }
        }

        parcial += File.separator + "Local Store" + File.separator + "signage.me";
        
        System.out.println(parcial);

        String[] proximos = new File(parcial).list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String diretorio : proximos) {
            if (diretorio.startsWith("business")) {
                parcial += File.separator + diretorio;
                idCliente = diretorio.replace("business", "");
            }
        }

        parcial += File.separator + "Resources";

        File salvarAqui = new File(parcial);

        if (salvarAqui.exists()) {
            System.out.println("Encontrei o path " + parcial);
        }
        
        pathArquivos = parcial + File.separator;
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
