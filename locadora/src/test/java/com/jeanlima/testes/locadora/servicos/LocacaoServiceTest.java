package com.jeanlima.testes.locadora.servicos;


import static com.jeanlima.testes.locadora.builders.FilmeBuilder.umFilme;
import static com.jeanlima.testes.locadora.builders.FilmeBuilder.umFilmeSemEstoque;
import static com.jeanlima.testes.locadora.builders.UsuarioBuilder.umUsuario;
import static com.jeanlima.testes.locadora.utils.DataUtils.isMesmaData;
import static com.jeanlima.testes.locadora.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.jeanlima.testes.locadora.builders.LocacaoBuilder;
import com.jeanlima.testes.locadora.dao.LocacaoDAO;
import com.jeanlima.testes.locadora.dao.LocacaoDAOFake;
import com.jeanlima.testes.locadora.entidades.Filme;
import com.jeanlima.testes.locadora.entidades.Locacao;
import com.jeanlima.testes.locadora.entidades.Usuario;
import com.jeanlima.testes.locadora.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.locadora.exceptions.LocadoraException;
import com.jeanlima.testes.locadora.matchers.DataDiferencaDiasMatcher;
import com.jeanlima.testes.locadora.matchers.DiaSemanaMatcher;
import com.jeanlima.testes.locadora.matchers.MathersProprios;
import com.jeanlima.testes.locadora.utils.DataUtils;


public class LocacaoServiceTest {
	
		
		@Rule 
		public ErrorCollector error = new ErrorCollector();
		
		@Rule
		public ExpectedException exception = ExpectedException.none();
		
		//injeta o mock na classe de serviço
		@InjectMocks
		private LocacaoService service;
		
		@Mock
		private SPCService spc;
		
		@Mock
		private LocacaoDAO dao;
		
		@Mock
		private EmailService email;
		
		@Before
		public void setup() {
		
			//init mocks is deprecated
			MockitoAnnotations.openMocks(this);
			
			//tbm posso apagar os métodos sets da classe de serviço 
			//que eram usados para injetar dependencias 
		
		}
		
		
		@Test
		public void deveAlugarFilme() throws Exception {
			
			Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
			//cenario
			//Usuario usuario = new Usuario("Usuario 1");
			Usuario usuario = umUsuario().agora();
			//List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
			//List<Filme> filmes = Arrays.asList(umFilme().agora());
			List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());
			
			//acao
			Locacao locacao = service.alugarFilme(usuario, filmes);
				
			//verificacao
			error.checkThat(locacao.getValor(), is(equalTo(5.0)));
			error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

