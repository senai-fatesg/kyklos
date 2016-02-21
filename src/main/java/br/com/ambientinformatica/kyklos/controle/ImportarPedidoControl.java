package br.com.ambientinformatica.kyklos.controle;

import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.Vendedor;
import br.com.ambientinformatica.kyklos.negocio.PedidoNeg;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.PedidoDao;
import br.com.ambientinformatica.kyklos.persistencia.VendedorDao;

@Named("ImportarPedidoControl")
@Scope("conversation")
public class ImportarPedidoControl {

   @Autowired
   private UsuarioLogadoControl usuarioLogadoControl;

   @Autowired
   private PedidoDao pedidoDao;

   @Autowired
   private PedidoNeg pedidoNeg;

   @Autowired
   private VendedorDao vendedorDao;

   @Autowired
   private EstoqueDao estoqueDao;

   private EmpresaUsuario empresaUsuario;

   private Pedido pedido = new Pedido();

   public void upLoadArquivo(FileUploadEvent event) {
      try {
         String dados = new String(event.getFile().getContents(), "ISO-8859-1");
         pedido = pedidoNeg.converterArquivoEmPedido(dados, usuarioLogadoControl.getEmpresaUsuario()); 
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }
   }

   public void salvarPedido(ActionEvent actionEvent) {
      try {
         Vendedor vendedor = new Vendedor();
         pedido.setEmpresaEmitente(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
         vendedor = vendedorDao.consultarPorEmpresaEPessoa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa(), usuarioLogadoControl.getEmpresaUsuario().getEmpresa().getPessoa());
         if(vendedor == null){
            vendedor = new Vendedor();   
            vendedor.setEmpresa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa());
            vendedor.setPessoa(usuarioLogadoControl.getEmpresaUsuario().getEmpresa().getPessoa());
            vendedorDao.incluir(vendedor);
         }

         pedido.setVendedor(vendedor);
         pedidoDao.incluir(pedido);

         pedido = new Pedido();
         UtilFaces.addMensagemFaces("Pedido Salvo com sucesso!");
      } catch (PersistenciaException e) {
         UtilFaces.addMensagemFaces(e);
      }
   }

   public Pedido getPedido() {
      return pedido;
   }

   public void setPedido(Pedido pedido) {
      this.pedido = pedido;
   }

   public EmpresaUsuario getEmpresaUsuario() {
      return empresaUsuario;
   }

   public void setEmpresaUsuario(EmpresaUsuario empresaUsuario) {
      this.empresaUsuario = empresaUsuario;
   }

}
