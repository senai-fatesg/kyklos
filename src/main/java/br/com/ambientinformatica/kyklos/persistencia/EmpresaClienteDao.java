package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;

public interface EmpresaClienteDao extends Persistencia<EmpresaCliente>{

   void incluirNovoUsuario(EmpresaCliente empresaCliente, String senha) throws PedidoException;
   
}
