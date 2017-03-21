package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Estoque extends Entidade implements Serializable{
   
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="estoque_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="estoque_seq", sequenceName="estoque_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @ManyToOne(optional=false)
   private PessoaEmpresa pessoaEmpresa;
   
   private boolean padrao;
   
   @Column(nullable=false)
   private String descricao;
   
   private String endereco;
   
   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public String getEndereco() {
      return endereco;
   }

   public void setEndereco(String endereco) {
      this.endereco = endereco;
   }

   public Long getId() {
      return id;
   }

   public boolean isPadrao() {
      return padrao;
   }

   public void setPadrao(boolean padrao) {
      this.padrao = padrao;
   }

   public PessoaEmpresa getPessoaEmpresa() {
      return pessoaEmpresa;
   }

   public void setPessoaEmpresa(PessoaEmpresa pessoaEmpresa) {
      this.pessoaEmpresa = pessoaEmpresa;
   }

   
}
