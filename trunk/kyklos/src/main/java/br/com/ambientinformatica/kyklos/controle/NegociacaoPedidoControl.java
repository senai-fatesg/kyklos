package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.dto.EstoqueProdutoDto;
import br.com.ambientinformatica.kyklos.entidade.BoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.CartaoCredito;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.entidade.Cheque;
import br.com.ambientinformatica.kyklos.entidade.ContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.ContratoBoletoBancario;
import br.com.ambientinformatica.kyklos.entidade.DepositoContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Endereco;
import br.com.ambientinformatica.kyklos.entidade.EnumInstrumentoRecebimento;
import br.com.ambientinformatica.kyklos.entidade.EnumStatusPedido;
import br.com.ambientinformatica.kyklos.entidade.EnumTipoFrete;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.FormaPagamento;
import br.com.ambientinformatica.kyklos.entidade.Frete;
import br.com.ambientinformatica.kyklos.entidade.InstrumentoRecebimento;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.MovimentacaoEstoque;
import br.com.ambientinformatica.kyklos.entidade.NegociacaoPedido;
import br.com.ambientinformatica.kyklos.entidade.Pagamento;
import br.com.ambientinformatica.kyklos.entidade.ParametroEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.Transportadora;
import br.com.ambientinformatica.kyklos.entidade.ValorProduto;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.negocio.CepNeg;
import br.com.ambientinformatica.kyklos.persistencia.CfopDao;
import br.com.ambientinformatica.kyklos.persistencia.ContaCorrenteDao;
import br.com.ambientinformatica.kyklos.persistencia.ContratoBoletoBancarioDao;
import br.com.ambientinformatica.kyklos.persistencia.EnderecoDao;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.FreteDao;
import br.com.ambientinformatica.kyklos.persistencia.InstrumentoRecebimentoDao;
import br.com.ambientinformatica.kyklos.persistencia.MovimentacaoEstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.MunicipioDao;
import br.com.ambientinformatica.kyklos.persistencia.NegociacaoPedidoDao;
import br.com.ambientinformatica.kyklos.persistencia.PagamentoDao;
import br.com.ambientinformatica.kyklos.persistencia.ParametroEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.PedidoDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.ProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.TransportadoraDao;
import br.com.ambientinformatica.kyklos.persistencia.ValorProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.VendedorDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.util.Data;
import br.com.ambientinformatica.util.UtilException;
import br.com.ambientinformatica.util.UtilLog;

@Named("NegociacaoPedidoControl")
@Scope("conversation")
public class NegociacaoPedidoControl implements Serializable {

   private static final long serialVersionUID = 1L;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private MovimentacaoEstoqueDao movimentacaoEstoqueDao;

   @Autowired
   private NegociacaoPedidoDao negociacaoPedidoDao;

   @Autowired
   private PessoaDao pessoaDao;
   
   @Autowired
   private CepNeg cepNeg;
   
   @Autowired
   private InstrumentoRecebimentoDao instrumentoRecebimentoDao;

   @Autowired
   private ContratoBoletoBancarioDao contratoBoletoBancarioDao;

   @Autowired
   private ContaCorrenteDao contaCorrenteDao;

   @Autowired
   private TransportadoraDao transportadoraDao;
   
   @Autowired
   private PagamentoDao pagamentoDao;

   @Autowired
   private EnderecoDao enderecoDao;

   @Autowired
   private MunicipioDao municipioDao;
   
   @Autowired
   private PedidoDao pedidoDao;

   @Autowired
   private FreteDao freteDao;

   @Autowired
   private PessoaEmpresaDao pessoaEmpresaDao;

   @Autowired
   private VendedorDao vendedorDao;

   @Autowired
   private EstoqueProdutoDao estoqueProdutoDao;

   @Autowired
   private ProdutoDao produtoDao;

   @Autowired
   private ValorProdutoDao valorProdutoDao;

   @Autowired
   private CfopDao cfopDao;

   @Autowired
   private ParametroEmpresaDao parametroEmpresaDao;
   
   private Cfop cfopPedido;
   
   private Frete frete = new Frete();
   
   private Endereco endereco = new Endereco();
   
   private Long numeroPedido;

   private EnumInstrumentoRecebimento formaPagamentoSelecionada;
   
   private FormaPagamento formaPagamento = new FormaPagamento();
   
   private Date dataMinimaVencimento = new Date();
   
   private String informacaoParcelamento = new String();
   
   private List<EstoqueProdutoDto> listaEstoquesProprios = new ArrayList<EstoqueProdutoDto>();

   private Pessoa cliente = new Pessoa();
   
   private List<EstoqueProdutoDto> listaEstoquesExternos = new ArrayList<EstoqueProdutoDto>();

   private List<EstoqueProdutoDto> listaEstoquesPropriosSelecionados = new ArrayList<EstoqueProdutoDto>();

   private List<EstoqueProdutoDto> listaEstoquesExternosSelecionados = new ArrayList<EstoqueProdutoDto>();

   private List<Transportadora> transportadoras = new ArrayList<Transportadora>();
   
