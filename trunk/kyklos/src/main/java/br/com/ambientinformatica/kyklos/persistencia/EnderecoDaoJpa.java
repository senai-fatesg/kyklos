package br.com.ambientinformatica.kyklos.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Endereco;

@Repository("enderecoDao")
public class EnderecoDaoJpa extends PersistenciaJpa<Endereco> implements EnderecoDao{

   private static final long serialVersionUID = 1L;

}