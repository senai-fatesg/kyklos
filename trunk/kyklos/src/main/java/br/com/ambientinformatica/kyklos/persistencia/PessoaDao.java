package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface PessoaDao extends Persistencia<Pessoa>{

   Pessoa consultarPorCpfCnpj(String cpfCnpj);
   
   Pessoa consultarPorNome(String nome);
   
   List<Pessoa> listarPessoasPorNome(String nome);

}