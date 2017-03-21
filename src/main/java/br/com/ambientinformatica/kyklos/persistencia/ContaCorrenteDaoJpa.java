package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.ContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;

@Repository("ContaCorrenteDao")
public class ContaCorrenteDaoJpa extends PersistenciaJpa<ContaCorrente> implements ContaCorrenteDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<ContaCorrente> listarContasCorrentePorNumeroOuNomeBanco(EmpresaCliente empresa, String numeroContaOuNomeBanco) {
      try {
         Query query = em.createQuery("select contaCorrente from ContaCorrente contaCorrente"
               + " where contaCorrente.empresa = :empresa "
               + " and (contaCorrente.numeroConta = :numeroConta or UPPER(contaCorrente.banco.nome) like UPPER(:nomeBanco))"
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
   public ContaCorrente consultarPorEmpresaENumeroConta(EmpresaCliente empresa, String numeroConta) {
      try {  
         Query query = em.createQuery("select contaCorrente from ContaCorrente contaCorrente"
               + " where contaCorrente.empresa = :empresa"
               + " and contaCorrente.numeroConta = :numeroConta");
         query.setParameter("numeroConta", numeroConta);
         query.setParameter("empresa", empresa);
         return (ContaCorrente)query.getSingleResult();  
      } catch ( NoResultException e ) {  
         return null;  
      }  
   }

   @Override
   public ContaCorrente consultarContaCaixaLocal(EmpresaCliente empresa) {
      try {  
         Query query = em.createQuery("select contaCorrente from ContaCorrente contaCorrente"
               + " where contaCorrente.empresa = :empresa"
               + " and UPPER(contaCorrente.banco.nome) = UPPER(:nomeBanco)");
         query.setParameter("nomeBanco", "caixa local");
         query.setParameter("empresa", empresa);
         return (ContaCorrente)query.getSingleResult();  
      } catch ( NoResultException e ) {  
         return null;  
      }  
   }
   
   
}
