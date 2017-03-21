package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import br.com.ambientinformatica.util.Entidade;


@Entity
public class Cfop extends Entidade implements Comparable<Cfop>, Serializable{
   
   private static final long serialVersionUID = 1L;

   @Id
   private Integer cfop;
   
   @Column(length=500)
   private String descricao;
   
   @Column(length=1000)
   private String aplicacao;


   public Integer getCfop() {
      return cfop;
   }
   public void setCfop(Integer cfop) {
      this.cfop = cfop;
   }
   public String getDescricao() {
      return descricao;
   }
   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }
   public String getAplicacao() {
      return aplicacao;
   }
   public void setAplicacao(String aplicacao) {
      this.aplicacao = aplicacao;
   }
   
   @Transient
   public EnumTipoCfop getTipoCfop(){
      int inicioCfop = cfop / 1000;
      switch(inicioCfop){
      case 1:
         return EnumTipoCfop.ENTRADAS_DO_ESTADO;
      case 2:
         return EnumTipoCfop.ENTRADAS_DE_OUTROS_ESTADOS;
      case 3:
         return EnumTipoCfop.ENTRADAS_DO_EXTERIOR;
      case 5:
         return EnumTipoCfop.SAIDAS_PARA_O_ESTADO;
      case 6:
         return EnumTipoCfop.SAIDAS_PARA_OUTROS_ESTADOS;
      case 7:
         return EnumTipoCfop.SAIDAS_PARA_O_EXTERIOR;
      default:
         return null;
      }
   }
   
   @Transient
   public boolean isCompra(){
      if(cfop == null){
         return false;
      }
      return cfop < 4000;
   }

   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((cfop == null) ? 0 : cfop.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Cfop other = (Cfop) obj;
      if (cfop == null) {
         if (other.cfop != null)
            return false;
      } else if (!cfop.equals(other.cfop))
         return false;
      return true;
   }

   public int compareTo(Cfop o) {
      try {
         return cfop - o.cfop;
      } catch (Exception e) {
         return 0;
      }
   }

   public String toString() {
      return String.valueOf(cfop);
   }

   @Override
   public Object getId() {
      return null;
   }

   
}
