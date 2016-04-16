package br.com.ambientinformatica.kyklos.negocio;

import br.com.ambientinformatica.kyklos.dto.Cep;

public interface CepNeg {

   Cep consultar(String cep) throws Exception;

}
