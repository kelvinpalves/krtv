/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forgeit.servico;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author kalves
 */

@Builder
@Data
public class ListaOrigemDTO {
    private final Integer id;
    private final String descricao;
    private final String url;
}
