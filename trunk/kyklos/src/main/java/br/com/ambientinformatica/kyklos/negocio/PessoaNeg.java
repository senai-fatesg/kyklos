package br.com.ambientinformatica.kyklos.negocio;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.controle.UsuarioLogadoControl;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;

public interface PessoaNeg {

   Pessoa consultar(String cpfCnpj) throws PedidoException;

   void salvar(Pessoa pessoa, UsuarioLogadoControl usuarioLogadoControl) throws PedidoException;
   
   
}
