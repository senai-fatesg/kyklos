package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface VendedorDao extends Persistencia<Vendedor>{

   public Vendedor consultarPorEmpresaEPessoa(EmpresaCliente empresa, Pessoa pessoa);
   
   public List<Vendedor> listarPorEmpresaENome(EmpresaCliente empresa, String vendedorNome);

   public List<Vendedor> listarPorEmpresaENomeOuCpfCnpj(EmpresaCliente empresa, String cpfCnpjNome) throws KyklosException;
   
}
