package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.EnumInstrumentoRecebimento;
import br.com.ambientinformatica.kyklos.entidade.InstrumentoRecebimento;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface InstrumentoRecebimentoDao extends Persistencia<InstrumentoRecebimento>{

   InstrumentoRecebimento consultarPorInstrumentoRecebimento(EnumInstrumentoRecebimento instrumentoRecebimento) throws KyklosException;

}
