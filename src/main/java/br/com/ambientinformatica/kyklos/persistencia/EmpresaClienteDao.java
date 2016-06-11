package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;

public interface EmpresaClienteDao extends Persistencia<EmpresaCliente>{

//   void incluirNovoUsuario(EmpresaCliente empresaCliente, String senha) throws PedidoException;
   
   EmpresaCliente consultarPorPessoa(Pessoa pessoa);
   
   EmpresaCliente consultarPorCpfCnpj(String cpfCnpj);
   
}
