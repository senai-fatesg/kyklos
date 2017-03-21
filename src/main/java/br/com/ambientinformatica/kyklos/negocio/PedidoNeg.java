package br.com.ambientinformatica.kyklos.negocio;

import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.util.KyklosException;

public interface PedidoNeg {

   Pedido converterArquivoEmPedido(String arquivo, EmpresaUsuario empresaUsuario) throws KyklosException;

}
