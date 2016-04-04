package br.com.ambientinformatica.kyklos.negocio;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.kyklos.controle.UsuarioLogadoControl;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.PessoaEmpresa;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;
import br.com.ambientinformatica.kyklos.persistencia.ParametroDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;

@Service("UnidadeNeg")
public class UnidadeMedidaNegImpl implements UnidadeMedidaNeg {

	private EntityManager manager;

	@Autowired
	UnidadeMedidaDao unidadeDao;

	@Autowired
	PessoaEmpresaDao pessoaEmpresaDao;

	@Autowired
	ParametroDao parametroDao;

	UnidadeMedida unidade = new UnidadeMedida();

	PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();

	@Override
	public UnidadeMedida consultar(Integer id) throws PedidoException {
		unidade = unidadeDao.consultar(id);
		return unidade;
	}

	@Override
	public void salvar(UnidadeMedida um,
			UsuarioLogadoControl usuarioLogadoControl) throws PedidoException {
		unidade = unidadeDao.alterar(um);

	}
	
	@Override
	public List<UnidadeMedida> todas() {
		return manager.createQuery("from Unidademedida", UnidadeMedida.class)
				.getResultList();
	}
	
	@Override
	public void excluir(UnidadeMedida um) {
		unidadeDao.excluirPorId(um.getId());
	}

}
