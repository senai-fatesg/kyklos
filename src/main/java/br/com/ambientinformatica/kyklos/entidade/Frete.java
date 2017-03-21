package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Frete extends Entidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="frete_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="frete_seq", sequenceName="frete_seq", allocationSize=1, initialValue=1)
	private Integer id;
	
	@ManyToOne(optional=true)
	private Transportadora transportadora;
	
	@Enumerated(EnumType.STRING)
	private EnumTipoFrete fretePorContaDe;
	
	private BigDecimal quantidadeVolumes = BigDecimal.ZERO;

	private String especieTransportada;
	
	private String marcaVeiculo;
	
	@ManyToOne(optional=true)
	private Endereco enderecoEntrega;

	private BigDecimal pesoBruto = BigDecimal.ZERO;
	
	private BigDecimal pesoLiquido = BigDecimal.ZERO;
	
	private BigDecimal valorFrete = BigDecimal.ZERO;

	private BigDecimal prazoEntrega = BigDecimal.ZERO;

	@Override
	public Integer getId() {
		return id;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public EnumTipoFrete getFretePorContaDe() {
		return fretePorContaDe;
	}

	public void setFretePorContaDe(EnumTipoFrete fretePorContaDe) {
		this.fretePorContaDe = fretePorContaDe;
	}

	public BigDecimal getQuantidadeVolumes() {
		return quantidadeVolumes;
	}

	public void setQuantidadeVolumes(BigDecimal quantidadeVolumes) {
		this.quantidadeVolumes = quantidadeVolumes;
	}

	public String getEspecieTransportada() {
		return especieTransportada;
	}

	public void setEspecieTransportada(String especieTransportada) {
		this.especieTransportada = especieTransportada;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public String getMarcaVeiculo() {
		return marcaVeiculo;
	}

	public void setMarcaVeiculo(String marcaVeiculo) {
		this.marcaVeiculo = marcaVeiculo;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

   public BigDecimal getPrazoEntrega() {
      return prazoEntrega;
   }

   public void setPrazoEntrega(BigDecimal prazoEntrega) {
      this.prazoEntrega = prazoEntrega;
   }

   public Endereco getEnderecoEntrega() {
      return enderecoEntrega;
   }

   public void setEnderecoEntrega(Endereco enderecoEntrega) {
      this.enderecoEntrega = enderecoEntrega;
   }

}
