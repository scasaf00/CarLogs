package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.GastosFacadeLocal;
import es.elchivy.carlogs.modelo.Gastos;
import es.elchivy.carlogs.modelo.Usuarios;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Named
@ViewScoped
public class UserController implements Serializable {
    private BarChartModel barModel;

    private DonutChartModel donutModel;

    private List<Gastos> gastos = new ArrayList<>();

    private Usuarios user;

    @EJB
    private GastosFacadeLocal ejbGastos;

    @PostConstruct
    public void init() {
        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        this.gastos = ejbGastos.getAllByUser(user);
        createBarModels();
        createDonutModels();
    }

    // Grafica para los Gastos anuales
    private void createBarModels() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        // Datos Totales

        BarChartDataSet totalDataset = new BarChartDataSet();
        totalDataset.setLabel("Total");

        List<Number> values = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            values.add(getGastosByMes(i));
        }
        totalDataset.setData(values);

        // Datos Repostajes

        BarChartDataSet repostajesDataset = new BarChartDataSet();
        repostajesDataset.setLabel("Repostaje");
        
        List<Number> valuesRepostajes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            valuesRepostajes.add(getGastosByMesRepostajes(i));
        }
        repostajesDataset.setData(valuesRepostajes);

        // Datos Mantenimientos

        BarChartDataSet mantenimientosDataset = new BarChartDataSet();
        mantenimientosDataset.setLabel("Mantenimiento");

        List<Number> valuesMantenimientos = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            valuesMantenimientos.add(getGastosByMesMantenimientos(i));
        }
        mantenimientosDataset.setData(valuesMantenimientos);

        // Datos Otros

        BarChartDataSet otrosDataset = new BarChartDataSet();
        otrosDataset.setLabel("Otros");

        List<Number> valuesOtros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            valuesOtros.add(getGastosByMesOtros(i));
        }
        otrosDataset.setData(valuesOtros);

        // Colores background

        List<String> bgColorTotal = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorTotal.add("rgba(255, 99, 132, 0.2)");
        }
        totalDataset.setBackgroundColor(bgColorTotal);

        List<String> bgColorRepostajes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorRepostajes.add("rgba(54, 162, 235, 0.2)");
        }
        repostajesDataset.setBackgroundColor(bgColorRepostajes);

        List<String> bgColorMantenimientos = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorMantenimientos.add("rgba(255, 206, 86, 0.2)");
        }
        mantenimientosDataset.setBackgroundColor(bgColorMantenimientos);

        List<String> bgColorOtros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorOtros.add("rgba(75, 192, 192, 0.2)");
        }
        otrosDataset.setBackgroundColor(bgColorOtros);

        // Colores border

        List<String> borderColorTotal = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorTotal.add("rgba(255,99,132,1)");
        }
        totalDataset.setBorderColor(borderColorTotal);
        totalDataset.setBorderWidth(1);

        List<String> borderColorRepostajes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorRepostajes.add("rgb(54, 162, 235, 1)");
        }
        repostajesDataset.setBorderColor(borderColorRepostajes);
        repostajesDataset.setBorderWidth(1);

        List<String> borderColorMantenimientos = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorMantenimientos.add("rgb(255, 159, 64, 1)");
        }
        mantenimientosDataset.setBorderColor(borderColorMantenimientos);
        mantenimientosDataset.setBorderWidth(1);

        List<String> borderColorOtros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorOtros.add("rgb(75, 192, 192, 1)");
        }
        otrosDataset.setBorderColor(borderColorOtros);
        otrosDataset.setBorderWidth(1);

        // Añadir los datasets al data

        data.addChartDataSet(totalDataset);
        data.addChartDataSet(repostajesDataset);
        data.addChartDataSet(mantenimientosDataset);
        data.addChartDataSet(otrosDataset);

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
        data.setLabels(labels);
        barModel.setData(data);

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

        barModel.setOptions(options);
    }

    private void createDonutModels(){
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(getGastosByMesRepostajes(Calendar.getInstance().get(Calendar.MONTH)));
        values.add(getGastosByMesMantenimientos(Calendar.getInstance().get(Calendar.MONTH)));
        values.add(getGastosByMesOtros(Calendar.getInstance().get(Calendar.MONTH)));
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgba(54, 162, 235, 1)");
        bgColors.add("rgb(255, 159, 64, 1)");
        bgColors.add("rgba(75, 192, 192, 1)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Repostaje");
        labels.add("Mantenimiento");
        labels.add("Otros");
        data.setLabels(labels);

        donutModel.setData(data);
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    private float getGastosByMes(int mes) {
        float total = 0;
        for (Gastos g : gastos) {
            if((g.getFecha().getYear() + 1900) == java.time.LocalDate.now().getYear() && g.getFecha().getMonth() == mes) {
                total += Float.parseFloat(g.getPrecio().toString());
            }
        }
        return total;
    }

    private float getGastosByMesRepostajes(int i) {
        float total = 0;
        for (Gastos g : gastos) {
            if((g.getFecha().getYear() + 1900) == Calendar.getInstance().get(Calendar.YEAR)){
                if (g.getFecha().getMonth() == i) {
                    if (g.getTipo().equals("REPOSTAJE")) {
                        total += Float.parseFloat(g.getPrecio().toString());
                    }
                }
            }
        }
        return total;
    }

    private float getGastosByMesMantenimientos(int i) {
        float total = 0;
        for (Gastos g : gastos) {
            if((g.getFecha().getYear() + 1900)== Calendar.getInstance().get(Calendar.YEAR)){
                if (g.getFecha().getMonth() == i) {
                    if (g.getTipo().equals("MANTENIMIENTO")) {
                        total += Float.parseFloat(g.getPrecio().toString());
                    }
                }
            }
        }
        return total;
    }

    private float getGastosByMesOtros(int i) {
        float total = 0;
        for (Gastos g : gastos) {
            if((g.getFecha().getYear() + 1900)== Calendar.getInstance().get(Calendar.YEAR)){
                if (g.getFecha().getMonth() == i) {
                    if (g.getTipo().equals("OTROS")) {
                        total += Float.parseFloat(g.getPrecio().toString());
                    }
                }
            }
        }
        return total;
    }

    private float getGastosbyYear(int year) {
        float total = 0;
        for (Gastos g : gastos) {
            if((g.getFecha().getYear() + 1900) == year) {
                total += Float.parseFloat(g.getPrecio().toString());
            }
        }
        return total;
    }

    public Integer getVehiculos() {
        return user.getVehiculosCollection().size();
    }

    public String getTotalG() {
        return String.valueOf(getGastosbyYear(Calendar.getInstance().get(Calendar.YEAR)));
    }
}