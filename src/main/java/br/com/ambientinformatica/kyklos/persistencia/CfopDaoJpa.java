package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Repository("CfopDao")
public class CfopDaoJpa extends PersistenciaJpa<Cfop> implements CfopDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<Cfop> listarCfop(Integer numero) {
      try {
         Query query = em.createQuery("select cfop from Cfop cfop"
               + " where cfop.cfop = :numero"
               + " order by cfop");
         query.setParameter("numero", numero);
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }

   @Override
   public Cfop consultarPorCfop(Integer cfop) throws KyklosException {
      Query query = em.createQuery("select cfop from Cfop cfop"
            + " where cfop.cfop = :cfop" );
      query.setParameter("cfop", cfop);
      return (Cfop) query.getSingleResult();
   }
}
