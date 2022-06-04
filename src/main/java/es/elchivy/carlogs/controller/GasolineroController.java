package es.elchivy.carlogs.controller;


import es.elchivy.carlogs.ejb.GasolinerasFacadeLocal;
import es.elchivy.carlogs.ejb.RepostajesFacadeLocal;
import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
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
import java.util.Collection;
import java.util.List;

@Named
@ViewScoped
public class GasolineroController implements Serializable {

    private BarChartModel barModelImporte;

    private BarChartModel barModelLitros;

    private Collection<Repostajes> repostajes;

    private float precioGasolina;

    private float precioGasoil;

    private Usuarios user;

    private Gasolineros gasolinero;

    private Gasolineras gasolinera;

    @EJB
    private GasolinerasFacadeLocal ejbGasolineras;


    @PostConstruct
    public void init(){

        this.user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        this.gasolinero = user.getGasolineros();
        this.gasolinera = gasolinero.getGasolinera();
        this.repostajes = new ArrayList<>(gasolinera.getRepostajesCollection());
        this.precioGasolina = gasolinera.getPrecioGasolina();
        this.precioGasoil = gasolinera.getPrecioGasoil();
        createBarModelImporte();
        createBarModelLitros();
    }
    public void createBarModelImporte() {

        barModelImporte = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet totalDataset = new BarChartDataSet();
        BarChartDataSet gasolinaDataset = new BarChartDataSet();
        BarChartDataSet gasoilDataset = new BarChartDataSet();
        totalDataset.setLabel("Total");
        gasolinaDataset.setLabel("Gasolina");
        gasoilDataset.setLabel("Gasoil");
        List<Number> valuesGasolina = new ArrayList<>();
        List<Number> valuesGasoil = new ArrayList<>();
        List<Repostajes> repostajesGasolina = getRepostajesGasolina();
        List<Repostajes> repostajesGasoil = getRepostajesGasoil();


        //Importe tipo gasolina
        for (int i = 0; i < 12; i++) {
            valuesGasolina.add(getRepostajesByMesPrecio(i,repostajesGasolina));
        }
        System.out.println("Importe Gasolina: " + valuesGasolina);
        gasolinaDataset.setData(valuesGasolina);

        //Importe tipo gasoil

        for (int i = 0; i < 12; i++) {
            valuesGasoil.add(getRepostajesByMesPrecio(i,repostajesGasoil));
        }
        gasoilDataset.setData(valuesGasoil);

        List<String> bgColorImporte = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorImporte.add("rgba(255, 99, 132, 0.2)");
        }
        gasolinaDataset.setBackgroundColor(bgColorImporte);
        gasoilDataset.setBackgroundColor("rgba(255, 159, 64, 0.2)");

