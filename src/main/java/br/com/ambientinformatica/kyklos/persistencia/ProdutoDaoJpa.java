package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Produto;

@Repository("produtoDao")
public class ProdutoDaoJpa extends PersistenciaJpa<Produto> implements ProdutoDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<Produto> listarProdutos(EmpresaCliente empresa, String codigoDescricao) {
      try {
         Query query = em.createQuery("select p from Produto p"
               + " where UPPER(p.codigo) = :codigo"
               + " or UPPER(p.descricao) like UPPER(:descricao)"
               + " and p.empresa = (:empresa)"
               + " order by codigo");
         query.setParameter("codigo", codigoDescricao.toUpperCase());
         query.setParameter("descricao", "%" + codigoDescricao + "%");
         query.setParameter("empresa", empresa);
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }

   @Override
   public Produto consultarPorCodigo(EmpresaCliente empresa, String codigo) {
      try {  
         Query query = em.createQuery("select p from Produto p"
               + " where UPPER(p.codigo) = UPPER(:codigo)"
               + " and p.empresa = (:empresa)");
         query.setParameter("codigo", codigo);
         query.setParameter("empresa", empresa);
         return (Produto)query.getSingleResult();  
      } catch ( NoResultException e ) {  
         return null;  
      }  
   }
}
