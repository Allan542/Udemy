package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(ParallelRunner.class)
public class CalculadoraTest {

    public static StringBuffer ordem = new StringBuffer();

    private Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
        System.out.println("Iniciando...");
        ordem.append("1");
    }

    @After
    public void tearDown(){
        System.out.println("Finalizando...");
    }

    @AfterClass
    public static void tearDownClass(){
        System.out.println(ordem.toString());
    }

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;


        //acao
        int resultado = calc.somar(a, b);


        //verificacao
        assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){
        //cenario
        int a = 8;
        int b = 5;


        //acao
        int resultado = calc.subtrair(a, b);


        //verificacao
        assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        //cenario
        int a = 6;
        int b = 3;


        //acao
        int resultado = calc.divide(a, b);


        //verificacao
        assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        //cenario
        int a = 10;
        int b = 0;


        //acao
        calc.divide(a, b);
    }

    @Test
    public void deveDividir() throws NaoPodeDividirPorZeroException {
        //cenario
        String a = "6";
        String b = "3";


        //acao
        int resultado = calc.divide(a, b);


        //verificacao
        assertEquals(2, resultado);
    }
}
