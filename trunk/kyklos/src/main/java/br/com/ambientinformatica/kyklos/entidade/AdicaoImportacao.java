package br.com.ambientinformatica.kyklos.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdicaoImportacao {

   @Id
   private Integer id;
   
   private String numeroAdicao;
   
   private Integer numeroSequencialItem;
   
   private String codigoFabricante;
   
   @Column(precision=10,scale=2)
   private BigDecimal valorDesconto = BigDecimal.ZERO;

   public String getNumeroAdicao() {
      return numeroAdicao;
   }

   public void setNumeroAdicao(String numeroAdicao) {
      this.numeroAdicao = numeroAdicao;
   }

   public Integer getNumeroSequencialItem() {
      return numeroSequencialItem;
   }

   public void setNumeroSequencialItem(Integer numeroSequencialItem) {
      this.numeroSequencialItem = numeroSequencialItem;
   }

   public String getCodigoFabricante() {
      return codigoFabricante;
   }

   public void setCodigoFabricante(String codigoFabricante) {
      this.codigoFabricante = codigoFabricante;
   }

   public BigDecimal getValorDesconto() {
      return valorDesconto;
   }

   public void setValorDesconto(BigDecimal valorDesconto) {
      this.valorDesconto = valorDesconto;
   }

   public Integer getId() {
      return id;
   }
   
}
