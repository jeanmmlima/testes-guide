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

import com.jeanlima.testes.locadora.entidades.Filme;
import com.jeanlima.testes.locadora.entidades.Locacao;
import com.jeanlima.testes.locadora.entidades.Usuario;
import com.jeanlima.testes.locadora.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.locadora.exceptions.LocadoraException;
import com.jeanlima.testes.locadora.matchers.DiaSemanaMatcher;
import com.jeanlima.testes.locadora.matchers.MathersProprios;
import com.jeanlima.testes.locadora.utils.DataUtils;


public class LocacaoServiceTest {
	
		
		@Rule 
		public ErrorCollector error = new ErrorCollector();
		
		@Rule
		public ExpectedException exception = ExpectedException.none();
		
		
		private LocacaoService service;
		
		@Before
		public void setup() {
			this.service = new LocacaoService();
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
		


}
