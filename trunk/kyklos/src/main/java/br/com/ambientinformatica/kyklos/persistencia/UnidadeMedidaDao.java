package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Transportadora;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;

public interface UnidadeMedidaDao extends Persistencia<UnidadeMedida> {

	UnidadeMedida consultarPorSigla(String sigla);

	List<UnidadeMedida> consultarTodasUnidadesDeMedida();

	public List<UnidadeMedida> listarUnidadesPorSigla(String sigla);

}
