package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.agilize.api.entidade.EnumCRT;
import br.com.ambientinformatica.agilize.api.entidade.EnumEquiparacaoContribuinte;
import br.com.ambientinformatica.agilize.api.entidade.EnumRegimeEspecial;
import br.com.ambientinformatica.agilize.api.entidade.RegimeEspecial;
import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class EmpresaCliente extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="empresacliente_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="empresacliente_seq", sequenceName="empresacliente_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @OneToOne(cascade=CascadeType.ALL)
   private Pessoa pessoa = new Pessoa();
   
   @Enumerated(EnumType.STRING)
   private EnumCRT crt;

   private Integer cnae;
   
   @Enumerated(EnumType.STRING)
   private EnumEquiparacaoContribuinte equiparacao;
   
   @OneToMany(cascade=CascadeType.ALL)
   @JoinColumn(name="empresacliente_id")
   private Set<RegimeEspecial> regimesEspeciais = new HashSet<RegimeEspecial>();
   
   public boolean isRegimeNormal(){
      return crt == EnumCRT.REGIME_NORMAL;
   }
   
   public boolean isSimplesNacional(){
   	return crt == EnumCRT.SIMPLES_NACIONAL;
   }
   
   public void addRegimeEspecial(EnumRegimeEspecial regimeEspecial){
   	if(!isContemRegimeEspecial(regimeEspecial)){
		   RegimeEspecial re = new RegimeEspecial();
		   re.setPapel(regimeEspecial);
		   regimesEspeciais.add(re);
	   }
   }
   
   public boolean isContemRegimeEspecial(EnumRegimeEspecial regimeEspecial){
      for(RegimeEspecial r : regimesEspeciais){
         if(r.getRegimeEspecial() == regimeEspecial){
            return true;
         }
      }
      return false;
   }
   
   public Pessoa getPessoa() {
      return pessoa;
   }

   public void setPessoa(Pessoa pessoa) {
      this.pessoa = pessoa;
   }

   public Integer getId() {
      return id;
   }

	public EnumCRT getCrt() {
		return crt;
	}

	public void setCrt(EnumCRT crt) {
		this.crt = crt;
	}

	public Integer getCnae() {
		return cnae;
	}

	public void setCnae(Integer cnae) {
		this.cnae = cnae;
	}

	public EnumEquiparacaoContribuinte getEquiparacao() {
		return equiparacao;
	}

	public void setEquiparacao(EnumEquiparacaoContribuinte equiparacao) {
		this.equiparacao = equiparacao;
	}

   public Set<RegimeEspecial> getRegimesEspeciais() {
      return regimesEspeciais;
   }
   
   
}
