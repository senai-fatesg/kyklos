package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class Pagamento extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "pagamento_seq")
   @SequenceGenerator(name="pagamento_seq", sequenceName = "pagamento_seq", allocationSize=1, initialValue=1)
   private Long id;

   @Temporal(TemporalType.DATE)
   private Date dataPagamento = new Date();

   private Boolean cancelado = false;

   @OneToMany(cascade = ALL, orphanRemoval= true)
   @JoinColumn(name="pagamento_id")
   private Set<FormaPagamento> formasPagamento = new HashSet<FormaPagamento>();

   @ManyToOne(optional=false)
   private Pessoa cliente;
   
   @ManyToOne(optional=false)
   private EmpresaCliente empresa;
   
   @OneToMany(cascade=ALL, orphanRemoval= true)
   @JoinColumn(name="pagamento_id")
   private List<Cheque> listaCheques = new ArrayList<Cheque>();

   @OneToMany(cascade=ALL, orphanRemoval = true)
   @JoinColumn(name="pagamento_id")
   private List<BoletoBancario> listaBoletosBancarios = new ArrayList<BoletoBancario>();

   @OneToMany(cascade=ALL, orphanRemoval = true)
   @JoinColumn(name="pagamento_id")
   private List<CartaoCredito> listaCartoesCredito = new ArrayList<CartaoCredito>();
   
   @OneToMany(cascade = ALL, orphanRemoval = true)
   @JoinColumn(name="pagamento_id")
   private List<DepositoContaCorrente> listaDepositosContaCorrente = new ArrayList<DepositoContaCorrente>();
   
   private String prazoBoletosNegociado;

   @Column(precision=10, scale=2)
   private BigDecimal valorPagoDinheiro = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorPagoEmCreditoCarteira = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorPagoEmCredito = BigDecimal.ZERO;
   
   @Column(precision=10, scale=2)
   private BigDecimal valorPagoContaCorrente = BigDecimal.ZERO;
   
   @Enumerated(EnumType.STRING)
   private EnumTipoRetornoTroco retornoTroco;

   /**
    * Se o pagamento foi efetuado
    */
	private boolean efetuado = false;
   
	/**
	 * Cancela um pagamento
	 */
   public void cancelar(){
      cancelado = true;
   }
   
   public void addFormaPagamento(FormaPagamento formaPagamento){
      formasPagamento.add(formaPagamento);
   }
   
   public void excluirFormaPagamento(FormaPagamento formaPagamento){
      formasPagamento.remove(formaPagamento);
   }
   
   public void addCheque(Cheque cheque){
      if(!getListaCheques().contains(cheque)){
         getListaCheques().add(cheque);
      }
   }

   /**
    * Limpa a lista de cheques
    * Adiciona todos os cheques informados
    * se a lista de cheques for null nada acontece
    * @param cheques
    */
   public void addCheques(Collection<Cheque> cheques){
      if (cheques != null) {
         getListaCheques().clear();
         getListaCheques().addAll(cheques);
      }
   }
   
   public void addDepositoContaCorrente(DepositoContaCorrente depositoContaCorrente){
      if(!getListaDepositosContaCorrente().contains(depositoContaCorrente)){
         getListaDepositosContaCorrente().add(depositoContaCorrente);
      }
   }

   /**
    * Limpa a lista de Depositos em Conta Corrente
    * Adiciona todos os depositos informados
    * se a lista de Depositos for null nada acontece
    * 
    * @param depositos
    */
   public void addDepositosContaCorrente(Collection<DepositoContaCorrente> depositos){
      if(depositos != null){
         getListaDepositosContaCorrente().clear();
         getListaDepositosContaCorrente().addAll(depositos);
      }
   }
   
   
   public void addCartaoCredito(CartaoCredito cartaoCredito){
      if(!listaCartoesCredito.contains(cartaoCredito)){
         listaCartoesCredito.add(cartaoCredito);
      }
   }
   
   /**
    * Limpa a lista de Cartoes
    * adiciona todos os cartoes informados
    * se a lista de cartoes for null nada acontece
    * @param cartoes
    */
   public void addCartoesCredito(Collection<CartaoCredito> cartoes){
      if (cartoes != null) {
         listaCartoesCredito.clear();
         listaCartoesCredito.addAll(cartoes);
      }
   }
   
   public void addBoletoBancario(BoletoBancario boletoBancario){
      if(!listaBoletosBancarios.contains(boletoBancario)){
         listaBoletosBancarios.add(boletoBancario);
      }
   }
   
   /**
    * Limpa a lista de boletos
    * adiciona todos os boletos informados
    * se a lista de boletos for null nada acontece
    * @param boletos
    */
   public void addBoletosBancarios(Collection<BoletoBancario> boletos){
      if (boletos != null) {
         listaBoletosBancarios.clear();
         listaBoletosBancarios.addAll(boletos);
      }
   }

   public void excluirCheque(Cheque cheque){
      getListaCheques().remove(cheque);
   }

   public void excluirBoletoBancario(BoletoBancario boletoBancario){
      listaBoletosBancarios.remove(boletoBancario);
   }

   public void excluirCartaoCredito(CartaoCredito cartaoCredito){
      listaCartoesCredito.remove(cartaoCredito);
   }
   
   /**
    * utilizar para renderizar ou não a grid para preenchimento dos cheques que serão utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoCheque(){
      for(FormaPagamento instrumento : formasPagamento){
         if(instrumento != null && instrumento.isCheque()){
            return true;
         }
      }
      return false;
   }
   
   /**
    * utilizar para renderizar ou não o Input de Dinheiro que será utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoDinheiro(){
      for(FormaPagamento instrumento : formasPagamento){
         if(instrumento != null && instrumento.isDinheiro()){
            return true;
         }
      }
      return false;
   }
   
   /**
    * utilizar para renderizar ou não o Input de Cartão de Crédito/Débito que será utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoCartaoCredito(){
      for(FormaPagamento instrumento : formasPagamento){
         if(instrumento != null && instrumento.isCartaoCredito()){
            return true;
         }
      }
      return false;
   }
   
   public boolean isTipoCartaoDebito(){
      for(FormaPagamento instrumento : formasPagamento){
         if(instrumento != null && instrumento.isCartaoDebito()){
            return true;
         }
      }
      return false;
   }
   
   /**
    * utilizar para renderizar ou não a grid para preenchimento dos cheques de Terceiros que serão utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoChequeTerceiros(){
      for(FormaPagamento instrumento : formasPagamento){
         if(instrumento != null && instrumento.isChequeTerceiros()){
            return true;
         }
      }
      return false;
   }
   
   /**
    * utilizar para renderizar ou não a grid para preenchimento dos boletos que serão utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoBoleto(){
      for(FormaPagamento instrumento : formasPagamento){
         if(instrumento != null && instrumento.isBoleto()){
            return true;
         }
      }
      return false;
   }
   
   /**
    * utilizar para renderizar ou não o Input de Deposito em Conta que será utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoDepositoConta(){
      for(FormaPagamento formaPagamento : formasPagamento){
         if(formaPagamento != null && formaPagamento.isDepositoEmConta()){
            return true;
         }
      }
      return false;
   }
   
   /**
    * utilizar para renderizar ou não o Input de Crédito em Carteira (Fiado, adicionando o valor no saldo do cliente com a empresa) que será utilizados para pagar o Pedido
    * 
    * @return true or false
    */
   public boolean isTipoEmCreditoCarteira(){
      for(FormaPagamento formaPagamento : formasPagamento){
         if(formaPagamento != null && formaPagamento.isEmCreditoCarteira()){
            return true;
         }
      }
      return false;
   }
   
   
   /**
    * Ainda não previsto no sistema
    * 
    * @return true or false
    */
   public boolean isTipoNotaPromissoria(){
      return false;
   }
   
   /**
    * utilizar para abrir ou não a tela de pagamento do Pedido.
    * 
    * Caso false, indica que o pagamento, mesmo com o status proprio para ser pago, não contém Formas de Pagamento adicionadas.
    * Solicite ao Vendedor/Caixa que informe a forma de pagamento do Pedido.
    * 
    * @return true or false
    */
   public boolean contemInstrumentoRecebimento(InstrumentoRecebimento instrumento){
      boolean resp = false;
      for(FormaPagamento f : formasPagamento){
         if(f.getInstrumentoRecebimento().equals(instrumento)){
            return true;
         }
      }
      return resp;
   }
   
   public void efetuarPagamento(){
      this.efetuado = true;
      this.dataPagamento = new Date();
   }
   /**
    * 
    * @return o valor total pago. Resultado da soma do dinheiro, boletos, cheques e credito
    */
   public BigDecimal getValorNegociado(){
      BigDecimal resultado = BigDecimal.valueOf(0);
      for(FormaPagamento forma : formasPagamento){
         resultado = resultado.add(forma.getValor()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
         resultado = resultado.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      }
      return resultado;
   }
   
   public BigDecimal getAPagarDinheiro(){
      BigDecimal totalDinheiro = BigDecimal.ZERO;
      for (FormaPagamento formaPagamento : formasPagamento) {
         if(formaPagamento.isDinheiro()){
            totalDinheiro = totalDinheiro.add(formaPagamento.getValor());
         }
      }
      return totalDinheiro;
   }
   
	public Long getId() {
	   return id;
   }

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Set<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(Set<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	public String getPrazoBoletosNegociado() {
		return prazoBoletosNegociado;
	}

	public void setPrazoBoletosNegociado(String prazoBoletosNegociado) {
		this.prazoBoletosNegociado = prazoBoletosNegociado;
	}

	public BigDecimal getValorPagoDinheiro() {
	   return valorPagoDinheiro;
	}

	public void setValorPagoDinheiro(BigDecimal valorPagoDinheiro) {
		this.valorPagoDinheiro = valorPagoDinheiro;
	}

	public BigDecimal getValorPagoEmCreditoCarteira() {
		return valorPagoEmCreditoCarteira;
	}

	public void setValorPagoEmCreditoCarteira(BigDecimal valorPagoEmCreditoCarteira) {
		this.valorPagoEmCreditoCarteira = valorPagoEmCreditoCarteira;
	}

	public BigDecimal getValorPagoEmCredito() {
		return valorPagoEmCredito;
	}

	public void setValorPagoEmCredito(BigDecimal valorPagoEmCredito) {
		this.valorPagoEmCredito = valorPagoEmCredito;
	}

	public BigDecimal getValorPagoContaCorrente() {
		return valorPagoContaCorrente;
	}

	public void setValorPagoContaCorrente(BigDecimal valorPagoContaCorrente) {
		this.valorPagoContaCorrente = valorPagoContaCorrente;
	}

	public EnumTipoRetornoTroco getRetornoTroco() {
		return retornoTroco;
	}

	public void setRetornoTroco(EnumTipoRetornoTroco retornoTroco) {
		this.retornoTroco = retornoTroco;
	}

	public boolean isEfetuado() {
		return efetuado;
	}

	public void setEfetuado(boolean efetuado) {
		this.efetuado = efetuado;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public EmpresaCliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaCliente empresa) {
		this.empresa = empresa;
	}

   public List<Cheque> getListaCheques() {
      return listaCheques;
   }

   public List<BoletoBancario> getListaBoletosBancarios() {
      return listaBoletosBancarios;
   }
   
   public List<CartaoCredito> getListaCartoesCredito() {
      return listaCartoesCredito;
   }

   public List<DepositoContaCorrente> getListaDepositosContaCorrente() {
      return listaDepositosContaCorrente;
   }
   
}