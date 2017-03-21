package br.com.ambientinformatica.kyklos.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumStatusCheque implements IEnum{

   /**
    * Estado quando o cheque está custodiado pela própria empresa.
    */
   CUSTODIA_PROPRIA("Custodiado local"),
   /**
    * Estado quando o cheque está custodiado pelo BANCO da empresa.
    */
   CUSTODIA_TERCERIZADA("Custodiado com terceiros"),
   /**
    * Estado quando o cheque está cedido a terceiros.
    * Ex.: O cheque foi utilizado para pagamento de contas, 
    * funcionários ou fornecedores
    */
   CEDIDO_TERCEIROS("Cedido a terceiros"),
   /**
    * Estado quando o cheque está depositado, ainda nao 
    * compensado.
    */
   DEPOSITADO("Depositado"),
   /**
    * Estado quando o cheque foi devolvido por qualquer outro motivo
    */
   DEVOLVIDO("Devolvido"),
   /**
    * Estado quando o cheque foi compensado.
    */
   COMPENSADO("Compensado"),
   /**
    * Estado quando o cheque foi descontado por um banco.
    */
   DESCONTADO("Descontado"),
   /**
    * Estado quando o cheque foi descontado por um banco e houve a compensação posterior
    */
   DESCONTADO_COMPENSADO("Compensado apos desconto"),
   /**
    * Estado quando o cheque foi quitado por algum acerto com o cliente.
    * Acerto externo, Lançamento manual, outro cheque para quitar este, dentre outras situações
    */
   QUITADO("Quitado");
   
   private final String descricao;

   private EnumStatusCheque(String descricao) {
      this.descricao = descricao;
   }

   public String getDescricao() {
      return descricao;
   }
   
}
