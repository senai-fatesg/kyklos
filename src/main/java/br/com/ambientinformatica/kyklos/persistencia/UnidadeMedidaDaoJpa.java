package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;

@Repository("unidadeMedidaDao")
public class UnidadeMedidaDaoJpa extends PersistenciaJpa<UnidadeMedida>
		implements UnidadeMedidaDao {

	private static final long serialVersionUID = -6621951628831277824L;

	@Override
	public UnidadeMedida consultarPorSigla(String sigla) {
		try {
			Query query = em
					.createQuery("select un from UnidadeMedida un where UPPER(un.sigla) = UPPER(:sigla)");
			query.setParameter("sigla", sigla);
			return (UnidadeMedida) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeMedida> consultarTodasUnidadesDeMedida() {
		try {
			Query query = em.createQuery("select un from UnidadeMedida un");
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeMedida> listarUnidadesPorSigla(String sigla) {
		try {
			Query query = em
					.createQuery("select u from UnidadeMedida u where upper(u.sigla) like :sigla");
			query.setParameter("sigla", "%" + sigla + "%");
			// query.setMaxResults(25);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeMedida> listarPorDescricao(String descricao) {
		try {
			Query query = em
					.createQuery("select u from UnidadeMedida u where upper(u.descricao) like :descricao");
			query.setParameter("descricao", "%" + descricao.toUpperCase() + "%");
			return query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

   @SuppressWarnings("unchecked")
   @Override
   public List<UnidadeMedida> listarUnidadesPorSiglaOuDescricao(String descricaoSigla){
         try {
            Query query = em.createQuery("select um from UnidadeMedida um where upper(um.sigla) like upper(:sigla)"
                  + " or upper(um.descricao) like upper(:descricao)");
            query.setParameter("sigla", "%" + descricaoSigla+ "%");
            query.setParameter("descricao", "%" + descricaoSigla+ "%");
            return query.getResultList();
         } catch (NoResultException e) {
            return null;
         }
   }

   @Override
   public void excluirPorId(UnidadeMedida unidadeMedida) throws Exception {
      try{
         Query query = em.createQuery("delete from UnidadeMedida u where u.id = :id");
         query.setParameter("id", unidadeMedida.getId());
         query.executeUpdate();
      }catch(Exception e){
         throw new Exception("Erro ao Excluir.");
      }
   }

}
