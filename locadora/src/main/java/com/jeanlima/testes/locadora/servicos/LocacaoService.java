package com.jeanlima.testes.locadora.servicos;


import static com.jeanlima.testes.locadora.utils.DataUtils.adicionarDias;

import java.util.Date;

import com.jeanlima.testes.locadora.entidades.Filme;
import com.jeanlima.testes.locadora.entidades.Locacao;
import com.jeanlima.testes.locadora.entidades.Usuario;
import com.jeanlima.testes.locadora.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.locadora.exceptions.LocadoraException;



public class LocacaoService {
	
	//FilmeSemEstoqueException, LocadoraException
	//public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {	
				
		/* ## PARTE 3 ### */
		if(usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		if(filme == null) {
			throw new LocadoraException("Filme vazio");
		}
		
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException();
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
 
}