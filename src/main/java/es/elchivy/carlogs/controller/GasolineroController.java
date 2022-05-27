package es.elchivy.carlogs.controller;


import es.elchivy.carlogs.ejb.RepostajesFacadeLocal;
import es.elchivy.carlogs.modelo.Gasolineras;
import es.elchivy.carlogs.modelo.Gasolineros;
import es.elchivy.carlogs.modelo.Repostajes;
import es.elchivy.carlogs.modelo.Usuarios;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Named
@ViewScoped
public class GasolineroController implements Serializable {

    private BarChartModel barModelImporte;

    private BarChartModel barModelLitros;

    private List<Repostajes> repostajes;

    private Usuarios user;

    private Gasolineros gasolinero;

    private Gasolineras gasolinera;


    @PostConstruct
    public void init(){

        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        gasolinero = user.getGasolinerosCollection().iterator().next();
        gasolinera = gasolinero.getGasolineras();
        this.repostajes = (List<Repostajes>) gasolinera.getRepostajesCollection();
        createBarModelImporte();
        createBarModelLitros();
    }
    public void createBarModelImporte() {
        barModelImporte = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet totalDataset = new BarChartDataSet();
        totalDataset.setLabel("Importe");
        List<Number> values = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            values.add(getRepostajesByMesPrecio(i));
        }
        totalDataset.setData(values);


        List<String> bgColorTotal = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorTotal.add("rgba(255, 99, 132, 0.2)");
        }
        totalDataset.setBackgroundColor(bgColorTotal);

        // Colores border

        List<String> borderColorTotal = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorTotal.add("rgba(255,99,132,1)");
        }
        totalDataset.setBorderColor(borderColorTotal);
        totalDataset.setBorderWidth(1);

        // Añadir los datasets al data

        data.addChartDataSet(totalDataset);

        List<String> labels = getMonths();
        data.setLabels(labels);
        barModelImporte.setData(data);

        BarChartOptions options = getBarChartOptions();

        barModelImporte.setOptions(options);
    }

    public void createBarModelLitros(){

        barModelLitros = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet litrosDataset = new BarChartDataSet();
        litrosDataset.setLabel("Litros");
        List<Number> valuesLitros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            valuesLitros.add(getRepostajesByMesLitros(i));
        }
        litrosDataset.setData(valuesLitros);

        List<String> bgColorLitros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorLitros.add("rgba(54, 162, 235, 0.2)");
        }
        litrosDataset.setBackgroundColor(bgColorLitros);

        // Colores border

        List<String> borderColorRepostajes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorRepostajes.add("rgb(54, 162, 235, 1)");
        }
        litrosDataset.setBorderColor(borderColorRepostajes);
        litrosDataset.setBorderWidth(1);

        // Añadir los datasets al data

        data.addChartDataSet(litrosDataset);

        List<String> labels = getMonths();
        data.setLabels(labels);
        barModelLitros.setData(data);

        BarChartOptions options = getBarChartOptions();

        barModelLitros.setOptions(options);

    }

    private BarChartOptions getBarChartOptions() {
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gastos");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);
        return options;
    }

    private List<String> getMonths() {
        List<String> labels = new ArrayList<>();
        labels.add("Enero");
        labels.add("Febrero");
        labels.add("Marzo");
        labels.add("Abril");
        labels.add("Mayo");
        labels.add("Junio");
        labels.add("Julio");
        labels.add("Agosto");
        labels.add("Septiembre");
        labels.add("Octubre");
        labels.add("Noviembre");
        labels.add("Diciembre");
        return labels;
    }


    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public BarChartModel getBarModelImporte() { return barModelImporte; }

    public void setBarModelImporte(BarChartModel barModelImporte) { this.barModelImporte = barModelImporte;}

    public BarChartModel getBarModelLitros() { return barModelLitros;}

    public void setBarModelLitros(BarChartModel barModelLitros) { this.barModelLitros = barModelLitros;}



    private float getRepostajesByMesPrecio(int mes) {
        float total = 0;
        for (Repostajes r : repostajes) {
            if((r.getFecha().getYear() + 1900) == Calendar.getInstance().get(Calendar.YEAR) && r.getFecha().getMonth() == mes){
                total += Float.parseFloat(r.getGastoId().getPrecio().toString());
            }
        }
        return total;
    }

    private float getRepostajesByMesLitros(int mes) {
        float total = 0;
        for (Repostajes r : repostajes) {
            if((r.getFecha().getYear() + 1900) == Calendar.getInstance().get(Calendar.YEAR) && r.getFecha().getMonth() == mes){
                total += Float.parseFloat(r.getLitros().toString());
            }
        }
        return total;
    }

}