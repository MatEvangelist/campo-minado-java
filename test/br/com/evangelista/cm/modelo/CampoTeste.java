package br.com.evangelista.cm.modelo;

import br.com.evangelista.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {

    private Campo campo;

    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3, 3);
    }

    @Test
    void testeVizinhoDistancia1Esquerda() {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia1Direita() {
        Campo vizinho = new Campo(3, 4);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia1EmCima() {
        Campo vizinho = new Campo(2, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia1EmBaixo() {
        Campo vizinho = new Campo(4, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia2() {
        Campo vizinho = new Campo(2, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeNaoVizinho() {
        Campo vizinho = new Campo(1, 1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    void testeValorPadraoAtributoMarcado() {
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoMarcado() {
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoNaoMarcado() {
        campo.minar();

        assertThrows(ExplosaoException.class, () -> {
            campo.abrir();
            //se campo.abrir() chamar a exceção ele seta como true
        });
    }

    @Test
    void testeAbrirComVizinho1() {
        Campo campo22 = new Campo(2, 2);
        Campo campo11 = new Campo(1, 1);

        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    void testeAbrirComVizinho2() {
        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 2);
        campo12.minar();

        Campo campo22 = new Campo(2, 2);

        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }

    @Test
    void testeObjetivoAlcancadoSemMinaEAberto() {
        campo.abrir();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoMarcadoEMinado() {
        campo.minar();
        campo.alternarMarcacao();

        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testeContagemDeMinasVizinhancaHori() {
        Campo campo32 = new Campo(3, 2);
        campo.adicionarVizinho(campo32);

        campo32.minar();

        assertEquals(campo.minasNaVizinhanca(), 1);
    }


    @Test
    void testeContagemDeMinasVizinhancaVerti() {
        Campo campo23 = new Campo(2, 3);
        campo.adicionarVizinho(campo23);

        campo23.minar();

        assertEquals(campo.minasNaVizinhanca(), 1);
    }

    @Test
    void testeContagemDeMinasVizinhancaHoriVerti() {
        Campo campo34 = new Campo(3, 4);
        Campo campo43 = new Campo(4, 3);
        campo.adicionarVizinho(campo34);
        campo.adicionarVizinho(campo43);

        campo34.minar();
        campo43.minar();

        assertEquals(campo.minasNaVizinhanca(), 2);
    }

    @Test
    void testeContagemDeMinasVizinhancaDiagonal1() {
        Campo campo22 = new Campo(2, 2);
        campo.adicionarVizinho(campo22);

        campo22.minar();

        assertEquals(campo.minasNaVizinhanca(), 1);
    }

    @Test
    void testeContagemDeMinasVizinhancaDiagonal2() {
        Campo campo22 = new Campo(2, 2);
        Campo campo44 = new Campo(4, 4);

        campo.adicionarVizinho(campo22);
        campo.adicionarVizinho(campo44);

        campo22.minar();
        campo44.minar();

        assertEquals(campo.minasNaVizinhanca(), 2);
    }

    @Test
    void testeReiniciar1() {
        campo.abrir();
        campo.reiniciar();

        assertTrue(campo.isFechado());
    }

    @Test
    void testeReiniciar2() {
        campo.minar();
        campo.reiniciar();

        assertFalse(campo.isMinado());
    }

    @Test
    void testeReiniciar3() {
        campo.alternarMarcacao();
        campo.reiniciar();

        assertFalse(campo.isMarcado());
    }

    @Test
    void testeReiniciar4() {
        campo.minar();
        campo.alternarMarcacao();
        campo.abrir();
        campo.reiniciar();

        assertTrue(campo.isFechado() && !campo.isMarcado());
    }

    @Test
    void testeToString1() {
        campo.alternarMarcacao();
        assertEquals(campo.toString(), "x");
    }

    @Test
    void testeToString2() {
        campo.abrir();

        assertEquals(campo.toString(), " ");
    }

    @Test
    void testeToString3() {
        campo.abrir();
        Campo campo23 = new Campo(2, 3);
        campo.adicionarVizinho(campo23);
        campo23.minar();

        assertEquals(Integer.parseInt(campo.toString()), campo.minasNaVizinhanca());
    }

}
