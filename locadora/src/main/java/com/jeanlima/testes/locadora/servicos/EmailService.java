package com.jeanlima.testes.locadora.servicos;

import com.jeanlima.testes.locadora.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);

}
