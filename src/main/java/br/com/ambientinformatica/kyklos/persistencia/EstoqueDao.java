package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface EstoqueDao extends Persistencia<Estoque>{

   Estoque consultarPorEstoquePadrao(EmpresaCliente empresa);

   List<Estoque> listarPorDescricaoEPessoa(String descricao, List<PessoaEmpresa> pessoas) throws KyklosException;

}
