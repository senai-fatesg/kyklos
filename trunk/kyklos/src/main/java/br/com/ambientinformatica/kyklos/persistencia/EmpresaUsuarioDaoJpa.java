package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.Usuario;

@Repository("empresaUsuarioDao")
public class EmpresaUsuarioDaoJpa extends PersistenciaJpa<EmpresaUsuario> implements EmpresaUsuarioDao {

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<EmpresaUsuario> listarPorUsuario(Usuario usuario, String nome) {
      String sql = "select e from EmpresaUsuario e where e.usuario = :usuario";

      if(nome != null && !nome.isEmpty()){
         sql += "  and upper(e.empresa.pessoa.nome) like upper(:nome)";
      }
      sql += " order by e.empresa.pessoa.nome";
      Query query = em.createQuery(sql);
      query.setParameter("usuario", usuario);
      if(nome != null && !nome.isEmpty()){
         query.setParameter("nome", "%" + nome + "%");
      }
      return query.getResultList();
   }

   @SuppressWarnings("unchecked")
   public List<EmpresaUsuario> listarPorEmpresaENomeOuLogin(EmpresaCliente empresa, String parametro) {
         Query query = em.createQuery("select eu from EmpresaUsuario eu where eu.empresa = :empresa"
               + " and (UPPER(eu.usuario.pessoa.nome) like :nome OR UPPER(eu.usuario.login) like :login)");
         query.setParameter("empresa", empresa);
         query.setParameter("nome", "%"+parametro.toUpperCase()+"%");
         query.setParameter("login", "%"+parametro.toUpperCase()+"%");
         return query.getResultList();
   }
}
