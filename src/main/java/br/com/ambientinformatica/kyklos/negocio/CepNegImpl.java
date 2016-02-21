package br.com.ambientinformatica.kyklos.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.entidade.EnumParametro;
import br.com.ambientinformatica.kyklos.entidade.Parametro;
import br.com.ambientinformatica.kyklos.persistencia.ParametroDao;
import br.com.ambientinformatica.kyklos.util.UtilServico;

@Service("cepNeg")
public class CepNegImpl implements CepNeg{

   @Autowired
   ParametroDao parametroDao;

   @Override
   public Cep consultar(String cep){
      try{
         Parametro parametroUrl = parametroDao.consultarPorChave(EnumParametro.URL_TOTH.getDescricao());
         String url = String.format("%s/services/cepservice/consultarcep/%s", parametroUrl.getValor(), cep);
         return UtilServico.consultarGet(url, Cep.class);
      }catch(Exception e){
         return null;
      }
   }

}
