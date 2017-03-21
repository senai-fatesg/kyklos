package br.com.ambientinformatica.kyklos.negocio;

import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.nfe.api.NfeRetDto;
import br.com.ambientinformatica.nfe.api.NotaFiscalIntegracaoDto;

public interface NotaFiscalEletronicaNeg {

	NfeRetDto criarNotaFiscalEletronica(Pedido pedido, EmpresaCliente empresaCliente) throws KyklosException;	

	NfeRetDto enviarNotaFiscal(NotaFiscalIntegracaoDto nfe) throws KyklosException;
	
}
