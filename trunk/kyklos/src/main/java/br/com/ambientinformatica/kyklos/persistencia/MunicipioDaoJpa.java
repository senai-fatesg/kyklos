package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("MunicipioDao")
public class MunicipioDaoJpa extends PersistenciaJpa<Municipio> implements MunicipioDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   public List<Municipio> listarPorDescricao(String descricao) {
      Query query = em.createQuery("select m from Municipio m where UPPER(m.descricao) like UPPER(:municipio)");
      query.setParameter("municipio", "%" + descricao+"%");
      return query.getResultList();
   }

   @Override
   public Municipio consultarPorCodigoIbge(Integer codigoIbge) {
      try{
         Query query = em.createQuery("select m from Municipio m where m.codigoIbge = :codigoIbge");
         query.setParameter("codigoIbge", codigoIbge);
         return (Municipio) query.getSingleResult();
      } catch (NoResultException nre){
         return null;
      }
   }

   @Override
   public Municipio consultarPorDescricao(String descricao) {
      try{
         Query query = em.createQuery("select m from Municipio m where m.descricao = :descricao");
         query.setParameter("descricao", descricao);
         return (Municipio) query.getSingleResult();
      } catch (NoResultException nre){
         return null;
      }
   }
}
