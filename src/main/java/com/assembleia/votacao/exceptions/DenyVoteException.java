package com.assembleia.votacao.exceptions;

public class DenyVoteException extends Exception {
    public DenyVoteException(String causa) {
        super("Voto negado: " + causa);
    }
}
