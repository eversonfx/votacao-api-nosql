package com.assembleia.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {

    @Transient
    public static final String SEQUENCE_NAME = "pauta_sequence";

    @Id
    private Long id;

    private String titulo;

    private String descricao;

}