package br.com.ambientinformatica.kyklos.negocio;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.kyklos.controle.UsuarioLogadoControl;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.persistencia.EmpresaClienteDao;
import br.com.ambientinformatica.kyklos.persistencia.ParametroSistemaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Service("PessoaNeg")
public class PessoaNegImpl implements PessoaNeg {

	@Autowired
	PessoaDao pessoaDao;

	@Autowired
	PessoaEmpresaDao pessoaEmpresaDao;

	@Autowired
	ParametroSistemaDao parametroDao;
	
	@Autowired
	EmpresaClienteDao empresaClienteDao;

	public Pessoa consultar(String cpfCnpj) throws KyklosException {
	   Pessoa pessoa;
	    EmpresaCliente empresaCliente = empresaClienteDao.consultarPorCnpj(cpfCnpj); 
		if (empresaCliente != null) {
			pessoa = empresaCliente.getPessoa();
		} else {
			pessoa = pessoaDao.consultarPorCpfCnpj(cpfCnpj);
			if (pessoa == null) {
				try{
					pessoa = new Pessoa();
				}catch(Exception e){
				   throw new KyklosException("Preencha todos os campos e Salve uma nova pessoa.");
				}
			}
		}
		return pessoa;
	}

	public void salvar(Pessoa pessoa, UsuarioLogadoControl usuarioLogadoControl) {
		try {
		   if(pessoa.getId() != null){
		      pessoa = pessoaDao.alterar(pessoa);
		   }else{
		      pessoaDao.incluir(pessoa);
		   }
			
		   PessoaEmpresa pessoaEmpresa = pessoaEmpresaDao.consultarPorPessoaAndEmpresa(pessoa, usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
			if (pessoaEmpresa == null) {
				pessoaEmpresa = new PessoaEmpresa();
				pessoaEmpresa.setData(new Date());
				pessoaEmpresa.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
				pessoaEmpresa.setPessoa(pessoa);
				pessoaEmpresaDao.incluir(pessoaEmpresa);
			}
		} catch (PersistenciaException e) {
			UtilFaces.addMensagemFaces("Erro ao salvar registro.");
		}

	}

}