        //Sumar lista valuesGasolina y valuesGasoil
        List<Number> valuesTotal = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            valuesTotal.add(valuesGasolina.get(i).floatValue() + valuesGasoil.get(i).floatValue());
        }
        totalDataset.setData(valuesTotal);


        // Colores border

        List<String> borderColorLitros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorLitros.add("rgba(255,99,132,1)");
        }
        gasolinaDataset.setBorderColor(borderColorLitros);
        gasolinaDataset.setBorderWidth(1);

        gasoilDataset.setBorderColor("rgb(255, 159, 64)");
        gasoilDataset.setBorderWidth(1);
        
        // Añadir los datasets al data



        data.addChartDataSet(totalDataset);
        data.addChartDataSet(gasolinaDataset);
        data.addChartDataSet(gasoilDataset);

        List<String> labels = getMonths();
        data.setLabels(labels);
        barModelImporte.setData(data);

        BarChartOptions options = getBarChartOptions("Importe");

        barModelImporte.setOptions(options);
    }

    public void createBarModelLitros(){

        barModelLitros = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet totalDataset = new BarChartDataSet();
        BarChartDataSet gasolinaDataset = new BarChartDataSet();
        BarChartDataSet gasoilDataset = new BarChartDataSet();
        totalDataset.setLabel("Total");
        gasolinaDataset.setLabel("Gasolina");
        gasoilDataset.setLabel("Gasoil");
        List<Number> valuesGasolina = new ArrayList<>();
        List<Number> valuesGasoil = new ArrayList<>();
        List<Repostajes> repostajesGasolina = getRepostajesGasolina();
        List<Repostajes> repostajesGasoil = getRepostajesGasoil();

        //Litros tipo gasolina
        for (int i = 0; i < 12; i++) {
            valuesGasolina.add(getRepostajesByMesLitros(i, repostajesGasolina));
        }
        gasolinaDataset.setData(valuesGasolina);

        //Litros tipo gasoil
        for (int i = 0; i < 12; i++) {
            valuesGasoil.add(getRepostajesByMesLitros(i,repostajesGasoil));
        }
        gasoilDataset.setData(valuesGasoil);

        List<String> bgColorLitros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bgColorLitros.add("rgba(0,0,255,0.2)");
        }
        gasolinaDataset.setBackgroundColor(bgColorLitros);
        gasoilDataset.setBackgroundColor("rgba(128, 0, 128, 0.2 )");

        //Sumar lista valuesGasolina y valuesGasoil
        List<Number> valuesTotal = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            valuesTotal.add(valuesGasolina.get(i).floatValue() + valuesGasoil.get(i).floatValue());
        }
        totalDataset.setData(valuesTotal);


        // Colores border

        List<String> borderColorLitros = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            borderColorLitros.add("rgba(0,0,255,0.6)");
        }
        gasolinaDataset.setBorderColor(borderColorLitros);
        gasolinaDataset.setBorderWidth(1);

        gasoilDataset.setBorderColor("rgba(128, 0, 128, 1 )");
        gasoilDataset.setBorderWidth(1);

        // Añadir los datasets al data

        data.addChartDataSet(totalDataset);
        data.addChartDataSet(gasolinaDataset);
        data.addChartDataSet(gasoilDataset);

        List<String> labels = getMonths();
        data.setLabels(labels);
        barModelLitros.setData(data);

        BarChartOptions options = getBarChartOptions("Litros");

        barModelLitros.setOptions(options);

    }

    private BarChartOptions getBarChartOptions(String tipo) {
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
        title.setText(tipo);
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
        String mes = getMonths().get(event.getItemIndex());
        int numeroRepostajesGasolina = getNumeroDeRepostajesCombustible(getRepostajesGasolina(), event.getItemIndex());
        int numeroRepostajesGasoil = getNumeroDeRepostajesCombustible(getRepostajesGasoil(),event.getItemIndex());
        int totalRepostajes = numeroRepostajesGasolina + numeroRepostajesGasoil;

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Numero de repostajes: ",
                "En el mes de " + mes + ", se repostó un número de " +totalRepostajes+ " repostajes." +
                        " De los cuales "+numeroRepostajesGasolina+" fueron de gasolina y "+numeroRepostajesGasoil+" fueron de gasoil.");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    private float getRepostajesByMesPrecio(int mes, List<Repostajes> repostajesCombustible) {
        float total = 0;
        for (Repostajes r : repostajesCombustible) {
            if((r.getFecha().getYear() + 1900) == Calendar.getInstance().get(Calendar.YEAR) && r.getFecha().getMonth() == mes){
                total += Float.parseFloat(r.getGastoId().getPrecio().toString());
            }
        }
        return total;
    }


    private float getRepostajesByMesLitros(int mes, List<Repostajes> repostajesCombustible) {
        float total = 0;
        for (Repostajes r : repostajesCombustible) {
            if((r.getFecha().getYear() + 1900) == Calendar.getInstance().get(Calendar.YEAR) && r.getFecha().getMonth() == mes){
                total += Float.parseFloat(r.getLitros().toString());
            }
        }
        return total;
    }

    private int getNumeroDeRepostajesCombustible(List<Repostajes> repostajesCombustible, int mes) {
        int total = 0;

        for (Repostajes r : repostajesCombustible) {
            if((r.getFecha().getYear() + 1900) == Calendar.getInstance().get(Calendar.YEAR) && r.getFecha().getMonth() == mes){
                total ++;
            }
        }
        return total;
    }

    public void cambiarPrecioGasolina(){
        this.gasolinera.setPrecioGasolina(this.precioGasolina);
        this.ejbGasolineras.edit(this.gasolinera);

    }

    public void cambiarPrecioGasoil(){
        this.gasolinera.setPrecioGasoil(this.precioGasoil);
        this.ejbGasolineras.edit(this.gasolinera);
    }

    public List<Repostajes> getRepostajesGasolina() {
        List<Repostajes> importesGasolina = new ArrayList<>();
        for(Repostajes r : repostajes){
            if(r.getGastoId().getMatricula().getTipoCombustible().equals("GASOLINA")){
                importesGasolina.add(r);
            }
        }

        return importesGasolina;
    }

    public List<Repostajes> getRepostajesGasoil() {
        List<Repostajes> importesGasoil = new ArrayList<>();
        for(Repostajes r : repostajes){
            if(r.getGastoId().getMatricula().getTipoCombustible().equals("GASOIL")){
                importesGasoil.add(r);
            }
        }

        return importesGasoil;

    }


    public BarChartModel getBarModelImporte() { return barModelImporte; }

    public void setBarModelImporte(BarChartModel barModelImporte) { this.barModelImporte = barModelImporte;}

    public BarChartModel getBarModelLitros() { return barModelLitros;}

    public void setBarModelLitros(BarChartModel barModelLitros) { this.barModelLitros = barModelLitros;}

    public float getPrecioGasolina() { return precioGasolina; }

    public void setPrecioGasolina(float precioGasolina) { this.precioGasolina = precioGasolina;}

    public float getPrecioGasoil() { return precioGasoil;}

    public void setPrecioGasoil(float precioGasoil) { this.precioGasoil = precioGasoil; }


}
