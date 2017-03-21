package br.com.ambientinformatica.kyklos.negocio;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;
import br.com.ambientinformatica.kyklos.entidade.InformacaoFiscal;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaClienteDao;
import br.com.ambientinformatica.kyklos.persistencia.InformacaoFiscalDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Service("informacaoFiscalNeg")
public class InformacaoFiscalNegImpl implements InformacaoFiscalNeg{

	@Autowired
	InformacaoFiscalDao infDao;
	
	@Autowired
	EmpresaClienteDao empresaClienteDao;
	
   public InformacaoFiscal consultarInformacaoFiscal(Cfop cfop, Produto produto, EnumUf ufOrigem, EnumUf ufDestino, boolean contribuinte, GrupoDiferencaFiscal grupoDiferencaFiscal)
   throws KyklosException {

      if(cfop == null){
         throw new KyklosException ("Informe o cfop do Pedido");
      }

      List<InformacaoFiscal> informacoes = infDao.listar(cfop, produto, ufOrigem, ufDestino, contribuinte, grupoDiferencaFiscal);
      if(informacoes.isEmpty()){
         informacoes = infDao.listar(cfop, produto, ufOrigem, null, contribuinte, grupoDiferencaFiscal);
      }
      if(informacoes.isEmpty()){
         informacoes = infDao.listar(cfop, produto, null, null, contribuinte, grupoDiferencaFiscal);
      }
      if(informacoes.isEmpty()){
         informacoes = infDao.listar(cfop, produto, (EnumUf)null, null, null, grupoDiferencaFiscal);
      }
      if(informacoes.isEmpty()){
         informacoes = infDao.listar(cfop, null, (EnumUf)null, null, null, grupoDiferencaFiscal);
      }
      if(informacoes.isEmpty()){
         informacoes = infDao.listar(cfop, null, (EnumUf)null, null, null, grupoDiferencaFiscal);
      }
      
      
      if(informacoes.isEmpty()){
         throw new KyklosException ("Não existe informação fiscal para o cfop " + cfop.getCfop());
      }
      
      Collections.sort(informacoes);

      return informacoes.get(0);
      
   }
   
   public InformacaoFiscal consultarInformacaoFiscal(ItemPedido item) throws KyklosException  {
      Cfop cfop = item.getPedido().getCfop();
      Produto produto = item.getProduto();
      EnumUf ufOrigem = item.getPedido().getEmpresa().getPessoa().getMunicipio().getUf();
      EnumUf ufDestino = item.getPedido().getCliente().getMunicipio().getUf();
      boolean contribuinte = false;
      if(item.getPedido().getCliente().getInscricaoEstadual() != null && item.getPedido().getCliente().getInscricaoEstadual().length() > 3){
      	contribuinte = true;
      }
      
//      GrupoDiferencaFiscal grupoDiferencaFiscal = item.getPedido().getCliente().getGrupoDiferencaFiscal(); TODO resolver como mapear o Grupo de Diferenca Fiscal
      GrupoDiferencaFiscal grupoDiferencaFiscal = null;
      
      return consultarInformacaoFiscal(cfop, produto, ufOrigem, ufDestino, contribuinte, grupoDiferencaFiscal);
   }

   public List<InformacaoFiscal> listar(Cfop cfop, Produto produto, Integer ncm, EnumUf ufOrigem, EnumUf ufDestino, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException  {
      return infDao.listar(cfop, produto, ncm, ufOrigem, ufDestino, grupoDiferencaFiscal);
   }

}
