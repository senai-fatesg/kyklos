package br.com.ambientinformatica.kyklos.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.PapelUsuario;


@Repository("papelUsuarioDao")
public class PapelUsuarioDaoJpa extends PersistenciaJpa<PapelUsuario> implements PapelUsuarioDao{

   private static final long serialVersionUID = 1L;
}