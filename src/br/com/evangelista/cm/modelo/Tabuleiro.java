package br.com.evangelista.cm.modelo;

import br.com.evangelista.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> listaCampos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            listaCampos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());
        } catch (ExplosaoException e) {
            listaCampos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void alternarMarcacao(int linha, int coluna) {
        listaCampos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }


    private void gerarCampos() {
        for (int inha = 0; inha < linhas; inha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                listaCampos.add(new Campo(inha, coluna));
            }
        }
    }

    private void associarOsVizinhos() {
        for (Campo c1 : listaCampos) {
            for (Campo c2 : listaCampos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();

        do {
            int aleatorio = (int) (Math.random() * listaCampos.size());
            listaCampos.get(aleatorio).minar();
            minasArmadas = listaCampos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado() {
        return listaCampos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        listaCampos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int i = 0; i < colunas ; i++) { // indice das colunas
            sb.append(" ");
            sb.append(i);
            sb.append(" ");
        }
        sb.append("\n");

        int i = 0;
        for (int inha = 0; inha < linhas; inha++) {
            sb.append(inha);
            sb.append(" ");
            for (int coluna = 0; coluna < colunas; coluna++) {
                sb.append(" ");
                sb.append(listaCampos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
