package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.ambientinformatica.util.AmbientValidator;
import br.com.ambientinformatica.util.Entidade;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;

@Entity
public class Usuario extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @Column(nullable=false, unique = true)
   @NotNull(message="Login do usuário é obrigatório", groups=AmbientValidator.class)
   @NotEmpty(message="Login do usuário é obrigatório", groups=AmbientValidator.class)
   @Email(message="O login do usuário deve ser um email", groups=AmbientValidator.class)
   private String login;

   public void addPapel(EnumPapelUsuario papel){
      if(!isContemPapel(papel)){
         PapelUsuario pu = new PapelUsuario();
         pu.setPapel(papel);
         papeis.add(pu);
      }
   }

   private String senha;

   private String nome;

   @Temporal(TemporalType.DATE)
   private Date dataAlteracaoSenha = new Date();

   @Temporal(TemporalType.DATE)
   private Date dataCriacao = new Date();

   @Temporal(TemporalType.DATE)
   private Date dataUltimoAcesso = new Date();

   @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
   @JoinColumn(name="usuario_id")
   private Set<PapelUsuario> papeis = new HashSet<PapelUsuario>();

   private boolean ativo;
   
   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getSenha() {
      return senha;
   }

   public void setSenhaNaoCriptografada(String senha) {
      this.senha = UtilHash.gerarStringHash(senha, Algoritimo.MD5);
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public Date getDataAlteracaoSenha() {
      return dataAlteracaoSenha;
   }

   public void setDataAlteracaoSenha(Date dataAlteracaoSenha) {
      this.dataAlteracaoSenha = dataAlteracaoSenha;
   }

   public Date getDataCriacao() {
      return dataCriacao;
   }

   public void setDataCriacao(Date dataCriacao) {
      this.dataCriacao = dataCriacao;
   }

   public Date getDataUltimoAcesso() {
      return dataUltimoAcesso;
   }

   public void setDataUltimoAcesso(Date dataUltimoAcesso) {
      this.dataUltimoAcesso = dataUltimoAcesso;
   }

   public boolean isContemPapel(EnumPapelUsuario papel){
      for(PapelUsuario p : papeis){
         if(p.getPapel() == papel){
            return true;
         }
      }
      return false;
   }

   public Set<PapelUsuario> getPapeis() {
      return papeis;
   }

   public Object getId() {
      return login;
   }


   public List<PapelUsuario> getPapeisList() {
      List<PapelUsuario> result = new ArrayList<PapelUsuario>(papeis);
      Collections.sort(result);
      return result;
   }

   public boolean isAtivo() {
      return ativo;
   }

   public void setAtivo(boolean ativo) {
      this.ativo = ativo;
   }



}
