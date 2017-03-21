package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaUsuarioDao;
import br.com.ambientinformatica.kyklos.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.AmbientException;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;
import br.com.ambientinformatica.util.UtilLog;

@Named
@Scope("session")
public class UsuarioLogadoControl implements Serializable {

   private static final long serialVersionUID = 1L;

   @Autowired
   private UsuarioDao usuarioDao;

   @Autowired
   private EmpresaUsuarioDao empresaUsuarioDao;

   private Usuario usuario;

   private EmpresaUsuario empresaUsuario;

   private boolean variasEmpresas = true;

   private String senhaAtual = null;

   private String novaSenha = null;

   private String confirmacaoNovaSenha = null;
   
   @PostConstruct
   public void init(){
      try {
         Principal principal = UtilFaces.getRequest().getUserPrincipal();
         if(principal != null){
            String usuarioStr = principal.getName();
            this.usuario = usuarioDao.consultar(usuarioStr);

            List<EmpresaUsuario> empresasUsuario = empresaUsuarioDao.listarPorUsuario(getUsuario(), null);
            if(empresasUsuario.size() == 1){
               this.empresaUsuario = empresasUsuario.get(0);
               variasEmpresas = false;
            }else if(empresasUsuario.size() > 1){
               for(EmpresaUsuario e : empresasUsuario){
                  if(e.isPrincipal()){
                     this.empresaUsuario = e;
                     break;
                  }
               }
               if(empresaUsuario == null){
                  this.empresaUsuario = empresasUsuario.get(0);
               }
            }
         }
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
      }
   }
   
   public void alterar() throws AmbientException{
      
      if(!usuario.getPessoa().getNome().equals("")){
         usuarioDao.alterar(usuario);
         UtilFaces.addMensagemFaces("Alterações de cadastro realizadas com sucesso!");
      }else{
         UtilFaces.addMensagemFaces("O campo \"Nome\" não pode ser vazio.");
      }
      
   }

   public void limparSenhas(){
      senhaAtual = null;
      novaSenha = null;
      confirmacaoNovaSenha = null;
   }

   public void alterarSenha() throws AmbientException{
      if(senhaAtual.equals(usuario.getSenha())){
         if(novaSenha.equals(confirmacaoNovaSenha)){
            usuario.setSenhaNaoCriptografada(confirmacaoNovaSenha);
            usuarioDao.alterar(usuario);
            UtilFaces.addMensagemFaces("Senha Alterada com sucesso!");
         }else{
            UtilFaces.addMensagemFaces("As senhas digitadas devem ser iguais.");
         }
      }else{
         UtilFaces.addMensagemFaces("Senha não confere.");
      }

   }

   public List<EmpresaUsuario> completeEmpresa(String nome) {
      return empresaUsuarioDao.listarPorUsuario(getUsuario(), nome);
   }   

   public Usuario getUsuario() {
      return usuario;
   }

   public EmpresaUsuario getEmpresaUsuario() {
      return empresaUsuario;
   }

   public void setEmpresaUsuario(EmpresaUsuario empresaUsuario) {
      this.empresaUsuario = empresaUsuario;
   }

   public boolean isVariasEmpresas() {
      return variasEmpresas;
   }

   public String getSenhaAtual() {
      return senhaAtual;
   }

   public void setSenhaAtual(String senhaAtual) {
      this.senhaAtual = UtilHash.gerarStringHash(senhaAtual, Algoritimo.MD5);
   }

   public String getNovaSenha() {
      return novaSenha;
   }

   public void setNovaSenha(String novaSenha) {
      this.novaSenha = novaSenha;
   }

   public String getConfirmacaoNovaSenha() {
      return confirmacaoNovaSenha;
   }

   public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
      this.confirmacaoNovaSenha = confirmacaoNovaSenha;
   }



}
