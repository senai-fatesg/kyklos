package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.controlefacil.api.EnumFormaDePagamento;
import br.com.ambientinformatica.controlefacil.api.LancamentoDto;
import br.com.ambientinformatica.controlefacil.api.PessoaCliente;
import br.com.ambientinformatica.controlefacil.api.util.UtilLancamento;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.entidade.Banco;
import br.com.ambientinformatica.kyklos.entidade.Cheque;
import br.com.ambientinformatica.kyklos.entidade.ContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.ContratoBoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.DepositoContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.EnumStatusPedido;
import br.com.ambientinformatica.kyklos.entidade.EnumTipoRetornoTroco;
import br.com.ambientinformatica.kyklos.entidade.FormaPagamento;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.negocio.NotaFiscalEletronicaNeg;
import br.com.ambientinformatica.kyklos.persistencia.BancoDao;
import br.com.ambientinformatica.kyklos.persistencia.ContaCorrenteDao;
import br.com.ambientinformatica.kyklos.persistencia.ContratoBoletoBancarioDao;
import br.com.ambientinformatica.kyklos.persistencia.PagamentoDao;
import br.com.ambientinformatica.kyklos.persistencia.PedidoDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.VendedorDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.nfe.api.NfeRetDto;
import br.com.ambientinformatica.util.Data;

@Named("FaturamentoListaControl")
@Scope("conversation")
public class FaturamentoListaControl implements Serializable {

   private static final long serialVersionUID = 1L;

   @Autowired
   private PessoaDao pessoaDao;

   @Autowired
   private VendedorDao vendedorDao;

   @Autowired
   private NotaFiscalEletronicaNeg notaFiscalEletronicaNeg;

   @Autowired
   private PedidoDao pedidoDao;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private PagamentoDao pagamentoDao;

   @Autowired
   private BancoDao bancoDao;
   
   @Autowired
   private ContaCorrenteDao contaCorrenteDao;
   
   @Autowired
   private ContratoBoletoBancarioDao contratoBoletoBancarioDao;
   
   private Long numeroPedidoExterno;

   private Date dataInicial = new Data(new Date()).getOntem();

   private Date dataFinal = new Date();

   private Pessoa cliente;

   private Vendedor vendedor;
   
   private ContratoBoletoBancario contratoBoletoBancarioSelecionado;
   
   private boolean alterarTodos = false;

   private List<Pedido> pedidos = new ArrayList<Pedido>();
   
   private Pedido pedidoSelecionado;

   private Cheque chequeSelecionado = new Cheque();
   
   private DepositoContaCorrente depositoEmContaCorrenteSelecionado = new DepositoContaCorrente();

   private int tamanhoDaListaDePedidos;

   private BigDecimal troco = BigDecimal.ZERO;

   private BigDecimal valorPagoDinheiro;
   
   private ContratoBoletoBancario contratoBoletoBancario;
   
   private Boolean lancamentoEnviado = false;

   public void listarPedidos() {
      try {
         pedidos = pedidoDao.listarPedidosAFaturar(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), numeroPedidoExterno, dataInicial, dataFinal, cliente, vendedor, EnumStatusPedido.FINALIZADO);
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }
   
   public List<Banco> completeBanco(String nome) {
      return bancoDao.listarBancosPorNome(nome);
   }
   
