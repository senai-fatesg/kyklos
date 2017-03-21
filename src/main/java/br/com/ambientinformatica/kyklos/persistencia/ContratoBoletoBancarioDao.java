package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.ContratoBoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;

public interface ContratoBoletoBancarioDao extends Persistencia<ContratoBoletoBancario>{

   List<ContratoBoletoBancario> listarContratoBoletoBancarioPorNumeroContaOuNomeBanco(EmpresaCliente empresa, String numeroContaOuNomeBanco);

   ContratoBoletoBancario consultarPorEmpresaENumeroConta(EmpresaCliente empresa, String numeroConta);

}
