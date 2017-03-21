package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface ProdutoDao extends Persistencia<Produto>{

   List<Produto> listarProdutos(EmpresaCliente empresa, String codigoDescricao) throws KyklosException;

   Produto consultarPorCodigo(EmpresaCliente empresa, String codigo);
   
}