   public List<ContaCorrente> completeContaCorrente(String numeroContaOuNomeBanco) {
      return contaCorrenteDao.listarContasCorrentePorNumeroOuNomeBanco(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), numeroContaOuNomeBanco);
   }
   
   public List<ContratoBoletoBancario> completeContratoBoletoBancario(String numeroContaOuNomeBanco) {
      return contratoBoletoBancarioDao.listarContratoBoletoBancarioPorNumeroContaOuNomeBanco(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), numeroContaOuNomeBanco);
   }

   public String abrirPedido() {
      pedidoSelecionado = pedidoDao.consultarPorNumeroPedido(pedidoSelecionado.getEmpresa(), pedidoSelecionado);
      return "faturamento?faces-redirect=true";
   }

   public void selecionarPedido() {
      pedidoSelecionado = pedidoDao.consultarPorNumeroPedido(pedidoSelecionado.getEmpresa(), pedidoSelecionado);
   }
   
   public void criarNFe() {
      try {
         pedidoSelecionado = pedidoDao.consultarPorNumeroPedido(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), pedidoSelecionado);
         NfeRetDto nfe = notaFiscalEletronicaNeg.criarNotaFiscalEletronica(pedidoSelecionado, usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
         System.out.println("XML GERADO:");
         System.out.println(nfe.getXml());
         System.out.println("------------");
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces("Erro ao emitir a NF-e." + e.getMessage());
      }
   }

   @SuppressWarnings("unused")
   public void calcularTroco() {
      if (false) {
         troco = valorPagoDinheiro.subtract(pedidoSelecionado.getPagamento().getAPagarDinheiro());
         pedidoSelecionado.getPagamento().setValorPagoDinheiro(valorPagoDinheiro);
         pedidoSelecionado.getPagamento().setRetornoTroco(EnumTipoRetornoTroco.EM_DINHEIRO);
      }
   }

   public List<Pessoa> completeCliente(String nomeCnpj) {
      try {
         return pessoaDao.listarPessoasPorNomeOuCnpj(nomeCnpj);
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces(e.getMessage());
         return null;
      }
   }

   public List<Vendedor> completeVendedor(String cpfCnpjNome) {
      try {
         return vendedorDao.listarPorEmpresaENomeOuCpfCnpj(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), cpfCnpjNome);
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces(e.getMessage());
         return null;
      }
   }
   
   public void salvarPagamento() {
      for(FormaPagamento formaPagamento : pedidoSelecionado.getPagamento().getFormasPagamento()){
         BigDecimal zero = new BigDecimal(0);
         if (formaPagamento.isDinheiro() && troco.compareTo(zero) > 0) {
            pedidoSelecionado.getPagamento().setRetornoTroco(EnumTipoRetornoTroco.EM_DINHEIRO);
         }
      }
      pagamentoDao.alterar(pedidoSelecionado.getPagamento());
   }

   public void montarEEnviarObjetosLancamento(){

      List<LancamentoDto> listaLancamentoDto = new ArrayList<LancamentoDto>();

      for (FormaPagamento formaPagamento : pedidoSelecionado.getPagamento().getFormasPagamento()) {
         LancamentoDto lancamentoDto = new LancamentoDto();
         
         lancamentoDto.setCpfCnpjCliente(pedidoSelecionado.getEmpresa().getPessoa().getCpfCnpj());
         lancamentoDto.setNumeroDocumento(pedidoSelecionado.getId().toString());
         lancamentoDto.setPessoaCliente(new PessoaCliente());
         lancamentoDto.getPessoaCliente().setId(pedidoSelecionado.getCliente().getId());
         lancamentoDto.getPessoaCliente().setCep(pedidoSelecionado.getCliente().getCep());
         lancamentoDto.getPessoaCliente().setCodigoIbgeMunicipio(pedidoSelecionado.getCliente().getMunicipio().getCodigoIbge());
         lancamentoDto.getPessoaCliente().setCpfCnpj(pedidoSelecionado.getCliente().getCpfCnpj());
         lancamentoDto.getPessoaCliente().setNome(pedidoSelecionado.getCliente().getNome());
         lancamentoDto.getPessoaCliente().setTelefone(pedidoSelecionado.getCliente().getTelefone());
         lancamentoDto.getPessoaCliente().setEndereco(pedidoSelecionado.getCliente().getEnderecoCompleto());
         lancamentoDto.getPessoaCliente().setEmail(pedidoSelecionado.getCliente().getEmail());
         lancamentoDto.setContacategoria(41001);

         String descricao = "Pedido Nº: " + pedidoSelecionado.getId() + " | Cliente: " + pedidoSelecionado.getCliente().getNome();
         for(ItemPedido itemPedido : pedidoSelecionado.getItens()){
            descricao += " | Produto: ";
            descricao += itemPedido.getProduto().getDescricao();
            descricao += " - Quantidade: ";
            descricao += itemPedido.getQuantidade();
         }

         lancamentoDto.setDescricao(descricao);
         
         if(formaPagamento.isBoleto()){
            lancamentoDto.setFormaPagamento(EnumFormaDePagamento.BOLETOBANCARIO);
         }
         
         if(formaPagamento.isCartaoCredito()){
            lancamentoDto.setFormaPagamento(EnumFormaDePagamento.CARTAODECREDITO);
         }
         
         if(formaPagamento.isCartaoDebito()){
            lancamentoDto.setFormaPagamento(EnumFormaDePagamento.DEBITOEMCONTA);
         }
         
         if(formaPagamento.isCheque() || formaPagamento.isChequeTerceiros()){
            lancamentoDto.setFormaPagamento(EnumFormaDePagamento.CHEQUE);
         }
         
         if(formaPagamento.isDepositoEmConta()){
            lancamentoDto.setFormaPagamento(EnumFormaDePagamento.DEBITOEMCONTA);
         }
         
         if(formaPagamento.isDinheiro()){
            lancamentoDto.setFormaPagamento(EnumFormaDePagamento.DINHEIRO);
         }

         lancamentoDto.setDataVencimento(formaPagamento.getDataVencimento());
         lancamentoDto.setValor(formaPagamento.getValor());
         listaLancamentoDto.add(lancamentoDto);
      }

      for (LancamentoDto lancamento : listaLancamentoDto) {

         Response response = UtilLancamento.criarLancamentoPost("123456", "http://inpai.com.br:8180/controlefacil/", lancamento);
         
         if(response.getStatus() != new Integer("201")){

            lancamentoEnviado = false;
            
         } else {
            
            lancamentoEnviado = true;
            
         }
         
      }
      
      if(lancamentoEnviado == false) {

         UtilFaces.addMensagemFaces("Erro ao enviar Lançamento para o Financeiro! Tente novamente clicando em 'Reenviar Lançamento'");
         
      }

   }

   public void finalizarFaturamento() {
      salvarPagamento();
      criarNFe();
   }

   public void limparPedidoSelecionado() {
      pedidoSelecionado = new Pedido();
   }

   public List<Pedido> getPedidos() {
      return pedidos;
   }

   public int getTamanhoDaListaDePedidos() {
      this.tamanhoDaListaDePedidos = pedidos.size();
      return tamanhoDaListaDePedidos;
   }

   public Long getNumeroPedidoExterno() {
      return numeroPedidoExterno;
   }

   public void setNumeroPedidoExterno(Long numeroPedidoExterno) {
      this.numeroPedidoExterno = numeroPedidoExterno;
   }

   public Date getDataInicial() {
      return dataInicial;
   }

   public void setDataInicial(Date dataInicial) {
      this.dataInicial = dataInicial;
   }

   public Date getDataFinal() {
      return dataFinal;
   }

   public void setDataFinal(Date dataFinal) {
      this.dataFinal = dataFinal;
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

   public Pedido getPedidoSelecionado() {
      return pedidoSelecionado;
   }

   public void setPedidoSelecionado(Pedido pedidoSelecionado) {
      this.pedidoSelecionado = pedidoSelecionado;
   }

   public BigDecimal getTroco() {
      return troco;
   }

   public BigDecimal getValorPagoDinheiro() {
      return valorPagoDinheiro;
   }

   public void setValorPagoDinheiro(BigDecimal valorPagoDinheiro) {
      this.valorPagoDinheiro = valorPagoDinheiro;
   }

   public Cheque getChequeSelecionado() {
      if(this.chequeSelecionado == null){
         return new Cheque();
      }
      return chequeSelecionado;
   }

   public void setChequeSelecionado(Cheque chequeSelecionado) {
      this.chequeSelecionado = chequeSelecionado;
   }

   public DepositoContaCorrente getDepositoEmContaCorrenteSelecionado() {
      return depositoEmContaCorrenteSelecionado;
   }

   public void setDepositoEmContaCorrenteSelecionado(DepositoContaCorrente depositoEmContaCorrenteSelecionado) {
      this.depositoEmContaCorrenteSelecionado = depositoEmContaCorrenteSelecionado;
   }

   public ContratoBoletoBancario getContratoBoletoBancarioSelecionado() {
      return contratoBoletoBancarioSelecionado;
   }

   public void setContratoBoletoBancarioSelecionado(ContratoBoletoBancario contratoBoletoBancarioSelecionado) {
      this.contratoBoletoBancarioSelecionado = contratoBoletoBancarioSelecionado;
   }

   public boolean isAlterarTodos() {
      return alterarTodos;
   }

   public void setAlterarTodos(boolean alterarTodos) {
      this.alterarTodos = alterarTodos;
   }

   public ContratoBoletoBancario getContratoBoletoBancario() {
      return contratoBoletoBancario;
   }

   public void setContratoBoletoBancario(ContratoBoletoBancario contratoBoletoBancario) {
      this.contratoBoletoBancario = contratoBoletoBancario;
   }

   public Boolean getLancamentoEnviado() {
      return lancamentoEnviado;
   }

   public void setLancamentoEnviado(Boolean lancamentoEnviado) {
      this.lancamentoEnviado = lancamentoEnviado;
   }

}
