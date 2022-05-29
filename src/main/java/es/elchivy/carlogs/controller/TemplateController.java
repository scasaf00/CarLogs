package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.modelo.Usuarios;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class TemplateController implements Serializable {

    private Usuarios usuario;

    public void logOut() {
        System.out.println("Logout");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", null);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
        }
        catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ha ocurrido un error insperado"));
        }
    }


    public void verificarYMostrar() {
        this.usuario = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

        if (this.usuario == null) {
            try {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe estar logueado para acceder a esta pagina"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml?faces-redirect=true ");

            } catch (IOException ex) {
                Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
