package br.com.ambientinformatica.kyklos.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.kyklos.entidade.Cfop;
import br.com.ambientinformatica.kyklos.entidade.GrupoDiferencaFiscal;
import br.com.ambientinformatica.kyklos.entidade.InformacaoFiscal;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.util.KyklosException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("informacaoFiscalDao")
public class InformacaoFiscalDaoJpa extends PersistenciaJpa<InformacaoFiscal> implements InformacaoFiscalDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   public List<InformacaoFiscal> listar(Cfop cfop, Produto produto,
         EnumUf ufOrigem, EnumUf ufDestino, Boolean contribuinte, GrupoDiferencaFiscal grupoDiferencaFiscal)
               throws KyklosException {
      try {
         String jpaql = "select inf from InformacaoFiscal inf "
               + "where inf.cfop = :cfop ";

         if(produto != null){
            jpaql += "and (inf.produto = :produto or inf.ncm = :ncm) ";
         }

         if(grupoDiferencaFiscal != null){
            jpaql += "and inf.grupoDiferencaFiscal = :grupoDiferencaFiscal ";
         }else{
            jpaql += "and inf.grupoDiferencaFiscal = null ";
         }

         if(ufOrigem != null){
            jpaql += "and inf.ufOrigem = :ufOrigem ";
         }

         if(ufDestino != null){
            jpaql += "and inf.ufDestino = :ufDestino ";
         }

         if(contribuinte != null){
            jpaql += "and inf.contribuinte = :contribuinte ";
         }

         Query query = em.createQuery(jpaql);
         query.setParameter("cfop", cfop);

         if(produto != null){
            query.setParameter("produto", produto);
            query.setParameter("ncm", produto.getNcm());
         }
         if(ufOrigem != null){
            query.setParameter("ufOrigem", ufOrigem);
         }
         if(ufDestino != null){
            query.setParameter("ufDestino", ufDestino);
         }
         if(contribuinte != null){
            query.setParameter("contribuinte", contribuinte);
         }
         if(grupoDiferencaFiscal != null){
            query.setParameter("grupoDiferencaFiscal", grupoDiferencaFiscal);
         }

         return query.getResultList();
      } catch (Exception e) {
         UtilLog.getLog().error("erro ao listar informacoes fiscais", e);
         throw new KyklosException();
      }
   }

   private String getComplemento(int ordemCampo){
      if(ordemCampo == 0){
         return " where ";
      }else{
         return " and ";
      }
   }

   @SuppressWarnings("unchecked")
   public List<InformacaoFiscal> listar(Cfop cfop, Produto produto, Integer ncm, EnumUf ufOrigem, EnumUf ufDestino, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException {
      try {
         int ordemCampo = 0;
         String jpaql = "select inf from InformacaoFiscal inf ";

         if(cfop != null && cfop.getCfop() != null){
            jpaql += getComplemento(ordemCampo) + " inf.cfop = :cfop ";
            ordemCampo ++;
         }

         if(produto != null && produto.getId() != null){
            jpaql += getComplemento(ordemCampo) +" inf.produto = :produto";
            ordemCampo ++;
         }

         if(grupoDiferencaFiscal != null && grupoDiferencaFiscal.getId() != null){
            jpaql += getComplemento(ordemCampo) +" inf.grupoDiferencaFiscal = :grupoDiferencaFiscal";
            ordemCampo ++;
         }

         if(ncm != null){
            jpaql += getComplemento(ordemCampo) +" inf.ncm = :ncm ";
            ordemCampo ++;
         }

         if(ufOrigem != null){
            jpaql += getComplemento(ordemCampo) +" inf.ufOrigem = :ufOrigem "; 
            ordemCampo ++;
         }

         if(ufDestino != null){
            jpaql += getComplemento(ordemCampo) +" inf.ufDestino = :ufDestino";
         }


         Query query = em.createQuery(jpaql);

         if(cfop != null && cfop.getCfop() != null){
            query.setParameter("cfop", cfop);
         }

         if(produto != null && produto.getId() != null){
            query.setParameter("produto", produto);
         }

         if(ncm != null){
            query.setParameter("ncm", ncm);
         }

         if(ufOrigem != null){
            query.setParameter("ufOrigem", ufOrigem);
         }

         if(ufDestino != null){
            query.setParameter("ufDestino", ufDestino);
         }

         if(grupoDiferencaFiscal != null && grupoDiferencaFiscal.getId() != null){
            query.setParameter("grupoDiferencaFiscal", grupoDiferencaFiscal);
         }

         return query.getResultList();
      } catch (Exception e) {
         UtilLog.getLog().error("erro ao listar informacoes fiscais", e);
         throw new KyklosException();
      }
   }

   @Override
   public InformacaoFiscal consultarInformacaoFiscal(Cfop cfop, Produto produto, Integer ncm, EnumUf ufOrigem, EnumUf ufDestino, Boolean contribuinte, GrupoDiferencaFiscal grupoDiferencaFiscal) throws KyklosException {
      try {
         int ordemCampo = 0;
         String jpaql = "select inf from InformacaoFiscal inf ";

         if(cfop != null && cfop.getCfop() != null){
            jpaql += getComplemento(ordemCampo) + " inf.cfop = :cfop ";
            ordemCampo ++;
         }

         if(produto != null && produto.getId() != null){
            jpaql += getComplemento(ordemCampo) +" inf.produto = :produto";
            ordemCampo ++;
         }

         if(grupoDiferencaFiscal != null && grupoDiferencaFiscal.getId() != null){
            jpaql += getComplemento(ordemCampo) +" inf.grupoDiferencaFiscal = :grupoDiferencaFiscal";
            ordemCampo ++;
         }

         if(ncm != null){
            jpaql += getComplemento(ordemCampo) +" inf.ncm = :ncm ";
            ordemCampo ++;
         }

         if(ufOrigem != null){
            jpaql += getComplemento(ordemCampo) +" inf.ufOrigem = :ufOrigem "; 
            ordemCampo ++;
         }

         if(ufDestino != null){
            jpaql += getComplemento(ordemCampo) +" inf.ufDestino = :ufDestino";
         }


         Query query = em.createQuery(jpaql);

         if(cfop != null && cfop.getCfop() != null){
            query.setParameter("cfop", cfop);
         }

         if(produto != null && produto.getId() != null){
            query.setParameter("produto", produto);
         }

         if(ncm != null){
            query.setParameter("ncm", ncm);
         }

         if(ufOrigem != null){
            query.setParameter("ufOrigem", ufOrigem);
         }

         if(ufDestino != null){
            query.setParameter("ufDestino", ufDestino);
         }

         if(grupoDiferencaFiscal != null && grupoDiferencaFiscal.getId() != null){
            query.setParameter("grupoDiferencaFiscal", grupoDiferencaFiscal);
         }

         return (InformacaoFiscal)query.getSingleResult();
      
      } catch (Exception e) {
         
         return null;
      
      }
   }
}


