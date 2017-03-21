package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.FormaPagamento;

@Repository("FormaPagamentoDao")
public class FormaPagamentoDaoJpa extends PersistenciaJpa<FormaPagamento> implements FormaPagamentoDao{

   private static final long serialVersionUID = 1L;
   
   @Override
   public FormaPagamento consultarPorId(FormaPagamento formaPagamento) {
      try {  
         Query query = em.createQuery("select fp from FormaPagamento fp"
               + " where fp.id = :id");
         query.setParameter("id", formaPagamento.getId());
         return (FormaPagamento)query.getSingleResult();  
      } catch ( NoResultException e ) {  
         return null;  
      }  
   }

}
