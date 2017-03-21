package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.entidade.Endereco;
import br.com.ambientinformatica.kyklos.entidade.Transportadora;
import br.com.ambientinformatica.kyklos.negocio.CepNeg;
import br.com.ambientinformatica.kyklos.negocio.PessoaNeg;
import br.com.ambientinformatica.kyklos.persistencia.EnderecoDao;
import br.com.ambientinformatica.kyklos.persistencia.MunicipioDao;
import br.com.ambientinformatica.kyklos.persistencia.TransportadoraDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Named("TransportadoraControl")
@Scope("conversation")
public class TransportadoraControl implements Serializable{

   private static final long serialVersionUID = 1L;

   private Transportadora transportadora = new Transportadora();
   
   private String cepTransportadora = new String();
   
   private Municipio municipioTransportadora = new Municipio();

   private Endereco enderecoTransportadora = new Endereco();
   
   private String cpfCnpjTransportadora = new String();
   
   private String buscaText = new String();

   private List<Transportadora> listaTransportadoras = new ArrayList<Transportadora>();

   private int activeIndex;

   @Autowired
   private TransportadoraDao transportadoraDao;
   
   @Autowired
   private EnderecoDao enderecoDao;

   @Autowired
   private PessoaNeg pessoaNeg;

   @Autowired
   private CepNeg cepNeg;
   
   @Autowired
   private MunicipioDao municipioDao;

   public void criarTransportadora(){
      activeIndex = 0;
      transportadora = new Transportadora();
      enderecoTransportadora = new Endereco();
   }
   
   public void listarTransportadoras(){
      try{
         enderecoTransportadora = new Endereco();
         listaTransportadoras = transportadoraDao.listarTransportadorasPorCpfCnpjRazaoSocial(buscaText);
         if(listaTransportadoras.isEmpty()){
            UtilFaces.addMensagemFaces("Nenhum resultado encontrado!");
         }
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro de consulta");
      }
   }

   public void consultarCpfCnpj(){
      try {
         Pessoa pessoaDto = pessoaNeg.consultar(cpfCnpjTransportadora);
         transportadora.setCpfCnpj(pessoaDto.getCpfCnpj());
         transportadora.setInscricaoEstadual(pessoaDto.getInscricaoEstadual());
         transportadora.setRazaoSocial(pessoaDto.getNome());
      } catch (Exception e) {
         UtilFaces.addMensagemFaces("Erro ao Consultar. Adicione os dados manualmente ou consulte novamente. " + e.getMessage());
      }
   }

   public void consultarCep(){
      try {
         Cep cepDto = cepNeg.consultar(cepTransportadora.replace(".", "").replace("-", ""));
         enderecoTransportadora.setAtivo(true);
         enderecoTransportadora.setBairro(cepDto.getBairro());
         enderecoTransportadora.setCep(cepDto.getCep());
         enderecoTransportadora.setLogradouro(cepDto.getLogradouro());
         enderecoTransportadora.setMunicipio(municipioDao.consultarPorCodigoIbge(cepDto.getCodigoIbge()));
         transportadora.setEndereco(enderecoTransportadora);
      } catch (KyklosException e) {
        UtilFaces.addMensagemFaces("Erro ao Consultar: " + e.getMessage());
      }
   }
   
   public void salvarTransportadora(){
	   Transportadora transportadoraConsultada = transportadoraDao.consultarTransportadoraPorCpfCnpj(cpfCnpjTransportadora);
	   if(transportadoraConsultada == null){
	      enderecoDao.incluir(enderecoTransportadora);
	      transportadora.setCpfCnpj(cpfCnpjTransportadora);
	      transportadoraDao.incluir(transportadora);
	      UtilFaces.addMensagemFaces(transportadora.getRazaoSocial() + " adicionada com sucesso!");
      }else{
         UtilFaces.addMensagemFaces("Cadastro já existente:");
         UtilFaces.addMensagemFaces("Razão Social: " + transportadoraConsultada.getRazaoSocial());
         UtilFaces.addMensagemFaces("CPF/CNPJ: " + transportadoraConsultada.getCpfCnpj());
      }
   }

   public String getBuscaText() {
      return buscaText;
   }

   public void setBuscaText(String buscaText) {
      this.buscaText = buscaText;
   }

   public Endereco getEnderecoTransportadora() {
      return enderecoTransportadora;
   }

   public void setEnderecoTransportadora(Endereco enderecoTransportadora) {
      this.enderecoTransportadora = enderecoTransportadora;
   }

   public Transportadora getTransportadora() {
      return transportadora;
   }

   public void setTransportadora(Transportadora transportadora) {
      this.transportadora = transportadora;
   }

   public List<Transportadora> getListaTransportadoras() {
      return listaTransportadoras;
   }

   public void setListaTransportadoras(List<Transportadora> listaTransportadoras) {
      this.listaTransportadoras = listaTransportadoras;
   }

   public int getActiveIndex() {
      return activeIndex;
   }

   public void setActiveIndex(int activeIndex) {
      this.activeIndex = activeIndex;
   }

   public String getCepTransportadora() {
      return cepTransportadora;
   }

   public void setCepTransportadora(String cepTransportadora) {
      this.cepTransportadora = cepTransportadora;
   }

   public Municipio getMunicipioTransportadora() {
      return municipioTransportadora;
   }

   public void setMunicipioTransportadora(Municipio municipioTransportadora) {
      this.municipioTransportadora = municipioTransportadora;
   }

   public String getCpfCnpjTransportadora() {
      return cpfCnpjTransportadora;
   }

   public void setCpfCnpjTransportadora(String cpfCnpjTransportadora) {
      this.cpfCnpjTransportadora = cpfCnpjTransportadora;
   }

}
