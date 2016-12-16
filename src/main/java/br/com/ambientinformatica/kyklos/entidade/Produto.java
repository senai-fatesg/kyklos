package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.ambientinformatica.util.AmbientValidator;
import br.com.ambientinformatica.util.Entidade;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo",
		"estoque_id" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Produto extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "produto_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "produto_seq", sequenceName = "produto_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(nullable = false)
	private BigDecimal quantidade;

	@ManyToOne(optional = false)
	private Estoque estoque;

	@Column(nullable = false)
	@NotEmpty(message = "Código do produto obrigatório", groups = AmbientValidator.class)
	private String codigo;

	@Column(nullable = false)
	@NotEmpty(message = "Descrição do produto obrigatória", groups = AmbientValidator.class)
	private String descricao;

	@ManyToOne(optional = false)
	private UnidadeMedida unidadeMedida;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Integer getId() {
		return id;
	}

}
