package br.com.ambientinformatica.kyklos.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UtilServico {

   public static <E> E consultarGet(String url, Class<E> classeResposta) {
      Client client = Client.create();
      WebResource resource = client.resource(url);

      ClientResponse response = resource.type("application/xml").get(
            ClientResponse.class);
      if (response.getStatus() == 200) {
         return response.getEntity(classeResposta);
      } else {
         throw new RuntimeException("Failed : HTTP error code : "
               + response.getStatus() + "\n" + response.getEntity(String.class));
      }

   }

}
