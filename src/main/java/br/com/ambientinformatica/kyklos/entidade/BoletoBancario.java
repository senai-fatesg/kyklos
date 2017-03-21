package br.com.ambientinformatica.kyklos.entidade;

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
import br.com.ambientinformatica.util.Entidade;

@Entity
public class BoletoBancario extends Entidade{

   public BoletoBancario() {
   }
   
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "boleto_seq")
   @SequenceGenerator(name="boleto_seq", sequenceName = "boleto_seq", allocationSize=1, initialValue=1)
   private Long id;
   
   @Temporal(TemporalType.DATE)
   private Date dataVencimento;

   @Temporal(TemporalType.DATE)
   private Date dataProtesto;

   @Temporal(TemporalType.DATE)
   private Date dataDocumento = new Date();

   @Column(precision=10, scale=2)
   private BigDecimal valor = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorDesconto = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorAcrescimo = BigDecimal.ZERO;
   
   @Temporal(TemporalType.DATE)
   private Date dataProcessamento = new Date();
   
   private long nossoNumero;
   
   @ManyToOne
   private Pessoa cliente;
   
   @ManyToOne
   private Pessoa sacado;
   
   @ManyToOne(optional=false)
   private EmpresaCliente empresa;
   
   @Enumerated(EnumType.STRING)
   private EnumStatusBoleto status = EnumStatusBoleto.GERADO;
   
   @ManyToOne
   private ContratoBoletoBancario contratoBoletoBancario = new ContratoBoletoBancario();
   
   public BoletoBancario(EmpresaCliente empresa, Date dataVencimento, Pessoa cliente) {
	   this.empresa = empresa;
	   this.dataVencimento = dataVencimento;
	   this.cliente = cliente;
   }

	@Override
   public String toString() {
      return "BoletoBancario [id=" + id + ", nossoNumero=" + nossoNumero + "]";
   }
   
   /*
    * getters and setters
    */
   public Long getId() {
      return id;
   }

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataProtesto() {
		return dataProtesto;
	}

	public void setDataProtesto(Date dataProtesto) {
		this.dataProtesto = dataProtesto;
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

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public long getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(long nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Pessoa getSacado() {
		return sacado;
	}

	public void setSacado(Pessoa sacado) {
		this.sacado = sacado;
	}

	public EmpresaCliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaCliente empresa) {
		this.empresa = empresa;
	}

	public EnumStatusBoleto getStatus() {
		return status;
	}

	public void setStatus(EnumStatusBoleto status) {
		this.status = status;
	}

   public ContratoBoletoBancario getContratoBoletoBancario() {
      return contratoBoletoBancario;
   }

   public void setContratoBoletoBancario(ContratoBoletoBancario contratoBoletoBancario) {
      this.contratoBoletoBancario = contratoBoletoBancario;
   }
   
}
