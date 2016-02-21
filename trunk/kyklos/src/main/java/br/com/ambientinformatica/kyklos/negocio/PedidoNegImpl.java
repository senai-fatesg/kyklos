package br.com.ambientinformatica.kyklos.negocio;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ambientinformatica.corporativo.entidade.Pessoa;
import br.com.ambientinformatica.kyklos.entidade.EmpresaCliente;
import br.com.ambientinformatica.kyklos.entidade.EmpresaUsuario;
import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.ItemPedido;
import br.com.ambientinformatica.kyklos.entidade.Pedido;
import br.com.ambientinformatica.kyklos.entidade.PedidoException;
import br.com.ambientinformatica.kyklos.entidade.Produto;
import br.com.ambientinformatica.kyklos.entidade.Usuario;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueDao;
import br.com.ambientinformatica.kyklos.persistencia.EstoqueProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.MunicipioDao;
import br.com.ambientinformatica.kyklos.persistencia.PedidoDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaDao;
import br.com.ambientinformatica.kyklos.persistencia.PessoaEmpresaDao;
import br.com.ambientinformatica.kyklos.persistencia.ProdutoDao;
import br.com.ambientinformatica.kyklos.persistencia.UnidadeMedidaDao;

@Service("pedidoNeg")
public class PedidoNegImpl implements PedidoNeg{

   @Autowired
   private ProdutoDao produtoDao;

   @Autowired
   private MunicipioDao municipioDao;
   
   @Autowired
   private PedidoDao pedidoDao;

   @Autowired
   private PessoaDao pessoaDao;

   @Autowired
   private PessoaEmpresaDao pessoaEmpresaDao;

   @Autowired
   private UnidadeMedidaDao unidadeMedidaDao;

   @Autowired
   private EstoqueDao estoqueDao;

   @Autowired
   private EstoqueProdutoDao estoqueProdutoDao;

   @SuppressWarnings("resource")
   @Transactional(rollbackFor=PedidoException.class)
   public Pedido converterArquivoEmPedido(String arquivo, EmpresaUsuario empresaUsuario) throws PedidoException {
      Scanner sc = new Scanner(arquivo);
      String linha = sc.nextLine();
      Pedido pedido = new Pedido();

      if(arquivo == null || arquivo.isEmpty()){
         throw new PedidoException("O arquivo no pode estar vazio");
      }
      if(linha.contains("Orçamento")){
         pedido.setNumero(linha.substring(59, 71).trim());
         Pedido pedidoConsultado = pedidoDao.consultarPorNumero(empresaUsuario.getEmpresa(), pedido.getNumero());
         if(pedidoConsultado == null){
            pedido = validarPedido(pedido, arquivo);
            pedido = validarCliente(pedido, arquivo, empresaUsuario.getEmpresa());
            pedido = validarItemPedido(pedido, arquivo, empresaUsuario.getEmpresa(), empresaUsuario.getUsuario());
         }else{
            throw new PedidoException("Pedido já importado!");
         }
      }
      return pedido;
   }

   @SuppressWarnings("resource")
   private Pedido validarPedido(Pedido pedido, String arquivo) throws PedidoException {
      Scanner sc = new Scanner(arquivo);
      String linha = sc.nextLine();
      while(sc.hasNextLine()){
         try {
            if(pedido.getNumero() == null && linha.contains("Orçamento")){
               pedido.setNumero(linha.substring(59, 71).trim());
            }
            if(linha.contains("/")){
               int ponteiroData = linha.indexOf('/');
               if(linha.substring(ponteiroData, ponteiroData+2).contains("/") && linha.substring(ponteiroData+3, ponteiroData+5).contains("/")){
                  String linhaTemp = sc.nextLine();
                  if(linhaTemp.contains("Emissão")){
                     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                     sdf.setLenient(false);
                     pedido.setData(sdf.parse(linha.substring(86, 96)));
                  }
               }
            }
            linha = sc.nextLine();
         } catch (ParseException e) {
            throw new PedidoException("O arquivo possui um campo data inválido. " + e.getMessage(), e);
         }
      }

      return pedido;
   }

