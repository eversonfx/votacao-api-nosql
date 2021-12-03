package com.assembleia.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaSessaoNewDTO {
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String titulo;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 10, max = 500, message = "O tamanho deve ser entre 10 e 500 caracteres")
    private String descricao;

    @Length(min = 8, max = 8, message = "O tempo de duração da sessão deve ter 8 caracteres: 00:00:00")
    private String tempoDuracao;
}
