package com.assembleia.votacao.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "associado_votos")
@Data
public class AssociadoVoto {
    @Id
    private Long id;

    String voto;

    Associado associado;

    Sessao sessao;

    LocalDateTime dateTimeVoto = LocalDateTime.now();
}