package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface ValorProdutoDao extends Persistencia<ValorProduto>{
   
   ValorProduto consultarValorAtual(Produto produto) throws KyklosException;
   
   List<ValorProduto> listarPorProduto(Produto produto) throws KyklosException;

}
