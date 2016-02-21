package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;

@Repository("estoqueDao")
public class EstoqueDaoJpa extends PersistenciaJpa<Estoque> implements EstoqueDao{

   private static final long serialVersionUID = 1L;

   public Estoque consultarPorEstoquePadrao(EmpresaCliente empresa) {
      try{
         Query query = em.createQuery("select e from Estoque e where e.empresa = :empresaCliente and e.padrao = 'true'");
         query.setParameter("empresaCliente", empresa);
         return (Estoque) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }

   }

   @SuppressWarnings("unchecked")
   public List<Estoque> listarPorEmpresaCliente(EmpresaCliente empresa) {
      try{
         Query query = em.createQuery("select e from Estoque e where e.empresa = :empresa");
         query.setParameter("empresa", empresa);
         return query.getResultList();
      }catch(NoResultException nre){
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<Estoque> listarPorDescricao(String descricao) {
      try{
         Query query = em.createQuery("select e from Estoque e where upper(e.descricao) like :descricao");
         query.setParameter("descricao", "%"+descricao.toUpperCase()+"%");
         return query.getResultList();
      }catch(NoResultException nre){
         return null;
      }
   }


}
