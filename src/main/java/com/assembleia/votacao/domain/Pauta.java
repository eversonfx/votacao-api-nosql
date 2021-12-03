package com.assembleia.votacao.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Pauta {

    @Id
    private Long id;

    private String titulo;


    private String descricao;

    private Sessao sessao;
}