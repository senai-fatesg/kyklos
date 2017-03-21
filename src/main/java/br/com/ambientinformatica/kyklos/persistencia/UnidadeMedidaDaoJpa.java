package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;

@Repository("unidadeMedidaDao")
public class UnidadeMedidaDaoJpa extends PersistenciaJpa<UnidadeMedida> implements UnidadeMedidaDao {

   private static final long serialVersionUID = -6621951628831277824L;

   @Override
   public UnidadeMedida consultarPorSigla(String sigla) {
      try {  
         Query query = em.createQuery("select un from UnidadeMedida un where UPPER(un.sigla) = UPPER(:sigla)");
         query.setParameter("sigla", sigla);
         return (UnidadeMedida) query.getSingleResult();
      } catch ( NoResultException e ) {  
         return null;  
      } 
   }
   
   @SuppressWarnings("unchecked")
   public List<UnidadeMedida> consultarTodasUnidadesDeMedida() {
      try {
         Query query = em.createQuery("select un from UnidadeMedida un");
         return query.getResultList();
      } catch (NoResultException e){
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<UnidadeMedida> consultarPorSiglaOuDescricao(String siglaOuDescricao) {
      try{
         Query query = em.createQuery("select unidadeMedida from UnidadeMedida unidadeMedida"
               + " where UPPER(unidadeMedida.sigla) like :sigla"
               + " or UPPER(unidadeMedida.descricao) like :descricao"
               + " order by unidadeMedida.descricao asc");
         query.setParameter("sigla", siglaOuDescricao.toUpperCase()+"%");
         query.setParameter("descricao", siglaOuDescricao.toUpperCase()+"%");
         return query.getResultList();
      }catch(NoResultException nre){
         return null;
      }
   }
   
}
