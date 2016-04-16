package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("pessoaDao")
public class PessoaDaoJpa extends PersistenciaJpa<Pessoa> implements PessoaDao{

   private static final long serialVersionUID = 1L;

   public Pessoa consultarPorCpfCnpj(String cpfCnpj) {
      try{
         Query query = em.createQuery("select p from Pessoa p where p.cpfCnpj = :cpfCnpj");
         query.setParameter("cpfCnpj", cpfCnpj);
         return (Pessoa) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }
   
   public Pessoa consultarPorNome(String nome) {
      try{
         Query query = em.createQuery("select c from Cliente c where c.nome like :nome");
         query.setParameter("nome", nome);
         return (Pessoa) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }
   
   @SuppressWarnings("unchecked")
   public List<Pessoa> listarPessoasPorNome(String nome){
	   try {
		   Query query = em.createQuery("select p from Pessoa p"
				   + " where UPPER(p.nome) like UPPER(:nome)");
		   query.setParameter("nome", "%" + nome + "%");
		   query.setMaxResults(25);
		   return query.getResultList();
	   } catch ( NoResultException e ) {  
		   return null;  
	   }
   }
}