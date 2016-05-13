package br.com.ambientinformatica.kyklos.controle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.MovimentacaoEstoque;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.MovimentacaoEstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.ProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;
import br.com.ambientinformatica.kyklos.persistencia.ValorProdutoDao;

@Named("ProdutoControl")
@Scope("conversation")
public class ProdutoControl {

	@Autowired
	private UsuarioLogadoControl usuarioLogadoControl;

	@Autowired
	private ProdutoDao produtoDao;

	@Autowired
	private EstoqueProdutoDao estoqueProdutoDao;

	@Autowired
	private UnidadeMedidaDao unidadeMedidaDao;

	@Autowired
	PessoaEmpresaDao pessoaEmpresaDao;

	@Autowired
	private EstoqueDao estoqueDao;

	@Autowired
	private ValorProdutoDao valorProdutoDao;

	@Autowired
	private MovimentacaoEstoqueDao movimentacaoEstoqueDao;

	private Produto produto = new Produto();

	private EstoqueProduto estoqueProduto = new EstoqueProduto();

	private ValorProduto valorProduto = new ValorProduto();

	private List<Produto> listaProduto = new ArrayList<Produto>();

	private List<EstoqueProduto> listaEstoqueProduto = new ArrayList<EstoqueProduto>();

	private List<ValorProduto> listaValorProduto;

	private String codigoDescricaoBuscaProduto;

