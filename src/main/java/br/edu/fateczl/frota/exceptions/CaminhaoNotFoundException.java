package br.edu.fateczl.frota.exceptions;

public class CaminhaoNotFoundException extends RuntimeException {

    public CaminhaoNotFoundException(Long id) {
        super("Caminhão não encontrado com ID: " + id);
    }

}