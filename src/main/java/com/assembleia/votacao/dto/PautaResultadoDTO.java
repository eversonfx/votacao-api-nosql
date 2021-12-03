package com.assembleia.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaResultadoDTO {
    String titulo;
    LocalDateTime dateTimeCriacaoSessao;
    LocalTime tempoDuracao;
    long votosSim;
    long votosNao;
    long votosTotal;
}
