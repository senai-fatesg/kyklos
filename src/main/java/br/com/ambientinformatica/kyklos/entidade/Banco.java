package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Banco extends Entidade implements Serializable{
   
   private static final long serialVersionUID = 1L;

   @Id
	private Integer codigo;
	
	private String nome;
	
	private String enderecoWeb;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome != null ? nome.toUpperCase() : nome; 
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

   public String getEnderecoWeb() {
      return enderecoWeb;
   }

   public void setEnderecoWeb(String enderecoWeb) {
      this.enderecoWeb = enderecoWeb;
   }

   @Override
   public Object getId() {
      return null;
   }


}