package br.com.ambientinformatica.kyklos.entidade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;

@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"numero", "empresa_id"})
      })
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class Pedido {

   @Id
   @GeneratedValue(generator="pedido_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="pedido_seq", sequenceName="pedido_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @Column(nullable=false)
   private String numero;

   @Enumerated(EnumType.STRING)
   private EnumStatusPedido status = EnumStatusPedido.ABERTO;

   @Temporal(TemporalType.DATE)
   private Date data = new Date();

   @ManyToOne(optional=false)
   private EmpresaCliente empresa;

   @ManyToOne
   private EmpresaCliente empresaEmitente;

   @ManyToOne(optional=false)
   private Vendedor vendedor;

   @ManyToOne(optional=false)
   private Pessoa cliente;

   @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
   @JoinColumn(name="pedido_id")
   private List<ItemPedido> itens = new ArrayList<ItemPedido>();

   @ManyToOne(optional=false)
   private Frete frete;
   
   public Set<EstoqueProduto> getEstoques(){
      Set<EstoqueProduto> estoques = new HashSet<EstoqueProduto>();
      for (ItemPedido itemPedido : itens) {
         estoques.add(itemPedido.getEstoque());
      }
      return estoques;
   }

   public void addItem(ItemPedido item){
      itens.add(item);
   }

   public String getNumero() {
      return numero;
   }

   public void setNumero(String numero) {
      this.numero = numero;
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

   public Integer getId() {
      return id;
   }

   public EmpresaCliente getEmpresaEmitente() {
      return empresaEmitente;
   }

   public void setEmpresaEmitente(EmpresaCliente empresaEmitente) {
      this.empresaEmitente = empresaEmitente;
   }

   public Vendedor getVendedor() {
      return vendedor;
   }

   public void setVendedor(Vendedor vendedor) {
      this.vendedor = vendedor;
   }

   public BigDecimal getTotal() {
      BigDecimal subtotalPedido = new BigDecimal(0);
      for(ItemPedido item : itens){
         subtotalPedido = subtotalPedido.add(item.getValorTotal());
      }
      return subtotalPedido;
   }

	public Frete getFrete() {
		return frete;
	}

	public void setFrete(Frete frete) {
		this.frete = frete;
	}

}
