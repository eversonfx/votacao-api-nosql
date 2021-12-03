package com.assembleia.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {
    @NotEmpty(message = "CPF do Associado é obrigatório")
    @CPF
    private String associadoCPF;

    @NotEmpty(message = "Id da pauta é obrigatório")
    private String pautaId;

    @NotEmpty(message = "Voto é obrigatório")
    private String voto;
}