   private NegociacaoPedido negociacaoPedido = new NegociacaoPedido();
   
   private NegociacaoPedido negociacaoSelecionada = new NegociacaoPedido();

   private Pedido pedido = new Pedido();

   private EnumTipoFrete tipoFreteSelecionado = EnumTipoFrete.SEM_FRETE;
   
   private Transportadora transportadoraSelecionada = new Transportadora();
   
   private ItemPedido itemPedido;

   private Vendedor vendedor = new Vendedor();

   private Produto produto = new Produto();


   @PostConstruct
   public void init() {
      
   }

	public void imprimirNegociacaoPdf() {
		String urlQRCode = criarStringQrCode(negociacaoSelecionada);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("urlQRCode", urlQRCode);
		try {
	      UtilFacesRelatorio.gerarRelatorioFaces("jasper/negociacao.jasper", negociacaoSelecionada.getPedidos(), parametros);
      } catch (UtilException e) {
      	UtilFaces.addMensagemFaces("Houve um erro ao Gerar o Relatório Solicitado.\n Msg:" + e.getMessage());
      	UtilLog.getLog().error(e);
      }
	}
   
	/**
	 * retorna uma String que será utilizada para a geração do QRCode da Negociação do Pedido.
	 * 
	 * @param negociacaoPedido
	 * @return
	 */
	public String criarStringQrCode(NegociacaoPedido negociacaoPedido){
		//TODO verificar como será a composição necessária da String para que seja gerado o QRCode. Ao ler o QRCode o sistema devera permitir que a Negociacao seja visualizada.
		
		return "";
	}
	
	public List<Pessoa> completeCliente(String nomeCpfCnpj) {
      try {
         return pessoaDao.listarPessoasPorNomeOuCnpj(nomeCpfCnpj);
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces("Cliente não encontrado.");
         return null;
      }
   }
   
   public List<Cfop> completeCfop(Integer numero) {
      return cfopDao.listarCfop(numero);
   }

