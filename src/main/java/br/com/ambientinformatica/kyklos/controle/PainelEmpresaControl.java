package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.ParametroEmpresa;
import br.com.ambientinformatica.kyklos.negocio.CepNeg;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaClienteDao;
import br.com.ambientinformatica.kyklos.persistencia.MunicipioDao;
import br.com.ambientinformatica.kyklos.persistencia.ParametroEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;

@Named("PainelEmpresaControl")
@Scope("conversation")
public class PainelEmpresaControl implements Serializable{

   private static final long serialVersionUID = 1L;

   @Autowired
   private EmpresaClienteDao empresaClienteDao;

   @Autowired
   private PessoaDao pessoaDao;

   @Autowired
   private CepNeg cepNeg;

   @Autowired
   private MunicipioDao municipioDao;

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private ParametroEmpresaDao parametroEmpresaDao;

   private List<Municipio> municipios = new ArrayList<Municipio>();

   private EmpresaCliente empresaCliente = new EmpresaCliente();

   private ParametroEmpresa parametroEmpresa = new ParametroEmpresa();

   private ParametroEmpresa parametroEmpresaSelecionado = new ParametroEmpresa();

   private List<ParametroEmpresa> listaParametroEmpresa = new ArrayList<ParametroEmpresa>();

   private String uf;
   
   private boolean possuiRegimeEspecial;
   
   @PostConstruct
   public void consultarDadosEmpresa(){
      listarParametrosEmpresa();
   }
   
   public void listarParametrosEmpresa(){
      listaParametroEmpresa = parametroEmpresaDao.listarTodosEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
   }

   public void limparParametroEmpresa(){
      parametroEmpresa = new ParametroEmpresa();
   }

   public void selecionarParametroEmpresa(){
      parametroEmpresa = parametroEmpresaSelecionado;
   }

   public void salvarParametroEmpresa(){
      try {

         if(parametroEmpresa.getChave() != null && parametroEmpresa.getValor() != null){

            if(parametroEmpresa.getId() == null){
               parametroEmpresa.setEmpresaCliente(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
               parametroEmpresaDao.incluir(parametroEmpresa);
               UtilFaces.addMensagemFaces("Parametro " + parametroEmpresa.getChave() + " adicionado com sucesso!");
            } else {
               parametroEmpresaDao.alterar(parametroEmpresa);
               UtilFaces.addMensagemFaces("Parametro " + parametroEmpresa.getChave() + " alterado com sucesso!");
            }

            listaParametroEmpresa = parametroEmpresaDao.listarTodosEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());

         } else {
            UtilFaces.addMensagemFaces("Preencha os campos (Chave, Valor) do parametro ou selecione um parâmetro abaixo para editá-lo");
         }

      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao Salvar Parâmetro!");
         UtilFaces.addMensagemFaces(e);
      }
   }

   public void removerParametroEmpresa(ParametroEmpresa parametroEmpresa){
      try {

         ParametroEmpresa parametroEmpresaConsultado = parametroEmpresaDao.consultar(parametroEmpresa.getId());
         
         if(parametroEmpresaConsultado != null){
            parametroEmpresaDao.excluirPorId(parametroEmpresaConsultado.getId());
            listarParametrosEmpresa();
            UtilFaces.addMensagemFaces("Parâmetro " + parametroEmpresa.getChave() + " removido com sucesso!");
            
         } else {
            UtilFaces.addMensagemFaces("Parâmetro não encontrado na base de dados!");
         }

      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao tentar remover Parâmetro!");
         UtilFaces.addMensagemFaces(e);
      }

   }
   
   public void adicionarRegimeEspecial(){
      try {
         empresaClienteDao.alterar(empresaCliente);
         consultarDadosEmpresa();
         UtilFaces.addMensagemFaces("Regime Especial adicionado com sucesso!");
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao adicionar Regime Especial");
         UtilFaces.addMensagemFaces(e);
      }
   }

   public void salvarDadosEmpresa(ActionEvent actionEvent){
      try {
         empresaClienteDao.alterar(empresaCliente);
         pessoaDao.alterar(empresaCliente.getPessoa());
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao salvar dados.");
         UtilFaces.addMensagemFaces(e.getMessage());
      }
   }

   public void consultarCep(){
      try {
         Cep cepDto = cepNeg.consultar(empresaCliente.getPessoa().getCep());
         empresaCliente.getPessoa().setMunicipio(municipioDao.consultarPorCodigoIbge(cepDto.getCodigoIbge()));
         empresaCliente.getPessoa().setLogradouro(cepDto.getLogradouro());
         empresaCliente.getPessoa().setBairro(cepDto.getBairro());
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao consultar o CEP: " + empresaCliente.getPessoa().getCep());
      }
   }

   public List<Municipio> completeMunicipio(String municipio) {
      return municipioDao.listarPorDescricao(municipio);
   }

   public List<Municipio> getMunicipios() {
      return municipios;
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
   
   public ParametroEmpresa getParametroEmpresa() {
      return parametroEmpresa;
   }

   public void setParametroEmpresa(ParametroEmpresa parametroEmpresa) {
      this.parametroEmpresa = parametroEmpresa;
   }

   public List<ParametroEmpresa> getListaParametroEmpresa() {
      return listaParametroEmpresa;
   }

   public void setListaParametroEmpresa(List<ParametroEmpresa> listaParametroEmpresa) {
      this.listaParametroEmpresa = listaParametroEmpresa;
   }

   public EmpresaCliente getEmpresaCliente() {
      return empresaCliente;
   }

   public void setEmpresaCliente(EmpresaCliente empresaCliente) {
      this.empresaCliente = empresaCliente;
   }

   public ParametroEmpresa getParametroEmpresaSelecionado() {
      return parametroEmpresaSelecionado;
   }

   public void setParametroEmpresaSelecionado(ParametroEmpresa parametroEmpresaSelecionado) {
      this.parametroEmpresaSelecionado = parametroEmpresaSelecionado;
   }

   public boolean isPossuiRegimeEspecial() {
      return possuiRegimeEspecial;
   }

   public void setPossuiRegimeEspecial(boolean possuiRegimeEspecial) {
      this.possuiRegimeEspecial = possuiRegimeEspecial;
   }

}