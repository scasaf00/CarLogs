package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.GasolinerosFacadeLocal;
import es.elchivy.carlogs.modelo.Gasolineros;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PeticionesAdminController implements Serializable {

    private List<Gasolineros> gasolineros;

    @EJB
    private GasolinerosFacadeLocal ejbGasolineros;

    @PostConstruct
    public void init(){
        gasolineros = ejbGasolineros.findAllNoAceptados();
    }

    public List<Gasolineros> getGasolineros() {
        this.gasolineros = ejbGasolineros.findAllNoAceptados();
        return gasolineros;
    }

    public void setGasolineros(List<Gasolineros> gasolineros) {
        this.gasolineros = gasolineros;
    }


    public void enviar(Gasolineros g){
        try {
            g.setAceptado(true);
            ejbGasolineros.edit(g);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", g.getAceptado() ? "Aceptado" : "No aceptado"));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido enviar el formulario"));
        }
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO","Cambios enviados"));
    }
}
