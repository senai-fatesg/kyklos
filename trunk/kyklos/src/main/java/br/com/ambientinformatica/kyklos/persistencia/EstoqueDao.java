package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;

public interface EstoqueDao extends Persistencia<Estoque>{

   Estoque consultarPorEstoquePadrao(EmpresaCliente empresa);

   List<Estoque> listarPorEmpresaCliente(EmpresaCliente empresa);

   List<Estoque> listarPorDescricao(String descricao);

}
