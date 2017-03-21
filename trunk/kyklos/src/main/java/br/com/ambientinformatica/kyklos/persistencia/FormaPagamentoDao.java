package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.FormaPagamento;

public interface FormaPagamentoDao extends Persistencia<FormaPagamento>{
   
   FormaPagamento consultarPorId(FormaPagamento formaPagamento);

}
