package com.jeanlima.testes.introducao.servicos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.jeanlima.testes.introducao.entidades.Filme;
import com.jeanlima.testes.introducao.entidades.Locacao;
import com.jeanlima.testes.introducao.entidades.Usuario;
import com.jeanlima.testes.introducao.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
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
		
	}

}
