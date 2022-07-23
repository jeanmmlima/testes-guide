package com.jeanlima.testes.locadora.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class CalculadoraMockTest {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void deveMostrarDiferencaEntreMockSpy() {
		System.out.println(calcMock.somar(1, 6)); //retorna 0 valor padão
		
		Mockito.when(calcMock.somar(1, 6)).thenReturn(8);
		Mockito.when(calcSpy.somar(1, 6)).thenReturn(8);
		System.out.println("MOCK: "+calcMock.somar(1, 6)); //retorna 0 valor padão
		System.out.println("SPY: "+calcSpy.somar(1, 6)); //retorna 0 valor padão
		
		System.out.println("MOCK: "+calcMock.somar(1, 5)); //retorna 0 valor padão - não sabe o que fazer
		System.out.println("SPY: "+calcSpy.somar(1, 5)); //spy vai retornar o valor do método
		
		//para o mock agir conforme o mock
		Mockito.when(calcMock.somar(1, 5)).thenCallRealMethod();
		System.out.println("MOCK: "+calcMock.somar(1, 5));
		
		
	}

	@Test
	public void teste(){
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(134345, -234));
		System.out.println(argCapt.getAllValues());
	}
}
