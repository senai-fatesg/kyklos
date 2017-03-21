package br.com.ambientinformatica.kyklos.entidade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;

@Entity
public class NegociacaoPedido {

   @Id
   @GeneratedValue(generator="negociacaopedido_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="negociacaopedido_seq", sequenceName="negociacaopedido_seq", allocationSize=1, initialValue=1)
   private Long id;

   @ManyToOne
   private EmpresaCliente empresa;

   @ManyToOne
   private Usuario usuario;

   @ManyToOne
   private Pessoa cliente;

   @Temporal(TemporalType.TIMESTAMP)
   private Date data = new Date();

   @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
   @JoinColumn(name="negociacao_id")
   private List<Pedido> pedidos = new ArrayList<Pedido>();

   public void addPedido(Pedido pedido, EmpresaCliente empresaGeradoraPedido, Vendedor vendedor){
      pedido.setEmpresaGeradoraPedido(empresaGeradoraPedido);
      pedido.setVendedor(vendedor);
      pedido.setCliente(cliente);
      pedidos.add(pedido);
   }
   
   public void removerPedido(Pedido pedido){
      pedidos.remove(pedido);
   }

   public EmpresaCliente getEmpresa() {
      return empresa;
   }

   public void setEmpresa(EmpresaCliente empresa) {
      this.empresa = empresa;
   }

   public Pessoa getCliente() {
      return cliente;
   }

   public void setCliente(Pessoa cliente) {
      this.cliente = cliente;
   }

   public Date getData() {
      return data;
   }

   public void setData(Date data) {
      this.data = data;
   }

   public Long getId() {
      return id;
   }

   public List<Pedido> getPedidos() {
      return pedidos;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public BigDecimal getSaldoRemanescenteNegociacao(){
      BigDecimal saldoRemanescenteNegociacao = new BigDecimal(0);
      for(Pedido pedido : pedidos){
         saldoRemanescenteNegociacao = saldoRemanescenteNegociacao.add(pedido.getValorTotal().subtract(pedido.getPagamento().getValorNegociado()));
      }
      return saldoRemanescenteNegociacao;
   }

   public BigDecimal getValorTotalNegociado(){
      BigDecimal valorTotalNegociado = new BigDecimal(0);
      for(Pedido pedido : pedidos){
         if(pedido.getPagamento() != null){
            valorTotalNegociado = valorTotalNegociado.add(pedido.getPagamento().getValorNegociado());
         }
      }
      return valorTotalNegociado;
   }

   public BigDecimal getTotal(){
      BigDecimal totalNegociacao = new BigDecimal(0);
      for(Pedido pedido : pedidos){
         totalNegociacao = totalNegociacao.add(pedido.getValorTotal());
      }
      return totalNegociacao;
   }

}
