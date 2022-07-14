package com.jeanlima.testes.introducao.servicos;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Date;


import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.jeanlima.testes.introducao.entidades.Filme;
import com.jeanlima.testes.introducao.entidades.Locacao;
import com.jeanlima.testes.introducao.entidades.Usuario;
import com.jeanlima.testes.introducao.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.introducao.exceptions.LocadoraException;
import com.jeanlima.testes.introducao.utils.DataUtils;

public class LocacaoServiceTest {
	
	//PARTE 2 - jUnit não garante que a ordem de execução é a de implementação dos testes!!!
	/* 
	 * USANDO o FIRST sem PROBLEMAS
	 * MAs se os testes dependerem um do outro aí problema
	 * 
	 * */
	
	//desafio contador
	
	//private static int contador = 0; util para passar valores entre um teste e outro caso necessário
	
	@Rule 
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//métodos usados para configurações genericas aos métodos de teste
	
	private LocacaoService service;
	
	@Before
	public void setup() {
		//System.out.println("before");//cenario
		this.service = new LocacaoService();
		//contador++;
		//System.out.println(contador);
		
	}
	
	@After
	public void tearDown() {
		//System.out.println("after");
	}
	
	//apenas uma vez antes da classe ser instanciada e feinalizada
	
	@BeforeClass
	public static void setupClass() {
		//System.out.println("before CLASS");//cenario
		//this.service = new LocacaoService();
		
	}
	
	@AfterClass
	public static void tearDownClass() {
		//System.out.println("after CLASS");
	}
	
	
	// PARTE 2 - vai gerar ERRO! Problema acontece durante a execução do teste e o mesmo não é concluido
	@Test
	public void testeLocacao() throws Exception {
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
		
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		//acao
		service.alugarFilme(usuario, filme);
	}
	/*
	@Test
	public void testLocacao_filmeSemEstoque_2() {
		//cenario
		
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		//acao
		try {
			service.alugarFilme(usuario, filme);
			//Assert.fail("Deveria ter lancado uma excecao");
			Assert.fail("Deveria ter lancado uma excecao"); //para não gerar falso positivo
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}*/
	
	/*
	
	@Test
	public void testLocacao_filmeSemEstoque3() throws Exception{
		//cenario
		
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		Exception e = assertThrows(Exception.class,() -> service.alugarFilme(usuario, filme));
		assertThat(e.getMessage(), containsString("Filme sem estoque"));
		
	}
	*/
	
	@Test(expected = FilmeSemEstoqueException.class)  //informo ao teste que existe uma exceção esperada
	public void testLocacao_filmeSemEstoque4() throws Exception{
		//cenario
		
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		//acao
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException{
		//cenario
		
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
