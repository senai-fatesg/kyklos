package br.com.ambientinformatica.kyklos.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Usuario;

public interface UsuarioDao extends Persistencia<Usuario> {

   Usuario consultarPorLogin(String login);

}
