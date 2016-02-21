package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaUsuarioDao;
import br.com.ambientinformatica.kyklos.persistencia.PapelUsuarioDao;
import br.com.ambientinformatica.kyklos.persistencia.UsuarioDao;

@Named("UsuarioControl")
@Scope("conversation")
public class UsuarioControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   private EmpresaUsuarioDao empresaUsuarioDao;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private UsuarioDao usuarioDao;

   @Autowired
   private PapelUsuarioDao papelUsuarioDao;
   
   private EmpresaUsuario empresaUsuario = new EmpresaUsuario();

   private DualListModel<EnumPapelUsuario> papeisUsuario = new DualListModel<EnumPapelUsuario>();
   
   private List<EmpresaUsuario> usuarios = new ArrayList<EmpresaUsuario>();

   private Usuario usuario = new Usuario();

   private String nome;

   private String login;
   
   private String parametro;

   public void limpar(){
      papeisUsuario = new DualListModel<EnumPapelUsuario>(getPapeisDisponiveis(), new ArrayList<EnumPapelUsuario>());
   }

   public List<EnumPapelUsuario> getPapeisDisponiveis() {
      List<EnumPapelUsuario> papeisUsuario = new ArrayList<EnumPapelUsuario>();
      for(EnumPapelUsuario papelUsuario : EnumPapelUsuario.values()){
         papeisUsuario.add(papelUsuario);
      }
      return papeisUsuario;
   }
   
   public void alterar(){
      usuarioDao.alterar(usuario);
   }
   
   public void listar(){
      usuarios = empresaUsuarioDao.listarPorEmpresaENomeOuLogin(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), parametro);
   }

   public void consultar(){
      usuario = usuarioDao.consultarPorLogin(login);
   }

   public void desativarUsuario(){
      usuario.setAtivo(false);
      usuarioDao.alterar(usuario);
   }
   
   public void salvar(){
      try{
      login = usuario.getLogin();
      consultar();
      if(usuario == null){
         usuario = new Usuario();
         usuario.setAtivo(true);
         usuario.setDataAlteracaoSenha(new Date());
         usuario.setDataCriacao(new Date());
         usuario.setSenhaNaoCriptografada("123456");
         usuario.setNome(nome);
         usuario.setLogin(login);
         usuarioDao.incluir(usuario);
         for(EnumPapelUsuario papelSelecionado : papeisUsuario.getTarget()){
            usuario.addPapel(papelSelecionado);
         }
      }
      
      empresaUsuario.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
      empresaUsuario.setUsuario(usuario);
      empresaUsuarioDao.incluir(empresaUsuario);
      UtilFaces.addMensagemFaces("Usuário cadastrado com sucesso!");
      }catch(Exception e){
      	UtilFaces.addMensagemFaces("Erro:"+e.getMessage());
      }
   }

   public List<EmpresaUsuario> getUsuarios() {
      return usuarios;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public DualListModel<EnumPapelUsuario> getPapeisUsuario() {
      return papeisUsuario;
   }

   public void setPapeisUsuario(DualListModel<EnumPapelUsuario> papeisUsuario) {
      this.papeisUsuario = papeisUsuario;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getParametro() {
      return parametro;
   }

   public void setParametro(String parametro) {
      this.parametro = parametro;
   }
   
}
