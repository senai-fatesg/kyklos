package br.com.ambientinformatica.kyklos.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EnumStatusPedido;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;

public interface PedidoDao extends Persistencia<Pedido>{

   Pedido consultarPorNumero(EmpresaCliente empresa, String numeroPedido);
   
   List<Pedido> listarPedidoPorAtributo(EmpresaCliente empresa, String numero, Date dataInicial, Date dataFinal, Pessoa cliente, Vendedor vendedor, List<EnumStatusPedido> statusPedidoSelecionados);
   
}
