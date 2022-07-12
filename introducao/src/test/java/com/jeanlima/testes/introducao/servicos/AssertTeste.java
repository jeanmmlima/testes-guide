package com.jeanlima.testes.introducao.servicos;

import org.junit.Assert;
import org.junit.Test;

import com.jeanlima.testes.introducao.entidades.Usuario;

public class AssertTeste {
	
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		//compara números
		Assert.assertEquals(1, 1);
		
		//depreciado por casas decmais - precisa de delta
		//delta é a margem de erro de comparação
		Assert.assertEquals(0.512, 0.514, 0.01);
		
		//tipo primitivo e objeto
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		//String
		Assert.assertEquals("bola", "bola");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		
		//sem o equals na calsse usuario, que compara o nome, vai
		//falar o test
		//com o equals, teste é sucedidp
		Assert.assertEquals(u1, u2); //duas instancias diferentes
		
		//Assert.assertSame(u1, u2); //avalia a instancia - dá erro
		Assert.assertSame(u2, u2); //avalia a instancia - dá certo
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
