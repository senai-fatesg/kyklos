package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;

public interface PessoaEmpresaDao extends Persistencia<PessoaEmpresa>{

   PessoaEmpresa consultarPorCpfOuCnpj(Pessoa pessoa);

   List<PessoaEmpresa> listarPorEmpresaCliente(EmpresaCliente empresaCliente);

   PessoaEmpresa consultarPorPessoaAndEmpresa(Pessoa pessoa, EmpresaCliente empresa);
   
   List<PessoaEmpresa> consultarEmpresasVinculadas(EmpresaCliente empresaCliente);
}
