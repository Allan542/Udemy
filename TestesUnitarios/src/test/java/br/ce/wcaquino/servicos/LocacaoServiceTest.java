package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.*;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

//@RunWith(ParallelRunner.class)
public class LocacaoServiceTest {

	// As anotações de mock abaixo retira a necessidade de um before para configurar cada classe mockada
	@InjectMocks @Spy // Classe de teste que terá os mocks abaixo injetados
	private LocacaoService service;

	// Criando os mocks para serem injetados na classe de teste
	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService email;

	// Como essa variável virou estática, ela deixou de estar no escopo de teste para se tornar parte do escopo da classe, então o Junit não usa mais esse valor
	private static Integer contador = 0;
	
	@Rule // Error Collector pega toda a stack de falha nos testes em vez de pegar apenas a primeira. Muito útil para quando se tem muitas assertivas em um teste
	// para poder tratá-las separadamente.
	public ErrorCollector error = new ErrorCollector();
	
	@Rule // ExpectedException é uma nova forma de se tratar/testar uma exception no Junit
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		System.out.println("Iniciando 2...");
		CalculadoraTest.ordem.append("2");
//		System.out.println("Before");
//		service = new LocacaoService();
//
//		dao = Mockito.mock(LocacaoDAO.class);
//		service.setLocacaoDao(dao);
//
//		spc = Mockito.mock(SPCService.class);
//		service.setSpcService(spc);
//
//		email = Mockito.mock(EmailService.class);
//		service.setEmailService(email);
//
//		contador++;
//		System.out.println(contador);
	}
	
	@After
	public void tearDown(){
		System.out.println("Finalizando 2...");
	}

