package br.com.ambientinformatica.kyklos.negocio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EnumIndicadorPresencaComprador;
import br.com.ambientinformatica.kyklos.entidade.EnumTipoFrete;
import br.com.ambientinformatica.kyklos.entidade.FormaPagamento;
import br.com.ambientinformatica.kyklos.entidade.InformacaoFiscal;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.ParametroEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.persistencia.CfopDao;
import br.com.ambientinformatica.kyklos.persistencia.ParametroEmpresaDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.nfe.api.EnumTipoAmbiente;
import br.com.ambientinformatica.nfe.api.EnumTipoNota;
import br.com.ambientinformatica.nfe.api.FormaPagamentoDto;
import br.com.ambientinformatica.nfe.api.ItemNotaFiscalIntegracaoDto;
import br.com.ambientinformatica.nfe.api.NfeRetDto;
import br.com.ambientinformatica.nfe.api.NotaFiscalIntegracaoDto;
import br.com.ambientinformatica.nfe.api.PessoaDto;
import br.com.ambientinformatica.nfe.util.UtilRequest;
import br.com.ambientinformatica.util.UtilLog;
import br.com.ambientinformatica.util.UtilTexto;

@Service("notaFiscalEletronicaNeg")
public class NotaFiscalEletronicaNegImpl implements NotaFiscalEletronicaNeg {

   @Autowired
   private ParametroEmpresaDao parametroEmpresaDao;
   
   @Autowired
   private InformacaoFiscalNeg informacaoFiscalNeg;
   
   @Autowired
   private CfopDao cfopDao;
   
   private Map<String, String> parametrosEmpresa;
   
