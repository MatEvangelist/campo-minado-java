package br.com.evangelista.cm;

import br.com.evangelista.cm.modelo.Tabuleiro;
import br.com.evangelista.cm.visao.TabuleiroConsole;

public class Aplicacao {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6, 6,6);
        new TabuleiroConsole(tabuleiro);


    }
}
