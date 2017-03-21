package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;
import br.com.ambientinformatica.kyklos.entidade.InformacaoFiscal;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface InformacaoFiscalDao extends Persistencia<InformacaoFiscal>{
   
   InformacaoFiscal consultarInformacaoFiscal(Cfop cfop, Produto produto, Integer ncm, EnumUf ufOrigem, EnumUf ufDestino, Boolean contribuinte, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException;
   
   List<InformacaoFiscal> listar(Cfop cfop, Produto produto, EnumUf ufOrigem, EnumUf ufDestino, Boolean contribuinte, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException;

   List<InformacaoFiscal> listar(Cfop cfop, Produto produto, Integer ncm, EnumUf ufOrigem, EnumUf ufDestino, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException;

}
