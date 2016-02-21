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

@Entity
public class MovimentacaoEstoque {
   
   @Id
   @GeneratedValue(generator="movimentacaoestoque_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="movimentacaoestoque_seq", sequenceName="movimentacaoestoque_seq", allocationSize=1, initialValue=1)
   private Integer id;
   
   @ManyToOne(optional=false)
   private Estoque estoque;

   @ManyToOne(optional=false)
   private Produto produto;
   
   @Column(nullable=false)
   private BigDecimal quantidade;
   
   @Column(nullable=false)
   private Date data;
   
   @ManyToOne(optional=false)
   private Usuario usuario;

   public Estoque getEstoque() {
      return estoque;
   }

   public void setEstoque(Estoque estoque) {
      this.estoque = estoque;
   }

   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }

   public BigDecimal getQuantidade() {
      return quantidade;
   }

   public void setQuantidade(BigDecimal quantidade) {
      this.quantidade = quantidade;
   }

   public Date getData() {
      return data;
   }

   public void setData(Date data) {
      this.data = data;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public Integer getId() {
      return id;
   }
}
