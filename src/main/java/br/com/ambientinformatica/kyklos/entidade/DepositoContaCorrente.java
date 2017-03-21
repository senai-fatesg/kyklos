package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.util.Entidade;

/**
 * Entidade para gerenciar os depositos ou transferencias feitas para a conta da empresa pelo Cliente.
 * 
 */
@Entity
public class DepositoContaCorrente extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "depositocontacorrente_seq")
   @SequenceGenerator(name="depositocontacorrente_seq", sequenceName = "depositocontacorrente_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @ManyToOne
   private ContaCorrente contaCorrente = new ContaCorrente();
   
   @Temporal(TemporalType.DATE)
   private Date dataDeposito;
   
   private BigDecimal valor = BigDecimal.ZERO;
   
   /**
    * Descrição para o deposito ou transferência feita pelo cliente para a conta da empresa
    */
   private String descricao;

   public DepositoContaCorrente() {
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((contaCorrente == null) ? 0 : contaCorrente.hashCode());
      result = prime * result + ((dataDeposito == null) ? 0 : dataDeposito.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((valor == null) ? 0 : valor.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (!(obj instanceof DepositoContaCorrente)) return false;
      DepositoContaCorrente other = (DepositoContaCorrente) obj;
      if (contaCorrente == null) {
         if (other.contaCorrente != null) return false;
      } else if (!contaCorrente.equals(other.contaCorrente)) return false;
      if (dataDeposito == null) {
         if (other.dataDeposito != null) return false;
      } else if (!dataDeposito.equals(other.dataDeposito)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (valor == null) {
         if (other.valor != null) return false;
      } else if (!valor.equals(other.valor)) return false;
      return true;
   }

   public Long getId() {
      return id;
   }

   public ContaCorrente getContaCorrente() {
      return contaCorrente;
   }

   public void setContaCorrente(ContaCorrente contaCorrente) {
      this.contaCorrente = contaCorrente;
   }

   public Date getDataDeposito() {
      return dataDeposito;
   }

   public void setDataDeposito(Date dataDeposito) {
      this.dataDeposito = dataDeposito;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public BigDecimal getValor() {
      return valor;
   }

   public void setValor(BigDecimal valor) {
      this.valor = valor;
   }
}