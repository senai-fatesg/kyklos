package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import br.com.ambientinformatica.util.Entidade;
import br.com.ambientinformatica.util.UtilNumero;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class ItemPedido extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="itempedido_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="itempedido_seq", sequenceName="itempedido_seq", allocationSize=1, initialValue=1)
   private Integer id; 

   @Column(nullable=false)
   private Integer numeroItem;

   @ManyToOne(optional = false)
   private Produto produto;

   @Column(length=500)
   private String observacao;

   /**
    * Estoque do qual o produto será separado
    */
   @ManyToOne(optional=false)
   private EstoqueProduto estoque;

   /**
    * Data inicial da reserva do estoque.
    * Normalmente esta data inicia com a inclusao do item no orçamento
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date dataInicioReserva;

   /**
    * Data final da reserva do estoque.
    * A partir desta data, o item não impacta no estoque (reservando quantidade)
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date dataFimReserva;

   /**
    * Usuario que adicionou o item. 
    * Necessário para identificar o responsavel pela reserva de estoque
    */
   @ManyToOne
   private Usuario usuario;

	@ManyToOne
	private Pedido pedido;
	
   @Column(precision=10,scale=2, nullable=false)
   private BigDecimal quantidade = BigDecimal.ZERO;

   @Column(precision=10,scale=2, nullable=false)
   private BigDecimal valorUnitario = BigDecimal.ZERO;
   
   @Version
   private Long versao;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIcms = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIpi = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaCofins = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaPis = BigDecimal.ZERO;
   
   /**
    * este valor deve ser null se nao existir imposto de importacao
    */
   @Column(precision=10,scale=2)
   private BigDecimal valorImpostoImportacao; //nao setar valor inicial

   /**
    * este valor deve ser null se nao existir imposto de importacao
    */
   @Column(precision=10,scale=2)
   private BigDecimal valorBaseCalculoImportacao; //nao setar valor inicial

   /**
    * este valor deve ser null se nao existir imposto de importacao
    */
   @Column(precision=10,scale=2)
   private BigDecimal valorDespesasAduaneiras; //nao setar valor inicial

   /**
    * este valor deve ser null se nao existir imposto de importacao
    */
   @Column(precision=10,scale=2)
   private BigDecimal valorIof; //nao setar valor inicial

   /**
    * este valor deve ser null se nao existir imposto de importacao
    */
   private String numeroDocumentoImportacao; //nao setar valor inicial
   
   /**
    * 0 - nacional
    * 1 - estrangeira, importacao
    * 2 - estrangeira, adquirida no mercado interno
    */
   private Integer origemProduto = 0;
   
   @Column(precision=10,scale=5)
   private BigDecimal pesoProduto = BigDecimal.ZERO;

   @ManyToOne
   private Cfop cfop;

   /**
    * ncm - nomenclatura comum do mercosul
    * http://www.desenvolvimento.gov.br/sitio/interna/interna.php?area=5&menu=1095&refr=605
    */
   private Integer ncm;

   /**
    * código da situacao tributaria do Icms
    */
   private Integer cstIcms;

   /**
    * código da situação tributária do IPI
    */
   private Integer cstIpi;

   /**
    * código da situação tributária da Cofins
    */
   private Integer cstCofins;
   
   /**
    * Código da situação tributária do Pis
    */
   private Integer cstPis;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoIcms = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoIcmsSt = BigDecimal.ZERO;
   
   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoIcmsStRet = BigDecimal.ZERO;
   
   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoIpi = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoCofins = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoPis = BigDecimal.ZERO;
   
   @Column(precision=10,scale=2)
   private BigDecimal valorDesconto = BigDecimal.ZERO;

   @ManyToOne
   private UnidadeMedida unidadeMedida;
   
   /**
    * codigo da situacao da operacao no simples nacional
    */
   private Integer codigoSituacaoOperacao;
   
   /**
    * Aliquota do ICMS ST retido 
    */
   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIcmsStRet = BigDecimal.ZERO;
   
   /**
    * Aliquota do ICMS ST 
    */
   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIcmsSt = BigDecimal.ZERO;
   
   @OneToOne
   private DocumentoImportacao documentoImportacao;
   
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="itemraiz_id")
	private Set<ItemPedido> itensAgrupados = new HashSet<ItemPedido>();
	
	@Column(precision=10,scale=3)
	private BigDecimal volumeProduto = BigDecimal.ZERO;

	@Column(precision=10,scale=3)
	private BigDecimal areaProduto = BigDecimal.ZERO;
	
	@Column(precision=10,scale=3)
	private BigDecimal comprimentoProduto = BigDecimal.ZERO;
	
	@Column(precision=10,scale=2)
	private BigDecimal valorCompra = BigDecimal.ZERO;
	
	public ItemPedido(){
	}
	
	public ItemPedido(Pedido pedido, Produto produto){
		this.pedido = pedido;
		this.produto = produto;

		this.pesoProduto = produto.getPesoLiquido();
		this.unidadeMedida = produto.getUnidadeMedida();
		this.areaProduto = produto.getArea();
		this.comprimentoProduto = produto.getComprimento();
		this.volumeProduto = produto.getVolume();
		this.valorCompra = produto.getValorCompra();
		this.valorUnitario = produto.getValorVenda();

		for(ProdutoAgrupado pa : produto.getProdutosAgrupados()){
			ItemPedido subItem = new ItemPedido(null, pa.getProdutoAgrupado());
			subItem.setQuantidade(pa.getQuantidade());
			subItem.setValorUnitario(pa.getValorProduto());
			itensAgrupados.add(subItem);
		}
	}
	
	/**
	 * Multiplica o valor com desconto pela quantidade
	 * @return o valor total dos produtos deste item pedido
	 */
   public BigDecimal getValorTotal() {
   	return valorUnitario.setScale(5, BigDecimal.ROUND_HALF_EVEN).multiply(quantidade.setScale(3, BigDecimal.ROUND_HALF_EVEN)).setScale(3, BigDecimal.ROUND_DOWN).subtract(valorDesconto).setScale(3, BigDecimal.ROUND_HALF_EVEN);
   }

   /**
    * Soma a quantidade com a quantidade que existe no item.
    * @param quantidade a ser adicionada. 
    */
   public void addQuantidade(BigDecimal quantidade){
      this.quantidade = this.quantidade.setScale(5, BigDecimal.ROUND_HALF_EVEN).add(quantidade).setScale(5, BigDecimal.ROUND_HALF_EVEN).setScale(5, BigDecimal.ROUND_HALF_EVEN);
   }
   
	/**
    * Multiplica o valor sem desconto pela quantidade
    * @return o valor total dos produtos deste item pedido
    */
   public BigDecimal getValorTotalSemDesconto() {
      return valorUnitario.setScale(5, BigDecimal.ROUND_HALF_EVEN).multiply(quantidade.setScale(3, BigDecimal.ROUND_HALF_EVEN)).setScale(3, BigDecimal.ROUND_DOWN);
   }
   
	public BigDecimal getPesoTotal(){
		return UtilNumero.multiplicar(pesoProduto, quantidade);
	}
	
	public BigDecimal getValorUnidadeMedidaProduto() {
		return getValorTotalSemDesconto().setScale(5, BigDecimal.ROUND_HALF_EVEN).divide(getQuantidadeUnidadeMedida(), 5, BigDecimal.ROUND_HALF_EVEN);
	}
	
	public BigDecimal getValorTotalIpi(){
		return getValorTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN).multiply(aliquotaIpi.divide(BigDecimal.valueOf(100)));
	}
	
	public BigDecimal getValorBaseCalculoIcms() {
      return getValorTotal().setScale(5, BigDecimal.ROUND_HALF_EVEN).multiply(getPorcentagemBaseCalculoIcms().divide(new BigDecimal(100)));
   }
	
	public BigDecimal getValorTotalCompra(){
		return quantidade.multiply(valorCompra);
	}
	
	public BigDecimal getQuantidadeUnidadeMedida() {
		BigDecimal resultado = BigDecimal.ZERO;
		
      /*
       * TODO fazer para as demais unidades de medida
       * 
       */
      if(unidadeMedida.getSigla().equals("UNID.")){
      	resultado = quantidade;
      }else if(unidadeMedida.getSigla().equals("KG")){
      	resultado = pesoProduto.multiply(quantidade);
      }
//		switch(unidadeMedida){
//		case UNIDADE:
//			resultado = quantidade;
//			break;
//		case QUILO:
//			resultado = pesoProduto.multiply(quantidade);
//			break;
//		case LITRO:
//			resultado = volumeProduto.multiply(quantidade);
//			break;
//		case METRO:
//			resultado = comprimentoProduto.multiply(quantidade);
//			break;
//		case METRO_QUADRADO:
//			resultado = areaProduto.multiply(quantidade);
//			break;
//		default:
//			resultado = quantidade;
//			break;
//		}

      return resultado;
	}
	
	public Set<ItemPedido> getItensAgrupados() {
		return Collections.unmodifiableSet(itensAgrupados);
	}

	public List<ItemPedido> getItensAgrupadosLista() {
		return new ArrayList<ItemPedido>(itensAgrupados);
	}

	public boolean isPossuiItensAgrupados(){
		return itensAgrupados.size() > 0;
	}
	
	public boolean isPossuiEstoque() {
		return produto.getQuantidadeEstoque().doubleValue() >= quantidade.doubleValue();
	}
	
	
   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }
   
   public Integer getNumeroItem() {
      return numeroItem;
   }

   public void setNumeroItem(Integer numeroItem) {
      this.numeroItem = numeroItem;
   }

   public BigDecimal getQuantidade() {
      return quantidade;
   }

   public void setQuantidade(BigDecimal quantidade) {
      this.quantidade = quantidade;
   }

   public BigDecimal getValorUnitario() {
      return valorUnitario;
   }

   public void setValorUnitario(BigDecimal valorUnitario) {
      this.valorUnitario = valorUnitario;
   }

   public Integer getId() {
      return id;
   }

   public EstoqueProduto getEstoque() {
      return estoque;
   }

   public void setEstoque(EstoqueProduto estoque) {
      this.estoque = estoque;
   }

   public Date getDataInicioReserva() {
      return dataInicioReserva;
   }

   public void setDataInicioReserva(Date dataInicioReserva) {
      this.dataInicioReserva = dataInicioReserva;
   }

   public Date getDataFimReserva() {
      return dataFimReserva;
   }

   public void setDataFimReserva(Date dataFimReserva) {
      this.dataFimReserva = dataFimReserva;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public String getObservacao() {
      return observacao;
   }

   public void setObservacao(String observacao) {
      this.observacao = observacao;
   }

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getAliquotaIcms() {
		return aliquotaIcms;
	}

	public void setAliquotaIcms(BigDecimal aliquotaIcms) {
		this.aliquotaIcms = aliquotaIcms;
	}

	public BigDecimal getAliquotaIpi() {
		return aliquotaIpi;
	}

	public void setAliquotaIpi(BigDecimal aliquotaIpi) {
		this.aliquotaIpi = aliquotaIpi;
	}

	public BigDecimal getAliquotaCofins() {
		return aliquotaCofins;
	}

	public void setAliquotaCofins(BigDecimal aliquotaCofins) {
		this.aliquotaCofins = aliquotaCofins;
	}

	public BigDecimal getAliquotaPis() {
		return aliquotaPis;
	}

	public void setAliquotaPis(BigDecimal aliquotaPis) {
		this.aliquotaPis = aliquotaPis;
	}

	public BigDecimal getValorImpostoImportacao() {
		return valorImpostoImportacao;
	}

	public void setValorImpostoImportacao(BigDecimal valorImpostoImportacao) {
		this.valorImpostoImportacao = valorImpostoImportacao;
	}

	public BigDecimal getValorBaseCalculoImportacao() {
		return valorBaseCalculoImportacao;
	}

	public void setValorBaseCalculoImportacao(
	      BigDecimal valorBaseCalculoImportacao) {
		this.valorBaseCalculoImportacao = valorBaseCalculoImportacao;
	}

	public BigDecimal getValorDespesasAduaneiras() {
		return valorDespesasAduaneiras;
	}

	public void setValorDespesasAduaneiras(BigDecimal valorDespesasAduaneiras) {
		this.valorDespesasAduaneiras = valorDespesasAduaneiras;
	}

	public BigDecimal getValorIof() {
		return valorIof;
	}

	public void setValorIof(BigDecimal valorIof) {
		this.valorIof = valorIof;
	}

	public String getNumeroDocumentoImportacao() {
		return numeroDocumentoImportacao;
	}

	public void setNumeroDocumentoImportacao(String numeroDocumentoImportacao) {
		this.numeroDocumentoImportacao = numeroDocumentoImportacao;
	}

	public Integer getOrigemProduto() {
		return origemProduto;
	}

	public void setOrigemProduto(Integer origemProduto) {
		this.origemProduto = origemProduto;
	}

	public BigDecimal getPesoProduto() {
		return pesoProduto;
	}

	public void setPesoProduto(BigDecimal pesoProduto) {
		this.pesoProduto = pesoProduto;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

	public Integer getNcm() {
		return ncm;
	}

	public void setNcm(Integer ncm) {
		this.ncm = ncm;
	}

	public Integer getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(Integer cstIcms) {
		this.cstIcms = cstIcms;
	}

	public Integer getCstIpi() {
		return cstIpi;
	}

	public void setCstIpi(Integer cstIpi) {
		this.cstIpi = cstIpi;
	}

	public Integer getCstCofins() {
		return cstCofins;
	}

	public void setCstCofins(Integer cstCofins) {
		this.cstCofins = cstCofins;
	}

	public Integer getCstPis() {
		return cstPis;
	}

	public void setCstPis(Integer cstPis) {
		this.cstPis = cstPis;
	}

	public BigDecimal getPorcentagemBaseCalculoIcms() {
		return porcentagemBaseCalculoIcms;
	}

	public void setPorcentagemBaseCalculoIcms(
	      BigDecimal porcentagemBaseCalculoIcms) {
		this.porcentagemBaseCalculoIcms = porcentagemBaseCalculoIcms;
	}

	public BigDecimal getPorcentagemBaseCalculoIcmsSt() {
		return porcentagemBaseCalculoIcmsSt;
	}

	public void setPorcentagemBaseCalculoIcmsSt(
	      BigDecimal porcentagemBaseCalculoIcmsSt) {
		this.porcentagemBaseCalculoIcmsSt = porcentagemBaseCalculoIcmsSt;
	}

	public BigDecimal getPorcentagemBaseCalculoIcmsStRet() {
		return porcentagemBaseCalculoIcmsStRet;
	}

	public void setPorcentagemBaseCalculoIcmsStRet(
	      BigDecimal porcentagemBaseCalculoIcmsStRet) {
		this.porcentagemBaseCalculoIcmsStRet = porcentagemBaseCalculoIcmsStRet;
	}

	public BigDecimal getPorcentagemBaseCalculoIpi() {
		return porcentagemBaseCalculoIpi;
	}

	public void setPorcentagemBaseCalculoIpi(BigDecimal porcentagemBaseCalculoIpi) {
		this.porcentagemBaseCalculoIpi = porcentagemBaseCalculoIpi;
	}

	public BigDecimal getPorcentagemBaseCalculoCofins() {
		return porcentagemBaseCalculoCofins;
	}

	public void setPorcentagemBaseCalculoCofins(
	      BigDecimal porcentagemBaseCalculoCofins) {
		this.porcentagemBaseCalculoCofins = porcentagemBaseCalculoCofins;
	}

	public BigDecimal getPorcentagemBaseCalculoPis() {
		return porcentagemBaseCalculoPis;
	}

	public void setPorcentagemBaseCalculoPis(BigDecimal porcentagemBaseCalculoPis) {
		this.porcentagemBaseCalculoPis = porcentagemBaseCalculoPis;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Integer getCodigoSituacaoOperacao() {
		return codigoSituacaoOperacao;
	}

	public void setCodigoSituacaoOperacao(Integer codigoSituacaoOperacao) {
		this.codigoSituacaoOperacao = codigoSituacaoOperacao;
	}

	public BigDecimal getAliquotaIcmsStRet() {
		return aliquotaIcmsStRet;
	}

	public void setAliquotaIcmsStRet(BigDecimal aliquotaIcmsStRet) {
		this.aliquotaIcmsStRet = aliquotaIcmsStRet;
	}

	public BigDecimal getAliquotaIcmsSt() {
		return aliquotaIcmsSt;
	}

	public void setAliquotaIcmsSt(BigDecimal aliquotaIcmsSt) {
		this.aliquotaIcmsSt = aliquotaIcmsSt;
	}

	public DocumentoImportacao getDocumentoImportacao() {
		return documentoImportacao;
	}

	public void setDocumentoImportacao(DocumentoImportacao documentoImportacao) {
		this.documentoImportacao = documentoImportacao;
	}

	public BigDecimal getVolumeProduto() {
		return volumeProduto;
	}

	public void setVolumeProduto(BigDecimal volumeProduto) {
		this.volumeProduto = volumeProduto;
	}

	public BigDecimal getAreaProduto() {
		return areaProduto;
	}

	public void setAreaProduto(BigDecimal areaProduto) {
		this.areaProduto = areaProduto;
	}

	public BigDecimal getComprimentoProduto() {
		return comprimentoProduto;
	}

	public void setComprimentoProduto(BigDecimal comprimentoProduto) {
		this.comprimentoProduto = comprimentoProduto;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

}