   @Override
   public NfeRetDto criarNotaFiscalEletronica(Pedido pedido, EmpresaCliente empresaCliente) throws KyklosException {
   	
      if(pedido.getItens() == null || pedido.getItens().isEmpty()){
         throw new KyklosException(String.format("O pedido %d não possui itens. A NFe não poderá ser gerada.", pedido.getId()));
      }
      
      parametrosEmpresa = parametroEmpresaDao.listarTodosMapsEmpresa(empresaCliente);
   	
      NotaFiscalIntegracaoDto nfi = new NotaFiscalIntegracaoDto();

      /*
       * Valida se o ambiente informado eh o da propria UF (Normal)
       * 
       * Caso seja de o ambiente de Contigencia, eh necessario informar a justificativa de uso do ambiente
       * de contingencia
       */
      int tipoEmissao = Integer.parseInt(parametrosEmpresa.get("tipoEmissao"));
      String justificativaContigencia = parametrosEmpresa.get("justicativaContigencia");
      if(tipoEmissao == 3 || tipoEmissao == 6 || tipoEmissao == 7){
      	nfi.setTipoEmissao(tipoEmissao);
      	if(justificativaContigencia == null || justificativaContigencia.length() < 15 || justificativaContigencia.length() > 256){
      		throw new KyklosException("557 - Rejeicao: A Justificativa (entre 15 e 256 caracteres) de entrada em contingencia deve ser informada.");
      	}
      	nfi.setxJusticativaContigencia(justificativaContigencia);
      	nfi.setDataHoraContigencia(new Date());
      }else{
      	nfi.setTipoEmissao(tipoEmissao);
      }
      
      if(pedido.getCliente() != null){
         PessoaDto destinatario = new PessoaDto();
         destinatario.setNome(pedido.getCliente().getNome());
         destinatario.setNomeFantasia(pedido.getCliente().getNome());
         destinatario.setTelefone(pedido.getCliente().getTelefone());
         destinatario.setCodIbgeMunicipio(pedido.getCliente().getMunicipio().getCodigoIbge());
         destinatario.setNomeMunicipio(pedido.getCliente().getMunicipio().getDescricao());
         destinatario.setUfMunicipio(pedido.getCliente().getMunicipio().getUf());
         destinatario.setCep(pedido.getCliente().getCep());
         destinatario.setBairro(pedido.getCliente().getBairro());
         destinatario.setContato(pedido.getCliente().getTelefone());
         destinatario.setCpfCnpj(pedido.getCliente().getCpfCnpj());
         destinatario.setInscricaoEstadual(pedido.getCliente().getInscricaoEstadual());
         destinatario.setLogradouro(pedido.getCliente().getLogradouro());
         destinatario.setNumeroEnd(pedido.getCliente().getNumero());
         destinatario.setComplemento(pedido.getCliente().getComplemento());
         nfi.setDestinatario(destinatario);
      }
      
      /*
       * Validar o tipo de Frete, se existir Frete é para setar os dados do Frete e da Transportadora aqui.
       */
      if(pedido.getFrete() != null && pedido.getFrete().getTransportadora() != null){
         PessoaDto transportadora = new PessoaDto();
         transportadora.setCpfCnpj(pedido.getFrete().getTransportadora().getCpfCnpj());
         transportadora.setNome(pedido.getFrete().getTransportadora().getRazaoSocial());
         transportadora.setComplemento(pedido.getFrete().getTransportadora().getEndereco().getEnderecoCompleto());
         transportadora.setNomeMunicipio(pedido.getFrete().getTransportadora().getEndereco().getMunicipio().getDescricao());
         nfi.setTransportadora(transportadora);
      }

      validarEInserirAmbienteDeEnvioNfe(nfi, empresaCliente);
      
      for(FormaPagamento formaPagamento : pedido.getPagamento().getFormasPagamento()){
         if(formaPagamento.getPrazo() == 0){
            // Pagamento a vista
            nfi.setIndicadorFormaPagamento(0); 
         }else{
            // Pagamento parcelado
            nfi.setIndicadorFormaPagamento(1);
         }
      }
      
      if(nfi.getDestinatario().getCpfCnpjSemMascara().length() == 11){
         nfi.setConsumidorFinal(true); 
      }else{
         nfi.setConsumidorFinal(false);
      }
      
      /*
       * Buscar do cadastro da empresa o codigo do Regime Tributário.
       * 1-Simples Nacional
       * 2-Simples Nacional – excesso de sublimite da receita bruta
       * 3-Regime Normal
       */
      nfi.setCodigoRegimeTributario(empresaCliente.getCrt().getCodigo());
      
      nfi.setDataEmissao(new Date());
      nfi.setTipoImpressao(pedido.getTipoImpressao().getCodigoNfe());

      nfi.setModelo(55);

      nfi.setNaturezaOperacao(UtilTexto.removerAcentos(UtilTexto.removerCaracteresNaoAlfaNumericos(pedido.getCfop().getDescricao())));
      nfi.setSerie(1);
      nfi.setTipoNota(pedido.getCfop().isCompra() ? EnumTipoNota.ENTRADA : EnumTipoNota.SAIDA);
      /*
       * TODO Verificar para trazer a EmpresaUsuariaLogada
       * Verificar com Edjalma como será melhor, passando a empresa do UsuarioLogado como parametro do metodo, ou pegando a empresa do pedido.
       */
      nfi.setCnpjEmitente(pedido.getEmpresa().getPessoa().getCpfCnpj());
      
      /*
       * O atributo IndPres segundo o manual, deve ser passado os valores para NFe:
       * 0 - Não se aplica (por exemplo, Nota Fiscal complementar ou de ajuste);
       * 1 - Operação presencial;
       * 2 - Operação não presencial, pela Internet;
       * 3 - Operação não presencial, Teleatendimento;
       * 9 - Operação não presencial, outros.
       * 
       * Nota: No caso da NFCe somente serão aceitas as operações 1 e 4.
       * 
       * TODO Validação do Frete em relação ao Indicador de Tipo de Venda.
       * Caso seja presencial, o frete já é setado para SEM_FRETE
       * Caso contrario, é setado o tipo de Frete cadastrado na venda.
       * 
       * Para a NFC-e, somente são aceitas as opções 1 e 4 do EnumIndicadorPresencaComprador
       * 
       */
      
      if(pedido.getFrete().getFretePorContaDe().getCodigoNfe() == EnumTipoFrete.SEM_FRETE.getCodigoNfe()){
         nfi.setIndicadorPresenca(EnumIndicadorPresencaComprador.PRESENCIAL.getCodigoNfe());
         nfi.setTipoFrete(EnumTipoFrete.SEM_FRETE.getCodigoNfe());
      }else{
         /*
          * TODO Verificar se o tipo da venda foi pela 
          * internet (indicadorPresenca = 2), 
          * teleatendimento (indicadorPresenca = 3) ou 
          * outros (indicadorPresenca = 9)
          * Obs.: Não Presenciais.
          */
         nfi.setTipoFrete(pedido.getFrete().getFretePorContaDe().getCodigoNfe());
      }
      
      for (ItemPedido item : pedido.getItens()) {
      	InformacaoFiscal informacaoFiscal = informacaoFiscalNeg.consultarInformacaoFiscal(item);
      	informacaoFiscal.preencherInformacoesItemNota(item);
      	
         ItemNotaFiscalIntegracaoDto itemNfi = new ItemNotaFiscalIntegracaoDto();
         
         itemNfi.setCodigoProduto(item.getProduto().getCodigo());
         itemNfi.setDescricaoProduto(item.getProduto().getDescricao());
         if (empresaCliente.isRegimeNormal()) {
            itemNfi.setCstIcms(informacaoFiscal.getCstIcms());
         }else{
            itemNfi.setCsosn(informacaoFiscal.getCsosn());
         }
         itemNfi.setCfop(item.getCfop().getCfop());
         itemNfi.setCstCofins(item.getCstCofins());
         itemNfi.setCstPis(item.getCstPis());
         
         if(item.getProduto().getCodigoEan() != null){
         	itemNfi.setEan(Long.parseLong(item.getProduto().getCodigoEan()));
         	itemNfi.setEanTrib(Long.parseLong(item.getProduto().getCodigoEan()));
         } 
         
         validarEInserirModalidadeBaseCalculo(itemNfi, empresaCliente);
         
         itemNfi.setNcm(item.getProduto().getNcm());
         itemNfi.setNumeroItem(item.getNumeroItem());
         itemNfi.setuTrib(item.getProduto().getUnidadeMedida().getSigla());
         itemNfi.setuCom(item.getProduto().getUnidadeMedida().getSigla());
         
         itemNfi.setAliquotaIcms(item.getAliquotaIcms());
         itemNfi.setQuantidade(item.getQuantidade());
         
         if(pedido.getFrete().getFretePorContaDe() == EnumTipoFrete.SEM_FRETE){
            itemNfi.setvFrete(BigDecimal.ZERO);          
         }else{
            itemNfi.setvFrete(pedido.getFrete().getValorFrete());
         }
         
         //TODO Verificar o ajuste da Porcentagem de BC, verificar se é para fazer aqui, ou se o NFESERVER já o faz.
//         item.setPorcentagemBaseCalculoIcms(informacaoFiscal.getPorcentagemBaseCalculoIcms());
         itemNfi.setValorBaseCalculoIcms(item.getValorBaseCalculoIcms());
         itemNfi.setValorUnitario(item.getValorUnitario());


         //TODO criar campo para informar a origem da mercadoria dentro do Cadastro do Produto, de acordo com o ENUM da NFE (criar este ENUM)
//       itemNfi.setOrigemProduto(item.getProduto().getOrigemMercadoria());
         itemNfi.setOrigemProduto(0);
         nfi.addItem(itemNfi);
      }
      for (FormaPagamento pagamentoVenda : pedido.getPagamento().getFormasPagamento()) {
         FormaPagamentoDto formaPagamento = new FormaPagamentoDto();
         formaPagamento.setValor(pagamentoVenda.getValor());
         formaPagamento.setTipoFormaPagamento(formaPagamento.getTipoFormaPagamento());
         formaPagamento.setCnpjCartao(formaPagamento.getCnpjCartao() != null ? formaPagamento.getCnpjCartao() : null);
         formaPagamento.setNumeroAut(formaPagamento.getNumeroAut() != null ? formaPagamento.getNumeroAut() : null);
         formaPagamento.setBandeira(formaPagamento.getBandeira() != null ? formaPagamento.getBandeira() : null);
         nfi.addFormaPagamento(formaPagamento);
      }
      return enviarNotaFiscal(nfi);
   }

