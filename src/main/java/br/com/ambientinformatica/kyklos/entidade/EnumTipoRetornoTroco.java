package br.com.ambientinformatica.kyklos.entidade;
import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoRetornoTroco implements IEnum{
   
   EM_DINHEIRO("DINHEIRO"),
   GERAR_CREDITO("GERAR CRÉDITO");
   
   private final String descricao;
   
   private EnumTipoRetornoTroco(String descricao) {
      this.descricao = descricao;
   }

   public String getDescricao() {
      return descricao;
   }

}