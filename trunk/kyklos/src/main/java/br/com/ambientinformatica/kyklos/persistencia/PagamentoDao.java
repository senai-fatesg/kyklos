package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.BoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.CartaoCredito;
import br.com.ambientinformatica.kyklos.entidade.Cheque;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Pagamento;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface PagamentoDao extends Persistencia<Pagamento>{

   Cheque consultarChequePorEmpresa(EmpresaCliente empresa, Cheque chequeSelecionado) throws KyklosException;
   
   List<Cheque> listarChequesPorPagamento(Pagamento pagamento) throws KyklosException;
   
   List<CartaoCredito> listarCartoesCreditoPorPagamento(Pagamento pagamento) throws KyklosException;
   
   List<BoletoBancario> listarBoletoBancarioPorPagamento(Pagamento pagamento) throws KyklosException;


}