   /**
    * Insere a Modalidade de Base de Calculo do ICMS.
    * Caso o parametro não exista, ele insere o valor 0 = Margem Valor Agregado (%)
    *  
    * @param itemNfi
    * @param empresaCliente
    */
   private void validarEInserirModalidadeBaseCalculo(ItemNotaFiscalIntegracaoDto itemNfi, EmpresaCliente empresaCliente) {
   	if(parametrosEmpresa.get("modBc") != null){
   		itemNfi.setModBC(Integer.parseInt(parametrosEmpresa.get("modBc")));
   	}else{
      	String chave = "modBc";
      	String valor = "0";
      	parametroEmpresaDao.incluir(new ParametroEmpresa(chave, valor, empresaCliente));
      	UtilLog.getLog().info(String.format("Incluido nas Propriedades da Empresa o Parametro Modalidade de Base de Calculo do ICMS.\n Empresa: %s, chave: %s, valor: %s", empresaCliente.getPessoa().getNome(), chave, valor));
      	itemNfi.setModBC(0);
      }
   }

	/**
    * Estrutura básica para setar o Ambiente da NFe.
    * Caso o parametro não exista, este é criado e setado no objeto como Homologação
    * @param empresaCliente 
    * @param nfi 
    * @throws KyklosException 
    */
   private void validarEInserirAmbienteDeEnvioNfe(NotaFiscalIntegracaoDto nfi, EmpresaCliente empresaCliente) throws KyklosException {
   	if(parametrosEmpresa.get("tipoAmbienteNfe") != null){
   		
   		if(!(parametrosEmpresa.get("tipoAmbienteNfe").toUpperCase().equals("PRODUCAO") || parametrosEmpresa.get("tipoAmbienteNfe").toUpperCase().equals("HOMOLOGACAO"))){
   			throw new KyklosException("O parametro informado na propriedade tipoAmbienteNfe está incorreto. \n Valores válidos: PRODUCAO, HOMOLOGACAO.\n OBS. Texto deve estar em CAIXA ALTA.");
   		}
   		
      	if(parametrosEmpresa.get("tipoAmbienteNfe").toUpperCase().equals("PRODUCAO")){
      		nfi.setTipoAmbiente(EnumTipoAmbiente.PRODUCAO);
      	}else{
      		nfi.setTipoAmbiente(EnumTipoAmbiente.HOMOLOGACAO);
      	}
      	
      }else{
      	String chave = "tipoAmbienteNfe";
      	String valor = "HOMOLOGACAO";
      	parametroEmpresaDao.incluir(new ParametroEmpresa(chave, valor, empresaCliente));
      	UtilLog.getLog().info(String.format("Incluido nas Propriedades da Empresa o Parametro Tipo NFe.\n Empresa: %s, chave: %s, valor: %s", empresaCliente.getPessoa().getNome(), chave, valor));
      	nfi.setTipoAmbiente(EnumTipoAmbiente.HOMOLOGACAO);
      }
   }

