package br.com.ambientinformatica.kyklos.dto;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;

@XmlRootElement
public class Cep {

   private String cep;
   
   private String bairro;

   private Integer codigoIbge;
   
   private String logradouro;
   
   private String municipio;
   
   private EnumUf uf;

   public String getCep() {
      return cep;
   }

   public void setCep(String cep) {
      this.cep = cep;
   }

   public String getBairro() {
      return bairro;
   }

   public void setBairro(String bairro) {
      this.bairro = bairro;
   }

   public Integer getCodigoIbge() {
      return codigoIbge;
   }

   public void setCodigoIbge(Integer codigoIbge) {
      this.codigoIbge = codigoIbge;
   }

   public String getLogradouro() {
      return logradouro;
   }

   public void setLogradouro(String logradouro) {
      this.logradouro = logradouro;
   }

   public String getMunicipio() {
      return municipio;
   }

   public void setMunicipio(String municipio) {
      this.municipio = municipio;
   }

   public EnumUf getUf() {
      return uf;
   }

   public void setUf(EnumUf uf) {
      this.uf = uf;
   }
   
   
}
