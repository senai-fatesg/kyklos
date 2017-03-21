package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Cor;

public interface CorDao extends Persistencia<Cor>{

   List<Cor> listarCores(String codigoDescricao);

   
}
