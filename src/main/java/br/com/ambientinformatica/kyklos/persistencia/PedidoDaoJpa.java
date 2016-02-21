package br.com.ambientinformatica.kyklos.persistencia;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EnumStatusPedido;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;

@Repository("pedidoDao")
public class PedidoDaoJpa extends PersistenciaJpa<Pedido> implements PedidoDao{

   private static final long serialVersionUID = 1L;

   public Pedido consultarPorNumero(EmpresaCliente empresa, String numeroPedido) {
      try{
         Query query = em.createQuery("select p from Pedido p where p.numero = :numero"
               + " and p.empresa = (:empresa)");
         query.setParameter("numero", numeroPedido);
         query.setParameter("empresa", empresa);
         return (Pedido) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }
   
   @SuppressWarnings("unchecked")
   public List<Pedido> listarPedidoPorAtributo(EmpresaCliente empresa, String numero , Date dataInicio, Date dataFim, Pessoa cliente, Vendedor vendedor, List <EnumStatusPedido> statusPedidoSelecionados){
      try {
         Query query = em.createQuery("select p from Pedido p left join fetch cliente c "
               + " where p.empresa = (:empresa) "
               + " and p.numero = :numero "
               + " and p.data between :dataInicio and :dataFim"
               + " and c = :cliente "
               + " and p.vendedor = :vendedor "
               + " and p.status = :status"
               + " order by p.data");
         query.setParameter("empresa", empresa);
         query.setParameter("numero", numero);
         query.setParameter("dataInicio", dataInicio);
         query.setParameter("dataFim", dataFim);
         query.setParameter("cliente", cliente);
         query.setParameter("vendedor", vendedor);
         query.setParameter("status", statusPedidoSelecionados);
         query.setMaxResults(100);
         return query.getResultList();
      } catch ( NoResultException e ) {  
         return null;  
      }
   }
}