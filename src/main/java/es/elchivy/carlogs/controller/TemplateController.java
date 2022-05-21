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
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //redireccionar a login
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
        }
        catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verificarYMostrar() {
        this.usuario = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        if (this.usuario == null) {
            // TODO aqui que seria lo mejor que se podria hacer??
            try {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe estar logueado para acceder a esta pagina"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
