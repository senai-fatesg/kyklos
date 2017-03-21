package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface CfopDao extends Persistencia<Cfop>{

   List<Cfop> listarCfop(Integer numero);

   Cfop consultarPorCfop(Integer cfop) throws KyklosException;
   
}
