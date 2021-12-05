package com.assembleia.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {

    @Transient
    public static final String SEQUENCE_NAME = "sessoes_sequence";

    @Id
    private Long id;
    private LocalDateTime datetimeCriacao;
    private LocalTime tempoDuracao = LocalTime.of(0, 1);

    private Pauta pauta;
}