//	@BeforeClass
//	public static void setupClass(){
//		System.out.println("Before Class");
//	}
//
//	@AfterClass
//	public static void afterClass(){
//		System.out.println("After Class");
//	}
	
	@AfterClass
	public static void tearDownClass(){
		System.out.println(CalculadoraTest.ordem.toString());
	}
	
	@Test // Se o teste não está esperando exceção alguma, deixe que o junit gerencie, pois isso reduz o código e aumenta a rastreabilidade do problema
	public void deveAlugarFilme() throws Exception {
		// O teste vai executar quando não for sábado, do contrário, ele será apenas ignorado.
//		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(15.00).agora());

		Mockito.doReturn(DataUtils.obterData(28, 4, 2017)).when(service).obterData();

		System.out.println("Teste!");
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificacao
		// o ideal para um teste é ter apenas 3 assertivas
		error.checkThat(locacao.getValor(), is(equalTo(15.0)));
		assertThat(locacao.getValor(), not(equalTo(16.0)));
//		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
//		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//		error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
//		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));

		error.checkThat(isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class) // O teste passa porque a exceção foi lançada, forma elegante
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = new ArrayList<>();
		Filme filme = umFilmeSemEstoque().agora();
		filmes.add(filme);
		
		//acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException{
		//cenario
//		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = new ArrayList<>();
		Filme filme = umFilme().agora();
		filmes.add(filme);
		
		//acao
		try {
			service.alugarFilme(null, filmes);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = umUsuario().agora();
//		Filme filme = new Filme("Filme 2", 1 , 4.0);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//acao
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		// O teste vai executar quando for sábado, caso contrario, ele será apenas ignorado.
//		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		Mockito.doReturn(DataUtils.obterData(29, 4, 2017)).when(service).obterData();
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
//		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
//		assertTrue(ehSegunda);
//		assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
//		assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		//cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		// Um mock não sabe o que fazer, ele vai fazer o padrão, que é quando ele recebe o usuário 1, ele não entende que na verdade foi requerido o usuário 2
		when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		
		//acao
		try { // para este caso, tem que usar a forma robusta de se tratar uma exception e não a forma nova
			service.alugarFilme(usuario, filmes);
		//verificacao
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario Negativado"));
		}
		
		verify(spc).possuiNegativacao(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas(){
		//cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
		Usuario usuario3 = umUsuario().comNome("Outro atrasado").agora();
		List<Locacao> locacoes = Arrays.asList(
				umLocacao().atrasada().comUsuario(usuario).agora(),
				umLocacao().comUsuario(usuario2).agora(),
				umLocacao().atrasada().comUsuario(usuario3).agora(),
				umLocacao().atrasada().comUsuario(usuario3).agora());
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		//acao
		service.notificarAtrasos();
		
		//verificacao
		// Este caso deixa o teste menos seguros e mais suscetivel a erros, porém, ele ajuda na manutenção e gera menos trabalho.
		// Matcher do Mockito serve como coringa, neste caso, para dizer que é para verificar qualquer usuário passado, se foi passado 3 vezes. (Lembrete para lembrar qualquer any do mockito é um matcher)
		verify(email, times(3)).notificarAtraso(Mockito.any((Usuario.class)));
		verify(email).notificarAtraso(usuario); // Verificar um por um é mais trabalhoso, porém, tem mais segurança de que o teste passará
		verify(email, atLeastOnce()).notificarAtraso(usuario3); // Então cabe a decisão de se realmente é necessário verificar um por um ou verificar tudo de uma vez num cenário controlado
		verify(email, never()).notificarAtraso(usuario2);
		verifyNoMoreInteractions(email);
		verifyZeroInteractions(spc);
	}
	
	@Test
	public void deveTratarErroNoSPC() throws Exception{
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha catratrófica"));
		
		//verificacao
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente");
//		exception.expectMessage("Falha catastrófica");
		
		//acao
		service.alugarFilme(usuario, filmes);
		
	}
	
	@Test
	public void deveProrrogarUmaLocacao(){
		//cenario
		Locacao locacao = umLocacao().agora();
		
		//acao
		service.prorrogarLocacao(locacao, 3);
		
		//verificacao
		// ArgumentCaptor captura um argumento de um determinado tipo de classe que foi informado na chamada de um método na parte de ação
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();

		// Como tem 3 assertivas e cada assertiva equivale a um teste e só retorna sempre a primeira, é necessário usar o ErrorCollector
		error.checkThat(locacaoRetornada.getValor(), is(30.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception{
		//cenario
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		//acao
		// Usando métodos padrões do java para fazer invocação de classes privadas a serem testadas
		Class<LocacaoService> clazz = LocacaoService.class;
		Method metodo = clazz.getDeclaredMethod("calcularValorLocacao", List.class);
		metodo.setAccessible(true);
		Double valor = (Double) metodo.invoke(service, filmes);
		
		//verificacao
		assertThat(valor, is(10.0));
	}

//	@Test
//	public void devePagar75PctDoFilme3() throws FilmeSemEstoqueException, LocadoraException {
//		//cenario
//		service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario");
//		List<Filme> filmes = new ArrayList<>();
//		filmes.add(new Filme("Harry Potter", 2, 10.00));
//		filmes.add(new Filme("Velozes e Furiosos", 1, 5.00));
//		filmes.add(new Filme("O Pequeno Stuart Little", 3, 15.00));
//
//		//acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//
//		//verificacao
//		assertEquals(26.25, locacao.getValor(), 0.01);
//	}
//
//	@Test
//	public void devePagar50PctDoFilme4() throws FilmeSemEstoqueException, LocadoraException {
//		//cenario
//		service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario");
//		List<Filme> filmes = new ArrayList<>();
//		filmes.add(new Filme("Harry Potter", 2, 10.00));
//		filmes.add(new Filme("Velozes e Furiosos", 1, 5.00));
//		filmes.add(new Filme("O Pequeno Stuart Little", 3, 15.00));
//		filmes.add(new Filme("A Bela e a Fera", 4, 7.50));
//
//		//acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//
//		//verificacao
//		assertEquals(30.00, locacao.getValor(), 0.01);
//	}
//
//	@Test
//	public void devePagar25PctDoFilme5() throws FilmeSemEstoqueException, LocadoraException {
//		//cenario
//		service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario");
//		List<Filme> filmes = new ArrayList<>();
//		filmes.add(new Filme("Harry Potter", 2, 10.00));
//		filmes.add(new Filme("Velozes e Furiosos", 1, 5.00));
//		filmes.add(new Filme("O Pequeno Stuart Little", 3, 15.00));
//		filmes.add(new Filme("A Bela e a Fera", 4, 7.50));
//		filmes.add(new Filme("Os Vingadores: Guerra Infinita", 4, 30.00));
//
//		//acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//
//		//verificacao
//		assertEquals(43.75, locacao.getValor(), 0.01);
//	}
//
//	@Test
//	public void devePagarNadaDoFilme6() throws FilmeSemEstoqueException, LocadoraException {
//		//cenario
//		service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario");
//		List<Filme> filmes = new ArrayList<>();
//		filmes.add(new Filme("Harry Potter", 2, 10.00));
//		filmes.add(new Filme("Velozes e Furiosos", 1, 5.00));
//		filmes.add(new Filme("O Pequeno Stuart Little", 3, 15.00));
//		filmes.add(new Filme("A Bela e a Fera", 4, 7.50));
//		filmes.add(new Filme("Os Vingadores: Guerra Infinita", 4, 30.00));
//		filmes.add(new Filme("Os Vingadores: Ultimato", 1, 40.00));
//
//		//acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//
//		//verificacao
//		assertEquals(34.25, locacao.getValor(), 0.01);
//	}
//
//	// Com a criação de uma Exception própriaa para filme sem estoque, não é mais necessário nenhum dos testes abaixo
//	@Test() // forma robusta tem a vantagem de que além de capturar a exceção, também é possível verificar a mensagem que vem da exceção
//	public void testeLocacao_filmeSemEstoque2() {
//		//cenario
//		Usuario usuario = new Usuario(" Usuário");
//		Filme filme = new Filme("Velozes e Furiosos", 2, 15.00);
//
//		//acao
//		try {
//			service.alugarFilme(usuario, filme);
//			Assert.fail("Deveria ter lançado uma exceção");
//		} catch (Exception e){
//			assertThat(e.getMessage(), is("Filme sem estoque"));
//		}
//	}
//
//	@Test() // O teste passa porque a exceção foi lançada, forma elegante
//	public void testeLocacao_filmeSemEstoque3() throws Exception {
//		//cenario
//		Usuario usuario = new Usuario(" Usuário");
//		Filme filme = new Filme("Velozes e Furiosos", 0, 15.00);
//
//		// A espera da exceção deve vir sempre antes da ação, portanto, ela deve ser considerada como um Cenário e não uma Verificação
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoque");
//
//		//acao
//		Locacao locacao = service.alugarFilme(usuario, filme);
//	}
}


// Curiosidade sobre testes: se executar um teste único, o teste passa. Porém, se executar uma bateria de testes, ele não passa,
// porque algum teste alterou o escopo que outro teste precisava e ele passou a não funcionar mais
