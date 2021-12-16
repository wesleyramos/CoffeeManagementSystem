package br.pucbr.model.dao;

import java.util.List;

public interface InterfaceDAO {

    public Object inserir( Object object ) throws Exception;
    public boolean alterar( Object object ) throws Exception;
    public boolean remover( Integer id ) throws Exception;
}
