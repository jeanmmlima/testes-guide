package com.jeanlima.testes.locadora.servicos;


import static com.jeanlima.testes.locadora.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jeanlima.testes.locadora.dao.LocacaoDAO;
import com.jeanlima.testes.locadora.entidades.Filme;
import com.jeanlima.testes.locadora.entidades.Locacao;
import com.jeanlima.testes.locadora.entidades.Usuario;
import com.jeanlima.testes.locadora.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.locadora.exceptions.LocadoraException;
import com.jeanlima.testes.locadora.utils.DataUtils;



public class LocacaoService {
	
	//
	private LocacaoDAO dao;
	private SPCService spcService;
	private EmailService emailService;
	
	//FilmeSemEstoqueException, LocadoraException
	//public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {	
				
		/* ## PARTE 3 ### */
		if(usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		
		for(Filme filme: filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}
		
		if(spcService.possuiNegativacao(usuario)) {
			throw new LocadoraException("Usuário Negativado");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for(int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i) {
				case 2: valorFilme = valorFilme * 0.75; break;
				case 3: valorFilme = valorFilme * 0.5; break;
				case 4: valorFilme = valorFilme * 0.25; break;
				case 5: valorFilme = 0d; break;
			}
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		
		//### ENFIM IMPLEMENTANDO O SALVAR
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		dao.salvar(locacao);
		
		//Testar logo apos adicionar o salvar - falha de ponteiro nullo, afinal método é da interface
		
		/*
		 * 1a solução implementar a camada de DAO: deixa de ser teste unitário e passa a ser de interação
		 * teste unitário não deve ter dependencia externa - acessar banco, serviço via rede
		 * teste não fica isolad! problema de rede, falta da massa de dado.
		 * teste unitário deve ser isolado para se falhar, já sabermos. 
		 * fere o principio do FIRST - tudo deve estar "perfeito"
		 * 
		 * TDD. Quando precisar usar algum serviço que NÃO esteja implementado ou disponível USAMOS MOCKS!!!!
		 * Mesmo que tivesse a camada de DAO, não deveriamos utilizá-la!!
		 */
		
		
		return locacao;
	}
	//### PARTE 3 //
	public void notificarAtrasos() {
		
		//parte 3
		/*
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		for(Locacao locacao : locacoes) {
			emailService.notificarAtraso(locacao.getUsuario());
		}*/
		
		//parte 4
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		for(Locacao locacao : locacoes) {
			if(locacao.getDataRetorno().before(new Date())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
			
		}
	}
	
	//injeção da dependencia da locação dao - agora posso instancia-lo e seta-lo no test!
	public void setDao(LocacaoDAO dao) {
		this.dao = dao;
	}
	
	public void setSPCService(SPCService spc) {
		this.spcService = spc;
	}
	
	public void setEmailService(EmailService email) {
		this.emailService = email;
	}
 
}