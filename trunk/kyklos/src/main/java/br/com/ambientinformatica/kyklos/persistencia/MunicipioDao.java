package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface MunicipioDao extends Persistencia<Municipio>{

   List<Municipio> listarPorDescricao(String descricao);

   Municipio consultarPorCodigoIbge(Integer codigoIbge);

   Municipio consultarPorDescricao(String descricao);

}