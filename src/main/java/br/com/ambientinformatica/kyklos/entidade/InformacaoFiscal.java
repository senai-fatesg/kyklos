package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;

@Entity
public class InformacaoFiscal implements Comparable<InformacaoFiscal>, Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "informacaofiscal_seq")
   @SequenceGenerator(name="informacaofiscal_seq", sequenceName = "informacaofiscal_seq", allocationSize=1, initialValue=1)
   private Integer id;

   /**
    * Código da situação tributária do ICMS
    */
   private Integer cstIcms;

   /**
    * Código da situação tributária do IPI
    */
   private Integer cstIpi;

   /**
    * Código da situação tributária do PIS
    */
   private Integer cstPis;

   /**
    * Código da situação tributária da COFINS
    */
   private Integer cstCofins;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIcms = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIpi = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaCofins = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal aliquotaPis = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoIcms = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoIpi = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoCofins = BigDecimal.ZERO;

   @Column(precision=10,scale=2)
   private BigDecimal porcentagemBaseCalculoPis = BigDecimal.ZERO;

   /**
    * Porcentagem do Valor da BC do ICMS ST retido 
    */
   @Column(precision=10,scale=2)
   private BigDecimal porcentagemVbcstret = BigDecimal.ZERO;

   /**
    * Aliquota do ICMS ST retido 
    */
   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIcmsstret = BigDecimal.ZERO;

   /**
    * Valor da BC do ICMS ST 
    */
   @Column(precision=10,scale=2)
   private BigDecimal porcentagemVbcst = BigDecimal.ZERO;

   /**
    * Valor do ICMS ST 
    */
   @Column(precision=10,scale=2)
   private BigDecimal aliquotaIcmsst = BigDecimal.ZERO;

   /*
    * Código de Situação da Operação - Simples Nacional
    */
   private Integer csosn;

   @ManyToOne
   private Cfop cfop;

   @ManyToOne
   private Produto produto;

   @ManyToOne
   private GrupoDiferencaFiscal grupoDiferencaFiscal;

   /**
    * nomenclatura comum do mercosul
    */
   private Integer ncm;

   private int prioridade;

   @Enumerated(EnumType.STRING)
   private EnumUf ufOrigem;

   @Enumerated(EnumType.STRING)
   private EnumUf ufDestino;

   private Boolean contribuinte;


   public void preencherInformacoesItemNota(ItemPedido itemNota){
      itemNota.setAliquotaIcms(getAliquotaIcms());
      itemNota.setAliquotaIpi(getAliquotaIpi());
      itemNota.setAliquotaCofins(getAliquotaCofins());
      itemNota.setAliquotaPis(getAliquotaPis());
      itemNota.setAliquotaIcmsStRet(getAliquotaIcmsstret());
      itemNota.setAliquotaIcmsSt(getAliquotaIcmsst());

      itemNota.setPorcentagemBaseCalculoCofins(getPorcentagemBaseCalculoCofins());
      itemNota.setPorcentagemBaseCalculoIcms(getPorcentagemBaseCalculoIcms());
      itemNota.setPorcentagemBaseCalculoIpi(getPorcentagemBaseCalculoIpi());
      itemNota.setPorcentagemBaseCalculoPis(getPorcentagemBaseCalculoPis());
      itemNota.setPorcentagemBaseCalculoIcmsStRet(getPorcentagemVbcstret());
      itemNota.setPorcentagemBaseCalculoIcmsSt(getPorcentagemVbcst());

      itemNota.setCstCofins(getCstCofins());
      itemNota.setCstIcms(getCstIcms());
      itemNota.setCstIpi(getCstIpi());
      itemNota.setCstPis(getCstPis());
      
      itemNota.setCodigoSituacaoOperacao(itemNota.getEstoque().getEstoque().getPessoaEmpresa().getEmpresa().getCrt().getCodigo());

   }

   public int compareTo(InformacaoFiscal o) {
      return prioridade - o.prioridade;
   }

   public Cfop getCfop() {
      return cfop;
   }

   public void setCfop(Cfop cfop) {
      this.cfop = cfop;
   }

   public Produto getProduto() {
      return produto;
   }

   public void setProduto(Produto produto) {
      this.produto = produto;
   }

   public Integer getNcm() {
      return ncm;
   }

   public void setNcm(Integer ncm) {
      this.ncm = ncm;
   }

   public EnumUf getUfOrigem() {
      return ufOrigem;
   }

   public void setUfOrigem(EnumUf ufOrigem) {
      this.ufOrigem = ufOrigem;
   }

   public EnumUf getUfDestino() {
      return ufDestino;
   }

   public void setUfDestino(EnumUf ufDestino) {
      this.ufDestino = ufDestino;
   }

   public Boolean getContribuinte() {
      return contribuinte;
   }

   public void setContribuinte(Boolean contribuinte) {
      this.contribuinte = contribuinte;
   }

   public Integer getId() {
      return id;
   }

   public BigDecimal getAliquotaIcms() {
      if(String.valueOf(aliquotaIcms).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return aliquotaIcms;
      }
   }

   public void setAliquotaIcms(BigDecimal aliquotaIcms) {
      this.aliquotaIcms = aliquotaIcms;
   }

   public BigDecimal getAliquotaIpi() {
      if(String.valueOf(aliquotaIpi).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return aliquotaIpi;
      }
   }

   public void setAliquotaIpi(BigDecimal aliquotaIpi) {
      this.aliquotaIpi = aliquotaIpi;
   }

   public BigDecimal getAliquotaCofins() {
      if(String.valueOf(aliquotaCofins).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return aliquotaCofins;
      }
   }

   public void setAliquotaCofins(BigDecimal aliquotaCofins) {
      this.aliquotaCofins = aliquotaCofins;
   }

   public BigDecimal getAliquotaPis() {
      if(String.valueOf(aliquotaPis).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return aliquotaPis;
      }
   }

   public void setAliquotaPis(BigDecimal aliquotaPis) {
      this.aliquotaPis = aliquotaPis;
   }
                     
   public BigDecimal getPorcentagemBaseCalculoIcms() {
      if(String.valueOf(porcentagemBaseCalculoIcms).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return porcentagemBaseCalculoIcms;
      }
   }

   public void setPorcentagemBaseCalculoIcms(BigDecimal porcentagemBaseCalculoIcms) {
      this.porcentagemBaseCalculoIcms = porcentagemBaseCalculoIcms;
   }

   public BigDecimal getPorcentagemBaseCalculoIpi() {
      if(String.valueOf(porcentagemBaseCalculoIpi).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return porcentagemBaseCalculoIpi;
      }
   }


   public void setPorcentagemBaseCalculoIpi(BigDecimal porcentagemBaseCalculoIpi) {
      this.porcentagemBaseCalculoIpi = porcentagemBaseCalculoIpi;
   }

   public BigDecimal getPorcentagemBaseCalculoCofins() {
      if(String.valueOf(porcentagemBaseCalculoCofins).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return porcentagemBaseCalculoCofins;
      }
   }

   public void setPorcentagemBaseCalculoCofins(BigDecimal porcentagemBaseCalculoCofins) {
      this.porcentagemBaseCalculoCofins = porcentagemBaseCalculoCofins;
   }

   public BigDecimal getPorcentagemBaseCalculoPis() {
      if(String.valueOf(porcentagemBaseCalculoPis).equals("0E-10")){
         return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }else{
         return porcentagemBaseCalculoPis;
      }
   }

   public void setPorcentagemBaseCalculoPis(BigDecimal porcentagemBaseCalculoPis) {
      this.porcentagemBaseCalculoPis = porcentagemBaseCalculoPis;
   }

   public int getPrioridade() {
      return prioridade;
   }

   public void setPrioridade(int prioridade) {
      this.prioridade = prioridade;
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

   public Integer getCstPis() {
      return cstPis;
   }

   public void setCstPis(Integer cstPis) {
      this.cstPis = cstPis;
   }

   public Integer getCstCofins() {
      return cstCofins;
   }

   public void setCstCofins(Integer cstCofins) {
      this.cstCofins = cstCofins;
   }

   public GrupoDiferencaFiscal getGrupoDiferencaFiscal() {
      return grupoDiferencaFiscal;
   }

   public void setGrupoDiferencaFiscal(GrupoDiferencaFiscal grupoDiferencaFiscal) {
      this.grupoDiferencaFiscal = grupoDiferencaFiscal;
   }

   public BigDecimal getAliquotaIcmsstret() {
      return aliquotaIcmsstret;
   }

   public void setAliquotaIcmsstret(BigDecimal aliquotaIcmsstret) {
      this.aliquotaIcmsstret = aliquotaIcmsstret;
   }

   public BigDecimal getAliquotaIcmsst() {
      return aliquotaIcmsst;
   }

   public void setAliquotaIcmsst(BigDecimal aliquotaIcmsst) {
      this.aliquotaIcmsst = aliquotaIcmsst;
   }

   public Integer getCsosn() {
      return csosn;
   }

   public void setCsosn(Integer csosn) {
      this.csosn = csosn;
   }

   public BigDecimal getPorcentagemVbcstret() {
      return porcentagemVbcstret;
   }

   public void setPorcentagemVbcstret(BigDecimal porcentagemVbcstret) {
      this.porcentagemVbcstret = porcentagemVbcstret;
   }

   public BigDecimal getPorcentagemVbcst() {
      return porcentagemVbcst;
   }

   public void setPorcentagemVbcst(BigDecimal porcentagemVbcst) {
      this.porcentagemVbcst = porcentagemVbcst;
   }

}
