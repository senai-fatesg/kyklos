package br.com.ambientinformatica.kyklos.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNeg;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNegImpl;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;

@Named("unidadeMedidaControl")
@Scope("conversation")
public class UnidadeMedidaControl {

	private static final long serialVersionUID = 1L;

	private UnidadeMedida unidadeMedida = new UnidadeMedida();

	private List<UnidadeMedida> unidadeMedidas = new ArrayList<UnidadeMedida>();

	@Autowired
	private UnidadeMedidaDao unidadeMedidaDao;

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private UnidadeMedidaNeg unidadeMedidaNeg;

	@Autowired
	private UnidadeMedidaNegImpl unidadeMedidaNegImpl;

	private UnidadeMedida unidadeSelecionada;

	private List<UnidadeMedida> todasUnidades;

	private String id;

	private int activeIndex;

	private String buscaText = new String();

	// public void consultar() {
	// try {
	// unidadeMedida = unidadeMedidaNeg.consultar(id);
	// if (unidadeMedida.getId() == null) {
	// UtilFaces.addMensagemFaces("Unidade de Medida não cadastrado.");
	//
	// }
	// } catch (Exception e) {
	// UtilFaces.addMensagemFaces("Erro ao consultar.");
	// UtilFaces.addMensagemFaces(e.getMessage());
	// }
	// }

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
		todasUnidades = unidadeMedidaNegImpl.todas();
	}

	// public void listarUnidades() {
	// try {
	//
	// listaUnidades = unidadeMedidaDao.listarUnidadesPorSigla(buscaText);
	// if (listaUnidades.isEmpty()) {
	// UtilFaces.addMensagemFaces("Nenhum resultado encontrado!");
	// }
	// } catch (Exception e) {
	// UtilFaces.addMensagemFaces("Erro de consulta");
	// }
	// }

	public void expanssionUnidade(UnidadeMedida unidade) {
		unidadeMedidas = unidadeMedidaDao.listar();
		if (unidadeMedidas == null) {
			unidadeMedidas = new ArrayList<UnidadeMedida>();
		}
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public List<UnidadeMedida> getUnidadeMedidas() {
		return unidadeMedidas;
	}

	public void setUnidadeMedidas(List<UnidadeMedida> unidadeMedidas) {
		this.unidadeMedidas = unidadeMedidas;
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

}
