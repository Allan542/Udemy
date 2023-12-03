package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

//Mockito não consegue mockar o construtor de um objeto, alterar o ocomportamento de métodos estáticos ou privados. O PowerMock vem para suprir esta necessidade
public class CalculadoraMockTest {

    @Mock
    private Calculadora calcMock;

    @Spy
    private Calculadora calcSpy;

    @Mock
    private EmailService email;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void devoMostrarDiferencaEntreMockSpy(){
//        Mockito.when(calcMock.somar(1, 2)).thenCallRealMethod(); // Como chamou a execução real, tanto o mock como o spy vão devolver o valor correto. Porém, o cenário de quando ele não sabe o que fazer ainda ocorre
        Mockito.when(calcSpy.somar(1, 2)).thenReturn(5); // Como chamou a execução real, tanto o mock como o spy vão devolver o valor correto. Porém, o cenário de quando ele não sabe o que fazer ainda ocorre
        // O método  somar não foi sexecutado, pois o método somar ficou separado do calcSpy
        // Assim o Java não tentou executar, mas o mockito entendeu e gravou a expectativa para quando a chamada fosse executada
        // Com isso, acabou com o problema da ordem de execução do Java e o Mockito entendeu somente por não executar diretamente o método
        Mockito.doReturn(5).when(calcSpy).somar(1, 2);
        Mockito.doNothing().when(calcSpy).imprime(); // Não executa o método real

        // Os dois não sabiam o que fazer quando o valor esperado não foi passado, porém, o mock mandou o valor padrão e o spy chamou o método real e devolveu 6
        // Spy não funciona com interfaces, justamente porque o padrão é chamar a implementação (No caso, se a interface foi implementada, então chamará a classe implementadora)
        //E no caso, a interface não possui implementações
        System.out.println("Mock: " + calcMock.somar(1, 2));
        System.out.println("Spy: " + calcSpy.somar(1, 2));

        System.out.println("Mock");
        calcMock.imprime(); // Como é um void, o mock tem o padrão de não executar o método real, então ele não faz nada

        System.out.println("Spy");
        calcSpy.imprime(); // Já neste caso, como é o contrário de mock, ele imprime
    }

    @Test
    public void teste(){
        Calculadora calc = Mockito.mock(Calculadora.class);

        // ArgumentCaptor também serve como matcher
        ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
        // Ensinei ao mock que quando ele receber 1 e 2, retornará 5. Caso tenha um cenário diferente deste, como exemplo passando 8 em vez de 2, ele retornará 0
        // porque quando o Mockito não sabe o que fazer, ele sempre retorna o valor padrão do tipo do objeto, que neste caso é 0 por ser um inteiro
        // Mockito não aceita colocar um valor fixo e um matcher. Se for usado um matcher, todos os valores têm que ser matcher e vice-versa
        // Mockito.eq(equals) é um matcher que fixa um valor, então entra na regra acima de todos os valores serem matchers, com a diferença de que o primeiro está fixado
        // Porém, volta na questão de que o mockito não saberá o que fazer quando o valor for diferente do valor dentro de um eq
//        Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5); // Mockito.anyQualquerCoisa é um matcher :O

        Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);

        Assert.assertEquals(5, calc.somar(1, 8));
        System.out.println(argCapt.getAllValues()); // Só imprime neste momento, pois antes disso, ele não tem nada para capturar
    }
}
