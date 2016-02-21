package br.com.ambientinformatica.kyklos.entidade;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parametro {

   @Id
   private String chave;
   
   private String valor;

   public String getChave() {
      return chave;
   }

   public void setChave(String chave) {
      this.chave = chave;
   }

   public String getValor() {
      return valor;
   }

   public void setValor(String valor) {
      this.valor = valor;
   }
}
