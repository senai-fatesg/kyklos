package br.com.ambientinformatica.kyklos.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PessoaDto {

   private String nome;

   private String nomeFantasia;

   private String cpfCnpj;

   private String inscricaoEstadual;
   
   private String inscricaoMunicipal;

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getNomeFantasia() {
      return nomeFantasia;
   }

   public void setNomeFantasia(String nomeFantasia) {
      this.nomeFantasia = nomeFantasia;
   }

   public String getCpfCnpj() {
      return cpfCnpj;
   }

   public void setCpfCnpj(String cpfCnpj) {
      this.cpfCnpj = cpfCnpj;
   }

   public String getInscricaoEstadual() {
      return inscricaoEstadual;
   }

   public void setInscricaoEstadual(String inscricaoEstadual) {
      this.inscricaoEstadual = inscricaoEstadual;
   }

   public String getInscricaoMunicipal() {
      return inscricaoMunicipal;
   }

   public void setInscricaoMunicipal(String inscricaoMunicipal) {
      this.inscricaoMunicipal = inscricaoMunicipal;
   }
   
   

}
