package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Banco;

@Repository("bancoDao")
public class BancoDaoJpa extends PersistenciaJpa<Banco> implements BancoDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<Banco> listarBancosPorNome(String nome) {
      try {
         Query query = em.createQuery("select b from Banco b"
               + " where UPPER(b.nome) like UPPER(:nome)"
               + " order by codigo");
         query.setParameter("nome", "%" + nome + "%");
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }

   @Override
   public Banco consultarBancoPorCodigo(String codigo) {
      try {  
         Query query = em.createQuery("select banco from Banco"
               + " where banco.codigo = :codigo");
         query.setParameter("codigo", codigo);
         return (Banco)query.getSingleResult();  
      } catch ( NoResultException e ) {  
         return null;  
      }  
   }
}
