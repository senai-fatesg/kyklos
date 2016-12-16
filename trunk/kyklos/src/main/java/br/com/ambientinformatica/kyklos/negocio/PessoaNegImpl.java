package br.com.ambientinformatica.kyklos.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.kyklos.controle.UsuarioLogadoControl;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.persistencia.ParametroDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;

@Service("PessoaNeg")
public class PessoaNegImpl implements PessoaNeg{

   @Autowired
   PessoaDao pessoaDao;
   
   @Autowired
   PessoaEmpresaDao pessoaEmpresaDao;
   
   @Autowired
   ParametroDao parametroDao;
   
   Pessoa pessoa = new Pessoa();
   
   PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();
   
   public Pessoa consultar(String cpfCnpj) throws PedidoException {
         PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();
         pessoa.setCpfCnpj(cpfCnpj);

         pessoaEmpresa = pessoaEmpresaDao.consultarPorCpfOuCnpj(pessoa);

         if(pessoaEmpresa != null){
            pessoa = pessoaEmpresa.getPessoa();    
         }else{
            pessoa = pessoaDao.consultarPorCpfCnpj(pessoa.getCpfCnpj());
            if(pessoa == null){
            	pessoa = new Pessoa();
            	pessoa.setCpfCnpj(cpfCnpj);
//               Parametro parametroUrl = parametroDao.consultarPorChave(EnumParametro.URL_GESTOR_NFE.getDescricao());
//
//               Parametro cnpjUsuarioGestorNfe = parametroDao.consultarPorChave(EnumParametro.USUARIO_GESTOR_NFE.getDescricao());
//               Parametro senhaGestorNfe = parametroDao.consultarPorChave(EnumParametro.SENHA_GESTOR_NFE.getDescricao());
//               String url = String.format("%s/services/cadastro/consultarCadastro/GO/PRODUCAO/%s", parametroUrl.getValor(), cpfCnpj);
//               pessoa = new Pessoa();
//
//               PessoaDto pessoaDto = UtilRequest.consultarGet(cnpjUsuarioGestorNfe.getValor(), senhaGestorNfe.getValor(), url, PessoaDto.class);
//
//               pessoa.setCpfCnpj(pessoaDto.getCpfCnpj());
//               pessoa.setNome(pessoaDto.getNome());
//               pessoa.setNomeFantasia(pessoaDto.getNomeFantasia());
//               if(pessoa.getNomeFantasia() == null){
//                  pessoa.setNomeFantasia(pessoa.getNome());
//               }
//               pessoa.setInscricaoEstadual(pessoaDto.getInscricaoEstadual());
            }
         }
         return pessoa;
   }

   public void salvar(Pessoa pessoa, UsuarioLogadoControl usuarioLogadoControl) {
     try{
      pessoa = pessoaDao.alterar(pessoa);
      pessoaEmpresa = pessoaEmpresaDao.consultarPorPessoaAndEmpresa(pessoa, usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
      if(pessoaEmpresa == null){
         pessoaEmpresa = new PessoaEmpresa();
         pessoaEmpresa.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
         pessoaEmpresa.setPessoa(pessoa);
      }
      pessoaEmpresaDao.incluir(pessoaEmpresa);
   } catch (PersistenciaException e) {
      UtilFaces.addMensagemFaces("Erro ao salvar registro.");
   }
      
   }

   
}
