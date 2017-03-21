package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;

public interface GrupoDiferencaFiscalDao extends Persistencia<GrupoDiferencaFiscal>{

   List<GrupoDiferencaFiscal> listarGrupoDiferencaFiscal(String descricao);
   
}
