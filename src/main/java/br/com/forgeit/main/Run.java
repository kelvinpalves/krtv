/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.main;

import br.com.forgeit.servico.Download;
import br.com.forgeit.servico.ListaOrigem;
import br.com.forgeit.servico.ListaOrigemDTO;
import java.util.List;

/**
 *
 * @author kalves
 */
public class Run {
    public static void main (String [] args) {
        
        try {
            ListaOrigem listaOrigem = new ListaOrigem("http://www.krtv.info/lista/475980.txt");
            listaOrigem.conexaoOK();
            
            System.out.println("Conexão OK");
            
            List<ListaOrigemDTO> lista = listaOrigem.ler();
            
            Download download = new Download();
            
            for (ListaOrigemDTO dto : lista) {
                try {
                    download.salvar(dto.getUrl());
                } catch(Exception ex) {
                    System.out.println("ERRO ao efetuar o download do arquivo, " + dto.getUrl());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
