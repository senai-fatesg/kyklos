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

import br.com.ambientinformatica.corporativo.entidade.Pessoa;

@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"pessoa_id", "empresa_id"})
      })
public class Vendedor implements Serializable{
   
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="vendedor_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="vendedor_seq", sequenceName="vendedor_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @ManyToOne(optional=false)
   private EmpresaCliente empresa;

   @ManyToOne(optional=false)
   private Pessoa pessoa;

   public EmpresaCliente getEmpresa() {
      return empresa;
   }

   public void setEmpresa(EmpresaCliente empresa) {
      this.empresa = empresa;
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
   
}
