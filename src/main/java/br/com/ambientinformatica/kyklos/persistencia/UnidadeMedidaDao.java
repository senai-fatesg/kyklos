package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;

public interface UnidadeMedidaDao extends Persistencia<UnidadeMedida> {
	
   public UnidadeMedida consultarPorSigla(String sigla);

   public List<UnidadeMedida> consultarUnidadesAtivas();
	
   public List<UnidadeMedida> listarUnidadesPorSigla(String sigla);
	
   public List<UnidadeMedida> listarPorDescricao(String descricao);
   
   public List<UnidadeMedida> listarUnidadesPorSiglaOuDescricao(String descricaoESiglaConsulta);
   
   public List<String> listarTodosStatus();

   public void excluirPorId(UnidadeMedida unidadeMedida) throws Exception;
   


	

}