	public void editarEstoqueProduto() {
		try{
		   EstoqueProduto estoqueProdutoConsultado = estoqueProdutoDao.consultar(estoqueProduto.getId());
		   BigDecimal quantidadeAtual = estoqueProdutoConsultado.getQuantidade();
		   estoqueProdutoDao.alterar(estoqueProduto);
	   
   		MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
   		movimentacaoEstoque.setData(new Date());
   		movimentacaoEstoque.setEstoque(estoqueProduto.getEstoque());
   		movimentacaoEstoque.setProduto(produto);
         /*
          * Insere a quantidade na movimentação de acordo com o resultado da subtração do valor a ser alterado com o valor existente.
          */
   		movimentacaoEstoque.setQuantidade(estoqueProduto.getQuantidade().subtract(quantidadeAtual));
   		
   		movimentacaoEstoque.setUsuario(usuarioLogadoControl.getUsuario());
   		movimentacaoEstoqueDao.incluir(movimentacaoEstoque);
   		
   		UtilFaces.addMensagemFaces("Alterado com sucesso!");
		}catch(Exception e){
		   UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void selecionarProdutoParaEdicao(Produto produto) {
		try {
			/*
			 * Consulta o produto selecionado da lista.
			 */
		   this.produto = produtoDao.consultarPorCodigo(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), produto.getCodigo());
		   
		   /*
		    * Preenche a Lista de estoqueProduto pelo produto selecionado.
		    */
			listaEstoqueProduto = estoqueProdutoDao.listarPorProduto(this.produto);
			/*
			 * Seta no estoqueProduto caso só tenha um item na lista de Estoque Produto.
			 */
			if(listaEstoqueProduto.size() == 1){
			   estoqueProduto = listaEstoqueProduto.get(0);
			}
			
			/*
			 * Preenche a lista de valores do produto pelo produto consultado, possibilitando visualizar o historico dos valores alterados.
			 */
			listaValorProduto = valorProdutoDao.listarPorProduto(this.produto);
			/*
			 * Consulta valor Atual de um produto (Ultima versão do valor.)
			 */
			valorProduto = valorProdutoDao.consultarValorAtual(this.produto);
			
		} catch (PedidoException e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void editar() {
		try {
			boolean teveMudanca = false;
			Produto produtoConsultado = produtoDao.consultarPorCodigo(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), produto.getCodigo());
			
			if (!produtoConsultado.getDescricao().equals(produto.getDescricao()) || !produtoConsultado.getUnidadeMedida().equals(produto.getUnidadeMedida())) {
				produtoConsultado  = produtoDao.alterar(produto);
				teveMudanca = true;
			}

			ValorProduto valorConsultado = valorProdutoDao.consultarValorAtual(produtoConsultado);
			if (valorConsultado != null) {
				if (valorConsultado.getValorCompra().compareTo(valorProduto.getValorCompra()) != 0 || valorConsultado.getValorVenda().compareTo(valorProduto.getValorVenda()) != 0) {
				   valorConsultado = new ValorProduto();
				   valorConsultado.setProduto(produtoConsultado);
				   valorConsultado.setUsuario(usuarioLogadoControl.getUsuario());
				   valorConsultado.setValorCompra(valorProduto.getValorCompra());
				   valorConsultado.setValorVenda(valorProduto.getValorVenda());
					valorProdutoDao.incluir(valorConsultado);
					teveMudanca = true;
				}
			}
			if (teveMudanca == true) {
				UtilFaces.addMensagemFaces("Produto Alterado com Sucesso!");
			} else {
				UtilFaces.addMensagemFaces("Nenhuma mudança foi realizada.");
			}
			limpar();
		} catch (PedidoException e) {
			UtilFaces.addMensagemFaces("Erro ao Editar o Produto" + e.getMessage());
		}
	}

	public void limpar() {
		codigoDescricaoBuscaProduto = "";
		
		produto = new Produto();
		valorProduto = new ValorProduto();
		estoqueProduto = new EstoqueProduto();

		listaProduto = new ArrayList<Produto>();
		listaValorProduto = new ArrayList<ValorProduto>();
		listaEstoqueProduto = new ArrayList<EstoqueProduto>();
	}
	
	public List<UnidadeMedida> completeUnidadeDeMedida(String siglaOuDescricao) {
		return unidadeMedidaDao.listarUnidadesPorSiglaOuDescricao(siglaOuDescricao);
	}

	public void excluir(Produto produto){
	   try{
	      produtoDao.excluirPorId(produto.getId());
	      listarProdutos();
	      UtilFaces.addMensagemFaces("Produto Excluído com sucesso!");
	   }catch(Exception e){
	      UtilFaces.addMensagemFaces(e.getMessage());
	   }
	}
	
	public void salvar() {
		try {
			/*
			 *  Verifica se todos os campos para o cadastro de um novo produto
			 */
			if (!produto.getDescricao().isEmpty() && !produto.getCodigo().isEmpty() && produto.getUnidadeMedida() != null) {

				/*
				 *  Verifica se o produto existe.
				 */
				Produto produtoConsultado = produtoDao.consultarPorCodigo(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), produto.getCodigo());
				if (produtoConsultado == null) {

					/*
					 * Seta os atributos do produto que foram digitados pelo usuário no TabView de cadastro de Produto.
					 */
					produto.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());

					produtoDao.incluir(produto);

					/*
					 * Validações para cadastro do valor do produto.
					 */
					if (valorProduto.getValorCompra().compareTo(BigDecimal.ZERO) != 0 && valorProduto.getValorVenda().compareTo(BigDecimal.ZERO) != 0) {
						valorProduto.setProduto(produto);
						valorProduto.setUsuario(usuarioLogadoControl.getUsuario());
						valorProdutoDao.incluir(valorProduto);
					}

					/*
					 * Validação para o cadastro de estoqueProduto.
					 */
					if (estoqueProduto.getQuantidade().compareTo(BigDecimal.ZERO) != 0 && estoqueProduto.getEstoque() != null) {
						estoqueProduto.setData(new Date());
						estoqueProduto.setProduto(produto);
						estoqueProduto.setQuantidade(estoqueProduto.getQuantidade());
						estoqueProdutoDao.incluir(estoqueProduto);
					}
					limpar();
					UtilFaces.addMensagemFaces("Produto cadastrado com sucesso.");
				} else {
					limpar();
					throw new Exception("Já existe um produto com o código informado, consulte pelo código ou descrição e edite-o.");
				}
			} else {
				throw new Exception("Preencha o código, descrição e unidade de medida.");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public List<Estoque> completeEstoqueProduto(String descricao) {
		try {
			List<PessoaEmpresa> pessoas = pessoaEmpresaDao.consultarEmpresasVinculadas(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
			return estoqueDao.listarPorDescricaoEPessoa(descricao, pessoas);
		} catch (PedidoException e) {
			e.printStackTrace();
			return new ArrayList<Estoque>();
		}
	}

	public void listarProdutos() {
	   listaProduto = produtoDao.listarProdutos(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), codigoDescricaoBuscaProduto);
	}

	/*
	 * Método responsável por receber um produto passado por parâmetro ao expandir a linha do DataTable (listaProduto)
	 */
	public void listarEstoqueProduto(Produto produto) {
	   listaEstoqueProduto = estoqueProdutoDao.listarPorProduto(produto);
	   if(listaEstoqueProduto == null){
	      listaEstoqueProduto = new ArrayList<EstoqueProduto>();
	   }
	}
	
	public BigDecimal getQuantidadeTotal() {
		BigDecimal quantidadeTotal = BigDecimal.ZERO;
		if(listaEstoqueProduto != null){
			for (EstoqueProduto estoqueProduto : listaEstoqueProduto) {
				quantidadeTotal = quantidadeTotal.add(estoqueProduto.getQuantidade());
			}
		}
		return quantidadeTotal;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public EstoqueProduto getEstoqueProduto() {
		return estoqueProduto;
	}

	public void setEstoqueProduto(EstoqueProduto estoqueProduto) {
		this.estoqueProduto = estoqueProduto;
	}

	public ValorProduto getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(ValorProduto valorProduto) {
		this.valorProduto = valorProduto;
	}

	public String getCodigoDescricaoBuscaProduto() {
		return codigoDescricaoBuscaProduto;
	}

	public void setCodigoDescricaoBuscaProduto(String codigoDescricaoBuscaProduto) {
		this.codigoDescricaoBuscaProduto = codigoDescricaoBuscaProduto;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public List<EstoqueProduto> getListaEstoqueProduto() {
		return listaEstoqueProduto;
	}

	public List<ValorProduto> getListaValorProduto() {
		return listaValorProduto;
	}

}
