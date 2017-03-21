package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.Entidade;

@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"id", "empresa_id"})
      })
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class Pedido extends Entidade implements Comparable<Pedido>, Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="pedido_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="pedido_seq", sequenceName="pedido_seq", allocationSize=1, initialValue=1)
   private Long id;

   /**
    * numero Original do Pedido no sistema Externo em que o pedido foi gerado e importando aqui.
    */
   private Long numeroPedidoExterno;

   @ManyToOne(optional=false)
   private Cfop cfop;
   
   @Enumerated(EnumType.STRING)
   private EnumTipoImpressaoNFe tipoImpressao = EnumTipoImpressaoNFe.DANFE_NORMAL;
   
   @Enumerated(EnumType.STRING)
   private EnumStatusPedido status = EnumStatusPedido.ABERTO;

   @Temporal(TemporalType.DATE)
   private Date data = new Date();

   @ManyToOne(optional=false)
   private EmpresaCliente empresa;

   @ManyToOne
   private EmpresaCliente empresaGeradoraPedido;

   @ManyToOne(optional=false)
   private Vendedor vendedor;

   @ManyToOne(optional=false)
   private Pessoa cliente;

   @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
   @JoinColumn(name="pedido_id")
   private List<ItemPedido> itens = new ArrayList<ItemPedido>();

   @ManyToOne(optional=false)
   private Frete frete = new Frete();

   private boolean pedidoExterno = false;
   
   private boolean migrado = false;

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Pagamento pagamento = new Pagamento();

   public Set<EstoqueProduto> getEstoques(){
      Set<EstoqueProduto> estoques = new HashSet<EstoqueProduto>();
      for (ItemPedido itemPedido : itens) {
         estoques.add(itemPedido.getEstoque());
      }
      return estoques;
   }

   public BigDecimal getValorTotal() {
      BigDecimal subtotalPedido = new BigDecimal(0);
      for(ItemPedido item : itens){
         subtotalPedido = subtotalPedido.add(item.getValorTotal());
      }
      if(frete != null){
         subtotalPedido = subtotalPedido.add(frete.getValorFrete());
      }
      return subtotalPedido.setScale(5, BigDecimal.ROUND_HALF_EVEN);
   }
   
   public BigDecimal getSaldoRemanescentePedido(){
      BigDecimal saldoRemanescentePedido = new BigDecimal(0);
      if(getPagamento() != null){
         saldoRemanescentePedido = getValorTotal().subtract(getPagamento().getValorNegociado());
      }
      return saldoRemanescentePedido;
   }

   public void addItem(ItemPedido item){
      itens.add(item);
   }

   public void removerItem(ItemPedido item){
      itens.remove(item);
   }
   
   public Long getNumeroPedidoExterno() {
      return numeroPedidoExterno;
   }

   public void setNumeroPedidoExterno(Long numeroPedidoExterno) {
      this.numeroPedidoExterno = numeroPedidoExterno;
   }

   public Cfop getCfop() {
      return cfop;
   }

   public void setCfop(Cfop cfop) {
      this.cfop = cfop;
   }
   
   public Date getData() {
      return data;
   }

   public void setData(Date data) {
      this.data = data;
   }

   public EnumStatusPedido getStatus() {
      return status;
   }

   public void setStatus(EnumStatusPedido enumStatusPedido){
      this.status = enumStatusPedido;
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

   public List<ItemPedido> getItens() {
      return itens;
   }

   public Long getId() {
      return id;
   }

   public EmpresaCliente getEmpresaGeradoraPedido() {
		return empresaGeradoraPedido;
	}

	public void setEmpresaGeradoraPedido(EmpresaCliente empresaGeradoraPedido) {
		this.empresaGeradoraPedido = empresaGeradoraPedido;
	}

	public Vendedor getVendedor() {
      return vendedor;
   }

   public void setVendedor(Vendedor vendedor) {
      this.vendedor = vendedor;
   }

   public Frete getFrete() {
      return frete;
   }

   public void setFrete(Frete frete) {
      this.frete = frete;
   }

   public boolean isPedidoExterno() {
      return pedidoExterno;
   }

   public void setPedidoExterno(boolean pedidoExterno) {
      this.pedidoExterno = pedidoExterno;
   }

   public Pagamento getPagamento() {
      return pagamento;
   }

   public void setPagamento(Pagamento pagamento) {
      this.pagamento = pagamento;
   }

   @Override
   public int compareTo(Pedido o) {
      return getData().compareTo(o.getData());
   }

	public boolean isMigrado() {
		return migrado;
	}

	public void setMigrado(boolean migrado) {
		this.migrado = migrado;
	}

   public EnumTipoImpressaoNFe getTipoImpressao() {
      return tipoImpressao;
   }

   public void setTipoImpressao(EnumTipoImpressaoNFe tipoImpressao) {
      this.tipoImpressao = tipoImpressao;
   }

}