package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.util.Entidade;

/**
 * O cheque é uma ordem de débito, em papel, do emitente para o Banco 
 * onde uma pessoa qualquer tem uma conta corrente para fazer um depósito 
 * à vista para pagar determinada quantia ao beneficiário. Um cheque é uma 
 * ordem de pagamento à vista expedida contra um banco sobre fundos 
 * depositados na conta do emitente.
 * 
 * 
 */
@Entity
public class Cheque extends Entidade implements Comparable<Cheque>, Serializable{

   private static final long serialVersionUID = 1L;

   public Cheque(){
   }
   
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "cheque_seq")
   @SequenceGenerator(name="cheque_seq", sequenceName = "cheque_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @Temporal(TemporalType.DATE)
   private Date dataDocumento = new Date();
   
   @Temporal(TemporalType.DATE)
   private Date dataBomPara;

   @Temporal(TemporalType.DATE)
   private Date dataCheque = new Date();

   @Column(precision=10,scale=2)
   private BigDecimal valor = BigDecimal.valueOf(0);
   
   @ManyToOne
   private Banco banco;

   private Long numero;
   
   private String cpfCnpjEmitente;
   
   private String agencia;
   
   private String contaCorrente;
   
   private String nomeEmitente;
   
   @ManyToOne(optional=false)
   private EmpresaCliente empresa;
   
   @ManyToOne
   private ContaCorrente contaCustodia;
   
   /**
    * Status do cheque, se está devolvido, compensado, cedito, etc.
    */
   @Enumerated(EnumType.STRING)
   private EnumStatusCheque status = EnumStatusCheque.CUSTODIA_PROPRIA;
   
   /**
    * Conta corrente na qual o cheque foi depositado 
    */
   @ManyToOne
   private ContaCorrente contaDeposito = new ContaCorrente();
   
   private Date dataDeposito;

   /**
    * Pessoa responsavel pelo cheque.
    * Ex.: O cheque é do Joao. A compra foi feita pelo Marcelo e este utilizou o cheque
    * do joao para paga-la (cheque de terceiro). O responsavel é o Marcelo.
    */
   @ManyToOne
   private Pessoa responsavel;
   
   @ManyToOne
   private Pessoa cliente;
   
   /**
    * Indica se o cheque é de um terceiro e não do responsavel
    */
   private boolean deTerceiro;
   
   /**
    * Cria um cheque
    * @param caixa
    * @param responsavel
    * @param deTerceiro
    * @param centroCusto
    * @param contaEmpresa
    * @param bomPara
    * @throws CaixaException
    */
   public Cheque(EmpresaCliente empresa, Pessoa cliente, Pessoa responsavel, boolean deTerceiro, Date bomPara) throws KyklosException{
      this.responsavel = responsavel;
      this.deTerceiro = deTerceiro;
      this.dataBomPara = bomPara;
      this.empresa = empresa;
      this.cliente = cliente;
      
      if(responsavel == null){
         throw new KyklosException("Informe o responsável pelo cheque.");
      }
   }

   public Date getDataBomPara() {
		return dataBomPara;
	}

	public void setDataBomPara(Date dataBomPara) {
		this.dataBomPara = dataBomPara;
	}

	public Date getDataCheque() {
		return dataCheque;
	}

	public void setDataCheque(Date dataCheque) {
		this.dataCheque = dataCheque;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getCpfCnpjEmitente() {
		return cpfCnpjEmitente;
	}

	public void setCpfCnpjEmitente(String cpfCnpjEmitente) {
		this.cpfCnpjEmitente = cpfCnpjEmitente;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

   public String getContaCorrente() {
      return contaCorrente;
   }

   public void setContaCorrente(String contaCorrente) {
      this.contaCorrente = contaCorrente;
   }

   public String getNomeEmitente() {
		return nomeEmitente;
	}

	public void setNomeEmitente(String nomeEmitente) {
		this.nomeEmitente = nomeEmitente;
	}

	public ContaCorrente getContaCustodia() {
		return contaCustodia;
	}

	public void setContaCustodia(ContaCorrente contaCustodia) {
		this.contaCustodia = contaCustodia;
	}

	public EnumStatusCheque getStatus() {
		return status;
	}

	public void setStatus(EnumStatusCheque status) {
		this.status = status;
	}

	public ContaCorrente getContaDeposito() {
		return contaDeposito;
	}

	public void setContaDeposito(ContaCorrente contaDeposito) {
		this.contaDeposito = contaDeposito;
	}

	public Date getDataDeposito() {
		return dataDeposito;
	}

	public void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}

	public boolean isDeTerceiro() {
		return deTerceiro;
	}

	public void setDeTerceiro(boolean deTerceiro) {
		this.deTerceiro = deTerceiro;
	}

	public EmpresaCliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaCliente empresa) {
		this.empresa = empresa;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public Long getId() {
		return id;
	}

	@Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
      result = prime * result + ((cpfCnpjEmitente == null) ? 0 : cpfCnpjEmitente.hashCode());
      result = prime * result + ((dataBomPara == null) ? 0 : dataBomPara.hashCode());
      result = prime * result + ((dataDeposito == null) ? 0 : dataDeposito.hashCode());
      result = prime * result + ((dataDocumento == null) ? 0 : dataDocumento.hashCode());
      result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((nomeEmitente == null) ? 0 : nomeEmitente.hashCode());
      result = prime * result + ((numero == null) ? 0 : numero.hashCode());
      result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((valor == null) ? 0 : valor.hashCode());
      return result;
   }

   public String getDescricao(){
      return String.format("%s %s %s %s", getBanco() == null ? "" : getBanco().getCodigo(), getAgencia(), getContaCorrente(), getNumero()); 
   }

   @Override
   public String toString() {
      return "Cheque [id=" + id + ", responsavel=" + responsavel + "]";
   }

   @Override
   public int compareTo(Cheque o) {
      return getDataDeposito().compareTo(o.getDataDeposito());
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (!super.equals(obj))
         return false;
      if (getClass() != obj.getClass())
         return false;
      Cheque other = (Cheque) obj;
      if (cliente == null) {
         if (other.cliente != null)
            return false;
      } else if (!cliente.equals(other.cliente))
         return false;
      if (cpfCnpjEmitente == null) {
         if (other.cpfCnpjEmitente != null)
            return false;
      } else if (!cpfCnpjEmitente.equals(other.cpfCnpjEmitente))
         return false;
      if (dataBomPara == null) {
         if (other.dataBomPara != null)
            return false;
      } else if (!dataBomPara.equals(other.dataBomPara))
         return false;
      if (dataDeposito == null) {
         if (other.dataDeposito != null)
            return false;
      } else if (!dataDeposito.equals(other.dataDeposito))
         return false;
      if (dataDocumento == null) {
         if (other.dataDocumento != null)
            return false;
      } else if (!dataDocumento.equals(other.dataDocumento))
         return false;
      if (empresa == null) {
         if (other.empresa != null)
            return false;
      } else if (!empresa.equals(other.empresa))
         return false;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      if (nomeEmitente == null) {
         if (other.nomeEmitente != null)
            return false;
      } else if (!nomeEmitente.equals(other.nomeEmitente))
         return false;
      if (numero == null) {
         if (other.numero != null)
            return false;
      } else if (!numero.equals(other.numero))
         return false;
      if (responsavel == null) {
         if (other.responsavel != null)
            return false;
      } else if (!responsavel.equals(other.responsavel))
         return false;
      if (status != other.status)
         return false;
      if (valor == null) {
         if (other.valor != null)
            return false;
      } else if (!valor.equals(other.valor))
         return false;
      return true;
   }

}