package com.assembleia.votacao.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "associados_votos")
@Data
public class AssociadoVoto {

    @Transient
    public static final String SEQUENCE_NAME = "associados_votos_sequence";

    @Id
    private Long id;

    String voto;

    Long associadoId;

    Long sessaoId;

    LocalDateTime dateTimeVoto = LocalDateTime.now();
}