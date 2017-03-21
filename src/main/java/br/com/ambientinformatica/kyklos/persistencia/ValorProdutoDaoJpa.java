package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("valorProdutoDao")
public class ValorProdutoDaoJpa extends PersistenciaJpa<ValorProduto> implements ValorProdutoDao{

   private static final long serialVersionUID = 1L;


   @Override
   public ValorProduto consultarValorAtual(Produto produto) throws KyklosException {
      try {
         Query query = em.createQuery("select v from ValorProduto v where v.produto = :produto order by v.data desc");
         query.setParameter("produto", produto);
         query.setMaxResults(1);
         return (ValorProduto) query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
         throw new KyklosException(e.getMessage(), e);
      }
   }

   @SuppressWarnings("unchecked")
   public List<ValorProduto> listarPorProduto(Produto produto) throws KyklosException {
      try {
         Query query = em.createQuery("select v from ValorProduto v where v.produto = :produto order by v.data desc");
         query.setParameter("produto", produto);
         query.setMaxResults(5);
         return query.getResultList();
      } catch (NoResultException e) {
         return null;
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
         throw new KyklosException(e.getMessage(), e);
      }
   }
}
