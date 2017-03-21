package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;

@Repository("GrupoDiferencaFiscalDao")
public class GrupoDiferencaFiscalDaoJpa extends PersistenciaJpa<GrupoDiferencaFiscal> implements GrupoDiferencaFiscalDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<GrupoDiferencaFiscal> listarGrupoDiferencaFiscal(String descricao) {
      try {
         Query query = em.createQuery("select gdf from GrupoDiferencaFiscal gdf"
               + " where UPPER(gdf.descricao) like UPPER(:descricao)"
               + " order by id");

         query.setParameter("descricao", "%" + descricao + "%");
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }
}
