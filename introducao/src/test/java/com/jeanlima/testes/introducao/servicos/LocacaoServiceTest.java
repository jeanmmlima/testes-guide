package com.jeanlima.testes.introducao.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.jeanlima.testes.introducao.entidades.Filme;
import com.jeanlima.testes.introducao.entidades.Locacao;
import com.jeanlima.testes.introducao.entidades.Usuario;
import com.jeanlima.testes.introducao.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule 
	public ErrorCollector error = new ErrorCollector();
	
	@Test
	public void testeLocacao() {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		/* verificacao
		System.out.println(locacao.getValor() == 5.0);
		System.out.println(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		System.out.println(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		*/
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		//Assert.assertEquals(4.0,locacao.getValor(), 0.01); - falha
		
		//AssertThat
		//valoe esperado como Matchers
		//use Source - add import
		assertThat(locacao.getValor(), is(5.0));
		assertThat(locacao.getValor(), is(equalTo(5.0)));
		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		
		
		//Problema de usar assim é que os testes das assertivas param na primeira falha encontrada
		//posso dividir um método para cada assertiva para o mesmo cenário
		//OU
		//usar RULES
	
		error.checkThat(locacao.getValor(), is(5.0));
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(false));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(false));
		
		
		
	}

}