   @SuppressWarnings("resource")
   private Pedido validarCliente(Pedido pedido, String arquivo, EmpresaCliente empresa) throws PedidoException {
      try{
         Pessoa clienteConsultado = null;
         String cpfCnpjCliente = null;

         Scanner sc = new Scanner(arquivo);
         Scanner scAuxiliar = new Scanner(arquivo);

         while(scAuxiliar.hasNextLine()){
            String linha = scAuxiliar.nextLine();

            if(linha.contains("CFOP")){
               linha = scAuxiliar.nextLine();
               linha = scAuxiliar.nextLine();
               linha = scAuxiliar.nextLine();
               linha = scAuxiliar.nextLine();
               cpfCnpjCliente = linha.substring(7, 34).replace(".", "").replace("/", "").replace("-", "").trim();
               clienteConsultado = pessoaDao.consultarPorCpfCnpj(cpfCnpjCliente);
               break;
            }
         }

         while(sc.hasNextLine()){
            String linha = sc.nextLine();

            if(linha.contains("CFOP")){

               if(clienteConsultado == null){
                  Pessoa cliente = new Pessoa();
                  
                  linha = sc.nextLine();
                  cliente.setNomeFantasia(linha.substring(0, 70).trim());
                  cliente.setNome(linha.substring(70, 96).trim());
                  linha = sc.nextLine();
                  cliente.setLogradouro(linha.substring(0, 70).trim());
                  cliente.setBairro(linha.substring(70, 96).trim());
                  linha = sc.nextLine();
                  cliente.setMunicipio(municipioDao.consultarPorDescricao(linha.substring(0, 33).trim()));
                  cliente.setCep(linha.substring(42, 50).trim()+"0");
                  linha = sc.nextLine();
                  cliente.setCpfCnpj(cpfCnpjCliente);
                  pessoaDao.incluir(cliente);
                  clienteConsultado = cliente;
               } 
               pedido.setCliente(clienteConsultado);
            }
         }

      }catch(Exception e){
         throw new PedidoException(e.getMessage(), e);
      }
      return pedido;
   }


   @SuppressWarnings({ "resource" })
   private Pedido validarItemPedido(Pedido pedido, String arquivo, EmpresaCliente empresa, Usuario usuario) throws PedidoException {
      try{
         Scanner sc = new Scanner(arquivo);   
         String linha = sc.nextLine();
         while(sc.hasNextLine()){

            while(linha.contains("AT-")){
               Produto produto = new Produto();
               ItemPedido itemPedido = new ItemPedido();

               itemPedido.setNumeroItem(Integer.parseInt(linha.substring(1, 5).trim()));
               produto.setCodigo(linha.substring(5, 12).trim());
               Produto produtoConsultado = produtoDao.consultarPorCodigo(empresa, produto.getCodigo());

               if(produtoConsultado == null){
                  if (linha.length() < 60) {
                     linha = sc.nextLine();
                  }
                  produto.setDescricao(linha.substring(23, 56).trim());
                  produto.setEmpresa(empresa);
                  produto.setUnidadeMedida(unidadeMedidaDao.consultarPorSigla(linha.substring(63, 65).toUpperCase()));

                  produtoDao.incluir(produto);
                  produtoConsultado = produto;
               }

               if (linha.length() < 60) {
                  linha = sc.nextLine();
               }
               if(!linha.substring(65, 67).trim().equals("") && !linha.substring(65, 67).trim().equals("-")){
                  itemPedido.setObservacao(linha.substring(65, 69).trim());
               }
               itemPedido.setQuantidade(new BigDecimal(linha.substring(58, 63).replace(",", ".")));
               itemPedido.setValorUnitario(new BigDecimal(linha.substring(69, 75).replace(",", ".").trim()));
               itemPedido.setProduto(produtoConsultado);
               Date dataInicioReserva = new Date();
               itemPedido.setDataInicioReserva(dataInicioReserva);

               Calendar cal = Calendar.getInstance();
               cal.setTime(dataInicioReserva);
               cal.add(Calendar.DATE, 1); // Add 1 dia
               dataInicioReserva = cal.getTime();
               itemPedido.setDataFimReserva(dataInicioReserva);

               EstoqueProduto estoqueProduto = new EstoqueProduto();
               Estoque estoque = estoqueDao.consultarPorEstoquePadrao(empresa);
               if(estoque == null){
                  estoque = new Estoque();
                  estoque.setDescricao("Estoque Físico - " + empresa.getPessoa().getNome());
                  estoque.setEmpresa(empresa);
                  estoque.setEndereco(empresa.getPessoa().getEnderecoCompleto());
                  estoque.setPadrao(true);
                  estoque.setPessoaEmpresa(pessoaEmpresaDao.consultarPorCpfOuCnpj(empresa.getPessoa()));
                  estoqueDao.incluir(estoque);

               }

               estoqueProduto = estoqueProdutoDao.consultarPorEstoque(estoque);
               if(estoqueProduto == null){     
                  estoqueProduto = new EstoqueProduto();
                  estoqueProduto.setData(new Date());
                  estoqueProduto.setEstoque(estoque);
                  estoqueProduto.setProduto(produtoConsultado);
                  estoqueProduto.setQuantidade(itemPedido.getQuantidade());
                  estoqueProdutoDao.incluir(estoqueProduto);
               }
               itemPedido.setEstoque(estoqueProduto);
               itemPedido.setUsuario(usuario);
               
               pedido.getItens().add(itemPedido);
               linha = sc.nextLine();
            }
            linha = sc.nextLine();
         }
         pedido.setEmpresa(empresa);
      }catch(Exception e){
         throw new PedidoException(e.getMessage(), e);
      }
      return pedido;
   }

}
