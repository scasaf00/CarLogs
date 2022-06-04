package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.ViajesFacadeLocal;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.modelo.Viajes;
import org.primefaces.model.map.MapModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ViajesController implements Serializable {

    private MapModel simpleModel;

    private List<Viajes> viajes;

    private Viajes viaje;

    private Usuarios user;

    @EJB
    private ViajesFacadeLocal ejbViajes;

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
        viaje = new Viajes();
        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        viajes = (List<Viajes>) user.getViajesCollection();
    }

    public List<Viajes> getViajes() {
        return viajes;
    }

    public void setViaje(Viajes viaje) {
        this.viaje = viaje;
    }

    public Viajes getViaje() {
        return viaje;
    }

    public void insertViaje() {
        viaje.setUsuario(user);
        ejbViajes.create(viaje);
        viajes.add(viaje);
    }

    public void deleteViaje(Viajes viaje) {
        ejbViajes.remove(viaje);
        viajes.remove(viaje);
    }
}
