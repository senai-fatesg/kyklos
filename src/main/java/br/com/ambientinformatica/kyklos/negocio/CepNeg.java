package br.com.ambientinformatica.kyklos.negocio;

import br.com.ambientinformatica.kyklos.dto.Cep;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface CepNeg {

   Cep consultar(String cep) throws KyklosException;

}
