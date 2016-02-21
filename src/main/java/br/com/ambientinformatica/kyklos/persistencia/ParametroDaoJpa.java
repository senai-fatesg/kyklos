package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Parametro;


@Repository("parametroDao")
public class ParametroDaoJpa extends PersistenciaJpa<Parametro> implements ParametroDao{

   private static final long serialVersionUID = 1L;

   public Parametro consultarPorChave(String chaveDoParametro) {
      try{
         Query query = em.createQuery("select p from Parametro p where p.chave = :chave");
         query.setParameter("chave", chaveDoParametro);
         return (Parametro) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }
}
