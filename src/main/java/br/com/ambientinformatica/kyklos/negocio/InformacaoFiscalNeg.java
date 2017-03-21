package br.com.ambientinformatica.kyklos.negocio;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;
import br.com.ambientinformatica.kyklos.entidade.InformacaoFiscal;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface InformacaoFiscalNeg {

   InformacaoFiscal consultarInformacaoFiscal(ItemPedido item) throws KyklosException;
   
   List<InformacaoFiscal> listar(Cfop cfop, Produto produto, Integer ncm, EnumUf ufOrigem, EnumUf ufDestino, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException;
   
   InformacaoFiscal consultarInformacaoFiscal(Cfop cfop, Produto produto, EnumUf ufOrigem, EnumUf ufDestino, boolean contribuinte, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException;
   
}
