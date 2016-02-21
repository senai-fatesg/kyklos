package br.com.ambientinformatica.kyklos.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoFrete implements IEnum{

   EMITENTE(0, "Emitente", "0"),
   DESTINATARIO(1, "Destinatário", "1"),
   TERCEIRO(2, "Por conta de terceiros", "2"),
   SEM_FRETE(9, "Sem frete", "9");

   private final int codigoNfe;
   private final String descricao;
   private final String id;


   private EnumTipoFrete(int codigoNfe, String descricao, String id) {
      this.codigoNfe = codigoNfe;
      this.descricao = descricao;
      this.id = id;
   }

   public int getCodigoNfe() {
      return codigoNfe;
   }

   public String getDescricao() {
      return descricao;
   }

   public String getId() {
      return id;
   }

}