	@Override
   public NfeRetDto enviarNotaFiscal(NotaFiscalIntegracaoDto nfe) throws KyklosException {

      String usuarioGestorNfe =  parametrosEmpresa.get("usuarioGestorNfe");
      String senhaGestorNfe = parametrosEmpresa.get("senhaGestorNfe");
      String urlNfeServer = parametrosEmpresa.get("urlGestorNfe");

      NfeRetDto nfeUltima = UtilRequest.consultarGet(usuarioGestorNfe, senhaGestorNfe, urlNfeServer.concat("/services/envio/consultarUltimaNfe/1/55/HOMOLOGACAO"), NfeRetDto.class);
      int numeroNfe = nfeUltima.getNumero();
      nfe.setNumero(++numeroNfe);

      NfeRetDto nfeRetorno = UtilRequest.consultarPost(usuarioGestorNfe, senhaGestorNfe, urlNfeServer.concat("/services/envio/enviarSincrono/"), nfe, NfeRetDto.class);
      System.out.println("Saida do servidor .... \n");
      System.out.printf("Nota fiscal incluida\nChave de acesso: %s\nToken: %s\nStatus: %d\nDesc Status: %s\n", nfeRetorno.getChaveAcesso(), nfeRetorno.getToken(), nfeRetorno.getStatus(), nfeRetorno.getDescStatus());
      return nfeRetorno;
   }

}