package br.com.ambientinformatica.kyklos.negocio;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.controle.UsuarioLogadoControl;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface PessoaNeg {

   Pessoa consultar(String cpfCnpj) throws KyklosException;

   void salvar(Pessoa pessoa, UsuarioLogadoControl usuarioLogadoControl) throws KyklosException;
   
   
}
