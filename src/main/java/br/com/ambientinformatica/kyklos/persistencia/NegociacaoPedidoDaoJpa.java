package br.com.ambientinformatica.kyklos.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.NegociacaoPedido;

@Repository("NegociacaoPedidoDao")
public class NegociacaoPedidoDaoJpa extends PersistenciaJpa<NegociacaoPedido> implements NegociacaoPedidoDao{

   private static final long serialVersionUID = 1L;
   
}