package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.ambientinformatica.util.Entidade;

@Entity
@Table(
		uniqueConstraints={
				@UniqueConstraint(columnNames={"chave", "empresacliente_id"})
		})
public class ParametroEmpresa extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;
   
	@Id
	@GeneratedValue(generator="parametroempresa_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="parametroempresa_seq", sequenceName="parametroempresa_seq", allocationSize=1, initialValue=1)
	private Long id;

	private String chave;
	
	private String valor;
	
	@ManyToOne(optional=false)
	private EmpresaCliente empresaCliente;

   public ParametroEmpresa() {
      super();
   }

   public ParametroEmpresa(String chave, String valor, EmpresaCliente empresaCliente) {
		this.setChave(chave);
		this.setValor(valor);
		this.setEmpresaCliente(empresaCliente);
	}

	public Long getId() {
		return id;
	}

   public String getChave() {
      return chave;
   }

   public void setChave(String chave) {
      this.chave = chave;
   }

   public String getValor() {
      return valor;
   }

   public void setValor(String valor) {
      this.valor = valor;
   }

   public EmpresaCliente getEmpresaCliente() {
      return empresaCliente;
   }

   public void setEmpresaCliente(EmpresaCliente empresaCliente) {
      this.empresaCliente = empresaCliente;
   }


}
