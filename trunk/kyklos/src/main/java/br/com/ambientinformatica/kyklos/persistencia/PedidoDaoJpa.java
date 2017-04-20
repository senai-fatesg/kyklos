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
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Repository("pedidoDao")
public class PedidoDaoJpa extends PersistenciaJpa<Pedido> implements PedidoDao {

   private static final long serialVersionUID = 1L;

   public Pedido consultarPorNumeroPedidoExterno(EmpresaCliente empresa, Long numeroPedidoExterno) {
      try {
         Query query = em.createQuery("select p from Pedido p"
         		+ " left join fetch p.frete left join fetch p.pagamento where p.numeroPedidoExterno = :numeroPedidoExterno" + " and p.empresa = (:empresa)");
         query.setParameter("numeroPedidoExterno", numeroPedidoExterno);
         query.setParameter("empresa", empresa);
         return (Pedido) query.getSingleResult();
      } catch (NoResultException nre) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public List<Pedido> listarPedidosPorAtributo(EmpresaCliente empresa, Long numeroPedidoExterno, Date dataInicio, Date dataFim, Pessoa cliente, Vendedor vendedor, List<EnumStatusPedido> statusPedidoSelecionados) {
      try {
         String sql = "select distinct(p) from Pedido p "
         // + " left join fetch p.itens i"
         // + " left join fetch p.pagamento pag"
         // + " left join fetch pag.formasPagamento"
               + " where p.empresa = :empresa";
         if (numeroPedidoExterno != null) {
            sql += " and p.numeroPedidoExterno = :numeroPedidoExterno";
         }
         if (dataInicio != null && dataFim != null) {
            sql += " and p.data between :dataInicio and :dataFim";
         }
         if (cliente != null) {
            sql += " and p.cliente = :cliente";
         }
         if (vendedor != null) {
            sql += " and p.vendedor = :vendedor";
         }

         sql += " and p.status = :status order by p.data desc";

         Query query = em.createQuery(sql);
         query.setParameter("empresa", empresa);

         if (numeroPedidoExterno != null) {
            query.setParameter("numeroPedidoExterno", numeroPedidoExterno);
         }
         if (dataInicio != null && dataFim != null) {
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
         }
         if (cliente != null) {
            query.setParameter("cliente", cliente);
         }
         if (vendedor != null) {
            query.setParameter("vendedor", vendedor);
         }

         query.setParameter("status", statusPedidoSelecionados);

         return query.getResultList();

      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   public Long consultarUltimoNumeroPedido(EmpresaCliente empresa) {
      try {
         Long resultado;
         Query query = em.createQuery("select max(p.numeroPedidoExterno) from Pedido p"
               + " where p.pedidoExterno is false"
               + " and p.empresa = (:empresa)");
         query.setParameter("empresa", empresa);
         resultado = (Long) query.getSingleResult();
         return resultado != null ? resultado : 0;
      } catch (NoResultException nre) {
         return new Long(0);
      }
   }

   @Override
   public Pedido consultarPorNumeroPedido(EmpresaCliente empresa, Pedido pedido) {
      try {
         Query query = em.createQuery("select p from Pedido p " 
               + " left join fetch p.frete frete " 
               + " left join fetch p.pagamento pagamento "
               + " left join fetch p.itens itens " 
               + " where p = :pedido and p.empresa = (:empresa)");
         query.setParameter("pedido", pedido);
         query.setParameter("empresa", empresa);
         return (Pedido) query.getSingleResult();
      } catch (NoResultException nre) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Pedido> listarPedidosAFaturar(EmpresaCliente empresa, Long numero, Date dataInicio, Date dataFim, Pessoa cliente, Vendedor vendedor, EnumStatusPedido statusDoPedido) throws KyklosException {
      try {
         String sql = "select distinct(p) from Pedido p" 
               + " where p.empresa = :empresa" 
               + " and p.data between :dataInicio and :dataFim" 
               + " and p.status = :status";
         if(numero != null){
            sql += " and p = :pedido";
         }
         if (cliente != null) {
            sql += " and p.cliente = :cliente";
         }
         if (vendedor != null) {
            sql += " and p.vendedor = :vendedor";
         }
         sql += " order by p.data desc";

         Query query = em.createQuery(sql);
         
         query.setParameter("empresa", empresa);
         query.setParameter("dataInicio", dataInicio);
         query.setParameter("dataFim", dataFim);
         query.setParameter("status", statusDoPedido);
         if(numero != null){
            query.setParameter("pedido.id", numero);
         }
         if (cliente != null) {
            query.setParameter("cliente", cliente);
         }
         if (vendedor != null) {
            query.setParameter("vendedor", vendedor);
         }
         return query.getResultList();
      } catch (Exception e) {
         throw new KyklosException("Erro ao listar pedidos.");
      }
   }
}