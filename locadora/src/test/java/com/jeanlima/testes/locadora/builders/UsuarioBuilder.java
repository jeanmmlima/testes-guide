package com.jeanlima.testes.locadora.builders;

import com.jeanlima.testes.locadora.entidades.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    //ninguem deve criar instancia do builder fora dele
    private UsuarioBuilder(){

    }

    //pode ser acessado externamente sem necessidade de uma instancia :)
    //porta de entrada para criar um usuário
    public static UsuarioBuilder umUsuario(){
        //instancia do builder
        UsuarioBuilder builder = new UsuarioBuilder();

        //cria usuario
        builder.usuario = new Usuario();
        builder.usuario.setNome("Usuario 1");

        //retorna o builder;
        return builder;
    }

    public Usuario agora(){
        return usuario;
    }
    
}
