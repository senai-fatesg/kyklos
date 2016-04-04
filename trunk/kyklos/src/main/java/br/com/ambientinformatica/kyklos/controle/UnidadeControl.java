package br.com.ambientinformatica.kyklos.controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.negocio.UnidadeMedidaNeg;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;
import br.com.ambientinformatica.kyklos.util.FacesMessages;

@Named("unidadeControl")
@Scope("conversation")
public class UnidadeControl {

	@PostConstruct
	private void init() {
		sigla = "";
		filtrarPorSigla();
	}

	protected String filtroGlobal = "";

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	private UnidadeMedida unidade = new UnidadeMedida();

	private String sigla;

	private List<UnidadeMedida> unidades = new ArrayList<UnidadeMedida>();

	@Autowired
	private UnidadeMedidaDao unidadeDao;

	@Autowired
	private UnidadeMedidaNeg unidadeNeg;
	
	private FacesMessages messages;

	public void preparaIncluir() {
		unidade = new UnidadeMedida();
	}

	public void salvar() {
		try {
			unidadeNeg.salvar(unidade, usuarioLogadoControl);
		} catch (PedidoException e) {
			e.printStackTrace();
		}
		filtrarPorSigla();
		messages.info("Unidade salva co sucesso");
		RequestContext.getCurrentInstance().update(
		Arrays.asList("frm:msgs", "frm:membros-table"));
	}

	public void listar(ActionEvent evt) {
		try {
			unidades.clear();

			Integer id = new Integer(filtroGlobal);
			UnidadeMedida un = unidadeNeg.consultar(id);
			if (un != null) {
				unidades.add(un);
			} else {
				filtrarPorSigla();
			}
		} catch (NumberFormatException e) {
			filtrarPorSigla();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void filtrarPorSigla() {
		unidades = unidadeDao.listarUnidadesPorSigla(sigla);

	}
	

	public void preparaAlterar(ActionEvent evt) {
		try {
			unidade = (UnidadeMedida) evt.getComponent().getAttributes()
					.get("unidade");
			unidade = unidadeDao.consultar(unidade.getId());
			
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void prepararExcluir(ActionEvent evt) {
		try {
			unidade = (UnidadeMedida) evt.getComponent().getAttributes()
					.get("unidade");
			unidade = unidadeDao.consultar(unidade.getId());
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmarExcluir(ActionEvent evt) {
		try {
			unidade = unidadeDao.consultar(unidade.getId());
			unidadeNeg.excluir(unidade);
			unidade = null;
			listar(evt);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public Integer getTamanhoLista() {
		return this.unidades.size();
	}

	public UsuarioLogadoControl getUsuarioLogadoControl() {
		return usuarioLogadoControl;
	}

	public void setUsuarioLogadoControl(UsuarioLogadoControl usuarioLogadoControl) {
		this.usuarioLogadoControl = usuarioLogadoControl;
	}

	public UnidadeMedida getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeMedida unidade) {
		this.unidade = unidade;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla.toUpperCase();
	}

	public List<UnidadeMedida> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<UnidadeMedida> unidades) {
		this.unidades = unidades;
	}

	public UnidadeMedidaDao getUnidadeDao() {
		return unidadeDao;
	}

	public void setUnidadeDao(UnidadeMedidaDao unidadeDao) {
		this.unidadeDao = unidadeDao;
	}

	public UnidadeMedidaNeg getUnidadeNeg() {
		return unidadeNeg;
	}

	public void setUnidadeNeg(UnidadeMedidaNeg unidadeNeg) {
		this.unidadeNeg = unidadeNeg;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

}
