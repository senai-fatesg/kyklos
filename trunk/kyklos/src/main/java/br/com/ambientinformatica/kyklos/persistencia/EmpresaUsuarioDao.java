package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.Usuario;

public interface EmpresaUsuarioDao extends Persistencia<EmpresaUsuario>{

   List<EmpresaUsuario> listarPorUsuario(Usuario usuario, String nome);

   List<EmpresaUsuario> listarPorEmpresaENomeOuLogin(EmpresaCliente empresa, String parametro);


}
