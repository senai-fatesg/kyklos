package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class Endereco extends Entidade implements Comparable<Endereco>, Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="endereco_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="endereco_seq", sequenceName="endereco_seq", allocationSize=1, initialValue=1)
   private Long id;
	
	@Column(nullable=false)
	private String logradouro;
	
	@Column(nullable=false)
	private String complemento;
	
	@Column(nullable=false)
	private String numero;
	
	@Column(nullable=false)
	private String bairro;
	
	@Column(nullable=false)
	private String cep;
	
	private boolean ativo = true;
	
	@ManyToOne(optional=false)
	private Municipio municipio;
	
	@Column(nullable=false)
	private String descricaoEndereco;
        
	public String getEnderecoCompleto(){
		return String.format("%s , Nº: %s, %s, %s",  
				logradouro != null ? logradouro : "LOGRADOURO NAO CADASTRADO", 
				numero != null ? numero : "SN", 
				complemento != null ? complemento : "NAO CADASTRADO",
				bairro != null ? bairro : "BAIRRO NAO CADASTRADO").toUpperCase();
	}
	
	public String getEndereco(){
	   String munTxt = "";
	   if(municipio != null){
	      munTxt = municipio.getDescricao() + " - " + municipio.getUf();
	   }
	   String endTxt = String.format("%s %s %s CEP: %s - %s", getLogradouroComplemento(), getNumero(), getBairro(), getCep(), munTxt);
	   return endTxt.toUpperCase();
	}
	
	public String getLogradouroComplemento(){
	   return getLogradouro() + " "+ getComplemento();
	}

   public Long getId() {
		return id;
	}

	public String getLogradouro() {
		return logradouro != null ? logradouro.toUpperCase() : logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro != null ? bairro.toUpperCase() : bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep != null ? cep.toUpperCase() : cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

   public String getComplemento() {
      return complemento != null ? complemento.toUpperCase() : complemento;
   }

   public void setComplemento(String complemento) {
      this.complemento = complemento;
   }

   public String getNumero() {
      return numero != null ? numero.toUpperCase() : numero;
   }

   public void setNumero(String numero) {
      this.numero = numero;
   }

   public boolean isAtivo() {
      return ativo;
   }

   public void setAtivo(boolean ativo) {
      this.ativo = ativo;
   }

	public String getDescricaoEndereco() {
		return descricaoEndereco != null ? descricaoEndereco.toUpperCase() : descricaoEndereco;
	}

	public void setDescricaoEndereco(String descricaoEndereco) {
		this.descricaoEndereco = descricaoEndereco;
	}

	@Override
    public int compareTo(Endereco o) {
	  if(o != null && o.getId() != null && getId() != null){
	     return (int) (getId() - o.getId()); 
	  }else{
	     return 0;
	  }
    }	

   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? super.hashCode() : id.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if(obj != null && obj.getClass().equals(getClass())){
         return obj.hashCode() == hashCode();
      }else{
         return false;
      }
   }
}
