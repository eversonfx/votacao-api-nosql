package com.assembleia.votacao.domain.enums;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Voto {

    SIM(1, "Sim"),
    NAO(2, "Nao");

    private int id;
    private String tipoVoto;

    public static Voto toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (Voto x : Voto.values()) {
            if (cod.equals(x.getId())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id Inválido: " + cod);
    }
}