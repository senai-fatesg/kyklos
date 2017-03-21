package br.com.ambientinformatica.kyklos.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DocumentoImportacao {

   @Id
   private String numero;
   
   @Temporal(TemporalType.DATE)
   private Date dataRegistro;
   
   private String localDesembaraco;
   
   private String ufDesembaraco;
   
   private Date dataDesembaraco;
   
   private String codigoExportador;
   
   @OneToMany(cascade=CascadeType.ALL)
   @JoinColumn(name="documentoimportacao_numero")
   private List<AdicaoImportacao> adicoes = new ArrayList<AdicaoImportacao>();

   public String getNumero() {
      return numero;
   }

   public void setNumero(String numero) {
      this.numero = numero;
   }

   public Date getDataRegistro() {
      return dataRegistro;
   }

   public void setDataRegistro(Date dataRegistro) {
      this.dataRegistro = dataRegistro;
   }

   public String getLocalDesembaraco() {
      return localDesembaraco;
   }

   public void setLocalDesembaraco(String localDesembaraco) {
      this.localDesembaraco = localDesembaraco;
   }

   public String getUfDesembaraco() {
      return ufDesembaraco;
   }

   public void setUfDesembaraco(String ufDesembaraco) {
      this.ufDesembaraco = ufDesembaraco;
   }

   public Date getDataDesembaraco() {
      return dataDesembaraco;
   }

   public void setDataDesembaraco(Date dataDesembaraco) {
      this.dataDesembaraco = dataDesembaraco;
   }

   public String getCodigoExportador() {
      return codigoExportador;
   }

   public void setCodigoExportador(String codigoExportador) {
      this.codigoExportador = codigoExportador;
   }

   public List<AdicaoImportacao> getAdicoes() {
      return adicoes;
   }

}
