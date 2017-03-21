package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;
import java.util.Map;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.ParametroEmpresa;

public interface ParametroEmpresaDao extends Persistencia<ParametroEmpresa>{

   ParametroEmpresa consultarPorChaveEmpresa(EmpresaCliente empresa, String chaveDoParametro);
   
   List<ParametroEmpresa> listarTodosEmpresa(EmpresaCliente empresaCliente);
   
   Map<String, String> listarTodosMapsEmpresa(EmpresaCliente empresaCliente);
   
}
