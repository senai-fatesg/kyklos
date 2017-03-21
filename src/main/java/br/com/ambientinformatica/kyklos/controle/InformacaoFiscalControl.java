package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;
import br.com.ambientinformatica.kyklos.entidade.InformacaoFiscal;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.persistencia.CfopDao;
import br.com.ambientinformatica.kyklos.persistencia.GrupoDiferencaFiscalDao;
import br.com.ambientinformatica.kyklos.persistencia.InformacaoFiscalDao;
import br.com.ambientinformatica.kyklos.persistencia.ProdutoDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Named("InformacaoFiscalControl")
@Scope("conversation")
public class InformacaoFiscalControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   ProdutoDao produtoDao;

   @Autowired
   CfopDao cfopDao;

   @Autowired
   InformacaoFiscalDao informacaoFiscalDao;

   @Autowired
   GrupoDiferencaFiscalDao grupoDiferencaFiscalDao;

   private List<InformacaoFiscal> informacoesFiscais = new ArrayList<InformacaoFiscal>();
   
   private InformacaoFiscal informacaoFiscal = new InformacaoFiscal();

   private InformacaoFiscal informacaoFiscalSelecionada = new InformacaoFiscal();

   private boolean modoEdicao = false;

   public List<Produto> completeProduto(String codigoDescricao) {
      try {
         return produtoDao.listarProdutos(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), codigoDescricao);
      } catch (KyklosException e) {
         e.printStackTrace();
         return null;
      }
   }

   public List<GrupoDiferencaFiscal> completeGrupoDiferencaFiscal(String descricao) {
      return grupoDiferencaFiscalDao.listarGrupoDiferencaFiscal(descricao);
   }

   public List<Cfop> completeCfop(Integer numero) {
      return cfopDao.listarCfop(numero);
   }

   public void listarInformacoesFiscais(){
      try {

         informacoesFiscais = informacaoFiscalDao.listar(informacaoFiscal.getCfop(), informacaoFiscal.getProduto(), informacaoFiscal.getNcm(), informacaoFiscal.getUfOrigem(), informacaoFiscal.getUfDestino(), informacaoFiscal.getGrupoDiferencaFiscal());

      } catch (KyklosException e) {

         UtilFaces.addMensagemFaces(e);

      }
   }

   public void salvarInformacoesFiscais() {
      try {
         
         InformacaoFiscal informacaoFiscalConsultada = informacaoFiscalDao.consultarInformacaoFiscal(informacaoFiscal.getCfop(), informacaoFiscal.getProduto(), informacaoFiscal.getNcm(), informacaoFiscal.getUfOrigem(), informacaoFiscal.getUfDestino(), informacaoFiscal.getContribuinte(), informacaoFiscal.getGrupoDiferencaFiscal());

         if(informacaoFiscalConsultada == null){

            informacaoFiscalDao.incluir(informacaoFiscal);
            UtilFaces.addMensagemFaces("Informação Fiscal cadastrada com sucesso!");

         } else {
            
            UtilFaces.addMensagemFaces("Informação fiscal já existente");
            
         }
         
         informacaoFiscal = new InformacaoFiscal();
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }

   }
   
   public void editarInformacoesFiscais() {
      try {

         InformacaoFiscal informacaoFiscalConsultada = informacaoFiscalDao.consultar(informacaoFiscal.getId());
         
         if(informacaoFiscalConsultada != null){
            informacaoFiscalDao.alterar(informacaoFiscal);
            listarInformacoesFiscais();
            modoEdicao = false;
            UtilFaces.addMensagemFaces("Informação Fiscal alterada com sucesso!");
            
         }
         
         else {
            UtilFaces.addMensagemFaces("Informação fiscal não encontrada!");
         }
         
         informacaoFiscal = new InformacaoFiscal();
         
      } catch (Exception e) {
         
         UtilFaces.addMensagemFaces(e);
      
      }
   }
   
   public void limparInformacoesFiscais() {
      
      informacaoFiscal = new InformacaoFiscal();
      informacaoFiscalSelecionada = new InformacaoFiscal();
      
   }
   
   public void selecionarInformacaoFiscal() {
      
      informacaoFiscalSelecionada = informacaoFiscalDao.consultar(informacaoFiscalSelecionada.getId());
      
      if(informacaoFiscalSelecionada != null) {
         
         informacaoFiscal = informacaoFiscalSelecionada;
         modoEdicao = true;
         
      }
      
   }

   public List<InformacaoFiscal> getInformacoesFiscais() {
      return informacoesFiscais;
   }

   public void setInformacoesFiscais(List<InformacaoFiscal> informacoesFiscais) {
      this.informacoesFiscais = informacoesFiscais;
   }

   public boolean isModoEdicao() {
      return modoEdicao;
   }

   public void setModoEdicao(boolean modoEdicao) {
      this.modoEdicao = modoEdicao;
   }

   public List<SelectItem> getUfs() {
      return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumUf.values()));
   }

   public InformacaoFiscal getInformacaoFiscalSelecionada() {
      return informacaoFiscalSelecionada;
   }

   public void setInformacaoFiscalSelecionada(InformacaoFiscal informacaoFiscalSelecionada) {
      this.informacaoFiscalSelecionada = informacaoFiscalSelecionada;
   }

   public InformacaoFiscal getInformacaoFiscal() {
      return informacaoFiscal;
   }

   public void setInformacaoFiscal(InformacaoFiscal informacaoFiscal) {
      this.informacaoFiscal = informacaoFiscal;
   }

   public int getCrtEmpresaCliente() {
      if(usuarioLogadoControl.getEmpresaUsuario().getEmpresa().getCrt().getCodigo() != null){
         return usuarioLogadoControl.getEmpresaUsuario().getEmpresa().getCrt().getCodigo();
      } else {
         return 0;
      }
   }

}
