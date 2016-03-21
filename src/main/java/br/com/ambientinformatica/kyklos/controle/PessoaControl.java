package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.negocio.CepNeg;
import br.com.ambientinformatica.kyklos.negocio.PessoaNeg;
import br.com.ambientinformatica.kyklos.persistencia.MunicipioDao;
import br.com.ambientinformatica.kyklos.persistencia.ParametroDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;

@Named("PessoaControl")
@Scope("conversation")
public class PessoaControl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PessoaDao pessoaDao;

	@Autowired
	private PessoaEmpresaDao pessoaEmpresaDao;

	@Autowired
	private PessoaNeg pessoaNeg;

	@Autowired
	private CepNeg cepNeg;

	@Autowired
	private MunicipioDao municipioDao;

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private ParametroDao parametroDao;

	private List<Municipio> municipios = new ArrayList<Municipio>();

	private Pessoa pessoa = new Pessoa();

	private PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();

	private String cpfCnpj;

	private String uf;

	public void consultarCep() {
		try {
			Cep cepDto = cepNeg.consultar(pessoa.getCep());
			pessoa.setMunicipio(municipioDao.consultarPorCodigoIbge(cepDto
					.getCodigoIbge()));
			pessoa.setLogradouro(cepDto.getLogradouro());
			pessoa.setBairro(cepDto.getBairro());
		} catch (PedidoException e) {
			UtilFaces.addMensagemFaces("Erro ao consultar o CEP: ");
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public List<Municipio> completeMunicipio(String municipio) {
		return municipioDao.listarPorDescricao(municipio);
	}

	public void consultar() {
		try {
			pessoa = pessoaNeg.consultar(cpfCnpj);
			if (pessoa.getId() == null) {
				UtilFaces.addMensagemFaces("CPF/CNPJ não cadastrado.");

			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao consultar.");
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void limpar() {
		pessoa = new Pessoa();
		municipios = new ArrayList<Municipio>();
	}

	public void salvar(ActionEvent actionEvent) {
		try {
			pessoaNeg.salvar(pessoa, usuarioLogadoControl);
			pessoa = new Pessoa();
			UtilFaces.addMensagemFaces("Salvo com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Salvar.");
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public PessoaEmpresa getPessoaEmpresa() {
		return pessoaEmpresa;
	}

	public void setPessoaEmpresa(PessoaEmpresa pessoaEmpresa) {
		this.pessoaEmpresa = pessoaEmpresa;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<SelectItem> getUfs() {
		return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumUf.values()));
	}

}