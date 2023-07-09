package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

@RunWith(PowerMockRunner.class) // Necessário ter para executar o PowerMock
@PrepareForTest({LocacaoService.class}) // Preparar as classes de teste que vão ser usadas pelo PowerMockito
@PowerMockIgnore("jdk.internal.reflect.*") // Necessário para versões mais recentes
// PowerMock só é recomendado quando o código é muito legado e ninguém tem coragem de mexer, porque é uma ferramenta que dá muitas possibilidades
public class LocacaoServiceTest_PowerMock {

	// As anotações de mock abaixo retira a necessidade de um before para configurar cada classe mockada
	@InjectMocks // Classe de teste que terá os mocks abaixo injetados
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
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		service = PowerMockito.spy(service);
		System.out.println("Iniciando 4...");
		CalculadoraTest.ordem.append(4);
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
		System.out.println("finalizando 4...");
	}

//	@BeforeClass
//	public void setupClass(){
//		System.out.println("Before Class");
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

		// PowerMockito permite mockar o construtor da data que não possui argumentos, ou seja, criar um valor para o teste
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, 28);
//		calendar.set(Calendar.MONTH, Calendar.APRIL);
//		calendar.set(Calendar.YEAR, 2017);
//		PowerMockito.mock(Calendar.class);
//		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

		System.out.println("Teste!");

		//acao
		// o ideal para um teste é ter apenas 3 assertivas
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(15.0)));
		assertThat(locacao.getValor(), not(equalTo(16.0)));
//		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
//		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//		error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
//		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));

		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception{
		// O teste vai executar quando for sábado, caso contrario, ele será apenas ignorado.
//		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// PowerMockito usando construtor
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, 29);
//		calendar.set(Calendar.MONTH, Calendar.APRIL);
//		calendar.set(Calendar.YEAR, 2017);
//		PowerMockito.mockStatic(Calendar.class); // PowerMockito usando método estático
//		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
//		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
//		assertTrue(ehSegunda);
//		assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
//		assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();

		PowerMockito.verifyStatic(Mockito.times(2));
		Calendar.getInstance();
	}
	
	@Test
	public void deveAlugarFilme_SemCalcularValor() throws Exception{
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		// PowerMockito usando método privado
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes); //(classe, método privado a ser executado, argumento(s) a ser(em) passado(s) no método)
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificacao
		Assert.assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception{
		//cenario
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		//acao
		Double valor = Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
		
		//verificacao
		Assert.assertThat(valor, is(10.0));
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
