package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Cor implements Comparable<Cor>, Serializable{

   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cor_seq")
   @SequenceGenerator(name = "cor_seq", sequenceName = "cor_seq", allocationSize = 1, initialValue = 1)
   private Integer id;
   
   private String codigo;
   
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

   public String getCodigo() {
      return codigo;
   }

   public void setCodigo(String codigo) {
      this.codigo = codigo;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? super.hashCode() : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj.getClass().equals(getClass())) {
         return obj.hashCode() == hashCode();
      } else {
         return false;
      }
   }

   @Override
   public int compareTo(Cor o) {
      try {
         return descricao.compareTo(o.descricao);
      } catch (Exception e) {
         return 0;
      }
   }

}
