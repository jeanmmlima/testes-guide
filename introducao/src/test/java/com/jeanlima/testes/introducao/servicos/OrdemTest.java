package com.jeanlima.testes.introducao.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //muito estranho!!
public class OrdemTest {
	
	public static int contador = 0;
	
	@Test
	public void inicia() {
		
		contador = 1;
		
	}
	
	@Test
	public void verifica() {
		Assert.assertEquals(1, contador);
	}
	
	//perde rastreabilidade
	/*
	@Test
	public void testGeral() {
		inicia();
		verifica();
	} 
	*/

}
