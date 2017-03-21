package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.ContratoBoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;

@Repository("ContratoBoletoBancarioDao")
public class ContratoBoletoBancarioDaoJpa extends PersistenciaJpa<ContratoBoletoBancario> implements ContratoBoletoBancarioDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<ContratoBoletoBancario> listarContratoBoletoBancarioPorNumeroContaOuNomeBanco(EmpresaCliente empresa, String numeroContaOuNomeBanco) {
      try {
         Query query = em.createQuery("select contratoBoletoBancario from ContratoBoletoBancario contratoBoletoBancario"
               + " where contratoBoletoBancario.empresaCliente = :empresa "
               + " and (contratoBoletoBancario.contaCorrente.numeroConta = :numeroConta or UPPER(contratoBoletoBancario.contaCorrente.banco.nome) like UPPER(:nomeBanco))"
               + " order by id");
         query.setParameter("empresa", empresa);
         query.setParameter("numeroConta", numeroContaOuNomeBanco);
         query.setParameter("nomeBanco", "%" + numeroContaOuNomeBanco + "%");
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }

   @Override
   public ContratoBoletoBancario consultarPorEmpresaENumeroConta(EmpresaCliente empresa, String numeroConta) {
      try {  
         Query query = em.createQuery("select contratoBoletoBancario from ContratoBoletoBancario contratoBoletoBancario"
               + " where contratoBoletoBancario.empresaCliente = :empresa"
               + " and contratoBoletoBancario.contaCorrente.numeroConta = :numeroConta");
         query.setParameter("numeroConta", numeroConta);
         query.setParameter("empresa", empresa);
         return (ContratoBoletoBancario)query.getSingleResult();  
      } catch ( NoResultException e ) {  
         return null;  
      }  
   }
   
}
