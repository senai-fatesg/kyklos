package br.com.ambientinformatica.kyklos.entidade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.util.Data;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class FormaPagamento extends Entidade implements Comparable<FormaPagamento>, Serializable{
   
   private static final long serialVersionUID = 1L;

   public FormaPagamento() {
      super();
   }

   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "formapagamento_seq")
   @SequenceGenerator(name = "formapagamento_seq", sequenceName = "formapagamento_seq", allocationSize = 1, initialValue = 1)
   private Long id;
   
   @ManyToOne
   private InstrumentoRecebimento instrumentoRecebimento;
   
   @Column(precision=10,scale=2)
   private BigDecimal valor = BigDecimal.valueOf(0);
   
   @Temporal(TemporalType.DATE)
   private Date dataVencimento = new Date();

   /**
    * Indica a forma de pagamento do pedido (Parte do pagamento ou o Todo)
    * 
    * @param instrumentoRecebimento
    * @param valor
    * @param dataVencimento
    * @throws ValidarException 
    */
   public FormaPagamento(InstrumentoRecebimento instrumentoRecebimento, BigDecimal valor, Date dataVencimento) throws KyklosException {
      this.instrumentoRecebimento = instrumentoRecebimento;
      if (valor != null) {
         this.valor = valor.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }
      
      boolean dataObrigatoria = instrumentoRecebimento.isBoleto() || instrumentoRecebimento.isCheque() || instrumentoRecebimento.isDepositoEmConta() || instrumentoRecebimento.isCartaoCredito();
      
      if(dataVencimento == null && dataObrigatoria){
         throw new KyklosException("A data da forma de pagamento é obrigatória para boletos, cheques, depósitos e cartões");
      }
      
      if(!dataObrigatoria){
         dataVencimento = new Date();
      }
      
      this.setDataVencimento(new Data(dataVencimento).getDataSemTempo()); 
   }
   
   /**
    * Calcula o prazo do pagamento em dias
    * @return o prazo do pagamento em dias
    */
   public int getPrazo(){
   	Data data = new Data();
      return data.calcularDiferencaEmDias(new Data(getDataVencimento())); 
   }
   
   public Long getId() {
      return id;
   }

   public InstrumentoRecebimento getInstrumentoRecebimento() {
      return instrumentoRecebimento;
   }
   
   public void setInstrumentoRecebimento(InstrumentoRecebimento instrumentoRecebimento) {
      this.instrumentoRecebimento = instrumentoRecebimento;
   }
   
   public BigDecimal getValor() {
      return valor;
   }
   
   public void setValor(BigDecimal valor) {
      this.valor = valor;
   }

   public boolean isCheque(){
      return instrumentoRecebimento.isCheque();
   }
   
   public boolean isCartaoCredito(){
      return instrumentoRecebimento.isCartaoCredito();
   }

   public boolean isDepositoEmConta(){
      return instrumentoRecebimento.isDepositoEmConta();
   }
   
   public boolean isDinheiro(){
      return instrumentoRecebimento.isDinheiro();
   }
   
   public boolean isChequeTerceiros(){
      return instrumentoRecebimento.isChequeTerceiros();
   }
   
   public boolean isBoleto(){
      return instrumentoRecebimento.isBoleto();
   }
   
   public boolean isEmCreditoCarteira(){
      return instrumentoRecebimento.isEmCreditoCarteira();
   }
   
   public boolean isCartaoDebito(){
      return instrumentoRecebimento.isCartaoDebito();
   }
   
   public Date getDataVencimento() {
      return dataVencimento;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((getDataVencimento() == null) ? 0 : getDataVencimento().hashCode());
      result = prime * result + ((instrumentoRecebimento == null) ? 0 : instrumentoRecebimento.hashCode());
      result = prime * result + ((valor == null) ? 0 : valor.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      FormaPagamento other = (FormaPagamento) obj;
      if (getDataVencimento() == null) {
         if (other.getDataVencimento() != null)
            return false;
      } else if (!getDataVencimento().equals(other.getDataVencimento()))
         return false;
      if (instrumentoRecebimento == null) {
         if (other.instrumentoRecebimento != null)
            return false;
      } else if (!instrumentoRecebimento.equals(other.instrumentoRecebimento))
         return false;
      if (valor == null) {
         if (other.valor != null)
            return false;
      } else if (!valor.equals(other.valor))
         return false;
      return true;
   }

   public int compareTo(FormaPagamento o) {
      return getDataVencimento().compareTo(o.getDataVencimento());
   }

   public void setDataVencimento(Date dataVencimento) {
      this.dataVencimento = dataVencimento;
   }
}