package br.com.ambientinformatica.kyklos.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;

@Entity
public class EmpresaCliente implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="empresacliente_seq", strategy=GenerationType.SEQUENCE)
   @SequenceGenerator(name="empresacliente_seq", sequenceName="empresacliente_seq", allocationSize=1, initialValue=1)
   private Integer id;

   @OneToOne(cascade=CascadeType.ALL)
   private Pessoa pessoa = new Pessoa();
   
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
