package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Repository("empresaClienteDao")
public class EmpresaClienteDaoJpa extends PersistenciaJpa<EmpresaCliente> implements EmpresaClienteDao {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PessoaDao pessoaDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private EmpresaUsuarioDao empresaUsuarioDao;

    @Autowired
    private PessoaEmpresaDao pessoaEmpresaDao;

    public EmpresaCliente consultarPorPessoa(Pessoa pessoa){
        Query query = em.createQuery("select e from EmpresaCliente e where e.pessoa = :pessoa");
        query.setParameter("pessoa", pessoa);
        return (EmpresaCliente) query.getSingleResult();
    }

    @Override
    @Transactional(rollbackFor=KyklosException.class)
    public void incluirNovoUsuario(EmpresaCliente empresaCliente, String senha) throws KyklosException {
        Pessoa pessoaConsultada = pessoaDao.consultarPorCpfCnpj(empresaCliente.getPessoa().getCpfCnpj());
        Usuario usuarioConsultado = usuarioDao.consultarPorLogin(empresaCliente.getPessoa().getEmail());
        if(usuarioConsultado == null){
            if(pessoaConsultada != null){
                EmpresaCliente ec = consultarPorPessoa(pessoaConsultada);
                if(ec != null){
                    throw new KyklosException("Este cnpj já está cadastrado");
                }
                empresaCliente.setPessoa(pessoaConsultada);
            }else{
            }
            Pessoa pessoa = new Pessoa();
            pessoa.setEmail(empresaCliente.getPessoa().getEmail());
            pessoa.setNome("Admin");
            
            pessoaDao.incluir(pessoa);
            
            Usuario usuario = new Usuario();
            usuario.setLogin(empresaCliente.getPessoa().getEmail());
            usuario.setSenhaNaoCriptografada(senha);
            usuario.setPessoa(pessoa);
            
            
            EmpresaUsuario empresaUsuario = new EmpresaUsuario();
            empresaUsuario.setEmpresa(empresaCliente);
            empresaUsuario.setPrincipal(true);
            empresaUsuario.setUsuario(usuario);

            usuarioDao.incluir(usuario);
            incluir(empresaCliente);
            empresaUsuarioDao.incluir(empresaUsuario);

            PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();
            pessoaEmpresa.setEmpresa(empresaCliente);
            pessoaEmpresa.setPessoa(empresaUsuario.getEmpresa().getPessoa());
            pessoaEmpresaDao.incluir(pessoaEmpresa);

        }else{
            throw new KyklosException(String.format("Usuário %s já cadastrado. Logue e registre uma nova empresa.", empresaCliente.getPessoa().getEmail()));
        }
    }

    @Override
    public EmpresaCliente consultarPorCnpj(String cpfCnpj) throws KyklosException {
       try{ 
       Query query = em.createQuery("select e from EmpresaCliente e where e.pessoa.cpfCnpj like :cpfCnpj");
        query.setParameter("cpfCnpj", cpfCnpj);
        return (EmpresaCliente) query.getSingleResult();
       }catch (NoResultException nre){
          return null;
       }
    }
    
    public EmpresaCliente consultarEmpresaEParametrosFiscais(EmpresaCliente empresaCliente) throws KyklosException {
       try {
          Query query = em.createQuery("select e from EmpresaCliente e"
                + " left join fetch e.regimesEspeciais re"
                + " where e = :empresaCliente");
          query.setParameter("empresaCliente", empresaCliente);
         return (EmpresaCliente) query.getSingleResult();
      } catch (NoResultException nre) {
         return null;
      }
    }
    



}
