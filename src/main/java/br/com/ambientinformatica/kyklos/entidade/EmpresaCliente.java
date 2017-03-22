package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class EmpresaCliente extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "empresacliente_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "empresacliente_seq", sequenceName = "empresacliente_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	private Pessoa pessoa = new Pessoa();

	private Integer cnae;

	public Pessoa getPessoa(){
		return this.pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCnae() {
		return cnae;
	}

	public void setCnae(Integer cnae) {
		this.cnae = cnae;
	}

}
