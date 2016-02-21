package br.com.ambientinformatica.kyklos.entidade;

import java.util.List;

import br.com.ambientinformatica.util.AmbientException;

public class PedidoException extends AmbientException{

   private static final long serialVersionUID = 1L;

   public PedidoException() {
      super();
   }

   public PedidoException(List<String> listaMensagens) {
      super(listaMensagens);
   }

   public PedidoException(String message, Throwable cause) {
      super(message, cause);
   }

   public PedidoException(String message) {
      super(message);
   }

   public PedidoException(Throwable cause) {
      super(cause);
   }
}
