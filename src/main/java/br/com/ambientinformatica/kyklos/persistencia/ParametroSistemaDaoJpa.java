package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.ParametroSistema;


@Repository("parametroSistemaDao")
public class ParametroSistemaDaoJpa extends PersistenciaJpa<ParametroSistema> implements ParametroSistemaDao{

   private static final long serialVersionUID = 1L;

   public ParametroSistema consultarPorChave(String chaveDoParametro) {
      try{
         Query query = em.createQuery("select ps from ParametroSistema ps where ps.chave = :chave");
         query.setParameter("chave", chaveDoParametro);
         return (ParametroSistema) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }
}
