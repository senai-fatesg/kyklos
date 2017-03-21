package br.com.ambientinformatica.kyklos.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.ambientinformatica.util.Entidade;
@Entity
@Table(
      uniqueConstraints={
            @UniqueConstraint(columnNames={"contacorrente_id", "empresacliente_id"})
      })
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class ContratoBoletoBancario extends Entidade implements Serializable{

   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "contratoboletobancario_seq")
   @SequenceGenerator(name="contratoboletobancario_seq", sequenceName = "contratoboletobancario_seq", allocationSize=1, initialValue=1)
   private Long id;

   @ManyToOne(optional=false)
   private ContaCorrente contaCorrente = new ContaCorrente();
   
   @ManyToOne(optional=false)
   private EmpresaCliente empresaCliente = new EmpresaCliente();

   public ContratoBoletoBancario() {
   }

   public Long getId() {
      return id;
   }
   
   public ContaCorrente getContaCorrente() {
      return contaCorrente;
   }

   public void setContaCorrente(ContaCorrente contaCorrente) {
      this.contaCorrente = contaCorrente;
   }

   public EmpresaCliente getEmpresaCliente() {
      return empresaCliente;
   }

   public void setEmpresaCliente(EmpresaCliente empresaCliente) {
      this.empresaCliente = empresaCliente;
   }
   
}