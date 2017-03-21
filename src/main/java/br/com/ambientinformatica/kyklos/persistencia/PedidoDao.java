package br.com.ambientinformatica.kyklos.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EnumStatusPedido;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface PedidoDao extends Persistencia<Pedido>{

   public Pedido consultarPorNumeroPedidoExterno(EmpresaCliente empresa, Long numeroPedidoExterno);
   
   public List<Pedido> listarPedidosAFaturar(EmpresaCliente empresa, Long numero, Date dataInicial, Date dataFinal, Pessoa cliente, Vendedor vendedor, EnumStatusPedido statusDoPedido) throws KyklosException;
   
   public List<Pedido> listarPedidosPorAtributo(EmpresaCliente empresa, Long numeroPedidoExterno, Date dataInicial, Date dataFinal, Pessoa cliente, Vendedor vendedor, List<EnumStatusPedido> statusPedidoSelecionados) throws KyklosException;

   public Long consultarUltimoNumeroPedido(EmpresaCliente empresa);

   public Pedido consultarPorNumeroPedido(EmpresaCliente empresa, Pedido pedido);

}
