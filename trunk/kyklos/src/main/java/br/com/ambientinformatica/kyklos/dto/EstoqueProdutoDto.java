package br.com.ambientinformatica.kyklos.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.kyklos.entidade.Estoque;
import br.com.ambientinformatica.kyklos.entidade.EstoqueProduto;
import br.com.ambientinformatica.kyklos.entidade.Produto;

public class EstoqueProdutoDto {

   private EstoqueProduto estoqueProduto;

   private BigDecimal quantidadeSelecionada;
   
   public static List<EstoqueProdutoDto> converterEstoqueProduto(List<EstoqueProduto> lista){
      List<EstoqueProdutoDto> result = new ArrayList<EstoqueProdutoDto>();
      for (EstoqueProduto ep : lista) {
         result.add(new EstoqueProdutoDto(ep));
      }
      return result;
   }

   public EstoqueProdutoDto(EstoqueProduto estoqueProduto) {
      this.estoqueProduto = estoqueProduto;
   }

   public BigDecimal getQuantidadeSelecionada() {
      return quantidadeSelecionada;
   }

   public void setQuantidadeSelecionada(BigDecimal quantidadeSelecionada) {
      this.quantidadeSelecionada = quantidadeSelecionada;
   }

   public EstoqueProduto getEstoqueProduto() {
      return estoqueProduto;
   }

   public Estoque getEstoque() {
      return estoqueProduto.getEstoque();
   }

   public void setEstoque(Estoque estoque) {
      estoqueProduto.setEstoque(estoque);
   }

   public Produto getProduto() {
      return estoqueProduto.getProduto();
   }

   public void setProduto(Produto produto) {
      estoqueProduto.setProduto(produto);
   }

   public BigDecimal getQuantidade() {
      return estoqueProduto.getQuantidade();
   }

   public void setQuantidade(BigDecimal quantidade) {
      estoqueProduto.setQuantidade(quantidade);
   }

   public Long getId() {
      return estoqueProduto.getId();
   }

   public Date getData() {
      return estoqueProduto.getData();
   }

   public void setData(Date data) {
      estoqueProduto.setData(data);
   }

}
