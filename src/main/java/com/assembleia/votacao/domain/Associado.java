package com.assembleia.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Associado {
    @Id
    private long id;
    private String nome;
    private String sobrenome;
    private String cpf;

    @DBRef
    private List<Sessao> sessaoId;
}
