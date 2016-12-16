package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.entidade.EnumStatusPedido;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.persistencia.PedidoDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.ProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.ValorProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.VendedorDao;

@Named("NegociacaoPedidoControl")
@Scope("conversation")
public class PedidoControl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private PessoaEmpresaDao pessoaEmpresaDao;

	@Autowired
	private PedidoDao pedidoDao;

	@Autowired
	private ProdutoDao produtoDao;

	@Autowired
	private PessoaDao pessoaDao;

	@Autowired
	private VendedorDao vendedorDao;

	@Autowired
	private ValorProdutoDao valorProdutoDao;

	private ItemPedido item = new ItemPedido();

	private ValorProduto valorProduto = new ValorProduto();

	private EnumStatusPedido pedStatus = EnumStatusPedido.ABERTO;

	private Pedido pedido = new Pedido();

	private Produto produto;

	private Pessoa cliente = new Pessoa();

	private Vendedor vendedor = new Vendedor();

	private String buscaText;

	private Date dataInicial;

	private Date dataFinal;

	private List<Pedido> pedidos = new ArrayList<Pedido>();

	private List<ItemPedido> itens = new ArrayList<ItemPedido>();

	private List<Pedido> pedidosSelecionados = new ArrayList<Pedido>();

	private Pedido pedidoExcluir;

	private boolean modoEdicao;

	private boolean gridEstoqueExterno;

	private List<EnumStatusPedido> statusPedidoSelecionados = new ArrayList<EnumStatusPedido>();

	private List<String> transportadoras = new ArrayList<String>();

	private List<String> transportadorasSelecionadas = new ArrayList<String>();

	private List<String> formasDePagamentoSelecionadas = new ArrayList<String>();

	public void salvarPedido() {
		try {
			pedido.setCliente(cliente);
			pedido.setEmpresa(usuarioLogadoControl.getEmpresaUsuario()
					.getEmpresa());
			pedido.setEmpresaEmitente(usuarioLogadoControl.getEmpresaUsuario()
					.getEmpresa());
			pedido.setVendedor(vendedor);
			numerarItens();
			pedidoDao.incluir(pedido);
			UtilFaces.addMensagemFaces("Pedido salvo com sucesso");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void numerarItens() {
		int numero = 0;
		for (ItemPedido item : pedido.getItens()) {
			item.setNumeroItem(++numero);
		}
	}

//	public void selecionarProduto(SelectEvent event) throws PedidoException {
//		try {
//			modoEdicao = false;
//			item = new ItemPedido();
//			listaEstoque = new ArrayList<EstoqueProdutoDto>();
//			listaEstoqueExterno = new ArrayList<EstoqueProdutoDto>();
//			Produto produto = (Produto) event.getObject();
//			item.setProduto(produto);
//			listaEstoque = EstoqueProdutoDto
//					.converterEstoqueProduto(estoqueProdutoDao
//							.listarPorEmpresa(usuarioLogadoControl
//									.getEmpresaUsuario().getEmpresa(), produto));
//			for (EstoqueProdutoDto e : listaEstoque) {
//				if (e.getEstoque()
//						.getPessoaEmpresa()
//						.getPessoa()
//						.equals(usuarioLogadoControl.getEmpresaUsuario()
//								.getEmpresa().getPessoa())) {
//					gridEstoqueExterno = false;
//				} else {
//					listaEstoqueExterno.add(e);
//					gridEstoqueExterno = true;
//				}
//			}
//			listaEstoque.removeAll(listaEstoqueExterno);
//
//		} catch (PedidoException e) {
//			UtilFaces.addMensagemFaces(e);
//		}
//	}

	public void salvarCliente() {
		Pessoa clienteConsultado = pessoaDao.consultarPorCpfCnpj(cliente
				.getCpfCnpj());
		if (clienteConsultado == null) {
			pessoaDao.incluir(cliente);
			UtilFaces.addMensagemFaces("Cliente cadastrado com Sucesso!");
			UtilFaces.addMensagemFaces("Nome: " + cliente.getNome());
			UtilFaces.addMensagemFaces("CPF / CNPJ: " + cliente.getCpfCnpj());
		} else {
			UtilFaces.addMensagemFaces("Cadastro de cliente já existente!");
			UtilFaces.addMensagemFaces("Nome: " + clienteConsultado.getNome());
			UtilFaces.addMensagemFaces("CPF / CNPJ: "
					+ clienteConsultado.getCpfCnpj());
		}
		cliente = new Pessoa();
	}

	public void limparClienteCadastroRapido() {
		cliente = new Pessoa();
	}

	public List<SelectItem> getStatusPedido() {
		return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumStatusPedido
				.values()));
	}

	public List<Produto> completeProduto(String codigoDescricao) {
		return produtoDao.listarProdutos(usuarioLogadoControl
				.getEmpresaUsuario().getEmpresa(), codigoDescricao);
	}

	public List<Pessoa> completeCliente(String clienteNome) {
		return pessoaDao.listarPessoasPorNome(clienteNome);
	}

	public List<Vendedor> completeVendedor(String vendedorNome) {
		return vendedorDao.listarPorEmpresaENome(usuarioLogadoControl
				.getEmpresaUsuario().getEmpresa(), vendedorNome);
	}

