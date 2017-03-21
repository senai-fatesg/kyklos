package br.com.ambientinformatica.kyklos.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoCfop implements IEnum{

   ENTRADAS_DO_ESTADO("ENTRADAS OU AQUISIÇÕES DE SERVIÇOS DO ESTADO"),
   ENTRADAS_DE_OUTROS_ESTADOS("ENTRADAS OU AQUISIÇÕES DE SERVIÇOS DE OUTROS ESTADOS"),
   ENTRADAS_DO_EXTERIOR("ENTRADAS OU AQUISIÇÕES DE SERVIÇOS DO EXTERIOR"),
   SAIDAS_PARA_O_ESTADO("SAÍDAS OU PRESTAÇÕES DE SERVIÇOS PARA O ESTADO"),
   SAIDAS_PARA_OUTROS_ESTADOS("SAÍDAS OU PRESTAÇÕES DE SERVIÇOS PARA OUTROS ESTADOS"),
   SAIDAS_PARA_O_EXTERIOR("SAÍDAS OU PRESTAÇÕES DE SERVIÇOS PARA O EXTERIOR");
   
   private final String descricao;

   private EnumTipoCfop(String descricao) {
      this.descricao = descricao;
   }

   public String getDescricao() {
      return descricao;
   }
   
}
