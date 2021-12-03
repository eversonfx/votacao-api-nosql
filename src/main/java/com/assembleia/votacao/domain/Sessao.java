package com.assembleia.votacao.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {
    @Id
    private Long id;
    private LocalDateTime datetimeCriacao;
    private LocalTime tempoDuracao = LocalTime.of(0, 1);
    private Pauta pauta;
    @DBRef
    private List<Associado> associadoId;
}
