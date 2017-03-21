package br.com.ambientinformatica.kyklos.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoImpressaoNFe implements IEnum {
   
   SEM_DANFE(0,"Sem geração de DANFE"),
   DANFE_NORMAL(1, "DANFE normal, Retrato");
//   2=DANFE normal, Paisagem;
//   3=DANFE Simplificado;
//   4=DANFE NFC-e;
//   5=DANFE NFC-e em mensagem eletrônica (o envio de mensagem eletrônica pode ser feita de forma simultânea com a impressão do DANFE; usar o tpImp=5

   private final String descricao;
   private final int codigoNfe;
   
         
   @Override
   public String getDescricao() {
      return descricao;
   }

   private EnumTipoImpressaoNFe(int codigoNfe, String descricao) {
   this.descricao = descricao;
   this.codigoNfe = codigoNfe;
}

   public int getCodigoNfe() {
      return codigoNfe;
   }
   
   
}
