package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Cor;

@Repository("corDao")
public class CorDaoJpa extends PersistenciaJpa<Cor> implements CorDao {

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<Cor> listarCores(String codigoDescricao) {
      try {
         Query query = em.createQuery("select c from Cor c"
               + " where (UPPER(c.codigo) like UPPER(:codigo) or UPPER(c.descricao) like UPPER(:descricao))");
         query.setParameter("codigo",codigoDescricao + "%");
         query.setParameter("descricao","%" + codigoDescricao + "%");
         query.setMaxResults(100);
         return query.getResultList();
      } catch (NoResultException e) {
         return null;
      }
   }
}
