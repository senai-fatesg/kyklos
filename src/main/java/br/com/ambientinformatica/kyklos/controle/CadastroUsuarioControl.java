package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaClienteDao;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaUsuarioDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.UsuarioDao;

@Named("CadastroUsuarioControl")
@Scope("conversation")
public class CadastroUsuarioControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   private EmpresaClienteDao empresaClienteDao;

   @Autowired
   private UsuarioDao usuarioDao;

   @Autowired
   private EmpresaUsuarioDao empresaUsuarioDao;
   
   @Autowired
   private PessoaEmpresaDao pessoaEmpresaDao;
   
   private EmpresaCliente empresaCliente = new EmpresaCliente();

   private String senha;

   private String senhaConfirm;

   public void cadastrarPessoaEmpresaEUsuario(){
      try{
         if(senha != null && senha.equals(senhaConfirm)){
            Usuario usuario = new Usuario();
            
            EmpresaCliente empresaClienteConsultada = empresaClienteDao.consultarPorCpfCnpj(empresaCliente.getPessoa().getCpfCnpj());
            if(empresaClienteConsultada == null){
               empresaClienteConsultada = empresaClienteDao.alterar(empresaCliente);
            }
            
            usuario = usuarioDao.consultarPorLogin(empresaCliente.getPessoa().getEmail());
            if (usuario == null) {
               usuario = new Usuario();
               usuario.setAtivo(false);
               usuario.setDataAlteracaoSenha(new Date());
               usuario.setDataCriacao(new Date());
               usuario.setLogin(empresaCliente.getPessoa().getEmail());
               usuario.setNome(empresaCliente.getPessoa().getNome());
               usuario.setSenhaNaoCriptografada(senha);
               usuarioDao.incluir(usuario);
            }else{
               throw new PedidoException("Já existe um usuário cadastrado com esse email.");
            }
            
            PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();
            pessoaEmpresa.setEmpresa(empresaClienteConsultada);
            pessoaEmpresa.setPessoa(empresaClienteConsultada.getPessoa());
            pessoaEmpresaDao.incluir(pessoaEmpresa);
            
            
            EmpresaUsuario empresaUsuario = new EmpresaUsuario();
            empresaUsuario.setEmpresa(empresaClienteConsultada);
            empresaUsuario.setUsuario(usuario);
            empresaUsuarioDao.incluir(empresaUsuario);
            
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
