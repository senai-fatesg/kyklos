package br.com.ambientinformatica.kyklos.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.EnumInstrumentoRecebimento;
import br.com.ambientinformatica.kyklos.entidade.InstrumentoRecebimento;
import br.com.ambientinformatica.kyklos.util.KyklosException;

@Repository("InstrumentoRecebimentoDao")
public class InstrumentoRecebimentoDaoJpa extends PersistenciaJpa<InstrumentoRecebimento> implements InstrumentoRecebimentoDao{

   private static final long serialVersionUID = 1L;

   @Override
   public InstrumentoRecebimento consultarPorInstrumentoRecebimento(EnumInstrumentoRecebimento instrumentoRecebimento) throws KyklosException {
      try{
         Query query = em.createQuery("select ir from InstrumentoRecebimento ir where ir.instrumentoRecebimento = :instrumentoRecebimento");
         query.setParameter("instrumentoRecebimento", instrumentoRecebimento);
         return (InstrumentoRecebimento) query.getSingleResult();
      }catch(NoResultException nre){
         return null;
      }
   }

}
