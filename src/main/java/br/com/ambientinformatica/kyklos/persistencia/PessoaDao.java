package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface PessoaDao extends Persistencia<Pessoa>{

   Pessoa consultarPorCpfCnpj(String cpfCnpj);
   
   Pessoa consultarPorNome(String nome);
   
   List<Pessoa> listarPessoasPorNome(String nome);

   List<Pessoa> listarPessoasPorNomeOuCnpj(String nomeCnpj) throws KyklosException;

}