//	public void adicionarProduto(ActionEvent event) throws PedidoException {
//		try {
//			//
//			// estoqueSelecionado.addAll(estoqueExternoSelecionado);
//			//
//			for (EstoqueProdutoDto estoque : estoqueSelecionado) {
//				ValorProduto valorProduto = valorProdutoDao
//						.consultarValorAtual(produto);
//				if (valorProduto != null) {
//					item.setValorUnitario(valorProduto.getValorVenda());
//				} else {
//					throw new PedidoException(
//							String.format(
//									"O produto %s não possui valor de venda cadastrado. Vá na tela de configuração do produto e cadastre.",
//									produto.getDescricao()));
//				}
//
//				for (EstoqueProduto estoqueDoPedido : pedido.getEstoques()) {
//					if (estoque
//							.getEstoque()
//							.getPessoaEmpresa()
//							.getEmpresa()
//							.getId()
//							.equals(estoqueDoPedido.getEstoque()
//									.getPessoaEmpresa().getEmpresa().getId())) {
//						item.setProduto(produto);
//						item.setEstoque(estoque.getEstoqueProduto());
//						item.setQuantidade(estoque.getQuantidadeSelecionada());
//						pedido.addItem(item);
//						item = new ItemPedido();
//					}
//				}
//				item.setProduto(produto);
//				item.setEstoque(estoque.getEstoqueProduto());
//				item.setQuantidade(estoque.getQuantidadeSelecionada());
//				pedido.addItem(item);
//				itens.add(item);
//				item = new ItemPedido();
//			}
//			produto = new Produto();
//			estoqueSelecionado = new ArrayList<EstoqueProdutoDto>();
//			estoqueExternoSelecionado = new ArrayList<EstoqueProdutoDto>();
//		} catch (Exception e) {
//			UtilFaces.addMensagemFaces(e);
//		}
//
//	}

	public void removerProduto() {
		try {
			Pedido pedidoTemp = null;
			if (pedido.getItens().contains(item)) {
				pedido.getItens().remove(item);

				if (pedido.getItens().size() == 0
						&& pedido.getStatus().equals(EnumStatusPedido.ABERTO)) {
					pedidoTemp = pedido;
				}
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listarPedidos(ActionEvent event) {
		pedidos = pedidoDao.listarPedidoPorAtributo(usuarioLogadoControl
				.getEmpresaUsuario().getEmpresa(), pedido.getNumero(),
				dataInicial, dataFinal, cliente, vendedor,
				statusPedidoSelecionados);
		dataInicial = new Date();
		dataFinal = new Date();
		cliente = new Pessoa();
		vendedor = new Vendedor();
		statusPedidoSelecionados = new ArrayList<EnumStatusPedido>();
	}

	public void editarProduto() {
		modoEdicao = true;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public EnumStatusPedido getPedStatus() {
		return pedStatus;
	}

	public void setPedStatus(EnumStatusPedido pedStatus) {
		this.pedStatus = pedStatus;
	}

	public Pedido getPedidoExcluir() {
		return pedidoExcluir;
	}

	public void setPedidoExcluir(Pedido pedidoExcluir) {
		this.pedidoExcluir = pedidoExcluir;
	}

	public ItemPedido getItem() {
		return item;
	}

	public void setItem(ItemPedido item) {
		this.item = item;
	}

	public ValorProduto getValorProduto() {
		return valorProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<String> getFormasDePagamentoSelecionadas() {
		return formasDePagamentoSelecionadas;
	}

	public void setFormasDePagamentoSelecionadas(
			List<String> formasDePagamentoSelecionadas) {
		this.formasDePagamentoSelecionadas = formasDePagamentoSelecionadas;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public boolean isGridEstoqueExterno() {
		return gridEstoqueExterno;
	}

	public void setGridEstoqueExterno(boolean gridEstoqueExterno) {
		this.gridEstoqueExterno = gridEstoqueExterno;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public String getBuscaText() {
		return buscaText;
	}

	public void setBuscaText(String buscaText) {
		this.buscaText = buscaText;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Pedido> getPedidosSelecionados() {
		return pedidosSelecionados;
	}

	public void setPedidosSelecionados(List<Pedido> pedidosSelecionados) {
		this.pedidosSelecionados = pedidosSelecionados;
	}

	public List<EnumStatusPedido> getStatusPedidoSelecionados() {
		return statusPedidoSelecionados;
	}

	public void setStatusPedidoSelecionados(
			List<EnumStatusPedido> statusPedidoSelecionados) {
		this.statusPedidoSelecionados = statusPedidoSelecionados;
	}

	public List<String> getTransportadoras() {
		return transportadoras;
	}

	public void setTransportadoras(List<String> transportadoras) {
		this.transportadoras = transportadoras;
	}

	public List<String> getTransportadorasSelecionadas() {
		return transportadorasSelecionadas;
	}

	public void setTransportadorasSelecionadas(
			List<String> transportadorasSelecionadas) {
		this.transportadorasSelecionadas = transportadorasSelecionadas;
	}

	public PedidoDao getPedidoDao() {
		return pedidoDao;
	}

	public void setPedidoDao(PedidoDao pedidoDao) {
		this.pedidoDao = pedidoDao;
	}

}
