package com.assembleia.votacao.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Document
@Data
public class Sessao {
    @Id
    private Long id;
    private LocalDateTime datetimeCriacao;
    private LocalTime tempoDuracao = LocalTime.of(0, 1);
    private Pauta pauta;
}
