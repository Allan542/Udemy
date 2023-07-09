package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.*;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    @InjectMocks
    private LocacaoService service;

    @Mock
    private LocacaoDAO dao;

    @Mock
    private SPCService spc;

    // As variáveis que são parâmetros precisam ser públicas
    @Parameter // Primeiro parâmetro da collection de array de objetos com parâmetros (padrão do valor do parâmetro é 0)
    public List<Filme> filmes;

    @Parameter(value = 1) // Segundo parâmetro da collection de array de objetos com parâmetros
    public Double valorLocacao;

    @Parameter(value = 2) // Terceiro parâmetro da collection de array de objetos com parâmetros
    public String cenario;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); // Equivalente a anotação na classe @RunWith(MockitoJUnitRunner.class)
        System.out.println("Iniciando 3");
        CalculadoraTest.ordem.append("3");
//        service = new LocacaoService();
//        LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
//        service.setLocacaoDao(dao);
//        SPCService spc = Mockito.mock(SPCService.class);
//        service.setSpcService(spc);
    }

    @After
    public void tearDown(){
        System.out.println("Finalizando 3...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println(CalculadoraTest.ordem.toString());
    }

    private static Filme filme1 = umFilme().agora();//new Filme("Harry Potter", 2, 10.00);
    private static Filme filme2 = filmeDois().agora();//new Filme("Velozes e Furiosos", 1, 5.00);
    private static Filme filme3 = filmeTres().agora();//new Filme("O Pequeno Stuart Little", 3, 15.00);
    private static Filme filme4 = filmeQuatro().agora();//new Filme("Marley e eu", 20, 6.00);
    private static Filme filme5 = filmeCinco().agora();//new Filme("As Crônicas de Nárnia", 5, 20.00);
    private static Filme filme6 = filmeSeis().agora();//new Filme("Os Vingadores: Guerra Infinita", 4, 30.00);
    private static Filme filme7 = filmeSete().agora();//new Filme("Os Vingadores: Ultimato", 1, 40.00);

    @Parameters(name = "{2}") // Definindo o nome do cenário através de um parâmetro
    // Este método que obtêm parâmetros, precisa ser estático, junto com atributos que são utilizados nele, porque é ele quem vai definir aquantidade de execuções de cada método de teste
    // Ou seja, o JUnit precisa dessa informação antes mesmo da instanciação desta classe
    // Ele devolve uma coleção de lista de objetos, ou traduzindo para um foprmato mais abstrato, uma matriz de Objetos, que nada mais é do que uma lista de listas
    // No primeiro valor, vai a lista de filmes, no segundo, o valor esperado e no terceiro, o nome do cenário
    public static Collection<Object[]> getParametros(){
        // A quantidade de testes executados é a quantidade de linhas informadas. Neste caso, 6 linhas = 6 cenários/execuções de método de teste
        return Arrays.asList(new Object[][]{
            {Arrays.asList(filme1, filme2), 15.00, "2 Filmes: Sem Desconto"},
            {Arrays.asList(filme1, filme2, filme3), 26.25, "3 Filmes: 25%"},
            {Arrays.asList(filme1, filme2, filme3, filme4), 29.25, "4 Filmes: 50%"},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 34.25, "5 Filmes: 75%"},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 34.25, "6 Filmes: 100%"},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 74.25, "7 Filmes: Sem Desconto"},
        });
    }

    @Test // Teste genérico para ser usado na definição dos valores necessários
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws InterruptedException, FilmeSemEstoqueException, LocadoraException {
        // Cenario
        Usuario usuario = umUsuario().agora();

//        Thread.sleep(5000);

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        // Verificação
        assertEquals(valorLocacao, locacao.getValor(), 0.01);
//        System.out.println("!");
    }

//    @Test // Com esse método print, o Junit dobra o número de execução de testes que neste caso, fica 12 (era 6)
//    public void print(){
//        System.out.println(valorLocacao);
//    }
}
