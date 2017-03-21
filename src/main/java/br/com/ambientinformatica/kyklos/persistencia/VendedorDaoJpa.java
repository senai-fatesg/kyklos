package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Repository("vendedorDao")
public class VendedorDaoJpa extends PersistenciaJpa<Vendedor> implements VendedorDao {

   private static final long serialVersionUID = 1L;

   public Vendedor consultarPorEmpresaEPessoa(EmpresaCliente empresa, Pessoa pessoa) {
      try {
         Query query = em.createQuery("select v from Vendedor v where v.empresa = :empresa and v.pessoa = :pessoa");
         query.setParameter("empresa", empresa);
         query.setParameter("pessoa", pessoa);
         return (Vendedor) query.getSingleResult();
      } catch (NoResultException nre) {

         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<Vendedor> listarPorEmpresaENome(EmpresaCliente empresa, String vendedorNome) {
      try {
         Query query = em.createQuery("select v from Vendedor v where v.empresa = :empresa " + "and UPPER(v.pessoa.nome) like UPPER(:vendedorNome)");
         query.setParameter("empresa", empresa);
         query.setParameter("vendedorNome", "%" + vendedorNome + "%");
         return query.getResultList();
      } catch (NoResultException nre) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Vendedor> listarPorEmpresaENomeOuCpfCnpj(EmpresaCliente empresa, String cpfCnpjNome) throws KyklosException {
      try {
         Query query = em.createQuery("select v from Vendedor v where v.empresa = :empresa" + " and (UPPER(v.pessoa.nome) like UPPER(:nome) or v.pessoa.cpfCnpj like :cpfCnpj)");
         query.setParameter("empresa", empresa);
         query.setParameter("nome", "%" + cpfCnpjNome + "%");
         query.setParameter("cpfCnpj", cpfCnpjNome + "%");
         return query.getResultList();
      } catch (NoResultException nre) {
         throw new KyklosException("Nenhum registro encontrado.");
      }

   }

}
