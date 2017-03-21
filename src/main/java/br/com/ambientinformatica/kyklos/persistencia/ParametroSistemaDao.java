package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.ParametroSistema;

public interface ParametroSistemaDao extends Persistencia<ParametroSistema>{

   ParametroSistema consultarPorChave(String chaveDoParametro);
   
}
