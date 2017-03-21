package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.Banco;
import br.com.ambientinformatica.kyklos.entidade.ContaCorrente;
import br.com.ambientinformatica.kyklos.entidade.ContratoBoletoBancario;
import br.com.ambientinformatica.kyklos.persistencia.BancoDao;
import br.com.ambientinformatica.kyklos.persistencia.ContaCorrenteDao;
import br.com.ambientinformatica.kyklos.persistencia.ContratoBoletoBancarioDao;

@Named("ContaCorrenteControl")
@Scope("conversation")
public class ContaCorrenteControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private ContaCorrenteDao contaCorrenteDao;

   @Autowired
   private BancoDao bancoDao;

   @Autowired
   private ContratoBoletoBancarioDao contratoBoletoBancarioDao;

   private List<ContaCorrente> listaContasCorrente = new ArrayList<ContaCorrente>();

   private ContaCorrente contaCorrente = new ContaCorrente();

   private String buscaText = new String();

   private Boolean edicaoContaCorrente = false;

   private boolean contaContratoBoletoBancario;


   public void limparContaCorrente(){
      contaCorrente = new ContaCorrente();
      edicaoContaCorrente = false;
   }

   public void acionarModoEdicao(ContaCorrente cc){
      
      edicaoContaCorrente = true;
      
      ContratoBoletoBancario contratoBoletoConsultado = new ContratoBoletoBancario();
      contratoBoletoConsultado = contratoBoletoBancarioDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), cc.getNumeroConta());
      
      if(contratoBoletoConsultado == null) {
      
         contaContratoBoletoBancario = false;
      
      } else {
      
         contaContratoBoletoBancario = true;
      
      }
   }

   public void editarContaCorrente(){
      try {

         ContaCorrente contaCorrenteConsultada = contaCorrenteDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), contaCorrente.getNumeroConta());

         if(contaCorrenteConsultada != null){

            contaCorrente = contaCorrenteDao.alterar(contaCorrente);

            if (contaContratoBoletoBancario == true) {
               
               ContratoBoletoBancario contratoBoletoConsultado = new ContratoBoletoBancario();
               contratoBoletoConsultado = contratoBoletoBancarioDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), contaCorrente.getNumeroConta());

               if (contratoBoletoConsultado == null) {
                 
                  ContratoBoletoBancario contratoBoleto = new ContratoBoletoBancario();
                  contratoBoleto.setEmpresaCliente(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
                  contratoBoleto.setContaCorrente(contaCorrente);
                  contratoBoletoBancarioDao.incluir(contratoBoleto);
                  UtilFaces.addMensagemFaces("Contrato de Boleto Bancário incluído com sucesso!");

               }

            } else {
               
               ContratoBoletoBancario contratoBoletoConsultado = new ContratoBoletoBancario();
               contratoBoletoConsultado = contratoBoletoBancarioDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), contaCorrente.getNumeroConta());

               if (contratoBoletoConsultado != null) {
                  
                  contratoBoletoBancarioDao.excluirPorId(contratoBoletoConsultado.getId());
                  UtilFaces.addMensagemFaces("Contrato de Boleto Bancário Removido!");
               
               }
               
            }

         }

         limparContaCorrente();
         contaContratoBoletoBancario = false;

      } catch (Exception e) {

         UtilFaces.addMensagemFaces("Erro ao salvar alterações na Conta Corrente");

      }
   }

   public void cadastrarContaCorrente(){
      try {

         edicaoContaCorrente = false;

         contaCorrente.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());

         ContaCorrente contaCorrenteConsultada = contaCorrenteDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), contaCorrente.getNumeroConta());

         if (contaCorrenteConsultada == null) {

            contaCorrenteDao.incluir(contaCorrente);

            contaCorrenteConsultada = contaCorrenteDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), contaCorrente.getNumeroConta());

            UtilFaces.addMensagemFaces("Cadastro de Conta Corrente realizado com sucesso!");

            UtilFaces.addMensagemFaces("Conta Nº: " + contaCorrenteConsultada.getNumeroConta() + "| Agência: " + contaCorrenteConsultada.getAgencia() + "| Banco: " + contaCorrenteConsultada.getBanco().getNome());

            if (contaContratoBoletoBancario == true) {
               ContratoBoletoBancario contratoBoletoConsultado = new ContratoBoletoBancario();
               contratoBoletoConsultado = contratoBoletoBancarioDao.consultarPorEmpresaENumeroConta(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), contaCorrente.getNumeroConta());

               if (contratoBoletoConsultado == null) {

                  ContratoBoletoBancario contratoBoleto = new ContratoBoletoBancario();
                  contratoBoleto.setEmpresaCliente(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
                  contratoBoleto.setContaCorrente(contaCorrente);
                  contratoBoletoBancarioDao.incluir(contratoBoleto);

               }

            }

         } else {

            UtilFaces.addMensagemFaces("Cadastro de ContaCorrente já existente");
            UtilFaces.addMensagemFaces("Conta Nº: " + contaCorrenteConsultada.getNumeroConta() + " | Agência: " + contaCorrenteConsultada.getAgencia() + " | Banco: " + contaCorrenteConsultada.getBanco().getNome());

         }

         limparContaCorrente();
         contaContratoBoletoBancario = false;
         
      } catch (Exception e) {

         UtilFaces.addMensagemFaces(e);

      }
   }

   public void listarContaCorrente(){
      listaContasCorrente = contaCorrenteDao.listarContasCorrentePorNumeroOuNomeBanco(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), buscaText);
   }

   public List<Banco> completeBanco(String nome) {
      return bancoDao.listarBancosPorNome(nome);
   }

   public List<ContaCorrente> getListaContasCorrente() {
      return listaContasCorrente;
   }

   public void setListaContasCorrente(List<ContaCorrente> listaContasCorrente) {
      this.listaContasCorrente = listaContasCorrente;
   }

   public ContaCorrente getContaCorrente() {
      return contaCorrente;
   }

   public void setContaCorrente(ContaCorrente contaCorrente) {
      this.contaCorrente = contaCorrente;
   }

   public String getBuscaText() {
      return buscaText;
   }

   public void setBuscaText(String buscaText) {
      this.buscaText = buscaText;
   }

   public Boolean getEdicaoContaCorrente() {
      return edicaoContaCorrente;
   }

   public void setEdicaoContaCorrente(Boolean edicaoContaCorrente) {
      this.edicaoContaCorrente = edicaoContaCorrente;
   }

   public Boolean getContaContratoBoletoBancario() {
      return contaContratoBoletoBancario;
   }

   public void setContaContratoBoletoBancario(Boolean contaContratoBoletoBancario) {
      this.contaContratoBoletoBancario = contaContratoBoletoBancario;
   }



}
