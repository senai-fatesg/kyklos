package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Banco;

public interface BancoDao extends Persistencia<Banco>{

   List<Banco> listarBancosPorNome(String nome);

   Banco consultarBancoPorCodigo(String codigo);
   
}
