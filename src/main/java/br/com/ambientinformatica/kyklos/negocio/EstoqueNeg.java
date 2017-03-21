package br.com.ambientinformatica.kyklos.negocio;

import java.util.List;

import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface EstoqueNeg {
   
   void reservarProduto(ItemPedido item, Usuario usuario) throws KyklosException;
   
   List<EstoqueProduto> listarEstoqueProduto(EmpresaCliente empresa, String codigo);
   
   ValorProduto consultarValorProduto(EmpresaCliente empresa, String codigo);

}
