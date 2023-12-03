package br.ce.wcaquino.suites;

import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// Suite é uma opção para executar uma bateria de testes em apenas um lugar
// A grande desvantagem de usar suite é que se rodar os testes a partir da source folder, ele rodará os testes de novo, duplicando sem necessidade
// Outra desvantagem é que será necessário lembrar de colocar uma classe de teste que foi criada recentemente dentro da suite
//@RunWith(Suite.class)
@SuiteClasses({
//	CalculadoraTest.class,
    CalculoValorLocacaoTest.class,
    LocacaoServiceTest.class
})
public class SuiteExecucao {
    //Remova se puder!

    @BeforeClass
    public static void before(){
        System.out.println("Before");
    }

    @AfterClass
    public static void after(){
        System.out.println("After");
    }
}