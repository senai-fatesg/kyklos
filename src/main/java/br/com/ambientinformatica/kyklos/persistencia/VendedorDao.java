package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;

public interface VendedorDao extends Persistencia<Vendedor>{

   Vendedor consultarPorEmpresaEPessoa(EmpresaCliente empresa, Pessoa pessoa);
   
   List<Vendedor> listarPorEmpresaENome(EmpresaCliente empresa, String vendedorNome);
   
}
