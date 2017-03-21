package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class InstrumentoRecebimento extends Entidade implements Comparable<InstrumentoRecebimento>, Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "instrumentorecebimento_seq")
   @SequenceGenerator(name="instrumentorecebimento_seq", sequenceName = "instrumentorecebimento_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   private String descricao;
   
   @Enumerated(EnumType.STRING)
   @Column(unique=true)
   private EnumInstrumentoRecebimento instrumentoRecebimento;

   public boolean isCheque(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CHEQUE 
            || getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CHEQUE_TERCEIROS;
   }
   
   public boolean isChequeProprio(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CHEQUE;
   }
   
   public boolean isCartaoCredito(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CARTAO_CREDITO;
   }
   
   public boolean isDepositoEmConta(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.DEPOSITO_EM_CONTA;
   }
   
   public boolean isDinheiro(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.DINHEIRO;
   }
   
   public boolean isChequeTerceiros(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CHEQUE_TERCEIROS;
   }
   
   public boolean isBoleto(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.BOLETO;
   }
   
   public boolean isEmCreditoCarteira(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CREDITO_EM_CARTEIRA;
   }
   
   public boolean isCartaoDebito(){
      return getInstrumentoRecebimento() == EnumInstrumentoRecebimento.CARTAO_DEBITO;
   }
   
   public String getDescricao() {
      return descricao;
   }
   
   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }
   
   public EnumInstrumentoRecebimento getInstrumentoRecebimento() {
      return instrumentoRecebimento;
   }
   
   public void setInstrumentoRecebimentoDisponivel(EnumInstrumentoRecebimento instrumentoRecebimento) {
      this.descricao = instrumentoRecebimento.getDescricao();
      this.instrumentoRecebimento = instrumentoRecebimento;
   }
   
   public Long getId() {
      return id;
   }
   
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((instrumentoRecebimento == null) ? super.hashCode() : instrumentoRecebimento.hashCode());
      return result;
   }
   public boolean equals(Object obj) {
      if(obj != null && obj.getClass().equals(getClass())){
         return obj.hashCode() == hashCode();
      }else{
         return false;
      }
   }

   public int compareTo(InstrumentoRecebimento o) {
      return instrumentoRecebimento.getOrdem() - o.instrumentoRecebimento.getOrdem();
   }
   
   
}