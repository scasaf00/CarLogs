package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.VehiculosFacadeLocal;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.modelo.Vehiculos;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class VehiculosController implements Serializable {

    private List<Vehiculos> vehiculos;

    private Vehiculos vehiculo;

    private Usuarios user;

    @EJB
    private VehiculosFacadeLocal ejbVehiculos;

    @PostConstruct
    public void init() {
        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        vehiculos = (List<Vehiculos>) user.getVehiculosCollection();
        vehiculo = new Vehiculos();
    }

    public List<Vehiculos> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculos> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public void insertVehiculo(){
        vehiculo.setUsuario(user);
        ejbVehiculos.create(vehiculo);
        init();
    }

    public void deleteVehiculo(Vehiculos v){
        ejbVehiculos.remove(v);
        vehiculos.remove(v);
        init();
    }
}