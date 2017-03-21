package br.com.ambientinformatica.kyklos.entidade;
import br.com.ambientinformatica.util.IEnumRestritivo;

public enum EnumInstrumentoRecebimento implements IEnumRestritivo{
   
   DINHEIRO("Dinheiro", true, 0),
   CHEQUE_TERCEIROS("Cheque de Terceiros", true, 1),
   CHEQUE("Cheque do cliente", true, 2),
   BOLETO("Boleto Bancário", true, 3),
   EM_HAVER("Em Aberto", false, 4),
   DEPOSITO_EM_CONTA("Depósito em Conta", true, 5),
   CARTAO_CREDITO("Cartão Crédito", true, 6),
   CARTAO_DEBITO("Cartão Débito", true, 7),
   CREDITO_EM_CARTEIRA("Carteira", true, 8);

   private final String descricao;
   private final boolean mostrar;
   private final int ordem;

   private EnumInstrumentoRecebimento(String descricao, boolean mostrar, int ordem) {
      this.descricao = descricao;
      this.mostrar = mostrar;
      this.ordem = ordem;
   }

   public String getDescricao() {
      return descricao;
   }

   public boolean isMostrar() {
      return mostrar;
   }

   public int getOrdem() {
      return ordem;
   }
   
   
   
}