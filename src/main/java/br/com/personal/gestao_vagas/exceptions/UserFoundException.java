package br.com.personal.gestao_vagas.exceptions;

public class UserFoundException extends  RuntimeException{

    public UserFoundException(){
        super("User já existe");
    }

}
