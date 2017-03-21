package br.com.ambientinformatica.kyklos.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("estoqueProdutoDao")
public class EstoqueProdutoDaoJpa extends PersistenciaJpa<EstoqueProduto> implements EstoqueProdutoDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<EstoqueProduto> listarPorEmpresa(EmpresaCliente empresa, Produto produto) throws KyklosException {
      try {
         Query query = em.createQuery("select ep from EstoqueProduto ep where ep.produto = :produto and (ep.estoque.pessoaEmpresa.empresa = :empresa"/*+" or ep.estoque.pessoaEmpresa.pessoa.id = :idEmpresaCliente)"*/);
         query.setParameter("produto", produto);
         query.setParameter("empresa", empresa);
//         query.setParameter("idEmpresaCliente", empresa.getId());
         return query.getResultList();
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
         throw new KyklosException(e.getMessage(), e);
      }
   }

   public EstoqueProduto consultarPorEstoque(Estoque estoque) {
      try{
         Query query = em.createQuery("select ep from EstoqueProduto ep where ep.estoque = :estoque");
         query.setParameter("estoque", estoque);
         return (EstoqueProduto) query.getResultList().get(0);
      }catch(NoResultException nre){
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<EstoqueProduto> listarPorProduto(Produto produto) {
      try{
         Query query = em.createQuery("select ep from EstoqueProduto ep where ep.produto = :produto");
         query.setParameter("produto", produto);
         return query.getResultList();
      }catch(NoResultException nre){
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<EstoqueProduto> listarEstoqueProduto(EmpresaCliente empresa) {
      try{
         Query query = em.createQuery("select ep from EstoqueProduto ep where ep.estoque.empresa = :empresa");
         query.setParameter("empresa", empresa);
         query.setParameter("empresa", empresa);
         return query.getResultList();
      }catch(NoResultException nre){
         return null;
      }
   }

   @Override
   public EstoqueProduto consultarPorProdutoAndEstoque(Produto produto, Estoque estoque) {
      try{
         Query query = em.createQuery("select ep from EstoqueProduto ep where ep.produto = :produto and ep.estoque = :estoque");
         query.setParameter("produto", produto);
         query.setParameter("estoque", estoque);
         return (EstoqueProduto) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }

@SuppressWarnings("unchecked")
@Override
public List<EstoqueProduto> listarPorEmpresa(List<PessoaEmpresa> pessoaEmpresas, Produto produto) throws KyklosException {
	try{
        List<EstoqueProduto> estoques = new ArrayList<EstoqueProduto>();
        Query query;
        for (PessoaEmpresa pessoaEmpresa : pessoaEmpresas) {
           query = em.createQuery("select ep from EstoqueProduto ep where ep.produto = :produto and ep.estoque.pessoaEmpresa.pessoa = :pessoa");
           query.setParameter("produto", produto);
           query.setParameter("pessoa", pessoaEmpresa.getPessoa());
           estoques.addAll(query.getResultList());
        }
        return estoques;
     } catch (NoResultException nre){
        return null;
     }
}

}
