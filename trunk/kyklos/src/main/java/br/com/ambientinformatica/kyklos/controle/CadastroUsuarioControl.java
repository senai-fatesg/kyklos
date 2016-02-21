package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaClienteDao;

@Named("CadastroUsuarioControl")
@Scope("conversation")
public class CadastroUsuarioControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   private EmpresaClienteDao empresaClienteDao;

   private EmpresaCliente empresaCliente = new EmpresaCliente();

   private String senha;

   private String senhaConfirm;

   public void cadastrarPessoaEmpresaEUsuario(){
      try{
         if(senha != null && senha.equals(senhaConfirm)){
            empresaClienteDao.incluirNovoUsuario(empresaCliente, senha);
            empresaCliente = new EmpresaCliente();
            UtilFaces.addMensagemFaces("Cadastro realizado com Sucesso! O link de ativação de cadastro foi enviado para o seu e-mail!");
         }else{
            throw new PedidoException("As senhas digitadas divergem");
         }
      }catch(PedidoException e){
         UtilFaces.addMensagemFaces(e);
      }
   }

   public EmpresaCliente getEmpresaCliente() {
      return empresaCliente;
   }

   public void setEmpresaCliente(EmpresaCliente empresaCliente) {
      this.empresaCliente = empresaCliente;
   }

   public String getSenha() {
      return senha;
   }

   public void setSenha(String senha) {
      this.senha = senha;
   }

   public String getSenhaConfirm() {
      return senhaConfirm;
   }

   public void setSenhaConfirm(String senhaConfirm) {
      this.senhaConfirm = senhaConfirm;
   }
   
}
