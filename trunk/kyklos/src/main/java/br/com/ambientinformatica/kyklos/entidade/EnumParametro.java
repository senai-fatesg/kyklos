package br.com.ambientinformatica.kyklos.entidade;

public enum EnumParametro {

   URL_GESTOR_NFE("urlGestorNfe"),
   
   USUARIO_GESTOR_NFE("usuarioGestorNfe"),
   
   SENHA_GESTOR_NFE("senhaGestorNfe"),
   
   URL_TOTH("urlToth");
   
   
   private final String descricao;
   
   private EnumParametro(String descricao) {
      this.descricao = descricao;
   }

   public String getDescricao() {
      return descricao;
   }
   
   
}
