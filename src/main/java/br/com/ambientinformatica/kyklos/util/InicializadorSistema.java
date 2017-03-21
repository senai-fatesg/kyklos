
package br.com.ambientinformatica.kyklos.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Service("inicializadorSistema")
public class InicializadorSistema {

   @Autowired
   private UsuarioDao usuarioDao;

   @Autowired
   private PessoaDao pessoaDao;
   
   @PostConstruct
   public void iniciar(){
      inicializarUsuarioAdmin();
   }

   private void inicializarUsuarioAdmin(){
      try {
         List<Usuario> usuarios = usuarioDao.listar();
         if(usuarios.isEmpty()){
            Pessoa pessoa = new Pessoa();
            pessoa.setNome("Admin");
            pessoa.setEmail("admin@ambientinformatica.com.br");
            pessoaDao.incluir(pessoa);
            
            Usuario usuario = new Usuario();
            usuario.setLogin(pessoa.getEmail());
            usuario.setAtivo(true);
            usuario.setSenhaNaoCriptografada("123456");
            usuario.addPapel(EnumPapelUsuario.ADMIN);
            usuario.addPapel(EnumPapelUsuario.USUARIO);
            usuario.setPessoa(pessoa);
            usuarioDao.incluir(usuario);
            UtilLog.getLog().info("*** USUÁRIO admin@ambientinformatica.com.br CRIADO com a senha 123456 ***");
         }
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
      }
   }

}
