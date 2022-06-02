package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.GastosFacadeLocal;
import es.elchivy.carlogs.ejb.VehiculosFacadeLocal;
import es.elchivy.carlogs.modelo.Gastos;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.modelo.Vehiculos;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class GastosController implements Serializable {

    private List<Gastos> gastos;

    private Gastos gasto;

    private Usuarios user;

    private List<String> matriculas;

    private String matricula;

    private List<String> viajes;

    private String viaje;

    @EJB
    private GastosFacadeLocal ejbGastos;

    @EJB
    private VehiculosFacadeLocal ejbVehiculos;

    @PostConstruct
    public void init() {
        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        List<Vehiculos> vehiculos = (List<Vehiculos>) user.getVehiculosCollection();
        gastos = new ArrayList<>();
        matriculas = new ArrayList<>();
        for (Vehiculos v : vehiculos) {
            gastos.addAll(v.getGastosCollection());
            matriculas.add(v.getMatricula());
        }
        gasto = new Gastos();
    }

    public List<Gastos> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gastos> gastos) {
        this.gastos = gastos;
    }

    public void setGasto(Gastos gasto) {
        this.gasto = gasto;
    }

    public Gastos getGasto() {
        return gasto;
    }

    public void delete(Gastos gasto) {
        ejbGastos.remove(gasto);
        init();
    }

    public void insertGasto() {
        gasto.setMatricula(ejbVehiculos.find(this.matricula));
        ejbGastos.create(gasto);
        init();
    }

    public Usuarios getUser() {
        return user;
    }

    public List<String> getMatriculas() {
        return matriculas;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<String> getViajes() {
        return viajes;
    }

    public void setViaje(String viaje) {
        this.viaje = viaje;
    }

    public String getViaje() {
        return viaje;
    }

}
