package com.jeanlima.testes.locadora.servicos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.jeanlima.testes.locadora.entidades.Filme;
import com.jeanlima.testes.locadora.entidades.Locacao;
import com.jeanlima.testes.locadora.entidades.Usuario;
import com.jeanlima.testes.locadora.exceptions.FilmeSemEstoqueException;
import com.jeanlima.testes.locadora.exceptions.LocadoraException;

//diferente da execução padrão do junit, deve rodar com parameterized
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    /*
     * TESTE DIRIGIDO A DADOS (DDT)
     */

    //instancia do servico

    private LocacaoService service;

    //Parametros do conjunto de dados que será testado
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1) //primeiro parametro do teste
	public Double valorLocacao;
	
	@Parameter(value=2) //segunfo paramettro
	public String cenario;
	
    //instancia do servico
	@Before
	public void setup(){
		service = new LocacaoService();
	}

    /*
     * valores estáticos para organizar lista de retorno
     */

    private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
	private static Filme filme7 = new Filme("Filme 7", 2, 4.0);

    /*
     * conjunto de dados que serão testados
     * retorna um a matriz, onde cada linha representa um cenário distinto
     */
    
    //informa ao JUnit que esse método é a fonte de dados do cenário
    //parametro print elemento de index 2 da lista para identificar o test
    @Parameters(name="{2}") 
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
            //varios cenários
			{Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto"}
		});
	}

    @Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		MatcherAssert.assertThat(resultado.getValor(), CoreMatchers.is(valorLocacao));
	}
    
}
