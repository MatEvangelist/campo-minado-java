package br.com.evangelista.cm.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabuleiroTeste {

    private Tabuleiro tabuleiro;

    @BeforeEach
    void iniciarCampo() {
        tabuleiro = new Tabuleiro(4, 4, 4);
    }
}
