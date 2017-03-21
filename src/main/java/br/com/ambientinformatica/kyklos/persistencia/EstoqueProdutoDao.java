package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface EstoqueProdutoDao extends Persistencia<EstoqueProduto>{
   
   List<EstoqueProduto> listarPorEmpresa(EmpresaCliente empresa, Produto produto) throws KyklosException;
   
   EstoqueProduto consultarPorEstoque(Estoque estoque);

   List<EstoqueProduto> listarPorProduto(Produto produto);

   List<EstoqueProduto> listarEstoqueProduto(EmpresaCliente empresa);

   EstoqueProduto consultarPorProdutoAndEstoque(Produto produto, Estoque estoque);

   List<EstoqueProduto> listarPorEmpresa(List<PessoaEmpresa> pessoaEmpresas, Produto produto) throws KyklosException;

}
