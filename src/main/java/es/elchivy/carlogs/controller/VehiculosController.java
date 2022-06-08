package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
import es.elchivy.carlogs.ejb.VehiculosFacadeLocal;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.modelo.Vehiculos;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
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

    @EJB
    private UsuariosFacadeLocal ejbUsuarios;

    @PostConstruct
    public void init() {
        Usuarios usuario = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(!usuario.getTipo().equals("USER")){
            //Redirigir a la página de inicio y mostrar mensaje de error
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tienes permisos", "No tienes permisos para acceder a esta página"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        user = ejbUsuarios.find(user.getUsername());
        vehiculos = ejbVehiculos.getVehiculos(user);
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
        vehiculos.add(vehiculo);
    }

    public void deleteVehiculo(Vehiculos v){
        ejbVehiculos.remove(v);
        vehiculos.remove(v);
    }
}
