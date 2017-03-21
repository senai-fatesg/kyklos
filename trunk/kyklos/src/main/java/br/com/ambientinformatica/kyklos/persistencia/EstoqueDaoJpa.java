package br.com.ambientinformatica.kyklos.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.util.KyklosException;

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
   @Override
   public List<Estoque> listarPorDescricaoEPessoa(String descricao, List<PessoaEmpresa> pessoas) throws KyklosException {
      try{
         List<Estoque> estoques = new ArrayList<Estoque>();
         Query query;
         for (PessoaEmpresa pessoaEmpresa : pessoas) {
            query = em.createQuery("select e from Estoque e where upper(e.descricao) like :descricao and e.pessoaEmpresa.pessoa = :pessoa");
            query.setParameter("descricao", descricao+"%");
            query.setParameter("pessoa", pessoaEmpresa.getPessoa());
            estoques.addAll(query.getResultList());
         }
         return estoques;
      } catch (NoResultException nre){
         return null;
      }
   }
}
