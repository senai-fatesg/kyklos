package br.com.ambientinformatica.kyklos.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.MovimentacaoEstoque;

@Repository("movimentacaoEstoqueDao")
public class MovimentacaoEstoqueDaoJpa extends PersistenciaJpa<MovimentacaoEstoque> implements MovimentacaoEstoqueDao{

   private static final long serialVersionUID = 1L;


}
