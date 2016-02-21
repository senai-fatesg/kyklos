package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Parametro;

public interface ParametroDao extends Persistencia<Parametro>{

   Parametro consultarPorChave(String chaveDoParametro);
   
}