   public List<Vendedor> completeVendedor(String nomeCpf) {
      try {
         return vendedorDao.listarPorEmpresaENomeOuCpfCnpj(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), nomeCpf);
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces("Vendedor não encontrado.");
         return null;
      }
   }

   public List<Produto> completeProduto(String codigoDescricao) {
      try {
         return produtoDao.listarProdutos(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), codigoDescricao);
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces("Vendedor não encontrado.");
         return null;
      }
   }

   public void removerItemDoPedido(ItemPedido item) {
      Pedido pedidoVazio = null;
      for (Pedido pedido : negociacaoPedido.getPedidos()) {
         if (pedido.getItens().contains(item)) {
            pedido.removerItem(item);

            MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
            movimentacao.setEstoque(item.getEstoque().getEstoque());
            movimentacao.setProduto(item.getEstoque().getProduto());
            movimentacao.setQuantidade(item.getQuantidade());
            movimentacao.setUsuario(usuarioLogadoControl.getUsuario());

            movimentacaoEstoqueDao.incluir(movimentacao);

            EstoqueProduto estoqueProduto = item.getEstoque();
            estoqueProduto.somarQuantidade(item.getQuantidade());

            estoqueProdutoDao.alterar(estoqueProduto);

            if (pedido.getItens().size() == 0) {
               pedidoVazio = pedido;
            }
         }
      }
      if (pedidoVazio != null) {
         negociacaoPedido.removerPedido(pedidoVazio);
      }
      negociacaoPedido = negociacaoPedidoDao.alterar(negociacaoPedido);
      UtilFaces.addMensagemFaces(item.getProduto().getDescricao() + " removido com sucesso.");
   }

   public void consultarCep(){
      try {
         Cep cepDto = cepNeg.consultar(endereco.getCep());
         endereco.setLogradouro(cepDto.getLogradouro());
         endereco.setBairro(cepDto.getBairro());
         endereco.setMunicipio(municipioDao.consultarPorCodigoIbge(cepDto.getCodigoIbge()));
         
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao consultar o CEP: ");
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }
   
   public boolean verificaPreenchimentoDoEndereco() {
      if (endereco.getLogradouro() != null && endereco.getCep() != null && endereco.getNumero() != null && endereco.getBairro() != null && endereco.getComplemento() != null) {
         return true;
      }
      return false;
   }
   
   public void adicionarFrete() {
      if (pedido != null && pedido.getId() != null) {
         if (!tipoFreteSelecionado.equals(EnumTipoFrete.SEM_FRETE)) {
            if (transportadoraSelecionada != null && verificaPreenchimentoDoEndereco()) {
               frete.setTransportadora(transportadoraSelecionada);
               frete.setFretePorContaDe(tipoFreteSelecionado);
               frete.setEnderecoEntrega(endereco);
               pedido.setFrete(frete);
               UtilFaces.addMensagemFaces("Frete Adicionado com sucesso.");
            } else {
               UtilFaces.addMensagemFaces("Para frete " + tipoFreteSelecionado.getDescricao() + ". Todos os campos são obrigatórios.");
            }
         } else {
            pedido.getFrete().setPrazoEntrega(BigDecimal.ZERO);
            pedido.getFrete().setPrazoEntrega(BigDecimal.ZERO);
            pedido.getFrete().setValorFrete(BigDecimal.ZERO);
            pedido.getFrete().setTransportadora(null);
            pedido.getFrete().setFretePorContaDe(tipoFreteSelecionado);
            freteDao.alterar(pedido.getFrete());
            UtilFaces.addMensagemFaces("Frete Adicionado com sucesso.");
         }
      } else {
         UtilFaces.addMensagemFaces("Selecione um pedido.");
      }
   }
   
   public String alterarNegociacaoPedido(FlowEvent flowEvent) {
      try {
         if (flowEvent.getOldStep().equals("formasDePagamento")) {
            salvarPagamento();
         }
         return flowEvent.getNewStep();
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e.getMessage());
         return flowEvent.getOldStep();
      }
   }
   
   public void salvarCliente() {
      try {
         
         Pessoa clienteConsultado = pessoaDao.consultarPorCpfCnpj(cliente.getCpfCnpj());
         Cep cepConsultado = cepNeg.consultar(cliente.getCep());
         
         if (clienteConsultado == null) {
            
            if(cepConsultado != null){
               cliente.setMunicipio(municipioDao.consultarPorCodigoIbge(cepConsultado.getCodigoIbge()));
               cliente.setLogradouro(cepConsultado.getLogradouro());
               cliente.setBairro(cepConsultado.getBairro());
            }
            
            pessoaDao.incluir(cliente);
            UtilFaces.addMensagemFaces("Cliente cadastrado com Sucesso!");
            UtilFaces.addMensagemFaces("Nome: " + cliente.getNome());
            UtilFaces.addMensagemFaces("CPF / CNPJ: " + cliente.getCpfCnpj());
         } else {
            UtilFaces.addMensagemFaces("Cadastro de cliente já existente!");
            UtilFaces.addMensagemFaces("Nome: " + clienteConsultado.getNome());
            UtilFaces.addMensagemFaces("CPF / CNPJ: " + clienteConsultado.getCpfCnpj());
         }
         
         cliente = new Pessoa();

      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces(e);
      }
   }
   
   public void salvarPagamento() throws Exception {
      try {
         for (Pedido pedido : negociacaoPedido.getPedidos()) {
            for (FormaPagamento formaPagamento : pedido.getPagamento().getFormasPagamento()) {
               InstrumentoRecebimento InstrumentoRecebimentoConsultado = instrumentoRecebimentoDao.consultarPorInstrumentoRecebimento(formaPagamento.getInstrumentoRecebimento().getInstrumentoRecebimento());
               if (InstrumentoRecebimentoConsultado == null) {
                  formaPagamento.setInstrumentoRecebimento(instrumentoRecebimentoDao.alterar(formaPagamento.getInstrumentoRecebimento()));
               } else {
                  formaPagamento.setInstrumentoRecebimento(InstrumentoRecebimentoConsultado);
               }
               pedido.getPagamento().addFormaPagamento(formaPagamento);

               if (formaPagamento.isBoleto()) {
                  BoletoBancario boleto = new BoletoBancario();
                  boleto.setCliente(pedido.getCliente());
                  boleto.setValor(formaPagamento.getValor());
                  boleto.setDataVencimento(formaPagamento.getDataVencimento());
                  boleto.setEmpresa(pedido.getEmpresa());
                  List<ContratoBoletoBancario> listaContratoBoletoBancario = contratoBoletoBancarioDao.listarContratoBoletoBancarioPorNumeroContaOuNomeBanco(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), "");
                  if(!listaContratoBoletoBancario.isEmpty()){
                     boleto.setContratoBoletoBancario(listaContratoBoletoBancario.get(0));
                  } else {
                     throw new Exception("Nenhum contrato de Boleto Bancário cadastrado!");
                  }
                  pedido.getPagamento().addBoletoBancario(boleto);
               }

               if (formaPagamento.isCheque() || formaPagamento.isChequeTerceiros()) {
                  Cheque cheque = new Cheque();
                  if(formaPagamento.isChequeTerceiros()){
                     cheque.setDeTerceiro(true);
                     cheque.setResponsavel(pedido.getCliente());
                  }
                  if(!formaPagamento.isChequeTerceiros()){
                     cheque.setNomeEmitente(pedido.getCliente().getNome());
                     cheque.setCpfCnpjEmitente(pedido.getCliente().getCpfCnpj());
                  }
                  cheque.setValor(formaPagamento.getValor());
                  cheque.setDataDeposito(formaPagamento.getDataVencimento());
                  cheque.setEmpresa(pedido.getEmpresa());
                  cheque.setCliente(pedido.getCliente());
                  ContaCorrente contaDeposito = contaCorrenteDao.consultarContaCaixaLocal(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
                  cheque.setContaDeposito(contaDeposito);
                  pedido.getPagamento().addCheque(cheque);
               }

               if (formaPagamento.isCartaoCredito() || formaPagamento.isCartaoDebito()) {
                  CartaoCredito cartao = new CartaoCredito();
                  cartao.setEmpresa(pedido.getEmpresa());
                  cartao.setCliente(pedido.getCliente());
                  cartao.setDataVencimento(formaPagamento.getDataVencimento());
                  cartao.setValor(formaPagamento.getValor());
                  pedido.getPagamento().addCartaoCredito(cartao);
               }
               
               if (formaPagamento.isDepositoEmConta()){
                  DepositoContaCorrente depositoContaCorrente = new DepositoContaCorrente();
                  depositoContaCorrente.setDataDeposito(formaPagamento.getDataVencimento());
                  depositoContaCorrente.setValor(formaPagamento.getValor());
                  ContaCorrente contaDeposito = contaCorrenteDao.consultarContaCaixaLocal(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
                  depositoContaCorrente.setContaCorrente(contaDeposito);
                  pedido.getPagamento().addDepositoContaCorrente(depositoContaCorrente);
               }
            }
            
            Pagamento pagamento = pagamentoDao.alterar(pedido.getPagamento());
            pedido.setPagamento(pagamento);
         }
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }
   
   public void adicionarFormaPagamento() {
      try {
         if (pedido != null) {

            BigDecimal valorTotalPedido = new BigDecimal(0);
            valorTotalPedido = formaPagamento.getValor().add(pedido.getPagamento().getValorNegociado());
            BigDecimal bigDecimal = new BigDecimal(0);

            if (valorTotalPedido.compareTo(pedido.getValorTotal()) <= 0 && formaPagamento.getValor().compareTo(bigDecimal) > 0) {

               InstrumentoRecebimento instrumentoRecebimento = new InstrumentoRecebimento();
               instrumentoRecebimento.setDescricao(formaPagamentoSelecionada.getDescricao());
               instrumentoRecebimento.setInstrumentoRecebimentoDisponivel(formaPagamentoSelecionada);
               formaPagamento.setInstrumentoRecebimento(instrumentoRecebimento);

               if (!informacaoParcelamento.isEmpty()) {
                  calcularParcelamento();
               } else {
                  pedido.getPagamento().addFormaPagamento(formaPagamento);
               }

               pedido.getPagamento().setCliente(pedido.getCliente());
               pedido.getPagamento().setEmpresa(pedido.getEmpresa());
               UtilFaces.addMensagemFaces("Forma de pagamento adicionada com sucesso!", FacesMessage.SEVERITY_INFO);
            } else {
               UtilFaces.addMensagemFaces("Valor de forma de pagamento inválido!", FacesMessage.SEVERITY_ERROR);
            }
            formaPagamento = new FormaPagamento();
            formaPagamento.setValor(null);
            informacaoParcelamento = null;
         } else {
            UtilFaces.addMensagemFaces("Selecione um pedido.", FacesMessage.SEVERITY_ERROR);
         }

      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }
   }
   
   public void calcularParcelamento() {
      String parcelamento[] = informacaoParcelamento.split("/");
      Integer prazosParcelamento[] = new Integer[parcelamento.length];

      for (int i = 0; i < prazosParcelamento.length; i++) {
         prazosParcelamento[i] = Integer.parseInt(parcelamento[i]);
      }

      BigDecimal numeroDeParcelas = new BigDecimal(prazosParcelamento.length);
      BigDecimal valorDasParcelas = formaPagamento.getValor().divide(numeroDeParcelas);
      InstrumentoRecebimento instrumentoRecebimento = formaPagamento.getInstrumentoRecebimento();

      for (int i = 0; i < prazosParcelamento.length; i++) {
         formaPagamento = new FormaPagamento();
         Date dataFormaPagamento = new Date();
         Integer diasAcrescentados = new Integer(prazosParcelamento[i]);

         Data data = new Data(dataFormaPagamento);

         dataFormaPagamento = data.adicionarDia(diasAcrescentados);
         formaPagamento.setDataVencimento(dataFormaPagamento);
         formaPagamento.setValor(valorDasParcelas);
         formaPagamento.setInstrumentoRecebimento(instrumentoRecebimento);
         pedido.getPagamento().addFormaPagamento(formaPagamento);
      }
   }
   
   public void cancelarNegociacao(){
      
      negociacaoPedidoDao.excluirPorId(negociacaoPedido.getId());
      limpar();
      negociacaoPedido = new NegociacaoPedido();
      vendedor = new Vendedor();
      UtilFaces.addMensagemFaces("Negociação cancelada com sucesso!");
   }
   
   public void adicionarItemDoPedido() {

      try {
         boolean existePedido = false;

         listaEstoquesPropriosSelecionados.addAll(listaEstoquesExternosSelecionados);

         for (EstoqueProdutoDto estoqueSelecionado : listaEstoquesPropriosSelecionados) {

            if (estoqueSelecionado.getQuantidadeSelecionada().compareTo(estoqueSelecionado.getQuantidade()) == 1) {
               throw new Exception("Quantidade de " + estoqueSelecionado.getProduto().getDescricao().toUpperCase() + " indisponível.");
            } else if (estoqueSelecionado.getQuantidadeSelecionada().compareTo(BigDecimal.ZERO) == -1) {
               throw new Exception("Quantidade de " + estoqueSelecionado.getProduto().getDescricao().toUpperCase() + " inválida.");
            } else if (estoqueSelecionado.getQuantidadeSelecionada().compareTo(BigDecimal.ZERO) == 0) {
               throw new Exception("Quantidade de " + estoqueSelecionado.getProduto().getDescricao().toUpperCase() + " é ZERO, desmarque o produto.");
            } else {
               ValorProduto valorProduto = valorProdutoDao.consultarValorAtual(estoqueSelecionado.getProduto());
               if (valorProduto == null) {
                  throw new KyklosException(String.format("O produto %s não possui valor de venda cadastrado. Vá na tela de configuração do produto e cadastre.", produto.getDescricao()));
               }
               Pagamento pagamento = new Pagamento();
               if (negociacaoPedido.getCliente() != null) {
                  pagamento.setCliente(negociacaoPedido.getCliente());
               } else {
                  produto = new Produto();
                  throw new Exception("Selecione um cliente para iniciar a negociação.");
               }
               pagamento.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());

               estoqueSelecionado.setProduto(produtoDao.consultar(estoqueSelecionado.getProduto().getId()));

               for (Pedido pedido : negociacaoPedido.getPedidos()) {
                  if (negociacaoPedido.getPedidos().size() == 1 && pedido.getItens().isEmpty()) {
                     itemPedido = new ItemPedido(pedido, estoqueSelecionado.getProduto());
                     itemPedido.setEstoque(estoqueSelecionado.getEstoqueProduto());
                     itemPedido.setQuantidade(estoqueSelecionado.getQuantidadeSelecionada());
                     itemPedido.setUsuario(usuarioLogadoControl.getUsuario());
                     itemPedido.setValorUnitario(valorProduto.getValorVenda());
                     itemPedido.setCfop(cfopPedido);
                     pedido.addItem(itemPedido);
                     existePedido = true;
                  }
                  for (EstoqueProduto estoqueDoPedido : pedido.getEstoques()) {
                     if (existePedido == false && estoqueSelecionado.getEstoque().getPessoaEmpresa().getId().equals(estoqueDoPedido.getEstoque().getPessoaEmpresa().getId())) {
                        Pedido itensPedido = new Pedido();

                        boolean itemEncontrado = false;
                        for (ItemPedido itemPedido : pedido.getItens()) {
                           if (itemPedido.getProduto().equals(estoqueSelecionado.getProduto()) && itemEncontrado == false) {
                              itemPedido.addQuantidade(estoqueSelecionado.getQuantidadeSelecionada());
                              baixarQuantidadeSelecionadaDoEstoqueProduto(estoqueSelecionado.getEstoqueProduto(), estoqueSelecionado.getQuantidadeSelecionada());
                              itemEncontrado = true;
                           }
                        }
                        if (!itemEncontrado) {
                           ItemPedido novoItem = new ItemPedido(pedido, estoqueSelecionado.getProduto());
                           novoItem.setEstoque(estoqueSelecionado.getEstoqueProduto());
                           novoItem.setQuantidade(estoqueSelecionado.getQuantidadeSelecionada());
                           baixarQuantidadeSelecionadaDoEstoqueProduto(estoqueSelecionado.getEstoqueProduto(), estoqueSelecionado.getQuantidadeSelecionada());
                           novoItem.setUsuario(usuarioLogadoControl.getUsuario());
                           pedido.setEmpresa(estoqueSelecionado.getEstoqueProduto().getEstoque().getPessoaEmpresa().getEmpresa());
                           novoItem.setValorUnitario(valorProduto.getValorVenda());
                           novoItem.setCfop(pedido.getCfop());
                           itensPedido.addItem(novoItem);
                           existePedido = true;
                        }
                        pedido.getItens().addAll(itensPedido.getItens());
                        existePedido = true;
                     }
                  }
               }
               if (!existePedido) {
               	pedido = new Pedido();
               	pedido.setEmpresa(estoqueSelecionado.getEstoqueProduto().getEstoque().getPessoaEmpresa().getEmpresa());
               	pedido.setEmpresaGeradoraPedido(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
               	validarEInserirCfop();

               	pedido.setPagamento(pagamento);
               	frete = new Frete();
               	frete.setFretePorContaDe(EnumTipoFrete.SEM_FRETE);
               	frete = freteDao.alterar(frete);
               	pedido.setFrete(frete);
               	itemPedido = new ItemPedido(pedido, estoqueSelecionado.getProduto());
               	itemPedido.setEstoque(estoqueSelecionado.getEstoqueProduto());
               	itemPedido.setQuantidade(estoqueSelecionado.getQuantidadeSelecionada());
               	itemPedido.setCfop(cfopPedido);
               	baixarQuantidadeSelecionadaDoEstoqueProduto(estoqueSelecionado.getEstoqueProduto(), estoqueSelecionado.getQuantidadeSelecionada());
               	itemPedido.setUsuario(usuarioLogadoControl.getUsuario());
               	itemPedido.setValorUnitario(valorProduto.getValorVenda());
               	pedido.addItem(itemPedido);
               	negociacaoPedido.addPedido(pedido, usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), vendedor);
               }

               numerarItens();
               negociacaoPedido = negociacaoPedidoDao.alterar(negociacaoPedido);
               limpar();
            }
         }
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }

   /**
    * 1 - Valida se o CFOP existe na Tabela de Parametros da empresa de acordo com o Regime Tributário
    * Se falso, insere na tabela de Parametros os CFOP's padroes
    * 2 - Verifica se o CFOP do Pedido esta null, caso positivo, insere de acordo com o valor existente na Tabela de Parametros.
    */
   private void validarEInserirCfop() {

   	EmpresaCliente empresaCliente = usuarioLogadoControl.getEmpresaUsuario().getEmpresa();
   	Map<String, String> parametrosEmpresa = parametroEmpresaDao.listarTodosMapsEmpresa(empresaCliente);
   	/*
   	 * para caso o CFOP esteja NULL nas propriedades da Empresa,
   	 * São inseridos estes Parametros Por Default
   	 */
   	try{
   		if(empresaCliente.isRegimeNormal()){
   			if(parametrosEmpresa.get("cfop-venda-interna") == null){
   				String chave = "cfop-venda-interna";
   				Integer cfop = 5101;
   				parametroEmpresaDao.incluir(new ParametroEmpresa(chave, cfop.toString(), empresaCliente));
   				UtilLog.getLog().info(String.format("Incluido nas Propriedades da Empresa o Parametro Tipo CFOP de Venda Padrão.\n Empresa: %s, chave: %s, valor: %s", empresaCliente.getPessoa().getNome(), chave, cfop.toString()));
   			}
   			if(parametrosEmpresa.get("cfop-venda-externa") == null){
   				String chave = "cfop-venda-externa";
   				Integer cfop = 6101;
   				parametroEmpresaDao.incluir(new ParametroEmpresa(chave, cfop.toString(), empresaCliente));
   				UtilLog.getLog().info(String.format("Incluido nas Propriedades da Empresa o Parametro Tipo CFOP de Venda Padrão.\n Empresa: %s, chave: %s, valor: %s", empresaCliente.getPessoa().getNome(), chave, cfop.toString()));
   			}
   		}else{
   			if(parametrosEmpresa.get("cfop-venda-interna") == null){
   				String chave = "cfop-venda-interna";
   				Integer cfop = 5102;
   				parametroEmpresaDao.incluir(new ParametroEmpresa(chave, cfop.toString(), empresaCliente));
   				UtilLog.getLog().info(String.format("Incluido nas Propriedades da Empresa o Parametro Tipo CFOP de Venda Padrão.\n Empresa: %s, chave: %s, valor: %s", empresaCliente.getPessoa().getNome(), chave, cfop.toString()));
   			}
   			if(parametrosEmpresa.get("cfop-venda-externa") == null){
   				String chave = "cfop-venda-externa";
   				Integer cfop = 6102;
   				parametroEmpresaDao.incluir(new ParametroEmpresa(chave, cfop.toString(), empresaCliente));
   				UtilLog.getLog().info(String.format("Incluido nas Propriedades da Empresa o Parametro Tipo CFOP de Venda Padrão.\n Empresa: %s, chave: %s, valor: %s", empresaCliente.getPessoa().getNome(), chave, cfop.toString()));
   			}
   		}
   	} catch(Exception e) {
   		UtilFaces.addMensagemFaces("Houve uma exceção ao inserir os CFOP's padrões nas propriedades da empresa.");
   	}

   	/*
   	 * Valida se o CFOP existe, caso contrário é setado o CFOP padrão de Venda Interna ou Externa.
   	 */
   	if(cfopPedido != null && cfopPedido.getCfop() != null){
   		pedido.setCfop(cfopPedido);
   	}else{
   		if(pedido.getEmpresa().getPessoa().getMunicipio().getUf() == pedido.getCliente().getMunicipio().getUf()){
   			try{
   				parametrosEmpresa = parametroEmpresaDao.listarTodosMapsEmpresa(empresaCliente);
   				cfopPedido = cfopDao.consultarPorCfop(Integer.parseInt(parametrosEmpresa.get("cfop-venda-interna")));
   				pedido.setCfop(cfopPedido);
   				UtilFaces.addMensagemFaces("CFOP padrão de Venda Interno foi inserida no Pedido.\n Caso a operação não seja de Venda, por favor alterar para o CFOP adequado");
   			} catch(Exception e) {
   				UtilFaces.addMensagemFaces("Houve uma exceção ao inserir o CFOP de Venda Padrão no Pedido.");
   			}
   		}else{
   			try{
   				parametrosEmpresa = parametroEmpresaDao.listarTodosMapsEmpresa(empresaCliente);
   				cfopPedido = cfopDao.consultarPorCfop(Integer.parseInt(parametrosEmpresa.get("cfop-venda-externa")));
   				pedido.setCfop(cfopPedido);
   				UtilFaces.addMensagemFaces("CFOP padrão de Venda Externa Inserida no Pedido.\n Caso a operação não seja de Venda, por favor alterar para o CFOP adequado");
   			} catch(Exception e) {
   				UtilFaces.addMensagemFaces("Houve uma exceção ao inserir o CFOP de Venda Padrão no Pedido.");
   			}
   		}
   	}

   }

	private void baixarQuantidadeSelecionadaDoEstoqueProduto(EstoqueProduto estoqueProduto, BigDecimal quantidade) throws Exception {
      /*
       * Inclui movimentação do estoque.
       */
      MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
      movimentacao.setEstoque(estoqueProduto.getEstoque());
      movimentacao.setProduto(estoqueProduto.getProduto());
      movimentacao.setQuantidade(quantidade.negate());
      movimentacao.setUsuario(usuarioLogadoControl.getUsuario());
      movimentacaoEstoqueDao.incluir(movimentacao);

      /*
       * Altera quantidade do estoqueProduto passado.
       */
      estoqueProduto.subtrairQuantidade(quantidade);
      estoqueProdutoDao.alterar(estoqueProduto);
   }

   public void numerarItens() {
      for (Pedido pedido : negociacaoPedido.getPedidos()) {
         int numItem = 1;
         for (ItemPedido itemPedido : pedido.getItens()) {
            itemPedido.setNumeroItem(numItem);
            numItem++;
         }
      }
   }

   public void selecionarProduto(SelectEvent event) {
      try {
         produto = (Produto) event.getObject();
         produto = produtoDao.consultar(produto.getId());

         List<PessoaEmpresa> pessoaEmpresas;
         pessoaEmpresas = pessoaEmpresaDao.consultarEmpresasVinculadas(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
         listaEstoquesProprios = EstoqueProdutoDto.converterEstoqueProduto(estoqueProdutoDao.listarPorEmpresa(pessoaEmpresas, produto));
         for (EstoqueProdutoDto estoqueProduto : listaEstoquesProprios) {
            if (!estoqueProduto.getEstoque().getPessoaEmpresa().getPessoa().equals(usuarioLogadoControl.getEmpresaUsuario().getEmpresa().getPessoa())) {
               listaEstoquesExternos.add(estoqueProduto);
            }
         }
         listaEstoquesProprios.removeAll(listaEstoquesExternos);

         if (listaEstoquesProprios.size() == 0) {
            listaEstoquesProprios = new ArrayList<EstoqueProdutoDto>();
         }

         UtilFaces.addMensagemFaces(produto.getCodigo() + " - " + produto.getDescricao() + " selecionado.");
      } catch (KyklosException e) {
         UtilFaces.addMensagemFaces("Erro ao selecionar o Produto.");
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }

   public void selecionarCliente(SelectEvent event) {
      
      if (negociacaoPedido.getId() == null) {
         negociacaoPedido.setUsuario(usuarioLogadoControl.getUsuario());
         negociacaoPedido.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
         Vendedor vendedorConsultado = vendedorDao.consultarPorEmpresaEPessoa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), usuarioLogadoControl.getUsuario().getPessoa());
         if (vendedorConsultado == null) {
            vendedorConsultado = new Vendedor();
            vendedorConsultado.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
            vendedorConsultado.setPessoa(usuarioLogadoControl.getUsuario().getPessoa());
            vendedorDao.incluir(vendedorConsultado);
         }
         vendedor = vendedorConsultado;
         negociacaoPedido = negociacaoPedidoDao.alterar(negociacaoPedido);
         
         UtilFaces.addMensagemFaces("Nova negociação com o cliente " + negociacaoPedido.getCliente().getNome());
      }
      if (negociacaoPedido.getPedidos().size() == 0) {
         UtilFaces.addMensagemFaces("Adicione produtos para que o pedido seja criado.");
      } else {
         UtilFaces.addMensagemFaces("Verifique os Produtos adicionados no(s) pedido(s).");
      }
   }

   public void encaminharNegociacaoAoCaixa() {
      try {
         if(pedido.getCliente().getEmail() == null && pedido.getCliente().getTelefone() == null){
            throw new Exception(String.format("Os dados do cliente não estão devidamente preenchidos.\n Tela de Cadastro de pessoa"));
         }
         for (Pedido pedido : negociacaoPedido.getPedidos()) {
            pedido.setStatus(EnumStatusPedido.FINALIZADO);
            pedidoDao.alterar(pedido);
         }
         negociacaoPedido = new NegociacaoPedido();
         pedido = new Pedido();
         produto = new Produto();
         vendedor = new Vendedor();

         UtilFaces.addMensagemFaces("Pedido(s) encaminhado(s) ao caixa com sucesso!");
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao encaminhar pedido(s) ao caixa" + e.getMessage());
      }
   }
   
   public void limparClienteCadastroRapido() {
      cliente = new Pessoa();
   }
   
   public void limpar() {
      listaEstoquesProprios = new ArrayList<EstoqueProdutoDto>();
      listaEstoquesExternos = new ArrayList<EstoqueProdutoDto>();
      listaEstoquesPropriosSelecionados = new ArrayList<EstoqueProdutoDto>();
      listaEstoquesExternosSelecionados = new ArrayList<EstoqueProdutoDto>();
      produto = new Produto();
   }

   public Frete getFrete() {
      return frete;
   }

   public void setFrete(Frete frete) {
      this.frete = frete;
   }

   public Endereco getEndereco() {
      return endereco;
   }

   public EnumInstrumentoRecebimento getFormaPagamentoSelecionada() {
      return formaPagamentoSelecionada;
   }

   public void setFormaPagamentoSelecionada(EnumInstrumentoRecebimento formaPagamentoSelecionada) {
      this.formaPagamentoSelecionada = formaPagamentoSelecionada;
   }

   public void setEndereco(Endereco endereco) {
      this.endereco = endereco;
   }

   public Long getNumeroPedido() {
      return numeroPedido;
   }

   public List<EstoqueProdutoDto> getListaEstoquesProprios() {
      return listaEstoquesProprios;
   }

   public List<EstoqueProdutoDto> getListaEstoquesExternos() {
      return listaEstoquesExternos;
   }

   public List<EstoqueProdutoDto> getListaEstoquesPropriosSelecionados() {
      return listaEstoquesPropriosSelecionados;
   }

   public void setListaEstoquesPropriosSelecionados(List<EstoqueProdutoDto> listaEstoquesPropriosSelecionados) {
      if (listaEstoquesPropriosSelecionados == null) {
         this.listaEstoquesPropriosSelecionados = new ArrayList<EstoqueProdutoDto>();
      } else {
         this.listaEstoquesPropriosSelecionados = listaEstoquesPropriosSelecionados;
      }
   }

   public List<EstoqueProdutoDto> getListaEstoquesExternosSelecionados() {
      return listaEstoquesExternosSelecionados;
   }

   public List<SelectItem> getTiposFrete() {
      return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumTipoFrete.values()));
   }
   
   public List<SelectItem> getFormasDePagamento() {
      return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumInstrumentoRecebimento.values()));
   }
   
   public List<Transportadora> getTransportadoras() {
      return transportadoras;
   }

   public void setListaEstoquesExternosSelecionados(List<EstoqueProdutoDto> listaEstoquesExternosSelecionados) {
      if (listaEstoquesExternosSelecionados == null) {
         this.listaEstoquesExternosSelecionados = new ArrayList<EstoqueProdutoDto>();
      } else {
         this.listaEstoquesExternosSelecionados = listaEstoquesExternosSelecionados;
      }
   }

   public NegociacaoPedido getNegociacaoPedido() {
      return negociacaoPedido;
   }

   public void setNegociacaoPedido(NegociacaoPedido negociacaoPedido) {
      this.negociacaoPedido = negociacaoPedido;
   }

   public Transportadora getTransportadoraSelecionada() {
      return transportadoraSelecionada;
   }

   public void setTransportadoraSelecionada(Transportadora transportadoraSelecionada) {
      this.transportadoraSelecionada = transportadoraSelecionada;
   }

   public EnumTipoFrete getTipoFreteSelecionado() {
      return tipoFreteSelecionado;
   }

   public void setTipoFreteSelecionado(EnumTipoFrete tipoFreteSelecionado) {
      this.tipoFreteSelecionado = tipoFreteSelecionado;
   }

   public Pedido getPedido() {
      return pedido;
   }

   public void setPedido(Pedido pedido) {
      this.pedido = pedido;
   }

   public ItemPedido getItemPedido() {
      return itemPedido;
   }

   public Vendedor getVendedor() {
      return vendedor;
   }

   public void setVendedor(Vendedor vendedor) {
      this.vendedor = vendedor;
   }

   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }

   public FormaPagamento getFormaPagamento() {
      return formaPagamento;
   }

   public void setFormaPagamento(FormaPagamento formaPagamento) {
      this.formaPagamento = formaPagamento;
   }

   public Date getDataMinimaVencimento() {
      return dataMinimaVencimento;
   }

   public void setDataMinimaVencimento(Date dataMinimaVencimento) {
      this.dataMinimaVencimento = dataMinimaVencimento;
   }

   public String getInformacaoParcelamento() {
      return informacaoParcelamento;
   }

   public Pessoa getCliente() {
      return cliente;
   }

   public void setCliente(Pessoa cliente) {
      this.cliente = cliente;
   }

   public void setInformacaoParcelamento(String informacaoParcelamento) {
      this.informacaoParcelamento = informacaoParcelamento;
   }

   public Cfop getCfopPedido() {
      return cfopPedido;
   }

   public void setCfopPedido(Cfop cfopPedido) {
      this.cfopPedido = cfopPedido;
   }

	public NegociacaoPedido getNegociacaoSelecionada() {
		return negociacaoSelecionada;
	}

	public void setNegociacaoSelecionada(NegociacaoPedido negociacaoSelecionada) {
		this.negociacaoSelecionada = negociacaoSelecionada;
	}

}