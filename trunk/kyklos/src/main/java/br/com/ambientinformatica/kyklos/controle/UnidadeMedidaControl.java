package br.com.ambientinformatica.kyklos.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNeg;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNegImpl;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;

@Named("UnidadeMedidaControl")
@Scope("conversation")
public class UnidadeMedidaControl {

	private static final long serialVersionUID = 1L;

	private UnidadeMedida unidadeMedida = new UnidadeMedida();

	private List<UnidadeMedida> listaUnidadesMedida = new ArrayList<UnidadeMedida>();

	@Autowired
	private UnidadeMedidaDao unidadeMedidaDao;

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private UnidadeMedidaNeg unidadeMedidaNeg;

	@Autowired
	private UnidadeMedidaNegImpl unidadeMedidaNegImpl;

	private UnidadeMedida unidadeSelecionada;

	private String descricaoOuSiglaConsulta = "";
	
	private String status = "ATIVO";

	@PostConstruct
	private void init() {
		descricaoOuSiglaConsulta = "";
		listarUnidadesDeMedida();
	}

	public void limpar() {
		unidadeMedida = new UnidadeMedida();
	}

	public void salvar() {
		try {
			unidadeMedida = unidadeMedidaDao.alterar(unidadeMedida);
			unidadeMedida = new UnidadeMedida();
			listarUnidadesDeMedida();
			UtilFaces.addMensagemFaces("Salvo com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Salvar.");
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void excluir(UnidadeMedida unidadeMedida) {
		try {
			unidadeMedidaDao.excluirPorId(unidadeMedida);
			listarUnidadesDeMedida();
			UtilFaces.addMensagemFaces("Registro Excluido com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Excluir.");
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public List<SelectItem> getSelectunidades() {
		List<UnidadeMedida> list = unidadeMedidaNegImpl.todas();
		List<SelectItem> itens = new ArrayList<SelectItem>(list.size());
		for (UnidadeMedida u : list) {
			itens.add(new SelectItem(u.getId(), u.getDescricao(), u.getSigla()));
		}
		return itens;
	}

	public void listarUnidadesDeMedida() {
		listaUnidadesMedida = unidadeMedidaDao
				.listarUnidadesPorSiglaOuDescricao(descricaoOuSiglaConsulta, status);
	}

	public int getTamanhoListaUnidadesMedida() {
		return listaUnidadesMedida.size();
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public UnidadeMedidaDao getUnidadeMedidaDao() {
		return unidadeMedidaDao;
	}

	public void setUnidadeMedidaDao(UnidadeMedidaDao unidadeMedidaDao) {
		this.unidadeMedidaDao = unidadeMedidaDao;
	}

	public UsuarioLogadoControl getUsuarioLogadoControl() {
		return usuarioLogadoControl;
	}

	public void setUsuarioLogadoControl(UsuarioLogadoControl usuarioLogadoControl) {
		this.usuarioLogadoControl = usuarioLogadoControl;
	}

	public UnidadeMedidaNeg getUnidadeMedidaNeg() {
		return unidadeMedidaNeg;
	}

	public void setUnidadeMedidaNeg(UnidadeMedidaNeg unidadeMedidaNeg) {
		this.unidadeMedidaNeg = unidadeMedidaNeg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UnidadeMedida getUnidadeSelecionada() {
		return unidadeSelecionada;
	}

	public void setUnidadeSelecionada(UnidadeMedida unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public UnidadeMedidaNegImpl getUnidadeMedidaNegImpl() {
		return unidadeMedidaNegImpl;
	}

	public void setUnidadeMedidaNegImpl(UnidadeMedidaNegImpl unidadeMedidaNegImpl) {
		this.unidadeMedidaNegImpl = unidadeMedidaNegImpl;
	}

	public List<UnidadeMedida> getListaUnidadesMedida() {
		return listaUnidadesMedida;
	}

	public String getDescricaoOuSiglaConsulta() {
		return descricaoOuSiglaConsulta;
	}

	public void setDescricaoOuSiglaConsulta(String descricaoOuSiglaConsulta) {
		this.descricaoOuSiglaConsulta = descricaoOuSiglaConsulta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setListaUnidadesMedida(List<UnidadeMedida> listaUnidadesMedida) {
		this.listaUnidadesMedida = listaUnidadesMedida;
	}
	

}
