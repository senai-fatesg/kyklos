package br.com.ambientinformatica.kyklos.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Frete;

@Repository("freteDao")
public class FreteDaoJpa extends PersistenciaJpa<Frete> implements FreteDao{

   private static final long serialVersionUID = 1L;

}
