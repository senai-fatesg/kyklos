package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.ContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;

public interface ContaCorrenteDao extends Persistencia<ContaCorrente>{

   List<ContaCorrente> listarContasCorrentePorNumeroOuNomeBanco(EmpresaCliente empresa, String numeroContaOuNomeBanco);

   ContaCorrente consultarPorEmpresaENumeroConta(EmpresaCliente empresa, String numeroConta);
   
   ContaCorrente consultarContaCaixaLocal(EmpresaCliente empresa);
   
}
