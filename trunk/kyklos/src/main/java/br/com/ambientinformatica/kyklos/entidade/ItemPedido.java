package br.com.ambientinformatica.kyklos.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import br.com.ambientinformatica.util.Entidade;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class ItemPedido extends Entidade{

   @Id
   @GeneratedValue(generator="itempedido_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="itempedido_seq", sequenceName="itempedido_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @Column(nullable=false)
   private Integer numeroItem;

   @ManyToOne(optional = false)
   private Produto produto;

   @Column(length=500)
   private String observacao;

   @ManyToOne
   private Usuario usuario;

   @Column(nullable=false)
   private BigDecimal quantidade;

   @Column(nullable=false)
   private BigDecimal valorUnitario;
   
   @Version
   private Long versao;

   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }
   
   public Integer getNumeroItem() {
      return numeroItem;
   }

   public void setNumeroItem(Integer numeroItem) {
      this.numeroItem = numeroItem;
   }

   public BigDecimal getQuantidade() {
      return quantidade;
   }

   public void setQuantidade(BigDecimal quantidade) {
      this.quantidade = quantidade;
   }

   public BigDecimal getValorUnitario() {
      return valorUnitario;
   }

   public void setValorUnitario(BigDecimal valorUnitario) {
      this.valorUnitario = valorUnitario;
   }

   public Integer getId() {
      return id;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public String getObservacao() {
      return observacao;
   }

   public void setObservacao(String observacao) {
      this.observacao = observacao;
   }

   public BigDecimal getValorTotal() {
      if(quantidade != null && valorUnitario != null){
         return quantidade.multiply(valorUnitario);      
      }else{
         return null;
      }
   }

}
