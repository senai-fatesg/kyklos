package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class ContaCorrente extends Entidade implements Serializable{
   
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "contacorrente_seq")
   @SequenceGenerator(name="contacorrente_seq", sequenceName = "contacorrente_seq", allocationSize=1, initialValue=1)
   private Long id;

   private String agencia;
   
   private String numeroConta;
   
   private Long contaContabil;

   @ManyToOne
   private Banco banco = new Banco();

   @ManyToOne(optional=false)
   private EmpresaCliente empresa = new EmpresaCliente();
   
   public ContaCorrente() {
   }
   
   /**
    * Retorna uma string contendo as informacoes da conta
    * @return
    */
   public String getDescricao(){
      return String.format("%s - %s - %s", banco.getCodigo(), agencia, numeroConta);
   }

   public String getDvAgencia(){
      if(agencia != null && agencia.indexOf("-") > 0){
         return agencia.substring(agencia.indexOf("-")+1);
      }else{
         return "";
      }
   }

   public String getDvNumeroConta(){
      if(numeroConta != null && numeroConta.indexOf("-") > 0){
         return numeroConta.substring(numeroConta.indexOf("-")+1);
      }else{
         return "";
      }
   }

   public String getAgenciaSemDv(){
      if(agencia != null && agencia.indexOf("-") > 0){
         return agencia.substring(0, agencia.indexOf("-"));
      }else{
         return agencia == null ? "" : agencia;
      }
   }

   public String getNumeroContaSemDv(){
      if(numeroConta != null && numeroConta.indexOf("-") > 0){
         return numeroConta.substring(0, numeroConta.indexOf("-"));
      }else{
         return "";
      }
   }

   public String toString() {
      return String.format("%s %s %s", banco.getNome(), agencia, numeroConta);
   }

   public String getAgencia() {
      return agencia;
   }
   
   public void setAgencia(String agencia) {
      this.agencia = agencia;
   }
   
   public String getNumeroConta() {
      return numeroConta;
   }
   
   public void setNumeroConta(String numeroConta) {
      this.numeroConta = numeroConta;
   }
   
   public Banco getBanco() {
      return banco;
   }
   
   public void setBanco(Banco banco) {
      this.banco = banco;
   }
   
   public Long getId() {
      return id;
   }

   public EmpresaCliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaCliente empresa) {
		this.empresa = empresa;
	}

	public Long getContaContabil() {
	   return contaContabil;
	}
	
	public void setContaContabil(Long contaContabil) {
	   this.contaContabil = contaContabil;
	}

	@Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
      result = prime * result + ((banco == null) ? 0 : banco.hashCode());
      result = prime * result
      + ((numeroConta == null) ? 0 : numeroConta.hashCode());
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
      ContaCorrente other = (ContaCorrente) obj;
      if (agencia == null) {
         if (other.agencia != null)
            return false;
      } else if (!agencia.equals(other.agencia))
         return false;
      if (banco == null) {
         if (other.banco != null)
            return false;
      } else if (!banco.equals(other.banco))
         return false;
      if (numeroConta == null) {
         if (other.numeroConta != null)
            return false;
      } else if (!numeroConta.equals(other.numeroConta))
         return false;
      return true;
   }

}