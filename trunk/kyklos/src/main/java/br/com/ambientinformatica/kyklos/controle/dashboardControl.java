package br.com.ambientinformatica.kyklos.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;

@Named("DashboardControl")
@Scope("conversation")
public class dashboardControl implements Serializable{

   private static final long serialVersionUID = 1L;
   private HorizontalBarChartModel barModel;
   private PieChartModel icmsSaidaBarModel;
   private PieChartModel icmsEntradaBarModel;
   private BarChartModel produtoBarModel;
   private BarChartModel impostometroBarModel;
   private PieChartModel impostometroPieModel;

   @PostConstruct
   public void init() {
      createBarModels();
   }

   public HorizontalBarChartModel getBarModel() {
      return barModel;
   }

   public BarChartModel getProdutoBarModel() {
      return produtoBarModel;
   }

   public PieChartModel getIcmsSaidaBarModel() {
      return icmsSaidaBarModel;
   }

   public PieChartModel getIcmsEntradaBarModel() {
      return icmsEntradaBarModel;
   }

   public BarChartModel getImpostometroBarModel() {
      return impostometroBarModel;
   }

   public PieChartModel getImpostometroPieModel() {
      return impostometroPieModel;
   }

   private HorizontalBarChartModel initBarModel() {
      HorizontalBarChartModel model = new HorizontalBarChartModel();

      ChartSeries outros = new ChartSeries();
      outros.setLabel("Outros");
      outros.set("CONFINS", 1555);
      outros.set("PIS", 1550);
      outros.set("IPI", 2000);
      outros.set("ICMS", 33000);

      ChartSeries agilize = new ChartSeries();
      agilize.setLabel("Agilize");
      agilize.set("CONFINS", 1050);
      agilize.set("PIS", 900);
      agilize.set("IPI", 1125);
      agilize.set("ICMS", 23500);

      model.addSeries(outros);
      model.addSeries(agilize);

      return model;
   }

   private HorizontalBarChartModel initProdutoBarModel() {
      HorizontalBarChartModel produtoModel = new HorizontalBarChartModel();

      ChartSeries tributos = new ChartSeries();
      tributos.setLabel("Tributos");
      tributos.set("Produto 1", 17);
      tributos.set("Produto 2", 13.5);
      tributos.set("Produto 3", 10.5);

      produtoModel.addSeries(tributos);

      return produtoModel;
   }

   private BarChartModel initImpostometroBarModel() {
      BarChartModel impostometroBarModel = new BarChartModel();

      ChartSeries totalTributos = new ChartSeries();
      totalTributos.setLabel("Total Tributos");
      totalTributos.set("ICMS Entrada", 38.500);
      totalTributos.set("ICMS Saida", 49.550);

      ChartSeries tributosAPagar = new ChartSeries();
      tributosAPagar.setLabel("A pagar");
      tributosAPagar.set("ICMS Entrada", 0);
      tributosAPagar.set("ICMS Saida", 18.750);

      impostometroBarModel.addSeries(totalTributos);
      impostometroBarModel.addSeries(tributosAPagar);

      return impostometroBarModel;
   }

   private PieChartModel icmsSaidaBarModel() {
      PieChartModel icmsSaidaModel = new PieChartModel();

      icmsSaidaModel.set("ICMS - Outros", 57000);
      icmsSaidaModel.set("ICMS - Agilize", 35800);

      icmsSaidaModel.setTitle("Custom Pie");
      icmsSaidaModel.setLegendPosition("e");
      icmsSaidaModel.setFill(true);
      icmsSaidaModel.setShowDataLabels(true);
      icmsSaidaModel.setDiameter(150);
      return icmsSaidaModel;
   }

   private PieChartModel icmsEntradaBarModel() {
      PieChartModel icmsEntradaModel = new PieChartModel();

      icmsEntradaModel.set("ICMS - Outros", 45000);
      icmsEntradaModel.set("ICMS - Agilize", 25600);

      icmsEntradaModel.setTitle("Custom Pie");
      icmsEntradaModel.setLegendPosition("e");
      icmsEntradaModel.setFill(true);
      icmsEntradaModel.setShowDataLabels(true);
      icmsEntradaModel.setDiameter(150);
      return icmsEntradaModel;
   }

   private void createBarModels() {
      createBarModel();
      createProdutoBarModel();
      createIcmsSaidaBarModel();
      createIcmsEntradaBarModel();
      createImpostometroBarModel();
      createImpostometroPieModel();
   }

   private void createBarModel() {
      barModel = initBarModel();

      barModel.setTitle("Tributação: Agilize / Outros");
      barModel.setLegendPosition("se");

      Axis xAxis = barModel.getAxis(AxisType.X);
      xAxis.setLabel("Valores");

      Axis yAxis = barModel.getAxis(AxisType.Y);
      yAxis.setLabel("Tributos");
      yAxis.setMin(0);
   }

   private void createImpostometroBarModel() {
      impostometroBarModel = initImpostometroBarModel();

      impostometroBarModel.setTitle("Impostômetro: ICMS Entrada / Saída");
      impostometroBarModel.setLegendPosition("ne");
      impostometroBarModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);

      Axis xAxis = impostometroBarModel.getAxis(AxisType.X);
      xAxis.setLabel("Tributo");

      Axis yAxis = impostometroBarModel.getAxis(AxisType.Y);
      yAxis.setLabel("Valor");
      yAxis.setMin(0);
   }
   
   private void createImpostometroPieModel() {
      impostometroPieModel = new PieChartModel();
      
      impostometroPieModel.set("IPI", 540);
      impostometroPieModel.set("CONFINS", 325);
      impostometroPieModel.set("ICMS", 702);
      impostometroPieModel.set("PIS", 421);
       
      impostometroPieModel.setTitle("Total em Tributos");
      impostometroPieModel.setLegendPosition("ne");
      impostometroPieModel.setFill(true);
      impostometroPieModel.setShowDataLabels(true);
      impostometroPieModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
      impostometroPieModel.setDiameter(150);
      impostometroPieModel.getDataLabelFormatString();
   }

   private void createProdutoBarModel() {
      produtoBarModel = initProdutoBarModel();

      produtoBarModel.setTitle("Produtos com maior taxa de tributação");
      produtoBarModel.setLegendPosition("ne");

      Axis xAxis = produtoBarModel.getAxis(AxisType.X);
      xAxis.setLabel(" % de Tributação");

      Axis yAxis = produtoBarModel.getAxis(AxisType.Y);
      yAxis.setLabel("Produtos");
      yAxis.setMin(0);
      yAxis.setMax(3);
   }

   private void createIcmsSaidaBarModel() {
      icmsSaidaBarModel = icmsSaidaBarModel();

      icmsSaidaBarModel.setTitle("ICMS Saída por CFOP: Agilize / Outros");
      icmsSaidaBarModel.setLegendPosition("ne");
      icmsSaidaBarModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
   }

   private void createIcmsEntradaBarModel() {
      icmsEntradaBarModel = icmsEntradaBarModel();

      icmsEntradaBarModel.setTitle("ICMS Entrada por CFOP: Agilize / Outros");
      icmsEntradaBarModel.setLegendPosition("ne");
      icmsEntradaBarModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
   }

}
