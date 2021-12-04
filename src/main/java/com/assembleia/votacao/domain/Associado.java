package com.assembleia.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Associado {

    @Transient
    public static final String SEQUENCE_NAME = "associados_sequence";

    @Id
    private long id;

    private String nome;

    private String sobrenome;

    @Indexed(unique = true)
    private String cpf;
}
