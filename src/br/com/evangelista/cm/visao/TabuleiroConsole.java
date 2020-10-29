package br.com.evangelista.cm.visao;

import br.com.evangelista.cm.excecao.ExplosaoException;
import br.com.evangelista.cm.excecao.SairException;
import br.com.evangelista.cm.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try {

            boolean continuar = true;
            while (continuar) {

                cicloDoJogo();
                System.out.println("Outra partida? (S/N)");
                String resposta = entrada.nextLine();
                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        } catch (SairException e) {
            System.out.println("Tchau!!");
        } finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try {
            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro.toString());
                String digitado = capturarValorDigitado("Digite (x, y): ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();
                // Iterator armazena os valores de x e y,
                // split divide os numeros digitados a cada virgula
                // trim tira os espaços dos numeros para nao bugar

                digitado = capturarValorDigitado("1 - Abrir ou 2 - Alternar Marcação: ");
                if ("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if ("2".equals(digitado)) {
                    tabuleiro.alternarMarcacao(xy.next(), xy.next());
                }

            }
            System.out.println(tabuleiro.toString());
            System.out.println("Você ganhou!!! :D");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro.toString());
            System.out.println("Você perdeu!!");

        }
    }

    public String capturarValorDigitado(String texto) {
        System.out.println(texto);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }
}
