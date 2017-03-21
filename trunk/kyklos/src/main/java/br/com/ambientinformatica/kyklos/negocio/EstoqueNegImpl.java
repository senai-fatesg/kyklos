package br.com.ambientinformatica.kyklos.negocio;

import java.util.List;

import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public class EstoqueNegImpl implements EstoqueNeg{

   @Override
   public void reservarProduto(ItemPedido item, Usuario usuario) throws KyklosException {
      // TODO Auto-generated method stub
   }

   @Override
   public List<EstoqueProduto> listarEstoqueProduto(EmpresaCliente empresa,
         String codigo) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ValorProduto consultarValorProduto(EmpresaCliente empresa,
         String codigo) {
      // TODO Auto-generated method stub
      return null;
   }

}
