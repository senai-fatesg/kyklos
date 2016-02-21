package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.kyklos.entidade.Transportadora;

public interface TransportadoraDao extends Persistencia<Transportadora>{
   
   public Transportadora consultarTransportadoraPorCpfCnpj(String cpfCnpj);

   public List<Transportadora> listarTransportadorasPorCpfCnpjRazaoSocial(String cpfCnpjRazaoSocial);
   
}
