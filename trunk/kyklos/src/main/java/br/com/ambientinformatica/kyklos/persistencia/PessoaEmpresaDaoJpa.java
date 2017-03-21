package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;

@Repository("pessoaEmpresaDao")
public class PessoaEmpresaDaoJpa extends PersistenciaJpa<PessoaEmpresa> implements PessoaEmpresaDao {

   private static final long serialVersionUID = 1L;

   public PessoaEmpresa consultarPorCpfOuCnpj(Pessoa pessoa) {
      try {
         Query query = em.createQuery("select pe from PessoaEmpresa pe where pe.pessoa.cpfCnpj = :cpfCnpj");
         query.setParameter("cpfCnpj", pessoa.getCpfCnpj());
         return (PessoaEmpresa) query.getResultList().get(0);
      } catch (NoResultException nre) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<PessoaEmpresa> listarPorEmpresaCliente(EmpresaCliente empresaCliente) {
      Query query = em.createQuery("select pe from PessoaEmpresa pe where pe.empresa = :empresa and LENGTH(pe.pessoa.cpfCnpj) = 14");
      query.setParameter("empresa", empresaCliente);
      return (List<PessoaEmpresa>) query.getResultList();
   }

   public PessoaEmpresa consultarPorPessoaAndEmpresa(Pessoa pessoa, EmpresaCliente empresaCliente) {
      try {
         Query query = em.createQuery("select pe from PessoaEmpresa pe where pe.empresa = :empresaCliente and pe.pessoa = :pessoa");
         query.setParameter("empresaCliente", empresaCliente);
         query.setParameter("pessoa", pessoa);
         return (PessoaEmpresa) query.getSingleResult();
      } catch (NoResultException nre) {
         return null;
      }

   }

   @SuppressWarnings("unchecked")
   @Override
   public List<PessoaEmpresa> consultarEmpresasVinculadas(EmpresaCliente empresaCliente) {
      try {
         Query query = em.createQuery("select pe from PessoaEmpresa pe where pe.empresa = :empresaCliente");
         query.setParameter("empresaCliente", empresaCliente);
         return query.getResultList();
      } catch (NoResultException nre) {
         return null;
      }
   }
}