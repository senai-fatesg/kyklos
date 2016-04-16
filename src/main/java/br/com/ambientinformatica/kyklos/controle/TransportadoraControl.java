package br.com.ambientinformatica.kyklos.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.entidade.Endereco;
import br.com.ambientinformatica.kyklos.entidade.Transportadora;
import br.com.ambientinformatica.kyklos.negocio.CepNeg;
import br.com.ambientinformatica.kyklos.negocio.PessoaNeg;
import br.com.ambientinformatica.kyklos.persistencia.MunicipioDao;
import br.com.ambientinformatica.kyklos.persistencia.TransportadoraDao;

@Named("TransportadoraControl")
@Scope("conversation")
public class TransportadoraControl {

	private static final long serialVersionUID = 1L;

	private Transportadora transportadora = new Transportadora();

	private String cepTransportadora = new String();

	private Municipio municipioTransportadora = new Municipio();

	private Endereco enderecoTransportadora = new Endereco();

	private String cpfCnpjTransportadora = new String();

	private String buscaText = new String();

	private List<Transportadora> listaTransportadoras = new ArrayList<Transportadora>();

	private int activeIndex;

	@Autowired
	private TransportadoraDao transportadoraDao;

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private PessoaNeg pessoaNeg;

	@Autowired
	private CepNeg cepNeg;

	@Autowired
	private MunicipioDao municipioDao;

	public void criarTransportadora() {
		limpar();
	}


	public void limpar() {
		transportadora = new Transportadora();
		enderecoTransportadora = new Endereco();
		cpfCnpjTransportadora = new String();
		cepTransportadora = new String();
		transportadora.setCpfCnpj("");
		enderecoTransportadora.setCep("");
	}
	
	public void salvarTransportadora() {
		String cpfCnpjConsulta = transportadora.getCpfCnpj();
		Transportadora transportadoraConsultada = transportadoraDao.consultarTransportadoraPorCpfCnpj(cpfCnpjConsulta);
		if (transportadoraConsultada == null) {
			transportadoraDao.incluir(transportadora);
			UtilFaces.addMensagemFaces("Registro incluido com sucesso.");
		} else {
			transportadora.setId(transportadoraConsultada.getId());
			transportadora = transportadoraDao.alterar(transportadora);
			UtilFaces.addMensagemFaces("Registro alterado com sucesso.");
		}
		limpar();
	}

	public void listarTransportadoras() {
		try {
			enderecoTransportadora = new Endereco();
			listaTransportadoras = transportadoraDao
					.listarTransportadorasPorCpfCnpjRazaoSocial(buscaText);
			if (listaTransportadoras.isEmpty()) {
				UtilFaces.addMensagemFaces("Nenhum resultado encontrado!");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro de consulta");
		}
	}

	public void consultarCpfCnpj() {
		try {
			Pessoa pessoaDto = pessoaNeg.consultar(transportadora.getCpfCnpj());
			transportadora.setCpfCnpj(pessoaDto.getCpfCnpj());
			transportadora.setInscricaoEstadual(pessoaDto.getInscricaoEstadual());
			transportadora.setRazaoSocial(pessoaDto.getNome());
			transportadora.setFantasia(pessoaDto.getNomeFantasia());

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void consultarCep() {
		try {
			Cep cepDto = cepNeg.consultar(transportadora.getEndereco().getCep().replace(".", "").replace("-", ""));
			enderecoTransportadora.setAtivo(true);
			enderecoTransportadora.setBairro(cepDto.getBairro());
			enderecoTransportadora.setCep(cepDto.getCep());
			enderecoTransportadora.setLogradouro(cepDto.getLogradouro());
			enderecoTransportadora.setMunicipio(municipioDao.consultarPorCodigoIbge(cepDto.getCodigoIbge()));
			transportadora.setEndereco(enderecoTransportadora);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public int getTamanhoListaTransportadoras() {
		return listaTransportadoras.size();
	}

	public String getBuscaText() {
		return buscaText;
	}

	public void setBuscaText(String buscaText) {
		this.buscaText = buscaText;
	}

	public Endereco getEnderecoTransportadora() {
		return enderecoTransportadora;
	}

	public void setEnderecoTransportadora(Endereco enderecoTransportadora) {
		this.enderecoTransportadora = enderecoTransportadora;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public List<Transportadora> getListaTransportadoras() {
		return listaTransportadoras;
	}

	public void setListaTransportadoras(
			List<Transportadora> listaTransportadoras) {
		this.listaTransportadoras = listaTransportadoras;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public String getCepTransportadora() {
		return cepTransportadora;
	}

	public void setCepTransportadora(String cepTransportadora) {
		this.cepTransportadora = cepTransportadora;
	}

	public Municipio getMunicipioTransportadora() {
		return municipioTransportadora;
	}

	public void setMunicipioTransportadora(Municipio municipioTransportadora) {
		this.municipioTransportadora = municipioTransportadora;
	}

	public String getCpfCnpjTransportadora() {
		return cpfCnpjTransportadora;
	}

	public void setCpfCnpjTransportadora(String cpfCnpjTransportadora) {
		this.cpfCnpjTransportadora = cpfCnpjTransportadora;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
