package br.com.ambientinformatica.kyklos.entidade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class ValorProduto {

   
   @Id
   @GeneratedValue(generator="valorproduto_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="valorproduto_seq", sequenceName="valorproduto_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @ManyToOne(optional=false)
   private Produto produto;
   
   @Column(nullable=false)
   private BigDecimal valorCompra;
   
   @Column(nullable=false)
   private BigDecimal valorVenda;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(nullable=false)
   private Date data = new Date();
   
   @ManyToOne(optional=false)
   private Usuario usuario;
   
   @Version
   private long versao;

   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }

   public BigDecimal getValorCompra() {
      return valorCompra;
   }

   public void setValorCompra(BigDecimal valorCompra) {
      this.valorCompra = valorCompra;
   }

   public BigDecimal getValorVenda() {
      return valorVenda;
   }

   public void setValorVenda(BigDecimal valorVenda) {
      this.valorVenda = valorVenda;
   }

   public Date getData() {
      return data;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public Long getId() {
      return id;
   }

   
}
