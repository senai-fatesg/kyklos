package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.util.Entidade;

@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"pessoa_id", "empresa_id"})
      })
public class PessoaEmpresa extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="pessoaempresa_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="pessoaempresa_seq", sequenceName="pessoaempresa_seq", allocationSize=1, initialValue=1)
   private Long id;

   @ManyToOne(optional=false)
   private Pessoa pessoa;
   
   @ManyToOne(optional=false)
   private EmpresaCliente empresa;
   
   @Temporal(TemporalType.TIMESTAMP)
   @Column(nullable=false)
   private Date data = new Date();

   @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
   @JoinColumn(name="empresacliente_id")
   private List<Endereco> enderecosEntrega = new ArrayList<Endereco>();
   
   public void addEnderecoEntrega(Endereco endereco){
   	enderecosEntrega.add(endereco);
   }
   
   public List<Endereco> getEnderecosEntrega() {
		return enderecosEntrega;
	}
   
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Pessoa getPessoa() {
      return pessoa;
   }

   public void setPessoa(Pessoa pessoa) {
      this.pessoa = pessoa;
   }

   public EmpresaCliente getEmpresa() {
      return empresa;
   }

   public void setEmpresa(EmpresaCliente empresa) {
      this.empresa = empresa;
   }

   public Date getData() {
      return data;
   }

   public void setData(Date data) {
      this.data = data;
   }
}
