package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;

public interface ValorProdutoDao extends Persistencia<ValorProduto>{
   
   ValorProduto consultarValorAtual(Produto produto) throws PedidoException;
   
   List<ValorProduto> listarPorProduto(Produto produto) throws PedidoException;

}
