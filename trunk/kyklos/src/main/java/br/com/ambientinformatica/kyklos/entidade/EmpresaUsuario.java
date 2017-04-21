package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;

@Entity
public class EmpresaUsuario implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="empresausuario_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="empresausuario_seq", sequenceName="empresausuario_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @ManyToOne(optional=false)
   private EmpresaCliente empresa;

   @ManyToOne(optional=false)
   private Usuario usuario;
   
   private Pessoa pessoa;

   private boolean principal;

   @Column(nullable=false)
   private Date dataInclusao = new Date();

   public EmpresaCliente getEmpresa() {
      return empresa;
   }

   public void setEmpresa(EmpresaCliente empresa) {
      this.empresa = empresa;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public Date getDataInclusao() {
      return dataInclusao;
   }

   public Integer getId() {
      return id;
   }

   public boolean isPrincipal() {
      return principal;
   }

   public void setPrincipal(boolean principal) {
      this.principal = principal;
   }

public Pessoa getPessoa() {
	return pessoa;
}

public void setPessoa(Pessoa pessoa) {
	this.pessoa = pessoa;
}
   
   


}

