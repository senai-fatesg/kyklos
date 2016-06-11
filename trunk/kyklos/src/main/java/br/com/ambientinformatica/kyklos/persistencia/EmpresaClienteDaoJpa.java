package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;

@Repository("empresaClienteDao")
public class EmpresaClienteDaoJpa extends PersistenciaJpa<EmpresaCliente> implements EmpresaClienteDao {

   private static final long serialVersionUID = 1L;
   
   public EmpresaCliente consultarPorPessoa(Pessoa pessoa){
      Query query = em.createQuery("select e from EmpresaCliente e where e.pessoa = :pessoa");
      query.setParameter("pessoa", pessoa);
      return (EmpresaCliente) query.getSingleResult();
   }

   public EmpresaCliente consultarPorCpfCnpj(String cpfCnpj){
      try{
      Query query = em.createQuery("select e from EmpresaCliente e where e.pessoa.cpfCnpj = :cpfCnpj");
      query.setParameter("cpfCnpj", cpfCnpj);
      return (EmpresaCliente) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }
   
//   @Override
//   @Transactional(rollbackFor=PedidoException.class)
//   public void incluirNovoUsuario(EmpresaCliente empresaCliente, String senha) throws PedidoException {
//      
//      Pessoa pessoaConsultada = pessoaDao.consultarPorCpfCnpj(empresaCliente.getPessoa().getCpfCnpj());
//      Usuario usuarioConsultado = usuarioDao.consultarPorLogin(empresaCliente.getPessoa().getEmail());
//      
//      if(usuarioConsultado == null){
//         if(pessoaConsultada != null){
//            EmpresaCliente ec = consultarPorPessoa(pessoaConsultada);
//            if(ec != null){
//               throw new PedidoException("Este cnpj já está cadastrado");
//            }
//            empresaCliente.setPessoa(pessoaConsultada);
//         }else{
//            empresaCliente = empresaClienteDao.alterar(empresaCliente);
//         }
//         Usuario usuario = new Usuario();
//         usuario.setLogin(empresaCliente.getPessoa().getEmail());
//         usuario.setSenhaNaoCriptografada(senha);
//         usuario.setNome(empresaCliente.getPessoa().getNome());
//         usuarioDao.incluir(usuario);
//         
//         EmpresaUsuario empresaUsuario = new EmpresaUsuario();
//         empresaUsuario.setEmpresa(empresaCliente);
//         empresaUsuario.setPrincipal(true);
//         empresaUsuario.setUsuario(usuario);
//         
//         empresaUsuarioDao.incluir(empresaUsuario);
//         
//      }else{
//         throw new PedidoException(String.format("Usuário %s já cadastrado. Logue e registre uma nova empresa.", empresaCliente.getPessoa().getEmail()));
//      }
//   }



}
