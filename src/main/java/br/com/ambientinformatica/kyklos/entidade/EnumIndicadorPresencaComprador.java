package br.com.ambientinformatica.kyklos.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumIndicadorPresencaComprador implements IEnum{

   NAO_SE_APLICA(0, "Não se aplica", "0"),
   PRESENCIAL(1, "Operação Presencial", "1"),
   NAO_PRESENCIAL_INTERNET(2, "Operação não presencial, pela Internet", "2"),
   NAO_PRESENCIAL_TELEATENDIMENTO(3, "Operação não presencial, Teleatendimento", "3"),
   NFCE_EM_OPERACAO_ENTREGA_EM_DOMICILIO(4, "NFCe em operação, com entrega em domicilio", "4"),
   NAO_PRESENCIAL_OUTROS(9, "Operação não presencial, outros", "9");

   private final int codigoNfe;
   private final String descricao;
   private final String id;


   private EnumIndicadorPresencaComprador(int codigoNfe, String descricao, String id) {
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
