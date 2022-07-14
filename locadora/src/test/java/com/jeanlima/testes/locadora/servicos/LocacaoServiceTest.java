package com.jeanlima.testes.locadora.servicos;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Date;


import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.jeanlima.testes.locadora.entidades.Filme;
import com.jeanlima.testes.locadora.entidades.Locacao;
import com.jeanlima.testes.locadora.entidades.Usuario;
import com.jeanlima.testes.locadora.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.locadora.exceptions.LocadoraException;
import com.jeanlima.testes.locadora.utils.DataUtils;


public class LocacaoServiceTest {
	
	@Rule 
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/* ### PARTE 1 -  VAI GERAR FALHA: teste feito mas alguma condição que era esperada NÃO é atendida
	@Test
	public void testeLocacao() {
		
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);
		
		//acao
		Locacao locacao;
		try {
			locacao = service.alugarFilme(usuario, filme);
			error.checkThat(locacao.getValor(), is(5.0));
			error.checkThat(locacao.getValor(), is(equalTo(5.0)));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//falha o teste se lançar a exceção
			Assert.fail("Não deveria lancar excecao!");
		}
		
	} */
	
	// PARTE 2 - vai gerar ERRO! Problema acontece durante a execução do teste e o mesmo não é concluido
	@Test
	public void testeLocacao() throws Exception {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao;
		locacao = service.alugarFilme(usuario, filme);
		error.checkThat(locacao.getValor(), is(5.0));
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
				
		
		
	}
	
	//@Test
	@Test(expected = Exception.class)  //informo ao teste que existe uma exceção esperada
	public void testLocacao_filmeSemEstoque() throws Exception{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		//acao
		service.alugarFilme(usuario, filme);
	}
	
	
	@Test(expected = FilmeSemEstoqueException.class)  //informo ao teste que existe uma exceção esperada
	public void testLocacao_filmeSemEstoque4() throws Exception{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		//acao
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException{
		//cenario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);
		
		//acao
		try {
			service.alugarFilme(null, filme);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	@Test
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		
		/*
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//acao
		service.alugarFilme(usuario, null);
		*/
		
		Exception e = assertThrows(Exception.class,() -> service.alugarFilme(usuario, null));
		assertThat(e.getMessage(), containsString("Filme vazio"));
		
		System.out.println("Forma robusta");
	}
	

}
