package br.com.ambientinformatica.kyklos.negocio;

import java.util.List;

import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;

public interface EstoqueNeg {
   
   void reservarProduto(ItemPedido item, Usuario usuario) throws PedidoException;
   
   List<EstoqueProduto> listarEstoqueProduto(EmpresaCliente empresa, String codigo);
   
   ValorProduto consultarValorProduto(EmpresaCliente empresa, String codigo);

}
