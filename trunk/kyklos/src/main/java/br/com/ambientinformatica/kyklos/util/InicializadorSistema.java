
package br.com.ambientinformatica.kyklos.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.kyklos.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Service("inicializadorSistema")
public class InicializadorSistema {

   @Autowired
   private UsuarioDao usuarioDao;

   @PostConstruct
   public void iniciar(){
      inicializarUsuarioAdmin();
   }

   private void inicializarUsuarioAdmin(){
      try {
         List<Usuario> usuarios = usuarioDao.listar();
         if(usuarios.isEmpty()){
            Usuario usu = new Usuario();
            usu.setNome("admin");
            usu.setLogin("admin@ambientinformatica.com.br");
            usu.setAtivo(true);
            usu.setSenhaNaoCriptografada("123456");
            usu.addPapel(EnumPapelUsuario.ADMIN);
            usu.addPapel(EnumPapelUsuario.USUARIO);
            usuarioDao.incluir(usu);
            UtilLog.getLog().info("*** USUÁRIO admin CRIADO com a senha 123456 ***");
         }
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
      }
   }

}
