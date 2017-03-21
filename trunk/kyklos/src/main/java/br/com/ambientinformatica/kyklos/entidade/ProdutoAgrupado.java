package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class ProdutoAgrupado {

   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "produtoagrupado_seq")
   @SequenceGenerator(name="produtoagrupado_seq", sequenceName = "produtoagrupado_seq", allocationSize=1, initialValue=1)
   private int id;
   
   @ManyToOne
   private Produto produtoRaiz;
   
   @ManyToOne
   private Produto produtoAgrupado;
   
   @Column(precision=10,scale=2)
   private BigDecimal valorProduto = BigDecimal.valueOf(0);
   
   @Column(precision=10,scale=3)
   private BigDecimal quantidade = BigDecimal.valueOf(1);
   
   @Transient
   public BigDecimal getValorTotal(){
      return valorProduto.multiply(quantidade);
   }
   
   public Produto getProdutoRaiz() {
      return produtoRaiz;
   }
   public void setProdutoRaiz(Produto produtoRaiz) {
      this.produtoRaiz = produtoRaiz;
   }
   public Produto getProdutoAgrupado() {
      return produtoAgrupado;
   }
   public void setProdutoAgrupado(Produto produtoAgrupado) {
      this.produtoAgrupado = produtoAgrupado;
      this.valorProduto = produtoAgrupado.getValorVenda();
   }

   public BigDecimal getValorProduto() {
      return valorProduto;
   }
   public void setValorProduto(BigDecimal valorProduto) {
      this.valorProduto = valorProduto;
   }
   public int getId() {
      return id;
   }

   
   public BigDecimal getQuantidade() {
      return quantidade;
   }
   public void setQuantidade(BigDecimal quantidade) {
      this.quantidade = quantidade;
   }
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((produtoAgrupado == null) ? 0 : produtoAgrupado.hashCode());
      result = prime * result + ((produtoRaiz == null) ? 0 : produtoRaiz.hashCode());
      return result;
   }
   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      ProdutoAgrupado other = (ProdutoAgrupado) obj;
      if (produtoAgrupado == null) {
         if (other.produtoAgrupado != null)
            return false;
      } else if (!produtoAgrupado.equals(other.produtoAgrupado))
         return false;
      if (produtoRaiz == null) {
         if (other.produtoRaiz != null)
            return false;
      } else if (!produtoRaiz.equals(other.produtoRaiz))
         return false;
      return true;
   }
   
}
