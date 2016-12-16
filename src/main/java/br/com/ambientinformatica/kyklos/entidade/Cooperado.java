package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;

@Entity
public class Cooperado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "empresacliente_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "empresacliente_seq", sequenceName = "empresacliente_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	private Pessoa pessoa = new Pessoa();

	@Column(unique = true)
	private String matricula;

	private Integer qtdPessoasRenda;

	private String nomePai;

	private String nomeMae;

	private String nacionalidade;

	private String carteiraDeTrabalho;

	private boolean estudante;

	private String motivoNaoEstudante;

	private boolean principalAtividadeProdutiva;

	private boolean aprovado;

	private boolean bolsaFamilia;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getQtdPessoasRenda() {
		return qtdPessoasRenda;
	}

	public void setQtdPessoasRenda(Integer qtdPessoasRenda) {
		this.qtdPessoasRenda = qtdPessoasRenda;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getCarteiraDeTrabalho() {
		return carteiraDeTrabalho;
	}

	public void setCarteiraDeTrabalho(String carteiraDeTrabalho) {
		this.carteiraDeTrabalho = carteiraDeTrabalho;
	}

	public boolean isEstudante() {
		return estudante;
	}

	public void setEstudante(boolean estudante) {
		this.estudante = estudante;
	}

	public String getMotivoNaoEstudante() {
		return motivoNaoEstudante;
	}

	public void setMotivoNaoEstudante(String motivoNaoEstudante) {
		this.motivoNaoEstudante = motivoNaoEstudante;
	}

	public boolean isPrincipalAtividadeProdutiva() {
		return principalAtividadeProdutiva;
	}

	public void setPrincipalAtividadeProdutiva(boolean principalAtividadeProdutiva) {
		this.principalAtividadeProdutiva = principalAtividadeProdutiva;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public boolean isBolsaFamilia() {
		return bolsaFamilia;
	}

	public void setBolsaFamilia(boolean bolsaFamilia) {
		this.bolsaFamilia = bolsaFamilia;
	}

	public Integer getId() {
		return id;
	}

}
