package br.com.ambientinformatica.kyklos.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumStatusPedido implements IEnum{

   ABERTO("Aberto"),
   FINALIZADO("Finalizado"),
   FATURADO("Faturado"),
   ENTREGA_AUTORIZADA("Entrega Autorizada"),
   ENTREGUE("Entregue"),
   CANCELADO("Cancelado");

   private final String descricao;

   private EnumStatusPedido(String descricao){
      this.descricao = descricao;
   }

   @Override
   public String getDescricao() {
      return descricao;
   }
}
