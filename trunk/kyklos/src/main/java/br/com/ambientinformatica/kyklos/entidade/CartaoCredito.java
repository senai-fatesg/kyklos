package br.com.ambientinformatica.kyklos.entidade;

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

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class CartaoCredito extends Entidade{

   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "cartaocredito_seq")
   @SequenceGenerator(name="cartaocredito_seq", sequenceName = "cartaocredito_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @Temporal(TemporalType.DATE)
   private Date dataVencimento;

   @Temporal(TemporalType.DATE)
   private Date dataDocumento = new Date();

   @Column(precision=10, scale=2)
   private BigDecimal valor = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorDesconto = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorAcrescimo = BigDecimal.ZERO;
   
   private long numero;
   
   @ManyToOne
   private Pessoa cliente;

   @ManyToOne
   private EmpresaCliente empresa;
   
   /**
    * Conta corrente da empresa onde será creditado a Transação eletrônica 
    */
   @ManyToOne
   private ContaCorrente contaCartaoCredito;
   
   public CartaoCredito(){
   }
   
   public CartaoCredito(EmpresaCliente empresa, Pessoa cliente, Date dataVencimento) {
	   this.empresa = empresa;
	   this.cliente = cliente;
	   this.dataVencimento = dataVencimento;
   }

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public EmpresaCliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaCliente empresa) {
		this.empresa = empresa;
	}

	public Long getId() {
		return id;
	}

	@Override
   public String toString() {
      return "CartaoCredito [id=" + id + ", valor=" + valor + "]";
   }

}
