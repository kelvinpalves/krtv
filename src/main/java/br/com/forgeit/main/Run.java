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
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author kalves
 */
public class Run {

    final static Logger logger = Logger.getLogger(Run.class);

    public static void main(String[] args) {
        try {
            int maxRepeticaoSemConfiguracao = 0;
            boolean lerConfiguracao = true;
            
            Configuracao configuracao = null;
            
            while(true) {
                if (lerConfiguracao) {
                    configuracao = new Configuracao();
                    lerConfiguracao = false;
                }
                
                maxRepeticaoSemConfiguracao++;
                
                if (maxRepeticaoSemConfiguracao >= 10) {
                    lerConfiguracao = true;
                    maxRepeticaoSemConfiguracao = 0;
                }
                
                if (configuracao != null) {
                    servico(configuracao);
                }
                
                Thread.sleep(configuracao.getTaxaAtualizacao() * 60000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void servico(Configuracao configuracao) throws Exception {
        File pathArquivos = new File(configuracao.getPathArquivos());
        
        logger.info(configuracao.getPathArquivos());

        if (!pathArquivos.exists()) {
            logger.warn(configuracao.getPathArquivos() + " nao encontrado");
            return;
        }

        ListaOrigem listaOrigem = new ListaOrigem(configuracao.getUrlLista());
        listaOrigem.conexaoOK();

        logger.info("Conectado a listagem de arquivos");

        List<ListaOrigemDTO> lista = listaOrigem.ler();

        Download download = new Download(configuracao.getPathArquivos());

        List<String> listaArquivosXML = new ArrayList<>();
        List<String> listaParaValidarArquivos = new ArrayList<>();

        for (ListaOrigemDTO dto : lista) {
            try {
                String arquivo = download.salvar(dto.getUrl());
                logger.info("Download: " + dto.getUrl());
                listaArquivosXML.add(arquivo);
                listaParaValidarArquivos.add(arquivo);
            } catch (Exception ex) {
                logger.error("Erro Download", ex);
            }
        }

        LeitorXML leitorXML = new LeitorXML();
        List<String> imagens = new ArrayList<>();
        
        for (String arquivo : listaArquivosXML) {
            try {
                logger.info("Inicio Download XML " + arquivo);
                imagens.addAll(leitorXML.baixarArquivos(arquivo, configuracao.getPathArquivos()));
                logger.info("Fim Download XML " + arquivo);
            } catch (Exception ex) {
                logger.error("Erro Download XML", ex);
            }
        }
        
        
        for (String imagem : imagens) {
            logger.info("inicio download imagem" + imagem);
            listaParaValidarArquivos.add(download.salvar(imagem));
            logger.info("fim download imagem" + imagem);
        }
        
        File[] files = pathArquivos.listFiles();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        
        List<String> listaArquivosPath = new ArrayList<>();
        
        for (File file : files) {
            listaArquivosPath.add(file.getAbsolutePath());
        }
        
        for (String servidor : listaArquivosPath) {
            System.out.println("ARQUIVO NA PASTA: " + servidor);
        }
        
        for (String xml : listaParaValidarArquivos) {
            System.out.println("ARQUIVO NO XML:" + xml);
        }
        
        for (String servidor : listaArquivosPath) {
            boolean existe = false;
            
            for (String arquivo : listaParaValidarArquivos) {
                
                if (servidor.equals(arquivo)) {
                    existe = true;
                }
                
            }
            
            if (!existe) {
                logger.info("Vou apagar o arquivo: " + servidor);
                new File(servidor).delete();
            }
        }
        
//        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
//        for (String arquivo : listaParaValidarArquivos) {
//            System.out.println(arquivo);
//        }
//        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");





    }
}
