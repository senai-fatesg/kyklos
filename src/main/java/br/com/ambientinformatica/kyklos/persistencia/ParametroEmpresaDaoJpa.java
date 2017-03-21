package br.com.ambientinformatica.kyklos.persistencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.ParametroEmpresa;


@Repository("parametroEmpresaDao")
public class ParametroEmpresaDaoJpa extends PersistenciaJpa<ParametroEmpresa> implements ParametroEmpresaDao{

   private static final long serialVersionUID = 1L;

   public ParametroEmpresa consultarPorChaveEmpresa(EmpresaCliente empresaCliente, String chaveDoParametro) {
      try{
         Query query = em.createQuery("select pe from ParametroEmpresa pe where pe.chave = :chave and pe.empresaCliente = :empresaCliente");
         query.setParameter("chave", chaveDoParametro);
         query.setParameter("empresaCliente", empresaCliente);
         return (ParametroEmpresa) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }

	@SuppressWarnings("unchecked")
   @Override
   public List<ParametroEmpresa> listarTodosEmpresa(EmpresaCliente empresaCliente) {
		Query query = em.createQuery("select pe from ParametroEmpresa pe where pe.empresaCliente = :empresaCliente");
      query.setParameter("empresaCliente", empresaCliente);
      return query.getResultList();
	}

	@Override
   public Map<String, String> listarTodosMapsEmpresa(EmpresaCliente empresaCliente) {
		List<ParametroEmpresa> parametros = listarTodosEmpresa(empresaCliente);
		Map<String, String> mapaParametros = new HashMap<String, String>();
		for (ParametroEmpresa parametro : parametros) {
	      mapaParametros.put(parametro.getChave(), parametro.getValor());
      }
	   return mapaParametros;
   }
}
