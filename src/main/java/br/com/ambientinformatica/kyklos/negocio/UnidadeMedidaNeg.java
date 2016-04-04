package br.com.ambientinformatica.kyklos.negocio;

import java.util.List;

import br.com.ambientinformatica.kyklos.controle.UsuarioLogadoControl;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.UnidadeMedida;

public interface UnidadeMedidaNeg {
	UnidadeMedida consultar(Integer id) throws PedidoException;

	void salvar(UnidadeMedida um, UsuarioLogadoControl usuarioLogadoControl)
			throws PedidoException;
	
	void excluir(UnidadeMedida um);
	
	List<UnidadeMedida> todas();
}
