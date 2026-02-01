package com.sicradi.votacao.exceptions;

public class UnableToVoteException extends RuntimeException {
    public UnableToVoteException(String message) {
        super(message);
    }
}