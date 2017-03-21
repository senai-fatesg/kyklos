package br.com.ambientinformatica.kyklos.util;

import java.util.List;

import br.com.ambientinformatica.util.AmbientException;

public class KyklosException extends AmbientException{

   private static final long serialVersionUID = 1L;

   public KyklosException() {
      super();
   }

   public KyklosException(List<String> listaMensagens) {
      super(listaMensagens);
   }

   public KyklosException(String message, Throwable cause) {
      super(message, cause);
   }

   public KyklosException(String message) {
      super(message);
   }

   public KyklosException(Throwable cause) {
      super(cause);
   }
}
