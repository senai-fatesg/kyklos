package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.com.ambientinformatica.util.Entidade;

@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"estoque_id", "produto_id"})
      })
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class EstoqueProduto extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="estoqueproduto_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="estoqueproduto_seq", sequenceName="estoqueproduto_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @ManyToOne(optional=false)
   private Estoque estoque;
   
   @ManyToOne(optional=false)
   private Produto produto;
   
   @Column(precision=10,scale=5, nullable=false)
   private BigDecimal quantidade = BigDecimal.ZERO;

   /**
    * Data da ultima modificação do estoque
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date data = new Date();
   
   /**
    * Método responsavel por somar a quantidade disponível no estoqueProduto com a quantidade passada.
    * @param quantidade a ser somada ao valor atual.   
    */
   public void somarQuantidade(BigDecimal quantidade){
      this.quantidade = this.quantidade.setScale(5, BigDecimal.ROUND_HALF_EVEN).add(quantidade.setScale(5, BigDecimal.ROUND_HALF_EVEN)).setScale(5, BigDecimal.ROUND_HALF_EVEN);
   }
   
   /**
    * Método responsavel por subtrair a quantidade disponível no estoqueProduto pela quantidade passada.
    * @param quantidade a ser subtraida ao valor atual.   
    */
   public void subtrairQuantidade(BigDecimal quantidade){
      if(quantidade.compareTo(this.quantidade) <= 0){
         this.quantidade = this.quantidade.setScale(5, BigDecimal.ROUND_HALF_EVEN).subtract(quantidade.setScale(5, BigDecimal.ROUND_HALF_EVEN)).setScale(5, BigDecimal.ROUND_HALF_EVEN);
      }
   }
   
   
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

   public Long getId() {
      return id;
   }

   public Date getData() {
      return data;
   }

   public void setData(Date data) {
      this.data = data;
   }

}
