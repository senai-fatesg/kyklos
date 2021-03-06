package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class UnidadeMedida extends Entidade implements Serializable{
   
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="unidademedida_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="unidademedida_seq", sequenceName="unidademedida_seq", allocationSize=1, initialValue=1)
   private Integer id;
   
   @Column(unique=true, nullable=false)
   private String sigla;
   
   @Column(nullable=false)
   private String descricao;

   public String getSigla() {
      return sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public Integer getId() {
      return id;
   }
}
