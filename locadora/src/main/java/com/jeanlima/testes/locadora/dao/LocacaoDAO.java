package com.jeanlima.testes.locadora.dao;

import java.util.List;

import com.jeanlima.testes.locadora.entidades.Locacao;

public interface LocacaoDAO {
	
	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();

}
