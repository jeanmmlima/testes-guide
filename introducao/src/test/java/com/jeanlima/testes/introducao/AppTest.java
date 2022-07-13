package com.jeanlima.testes.introducao;

import java.util.Date;

import com.jeanlima.testes.introducao.entidades.Filme;
import com.jeanlima.testes.introducao.entidades.Locacao;
import com.jeanlima.testes.introducao.entidades.Usuario;
import com.jeanlima.testes.introducao.servicos.LocacaoService;
import com.jeanlima.testes.introducao.utils.DataUtils;


import org.junit.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	@Test
	public void teste() {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao;
		try {
			locacao = service.alugarFilme(usuario, filme);
			//verificacao
			Assert.assertTrue(locacao.getValor() == 4.0);
			Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
			Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
   
}