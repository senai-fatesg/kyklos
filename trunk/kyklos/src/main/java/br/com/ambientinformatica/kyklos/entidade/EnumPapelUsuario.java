package br.com.ambientinformatica.kyklos.entidade;

public enum EnumPapelUsuario {

   ADMIN("Administrador"),
   
   USUARIO("usuario");
 
   private final String descricao;

   private EnumPapelUsuario(String descricao) {
      this.descricao = descricao;
   }

   public String getDescricao() {
      return descricao;
   }
   
}