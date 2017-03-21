package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Transportadora;

@Repository("transportadoraDao")
public class TransportadoraDaoJpa extends PersistenciaJpa<Transportadora> implements TransportadoraDao{

   private static final long serialVersionUID = 1L;
   
   public Transportadora consultarTransportadoraPorCpfCnpj(String cpfCnpj){
      try {
         Query query = em.createQuery("select t from Transportadora t"
               + " where t.cpfCnpj = :cpfCnpj");
         query.setParameter("cpfCnpj", cpfCnpj);
         return (Transportadora)query.getSingleResult();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Transportadora> listarTransportadorasPorCpfCnpjRazaoSocial(String cpfCnpjRazaoSocial) {
      try {
         Query query = em.createQuery("select t from Transportadora t"
               + " where t.cpfCnpj = :cpfCnpj"
               + " or UPPER(t.razaoSocial) like UPPER(:razaoSocial)");
         query.setParameter("cpfCnpj", cpfCnpjRazaoSocial.toUpperCase());
         query.setParameter("razaoSocial", "%" + cpfCnpjRazaoSocial + "%");
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }
}
