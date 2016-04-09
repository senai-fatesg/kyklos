package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.MovimentacaoEstoque;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.MovimentacaoEstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.ProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;
import br.com.ambientinformatica.kyklos.persistencia.ValorProdutoDao;

@Named("ProdutoControl")
@Scope("conversation")
public class ProdutoControl implements Serializable{

   private static final long serialVersionUID = 1L;

   private Produto produto = new Produto();

   private ValorProduto valorProduto = new ValorProduto();

   private ValorProduto valorProdutoConsultado = new ValorProduto();

   private List<Produto> produtos = new ArrayList<Produto>();

   private EstoqueProduto estoqueProduto = new EstoqueProduto();

   private Estoque estoque = new Estoque();

   private List<EstoqueProduto> estoquesProdutos = new ArrayList<EstoqueProduto>();

   private String buscaText;

   private UnidadeMedida unidadeMedida = new UnidadeMedida();

   private List<ValorProduto> alteracoesValorProduto = new ArrayList<ValorProduto>();

   private BigDecimal quantidade;

   private BigDecimal quantidadeTotal;

   private int activeIndex;

   @Autowired
   private MovimentacaoEstoqueDao movimentacaoEstoqueDao;

   @Autowired
   private ValorProdutoDao valorProdutoDao;

   @Autowired
   private ProdutoDao produtoDao;

   @Autowired
   private UnidadeMedidaDao unidadeMedidaDao;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private EstoqueProdutoDao estoqueProdutoDao;

   @Autowired
   private EstoqueDao estoqueDao;


   public void expanssionProduto(Produto produto) {
      estoquesProdutos = estoqueProdutoDao.listarPorProduto(produto);
      if(estoquesProdutos == null){
         estoquesProdutos = new ArrayList<EstoqueProduto>();
      }
   }

   public void alterarEstoqueProduto(){
      estoqueProduto = estoqueProdutoDao.consultarPorProdutoAndEstoque(produto, estoque);
      if(estoqueProduto == null){
         estoqueProduto = new EstoqueProduto();
         estoqueProduto.setProduto(produto);
         estoqueProduto.setEstoque(estoque);
      }
      estoqueProduto.setQuantidade(quantidade);
      estoqueProdutoDao.alterar(estoqueProduto);
      MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
      movimentacaoEstoque.setData(new Date());
      movimentacaoEstoque.setEstoque(estoque);
      movimentacaoEstoque.setProduto(produto);
      movimentacaoEstoque.setQuantidade(quantidade);
      movimentacaoEstoque.setUsuario(usuarioLogadoControl.getUsuario());
      movimentacaoEstoqueDao.incluir(movimentacaoEstoque);
      UtilFaces.addMensagemFaces("Alterado com sucesso!");
   }

   public void criarProduto(){
      activeIndex = 0;
      produto = new Produto();
      valorProdutoConsultado = new ValorProduto();
      alteracoesValorProduto = new ArrayList<ValorProduto>();
      quantidadeTotal = new BigDecimal(0);
      estoquesProdutos = new ArrayList<EstoqueProduto>();
      valorProduto = new ValorProduto();
      unidadeMedida = new UnidadeMedida();
      produto.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
   }

