package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements UsuarioDao {

   private static final long serialVersionUID = 1L;

   public Usuario consultarPorLogin(String login) {
      try{
         Query query = em.createQuery("select u from Usuario u where u.login = :login");
         query.setParameter("login", login);
         return (Usuario) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }

   }

}
