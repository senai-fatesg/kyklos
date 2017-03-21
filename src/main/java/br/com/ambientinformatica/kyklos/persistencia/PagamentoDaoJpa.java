package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.BoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.CartaoCredito;
import br.com.ambientinformatica.kyklos.entidade.Cheque;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Pagamento;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Repository("PagamentoDao")
public class PagamentoDaoJpa extends PersistenciaJpa<Pagamento> implements PagamentoDao{

   private static final long serialVersionUID = 1L;

   @Override
   public Cheque consultarChequePorEmpresa(EmpresaCliente empresa, Cheque chequeSelecionado) throws KyklosException {
      try{
         Query query = em.createQuery("select cheque from Cheque cheque where cheque = :cheque and cheque.empresa = :empresa");
         query.setParameter("cheque", chequeSelecionado);
         query.setParameter("empresa", empresa);
         return (Cheque) query.getSingleResult();
      }catch(Exception e){
         throw new KyklosException("Erro: "+e.getMessage());
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Cheque> listarChequesPorPagamento(Pagamento pagamento) throws KyklosException {
      try {
         Query query = em.createQuery("select cheque from Cheque where pagamento = :pagamento");
         query.setParameter("pagamento", pagamento);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         throw new KyklosException("Erro: "+e.getMessage());  
      }
   }

   @Override
   public List<CartaoCredito> listarCartoesCreditoPorPagamento(Pagamento pagamento) throws KyklosException {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public List<BoletoBancario> listarBoletoBancarioPorPagamento(Pagamento pagamento) throws KyklosException {
      // TODO Auto-generated method stub
      return null;
   }

}