   public void editarProduto(Produto produto){
      try {
         activeIndex = 0;
         quantidadeTotal = new BigDecimal(0);
         produto = produtoDao.consultarPorCodigo(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), produto.getCodigo());
         estoquesProdutos = estoqueProdutoDao.listarPorProduto(produto);
         valorProdutoConsultado = valorProdutoDao.consultarValorAtual(produto);
         alteracoesValorProduto = valorProdutoDao.listarPorProduto(produto);
      } catch (PedidoException e) {
         UtilFaces.addMensagemFaces("Cadastro de produto sem valor!");
      }
   }

   public void limpar(){
      quantidade = null;
   }

   public void salvar(){
      try {
         Produto produtoConsultado = produtoDao.consultarPorCodigo(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), produto.getCodigo());
         valorProduto = new ValorProduto();
         if(produtoConsultado == null){
            produtoDao.incluir(produto);
            produtoConsultado = produto;
         }
         valorProduto.setProduto(produtoConsultado);
         valorProduto.setValorCompra(valorProdutoConsultado.getValorCompra());
         valorProduto.setValorVenda(valorProdutoConsultado.getValorVenda());
         valorProduto.setUsuario(usuarioLogadoControl.getUsuario());
         valorProdutoDao.incluir(valorProduto);
         if(estoque.getId() != null){
            estoqueProduto.setQuantidade(quantidade);
            estoqueProduto.setEstoque(estoque);
            estoqueProduto.setProduto(produtoConsultado);
            estoqueProduto.setData(new Date());
            estoqueProdutoDao.alterar(estoqueProduto);
         }
         MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
         movimentacaoEstoque.setData(new Date());
         movimentacaoEstoque.setEstoque(estoque);
         movimentacaoEstoque.setProduto(produtoConsultado);
         movimentacaoEstoque.setQuantidade(quantidade);
         movimentacaoEstoque.setUsuario(usuarioLogadoControl.getUsuario());
         movimentacaoEstoqueDao.incluir(movimentacaoEstoque);

         UtilFaces.addMensagemFaces("Produto cadastrado com sucesso!");
      } catch (Exception cve) {
         UtilFaces.addMensagemFaces("Este produto já esta cadastrado.");
      }
   }

   public void excluir(Produto prod){
      try {
         EstoqueProduto estoqueProdutoExcluir = estoqueProdutoDao.consultarPorProdutoAndEstoque(prod, estoque);
         estoqueProdutoDao.excluirPorId(estoqueProdutoExcluir);
         produtoDao.excluirPorId(prod.getId());
         produtos.remove(prod);
         UtilFaces.addMensagemFaces("(" + prod.getCodigo() + ") " + prod.getDescricao() + " excluído com sucesso!");
      } catch (PersistenciaException e) {
         UtilFaces.addMensagemFaces(e);
      }
   }

   public void trocarTab(int index) {
      this.activeIndex = index;
   }

   public List<Estoque> completeEstoqueProduto(String estoqueProduto) {
      return estoqueDao.listarPorEmpresaCliente(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
   }

   public void listarProduto(ActionEvent event){
      produtos = produtoDao.listarProdutos(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), buscaText);
   }

   public List<UnidadeMedida> listarUnidadeDeMedida() {
      return unidadeMedidaDao.consultarUnidadesAtivas();
   }

   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }

   public List<Produto> getProdutos() {
      return produtos;
   }

   public void setProdutos(List<Produto> produtos) {
      this.produtos = produtos;
   }

   public String getBuscaText() {
      return buscaText;
   }

   public void setBuscaText(String buscaText) {
      this.buscaText = buscaText;
   }

   public UnidadeMedida getUnidadeMedida() {
      return unidadeMedida;
   }

   public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
      this.unidadeMedida = unidadeMedida;
   }

   public ValorProduto getValorProduto() {
      return valorProduto;
   }

   public void setValorProduto(ValorProduto valorProduto) {
      this.valorProduto = valorProduto;
   }

   public List<EstoqueProduto> getEstoquesProdutos() {
      return estoquesProdutos;
   }

   public List<ValorProduto> getAlteracoesValorProduto() {
      return alteracoesValorProduto;
   }

   public void setAlteracoesValorProduto(ArrayList<ValorProduto> alteracoesValorProduto) {
      this.alteracoesValorProduto = alteracoesValorProduto;
   }

   public EstoqueProduto getEstoqueProduto() {
      return estoqueProduto;
   }

   public void setEstoqueProduto(EstoqueProduto estoqueProduto) {
      this.estoqueProduto = estoqueProduto;
   }

   public Estoque getEstoque() {
      return estoque;
   }

   public void setEstoque(Estoque estoque) {
      this.estoque = estoque;
   }

   public ValorProduto getValorProdutoConsultado() {
      return valorProdutoConsultado;
   }

   public void setValorProdutoConsultado(ValorProduto valorProdutoConsultado) {
      this.valorProdutoConsultado = valorProdutoConsultado;
   }

   public BigDecimal getQuantidade() {
      return quantidade;
   }

   public void setQuantidade(BigDecimal quantidade) {
      this.quantidade = quantidade;
   }

   public BigDecimal getQuantidadeTotal() {
      for(EstoqueProduto ep : estoquesProdutos){
         quantidadeTotal = quantidadeTotal.add(ep.getQuantidade());
      }
      return quantidadeTotal;
   }

   public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
      this.quantidadeTotal = quantidadeTotal;
   }

   public int getActiveIndex() {
      return activeIndex;
   }

   public void setActiveIndex(int activeIndex) {
      this.activeIndex = activeIndex;
   }

}
