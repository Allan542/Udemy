package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    // Testes unitários devem seguir o padrão FIRST
    // F - Fast: O teste deve ser executado muito rápido. (Rápido)
    // I - Independent: Um teste não deve depender do outro, podendo rodar em qualquer ordem. (Independente)
    // R - Repeatable: Um teste pode ser executado quantas vezes quiser, na hora que eu quiser. (Repetitivo)
    // S - Self-Verifying: Um teste deve saber quando sua execução foi correta ou quando falhou. (Auto-verificável/validável)
    // T - Timely: Um teste deve ser criado no momento correto. (Oportuno)

    @Test
    public void test(){
        Assert.assertTrue(true);
//        Assert.assertTrue(false);

        Assert.assertEquals("Erro de comparação", 1, 1);
        Assert.assertEquals(0.51234, 0.512, 0.001);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        Assert.assertEquals("bola", "bola");
        Assert.assertNotEquals("bola", "casa");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));

        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;

        // Só passa se equals and hash code for implementado no objeto
        //Assert.assertEquals(u1,u2);

        // São os mesmos objetos
        Assert.assertSame(u2, u2);
        Assert.assertNotSame(u1, u2);

        Assert.assertNull(u3);
        Assert.assertNotNull(u2);
    }
}