			/*
			 * 
			 * Desafio - Mathers próprios
			 * error.checkThat(isMesmaData(locacao.getDataLocacao(), ehHoje());
			 * error.checkThat(isMesmaData(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
			 */
		}
		
		@Test(expected = FilmeSemEstoqueException.class)
		public void naoDeveAlugarFilmeSemEstoque() throws Exception{
			//cenario
			Usuario usuario = umUsuario().agora();
			//List<Filme> filmes = Arrays.asList(umFilme().agora());
			//List<Filme> filmes = Arrays.asList(umFilme().semEstoque().agora());
			List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

			
			//acao
			service.alugarFilme(usuario, filmes);
		}
		
		@Test
		public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException{
			//cenario
			List<Filme> filmes = Arrays.asList(umFilme().agora());
			
			//acao
			try {
				service.alugarFilme(null, filmes);
				Assert.fail();
			} catch (LocadoraException e) {
				assertThat(e.getMessage(), is("Usuario vazio"));
			}
		}

		@Test
		public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
			//cenario
			Usuario usuario = umUsuario().agora();
			
			exception.expect(LocadoraException.class);
			exception.expectMessage("Filme vazio");
			
			//acao
			service.alugarFilme(usuario, null);
		}
		
		@Test
		public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException{
			//cenario
			Usuario usuario = umUsuario().agora();
			List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0));
			
			//acao
			Locacao resultado = service.alugarFilme(usuario, filmes);
			
			//verificacao
			assertThat(resultado.getValor(), is(11.0));
		}
		

		@Test
		public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException{
			//cenario
			Usuario usuario = umUsuario().agora();
			List<Filme> filmes = Arrays.asList(
					new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));
			
			//acao
			Locacao resultado = service.alugarFilme(usuario, filmes);
			
			//verificacao
			assertThat(resultado.getValor(), is(13.0));
		}
		

		@Test
		public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException{
			//cenario
			Usuario usuario = umUsuario().agora();
			List<Filme> filmes = Arrays.asList(
					new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), 
					new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
					new Filme("Filme 5", 2, 4.0));
			
			//acao
			Locacao resultado = service.alugarFilme(usuario, filmes);
			
			//verificacao
			assertThat(resultado.getValor(), is(14.0));
		}
		

		@Test
		public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException{
			//cenario
			Usuario usuario = umUsuario().agora();
			List<Filme> filmes = Arrays.asList(
					new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), 
					new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
					new Filme("Filme 5", 2, 4.0), new Filme("Filme 6", 2, 4.0));
			
			//acao
			Locacao resultado = service.alugarFilme(usuario, filmes);
			
			//verificacao
			assertThat(resultado.getValor(), is(14.0));
		}
		
		@Test
		//@Ignore
		public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException{
				
			Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
			
			//cenario
			Usuario usuario = umUsuario().agora();
			List<Filme> filmes = Arrays.asList(umFilme().agora());
			
			//acao
			Locacao retorno = service.alugarFilme(usuario, filmes);
			
			//verificacao
			boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);

			Assert.assertTrue(ehSegunda);

			//verificação com mather personalizável

			MatcherAssert.assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
			MatcherAssert.assertThat(retorno.getDataRetorno(), MathersProprios.caiNumaSegunda());
		}
		/*
		 * Primeiro o teste vai falhar mesmo com a dependencia do spcservice injetada
		 * o retorno padrao do mock é false
		 * mas a execção esperada só é lançada quando o método retorn verdadeiro
		 */
		@Test
		public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
			 
			
			//cenario
			Usuario usuario = umUsuario().agora();
			//Usuario usuario2 = umUsuario().agora();
			Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
			List<Filme> filmes = Arrays.asList(umFilme().agora());
			
			//PARTE 2 - quando o mock spc chamar o método possuiNegativacao entao retorna true
			//Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true);
			
			//parte 4 - com usuário genérico
			Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
			
			
			try {
				service.alugarFilme(usuario, filmes);
				Assert.fail();
			} catch (LocadoraException e) {
				// TODO: handle exception
				MatcherAssert.assertThat(e.getMessage(), is("Usuário Negativado"));
			}
			
			
			//verificacao - parte 4
			Mockito.verify(spc).possuiNegativacao(usuario);
			
			
		}
		
		@Test
		public void deveEnviarEmailParaLocacoesAtrasadas() {
			//cenario
			Usuario usuario = umUsuario().agora();
			Usuario usuario2 = umUsuario().comNome("Usuário 2").agora();
			List<Locacao> locacoes = Arrays.asList(LocacaoBuilder.umLocacao().comUsuario(usuario).comDataRetorno(DataUtils.obterDataComDiferencaDias(-2)).agora());
			
			Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
			//acao
			service.notificarAtrasos();
			
			//test
			Mockito.verify(email).notificarAtraso(usuario);
		}
		
		@Test
		public void deveEnviarEmailParaLocacoesAtrasadas2() {
			//cenario
			Usuario usuario = umUsuario().agora();
			Usuario usuario2 = umUsuario().comNome("Usuário Atrasado").agora();
			
			Usuario usuario3 = umUsuario().comNome("Usuário em dia").agora();
			
			List<Locacao> locacoes = Arrays.asList(
					LocacaoBuilder
						.umLocacao().atrasada().comUsuario(usuario).agora(),
					LocacaoBuilder
						.umLocacao().atrasada().comUsuario(usuario3).agora(),	
					LocacaoBuilder
						.umLocacao().atrasada().comUsuario(usuario3).agora(),	
					LocacaoBuilder
						.umLocacao().comUsuario(usuario2).agora());
			
			Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
			//acao
			service.notificarAtrasos();
			
			//test
			//passa com usuário 2 tbm, mas está errado
			Mockito.verify(email).notificarAtraso(usuario);
			
			//Mockito.verify(email, Mockito.times(2)).notificarAtraso(usuario3);
			Mockito.verify(email, Mockito.atLeastOnce()).notificarAtraso(usuario3);
			
			//garantir que usuario 2 nao receba
			Mockito.verify(email,never()).notificarAtraso(usuario2);
			
			//usuario generico
			Mockito.verify(email,Mockito.times(3)).notificarAtraso(Mockito.any(Usuario.class));
			
			//voce decide o trabalho!
			
			Mockito.verifyNoMoreInteractions(email);
			
		}
		
		@Test
		public void deveTratarErronoSPC() throws Exception {
			//cenario
			Usuario usuario = umUsuario().agora();
			List<Filme> filmes = Arrays.asList(umFilme().agora());
			
			Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha catastrófica"));
			//Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new RuntimeException("Falha catastrófica"));
			
			exception.expect(LocadoraException.class);
			//exception.expect(Exception.class);
			exception.expectMessage("Problemas com SPC, tente novamente");
			//exception.expectMessage("Falha catastrófica");
			
			//acao
			service.alugarFilme(usuario, filmes);
			
			//verificação
			
		}
		
		@Test
		public void deveProrrogarUmaLocacao(){
			//cenario
			Locacao locacao = LocacaoBuilder.umLocacao().agora();
			
			//acao
			service.prorrogarLocacao(locacao, 3);
			
			//verificacao
			//
			
			ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
			Mockito.verify(dao).salvar(argCapt.capture()); //pega o que foi passado no método dao
			Locacao locacaoRetornada = argCapt.getValue();
			
			error.checkThat(locacaoRetornada.getValor(), is(12.0));
			error.checkThat(locacaoRetornada.getDataLocacao(), MathersProprios.ehHoje());
			error.checkThat(locacaoRetornada.getDataRetorno(), MathersProprios.ehHojeComDiferencaDias(3));
		}
		
		


}
