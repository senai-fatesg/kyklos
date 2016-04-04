package br.com.ambientinformatica.kyklos.controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNeg;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNegImpl;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;
import br.com.ambientinformatica.kyklos.util.FacesMessages;

@Named("unidadeTableControl")
@Scope("conversation")
public class UnidadeTableControl {

	private static final long serialVersionUID = 1L;

	private UnidadeMedida unidadeMedida = new UnidadeMedida();

	private List<UnidadeMedida> listaUnidadeMedida = new ArrayList<UnidadeMedida>();

	@Autowired
	private UnidadeMedidaDao unidadeMedidaDao;

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private UnidadeMedidaNeg unidadeMedidaNeg;

	@Autowired
	private UnidadeMedidaNegImpl unidadeMedidaNegImpl;

	private UnidadeMedida unidadeSelecionada;

	private UnidadeMedida unidadeEdicao = new UnidadeMedida();

	private List<UnidadeMedida> todasUnidades = new ArrayList<UnidadeMedida>();

	private String id;

	private String descricao;

	private String sigla;

	private int activeIndex;

	private String buscaText = new String();

	private FacesMessages messages;

	public void prepararNovoCadastro() {
		unidadeEdicao = new UnidadeMedida();
	}

	public void limpar() {
		unidadeMedida = new UnidadeMedida();
	}

	public void salvar(ActionEvent actionEvent) {
		try {
			unidadeMedidaNeg.salvar(unidadeMedida, usuarioLogadoControl);
			unidadeMedida = new UnidadeMedida();
			UtilFaces.addMensagemFaces("Salvo com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Salvar.");
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void salvar() {
		try {
			unidadeMedidaNeg.salvar(unidadeEdicao, usuarioLogadoControl);
		} catch (PedidoException e) {

			e.printStackTrace();
		}
		consultar();

		messages.info("Unidade salva co sucesso");

		RequestContext.getCurrentInstance().update(
				Arrays.asList("frm:msgs", "frm:membros-table"));
	}

	public void excluir() {
		unidadeMedidaNeg.excluir(unidadeSelecionada);
		unidadeSelecionada = null;
		consultar();
		messages.info("Unidade excluida com sucesso!");
	}

	public void criarUnidade() {
		activeIndex = 0;
		limpar();
	}

	public List<SelectItem> getSelectunidades() {
		List<UnidadeMedida> list = unidadeMedidaNegImpl.todas();
		List<SelectItem> itens = new ArrayList<SelectItem>(list.size());
		for (UnidadeMedida u : list) {
			itens.add(new SelectItem(u.getId(), u.getDescricao(), u.getSigla()));
		}
		return itens;
	}

	public void consultar() {
		// todasUnidades = unidadeMedidaNegImpl.todas();
		// listaUnidadeMedida = unidadeMedidaDao.listarPorDescricao(descricao);
		todasUnidades = unidadeMedidaDao.listarUnidadesPorSigla(sigla);
	}

	public void expanssionUnidade(UnidadeMedida unidade) {
		listaUnidadeMedida = unidadeMedidaDao.listar();
		if (listaUnidadeMedida == null) {
			listaUnidadeMedida = new ArrayList<UnidadeMedida>();
		}
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public List<UnidadeMedida> getUnidadeMedidas() {
		return listaUnidadeMedida;
	}

	public void setUnidadeMedidas(List<UnidadeMedida> unidadeMedidas) {
		this.listaUnidadeMedida = unidadeMedidas;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public String getBuscaText() {
		return buscaText;
	}

	public void setBuscaText(String buscaText) {
		this.buscaText = buscaText;
	}

	public List<UnidadeMedida> getTodasUnidades() {
		return todasUnidades;
	}

	public void setTodasUnidades(List<UnidadeMedida> todasUnidades) {
		this.todasUnidades = todasUnidades;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<UnidadeMedida> getListaUnidadeMedida() {
		return listaUnidadeMedida;
	}

	public void setListaUnidadeMedida(List<UnidadeMedida> listaUnidadeMedida) {
		this.listaUnidadeMedida = listaUnidadeMedida;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla.toUpperCase();
	}

	public UnidadeMedida getUnidadeEdicao() {
		return unidadeEdicao;
	}

	public void setUnidadeEdicao(UnidadeMedida unidadeEdicao) {
		this.unidadeEdicao = unidadeEdicao;
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

}
