package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.AmbientValidator;
import br.com.ambientinformatica.util.Entidade;
import br.com.ambientinformatica.util.UtilNumero;

@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"codigo", "empresa_id"})
      })
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class Produto extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="produto_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="produto_seq", sequenceName="produto_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @ManyToOne(optional=false)
   private EmpresaCliente empresa;

   @Column(nullable=false)
   @NotEmpty(message="Código do produto obrigatório", groups=AmbientValidator.class)
   private String codigo;

   @Column(nullable=false)
   @NotEmpty(message="Descrição do produto obrigatória", groups=AmbientValidator.class)
   private String descricao;

   @ManyToOne(optional = false)
   private UnidadeMedida unidadeMedida;

   private boolean bemDeConsumo;
   
   private boolean travarPreco = false;
   
   @Column(precision=10,scale=3)
   private BigDecimal comprimento = BigDecimal.ZERO;

   @Column(precision=10,scale=3)
   private BigDecimal pesoBruto = BigDecimal.ZERO;

   @Column(precision=10,scale=3)
   private BigDecimal pesoLiquido = BigDecimal.ZERO;

   @Column(precision=10,scale=3)
   private BigDecimal volume = BigDecimal.ZERO;

   @Column(precision=10,scale=3)
   private BigDecimal area = BigDecimal.ZERO;

   @ManyToOne
   private Cor cor;

   @Column(precision=10,scale=3)
   private BigDecimal quantidadePacote = BigDecimal.ZERO;

   @Column(precision=10,scale=5)
   private BigDecimal valorUnidadeMedida = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal valorCompra = BigDecimal.ZERO;

   @Column(precision=10,scale=3)
   private BigDecimal quantidadeEstoque = BigDecimal.ZERO;

   @OneToMany(mappedBy="produtoRaiz", cascade={CascadeType.ALL, CascadeType.REMOVE})
   private Set<ProdutoAgrupado> produtosAgrupados = new HashSet<ProdutoAgrupado>();

   @Column(precision=10,scale=3)
   private BigDecimal quantidadeMaximaEstoque = BigDecimal.ZERO;

   @Column(precision=10,scale=3)
   private BigDecimal quantidadeMinimaEstoque = BigDecimal.ZERO;
   
   @ManyToOne(fetch=FetchType.LAZY)
   private Pessoa fornecedor;
   
   /**
    * ncm - nomenclatura comum do mercosul
    * http://www.desenvolvimento.gov.br/sitio/interna/interna.php?area=5&menu=1095&refr=605
    */
   private Integer ncm;

   private Integer generoNcm;
   
   private String codigoEan;
   

   /**
    * Inclui ou Altera a quantidade e o desconto de um produto agrupado.
    * 
    * Se o produto nao existir no conjunto de produtos agrupados este é incluido
    * 
    * Se o produto existir este é alterado.
    * Para alterar o produto agrupado ou o produto raiz é necessário
    * excluir o produto agrupado e incluir um novo com os dados alterados
    * @param produtoAgrupado
    */
   public void confirmarProdutoAgrupado(ProdutoAgrupado produtoAgrupado){
      if(produtosAgrupados.contains(produtoAgrupado)){
         for(ProdutoAgrupado pa : produtosAgrupados){
            if(pa.equals(produtoAgrupado)){
               pa.setValorProduto(produtoAgrupado.getValorProduto());
               pa.setQuantidade(produtoAgrupado.getQuantidade());
            }
         }
      }else{
         produtoAgrupado.setProdutoRaiz(this);
         produtosAgrupados.add(produtoAgrupado);
      }
   }

   public void removeProdutoAgrupado(ProdutoAgrupado produtoAgrupado){
      produtosAgrupados.remove(produtoAgrupado);
      produtoAgrupado.setProdutoRaiz(null);
   }
   
   public boolean isPossuiProdutosAgrupados(){
      return getProdutosAgrupados().size() > 0;
   }

   
   /**
    * Retorna o valor de venda do produto.
    * 
    * caso a unidade de medida seja UNIDADE, o valor de venda é o valor da unidade de medida
    * caso contrario será multiplicado quilos, litros, metros, etc, pelo valor da unidade de medida
    * @return
    */
   public BigDecimal calcularValorVenda(BigDecimal pesoLiquido, BigDecimal volume, BigDecimal comprimento, BigDecimal area) {
      if(isPossuiProdutosAgrupados()){
         BigDecimal valorTotal = BigDecimal.ZERO;
         for(ProdutoAgrupado pa : produtosAgrupados){
            valorTotal = valorTotal.add(pa.getValorTotal());
         }
         return valorTotal;
      }else{
         BigDecimal resultado = BigDecimal.ZERO;

         /*
          * TODO fazer para as demais unidades de medida
          * 
          */
         if(unidadeMedida.getSigla().equals("UNID.")){
         	resultado = valorUnidadeMedida;
         }else if(unidadeMedida.getSigla().equals("KG")){
         	resultado = pesoLiquido.multiply(valorUnidadeMedida);
         }
         
//         switch(unidadeMedida){
//         case UNIDADE:
//            break;
//         case QUILO:
//            break;
//         case LITRO:
//            resultado = volume.multiply(valorUnidadeMedida);
//            break;
//         case METRO:
//            resultado = comprimento.multiply(valorUnidadeMedida);
//            break;
//         case METRO_QUADRADO:
//            resultado = area.multiply(valorUnidadeMedida);
//            break;
//         default:
//            resultado = valorUnidadeMedida;
//            break;
//         }
         return resultado;
      }

   }

   /*
    * Para valores de Custo da mercadoria
    */
   public BigDecimal getValorBrutoCusto(){
      return getQuantidadeEstoque().multiply(getValorCompra());
   }
   
   /*
    * Para valores de Venda da mercadoria 
    */
   public BigDecimal getValorBruto(){
      return getQuantidadeEstoque().multiply(getValorVenda());
   }

   public BigDecimal getValorVenda() {
      return calcularValorVenda(pesoLiquido, volume, comprimento, area);
   }

   public BigDecimal calcularDesconto(BigDecimal porcentagemDesconto, BigDecimal valor){
      BigDecimal valorVendaDesconto = BigDecimal.ZERO;
      /*
       * Se o produto tiver o atributo travar preco marcado (true)
       * nao é calculado com desconto.
       */

      if(isTravarPreco() || isPossuiProdutosAgrupados()){
         valorVendaDesconto = valor;
      }else{
         BigDecimal porcent = UtilNumero.dividir(porcentagemDesconto,100);
         BigDecimal diferenca = BigDecimal.valueOf(1).subtract(porcent);

         valorVendaDesconto = valor.multiply(diferenca);
      }
      return valorVendaDesconto;
   }

   public BigDecimal getValorVendaDesconto(BigDecimal porcentagemDesconto){
      BigDecimal valorVenda = calcularValorVenda(pesoLiquido, volume, comprimento, area);
      return calcularDesconto(porcentagemDesconto, valorVenda);
   }

   public BigDecimal getValorUnidadeMedidaDesconto(BigDecimal porcentagemDesconto){
      return calcularDesconto(porcentagemDesconto, valorUnidadeMedida).setScale(5, BigDecimal.ROUND_HALF_EVEN);
   }
   
   public Set<ProdutoAgrupado> getProdutosAgrupados() {
      return Collections.unmodifiableSet(produtosAgrupados);
   }

	public EmpresaCliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaCliente empresa) {
		this.empresa = empresa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public boolean isBemDeConsumo() {
		return bemDeConsumo;
	}

	public void setBemDeConsumo(boolean bemDeConsumo) {
		this.bemDeConsumo = bemDeConsumo;
	}

	public boolean isTravarPreco() {
		return travarPreco;
	}

	public void setTravarPreco(boolean travarPreco) {
		this.travarPreco = travarPreco;
	}

	public BigDecimal getComprimento() {
		return comprimento;
	}

	public void setComprimento(BigDecimal comprimento) {
		this.comprimento = comprimento;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public BigDecimal getQuantidadePacote() {
		return quantidadePacote;
	}

	public void setQuantidadePacote(BigDecimal quantidadePacote) {
		this.quantidadePacote = quantidadePacote;
	}

	public BigDecimal getValorUnidadeMedida() {
		return valorUnidadeMedida;
	}

	public void setValorUnidadeMedida(BigDecimal valorUnidadeMedida) {
		this.valorUnidadeMedida = valorUnidadeMedida;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public BigDecimal getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(BigDecimal quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public BigDecimal getQuantidadeMaximaEstoque() {
		return quantidadeMaximaEstoque;
	}

	public void setQuantidadeMaximaEstoque(BigDecimal quantidadeMaximaEstoque) {
		this.quantidadeMaximaEstoque = quantidadeMaximaEstoque;
	}

	public BigDecimal getQuantidadeMinimaEstoque() {
		return quantidadeMinimaEstoque;
	}

	public void setQuantidadeMinimaEstoque(BigDecimal quantidadeMinimaEstoque) {
		this.quantidadeMinimaEstoque = quantidadeMinimaEstoque;
	}

	public Pessoa getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Pessoa fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getNcm() {
      return ncm;
   }

   public void setNcm(Integer ncm) {
      this.ncm = ncm;
   }

   public Integer getGeneroNcm() {
		return generoNcm;
	}

	public void setGeneroNcm(Integer generoNcm) {
		this.generoNcm = generoNcm;
	}

	public String getCodigoEan() {
		return codigoEan;
	}

	public void setCodigoEan(String codigoEan) {
		this.codigoEan = codigoEan;
	}

	public Integer getId() {
		return id;
	}

	public void setProdutosAgrupados(Set<ProdutoAgrupado> produtosAgrupados) {
		this.produtosAgrupados = produtosAgrupados;
	}

}