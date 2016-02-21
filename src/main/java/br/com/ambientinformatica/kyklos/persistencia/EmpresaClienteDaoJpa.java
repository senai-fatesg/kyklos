package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.Usuario;

@Repository("empresaClienteDao")
public class EmpresaClienteDaoJpa extends PersistenciaJpa<EmpresaCliente> implements EmpresaClienteDao {

   private static final long serialVersionUID = 1L;
   
   @Autowired
   private PessoaDao pessoaDao;
   
   @Autowired
   private UsuarioDao usuarioDao;
   
   @Autowired
   private EmpresaUsuarioDao empresaUsuarioDao;
   
   public EmpresaCliente consultarPorPessoa(Pessoa pessoa){
      Query query = em.createQuery("select e from EmpresaCliente e where e.pessoa = :pessoa");
      query.setParameter("pessoa", pessoa);
      return (EmpresaCliente) query.getSingleResult();
   }

   @Override
   @Transactional(rollbackFor=PedidoException.class)
   public void incluirNovoUsuario(EmpresaCliente empresaCliente, String senha) throws PedidoException {
      Pessoa pessoaConsultada = pessoaDao.consultarPorCpfCnpj(empresaCliente.getPessoa().getCpfCnpj());
      Usuario usuarioConsultado = usuarioDao.consultarPorLogin(empresaCliente.getPessoa().getEmail());
      if(usuarioConsultado == null){
         if(pessoaConsultada != null){
            EmpresaCliente ec = consultarPorPessoa(pessoaConsultada);
            if(ec != null){
               throw new PedidoException("Este cnpj já está cadastrado");
            }
            empresaCliente.setPessoa(pessoaConsultada);
         }else{
         }
         Usuario usuario = new Usuario();
         usuario.setLogin(empresaCliente.getPessoa().getEmail());
         usuario.setSenhaNaoCriptografada(senha);
         
         EmpresaUsuario empUsu = new EmpresaUsuario();
         empUsu.setEmpresa(empresaCliente);
         empUsu.setPrincipal(true);
         empUsu.setUsuario(usuario);
         
         usuarioDao.incluir(usuario);
         incluir(empresaCliente);
         empresaUsuarioDao.incluir(empUsu);
         
      }else{
         throw new PedidoException(String.format("Usuário %s já cadastrado. Logue e registre uma nova empresa.", empresaCliente.getPessoa().getEmail()));
      }
   }



}
