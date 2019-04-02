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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            Configuracao configuracao = null;
            
            try {
                configuracao = new Configuracao();
            } catch (Exception ex) {
                configuracao = null;
            }

            if (configuracao != null) 
                novoServico(configuracao);
        } catch (Exception ex) {
            logger.error("ERRO", ex);
        }
    }

    /*
        1 - Fazer o download dos arquivos XML
        2 - Ao baixar o XML adicionar na lista de arquivos baixados. (Para tratar posteriormente na hora da limpeza)
        3 - Fazer o download dos arquivos JPEG dentro do arquivo XML
        4 - Alterar o arquivo XML com o path dos novos arquivos
        5 - Ao baixar o JPEG adicionar a lista de arquivos baixados. (Para tratar posteriormente na hora da limpeza)
        6 - Verificar os arquivos que eu adicionei e não fazem mais parte da lista e removê-los. (Limpeza)
        7 - Repetir passos anteriores depois de determinado tempo
     */
    public static void novoServico(Configuracao configuracao) throws Exception {
        List<ListaOrigemDTO> listaOrigemDTO = null;
        Download download = null;
        LeitorXML leitorXML = new LeitorXML();

        List<String> listaXML = new ArrayList<>();
        List<String> listaArquivosBaixados = new ArrayList<String>();
        List<String> listaArquivosAntigos = new ArrayList<String>();

        try {
            ListaOrigem listaArquivosXML = null;
            listaArquivosXML = new ListaOrigem(configuracao.getUrlLista());
            listaArquivosXML.conexaoOK();
            listaOrigemDTO = listaArquivosXML.ler();
        } catch (Exception ex) {
            logger.error("Não foi possível ler a lista de arquivos XML para baixar os arquivos.");
            return;
        }

        download = new Download(configuracao.getPathArquivos());

        for (ListaOrigemDTO dto : listaOrigemDTO) {
            try {
                String nome = download.salvar(dto.getUrl());
                listaArquivosBaixados.add(nome);
                listaXML.add(nome);
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("ERRO AO BAIXAR XML", ex);
                logger.error("Erro ao baixar o arquivo XML: " + dto.getUrl());
            }
        }

        for (String xml : listaXML) {
            List<String> listaImagens;

            try {
                listaImagens = new ArrayList<>();
                listaImagens = leitorXML.baixarArquivos(xml, configuracao.getPathArquivos());
            } catch (Exception ex) {
                logger.error("Erro ao ler o arquivo XML para criar a lista de imagens que serão baixadas: " + xml);
                continue;
            }

            for (String imagem : listaImagens) {
                try {
                    String nome = download.salvar(imagem);
                    listaArquivosBaixados.add(nome);
                } catch (Exception ex) {
                    logger.error("Erro ao efetuar o downloadda imagem: " + imagem);
                }
            }
        }

        Path path = Paths.get("/tmp/krtv-meus-arquivos.txt");
        
        if (Files.exists(path)) {
            try {
                listaArquivosAntigos = Files.readAllLines(path);
            } catch (IOException ex) {
                logger.error("Erro ao ler a lista de arquivos antigos. Contate o ADM.");
            }
        } else {
            logger.info("Primeira execução");
        }

        boolean removerArquivosAntigos = true;
        
        List<String> listaParaRemocao = new ArrayList<>();
        List<String> listaParaArquivoTxt = new ArrayList<>();
        
        if (listaArquivosAntigos.isEmpty()) {
            logger.info("Não possui arquivos antigos para remover.");
            removerArquivosAntigos = false;
        } else {
            for (String antigo : listaArquivosAntigos) {
                boolean remover = true;
                
                for (String novo : listaArquivosBaixados) {
                    if (antigo.equals(novo)) {
                        remover = false;
                    }
                }
                
                if (remover) {
                    listaParaRemocao.add(antigo);
                }
            }
        }
        
        listaParaArquivoTxt.addAll(listaArquivosBaixados);
        
        for (String deletar : listaParaRemocao) {    
            logger.info("Vou remover o arquivo: " + deletar);
            File arquivo = new File(deletar);
            arquivo.delete();
            if (arquivo.exists()) listaParaArquivoTxt.add(deletar);
        }
        
        String txt = "";
        
        for (String adicionar : listaParaArquivoTxt) {
            txt += adicionar + "\n";
        }
        
        Files.write(path, txt.getBytes());
    }
}
