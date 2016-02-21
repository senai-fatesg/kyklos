package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.negocio.PessoaNeg;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.ParametroDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;

@Named("EstoqueControl")
@Scope("conversation")
public class EstoqueControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private PessoaEmpresaDao pessoaEmpresaDao;

   @Autowired
   private EstoqueDao estoqueDao;

   @Autowired
   private PessoaDao pessoaDao;

   @Autowired
   private PessoaNeg pessoaNeg;
   
   @Autowired
   private ParametroDao parametroDao;
   
   private List<Estoque> listaEstoques = new ArrayList<Estoque>(); 

   private PessoaEmpresa pessoaEmpresa;

   private Pessoa pessoa = new Pessoa();
   
   private Estoque estoque = new Estoque();

   private EmpresaCliente empresaCliente;

   private String descricao;
   
   private boolean estoquePadrao;

   private boolean novaEmpresa = false;
   
   private String uf = "";
   
   public void limpar(){
      listaEstoques = new ArrayList<Estoque>();
      estoque = new Estoque();
      estoquePadrao = false;
   }

   public void novaEmpresa(ActionEvent actionEvent) {
      novaEmpresa = true;
      pessoa = new Pessoa();
  }
   
   public void incluirPessoa(){
      
   }
   
   public void cadastrar(){
      novaEmpresa = false;
      limpar();
      pessoaEmpresa = pessoaEmpresaDao.consultarPorCpfOuCnpj(usuarioLogadoControl.getEmpresaUsuario().getEmpresa().getPessoa());
      empresaCliente = usuarioLogadoControl.getEmpresaUsuario().getEmpresa();
   }

   public List<PessoaEmpresa> listarPessoaEmpresa(String consulta){
      return pessoaEmpresaDao.listarPorEmpresaCliente(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
   }

   public void consultar(){
      listaEstoques = estoqueDao.listarPorDescricao(descricao);
   }

   public void consultarPessoa(){
      try {
         pessoa = pessoaNeg.consultar(pessoa.getCpfCnpj());
      } catch (PedidoException e) {
         UtilFaces.addMensagemFaces("Erro ao consultar.");
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }
   
   public void salvarPessoa(ActionEvent actionEvent){
      try {
         pessoa.setMunicipio(pessoa.getMunicipio());
         pessoaNeg.salvar(pessoa, usuarioLogadoControl);
         pessoa = new Pessoa();
         UtilFaces.addMensagemFaces("Salvo com sucesso!");
      } catch (PersistenciaException e) {
         UtilFaces.addMensagemFaces("Erro ao salvar registro.");
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao salvar registro.");
      }
   }
   
   public void salvar(){
      try {
         estoque.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
         estoque.setPadrao(estoquePadrao);
         estoque.setPessoaEmpresa(pessoaEmpresa);
         estoqueDao.incluir(estoque);
         estoque = new Estoque();
         UtilFaces.addMensagemFaces("Novo Estoque Cadastrado!");
      } catch (PersistenciaException e) {
         UtilFaces.addMensagemFaces("Erro ao salvar o estoque: "+e.getMessage());
      }
   }

   public Estoque getEstoque() {
      return estoque;
   }

   public void setEstoque(Estoque estoque) {
      this.estoque = estoque;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public boolean isEstoquePadrao() {
      return estoquePadrao;
   }

   public void setEstoquePadrao(boolean estoquePadrao) {
      this.estoquePadrao = estoquePadrao;
   }

   public boolean isNovaEmpresa() {
      return novaEmpresa;
   }

   public void setNovaEmpresa(boolean novaEmpresa) {
      this.novaEmpresa = novaEmpresa;
   }

   public List<Estoque> getListaEstoques() {
      return listaEstoques;
   }

   public PessoaEmpresa getPessoaEmpresa() {
      return pessoaEmpresa;
   }

   public void setPessoaEmpresa(PessoaEmpresa pessoaEmpresa) {
      this.pessoaEmpresa = pessoaEmpresa;
   }

   public Pessoa getPessoa() {
      return pessoa;
   }

   public void setPessoa(Pessoa pessoa) {
      this.pessoa = pessoa;
   }

   public EmpresaCliente getEmpresaCliente() {
      return empresaCliente;
   }

   public String getUf() {
      return uf;
   }

   public void setUf(String uf) {
      this.uf = uf;
   }

   public List<SelectItem> getUfs() {
      return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumUf.values()));
   }
   
}
