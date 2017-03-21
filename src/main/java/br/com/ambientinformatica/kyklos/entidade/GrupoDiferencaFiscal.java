package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class GrupoDiferencaFiscal extends Entidade implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "grupodiferencafiscal_seq")
   @SequenceGenerator(name="grupodiferencafiscal_seq", sequenceName = "grupodiferencafiscal_seq", allocationSize=1, initialValue=1)
   private Integer id;
   
   private String descricao;

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public Integer getId() {
      return id;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((descricao == null) ? 0 : descricao.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
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
      GrupoDiferencaFiscal other = (GrupoDiferencaFiscal) obj;
      if (descricao == null) {
         if (other.descricao != null)
            return false;
      } else if (!descricao.equals(other.descricao))
         return false;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "GrupoDiferencaFiscal [descricao=" + descricao + ", id=" + id
            + "]";
   }

   
}
