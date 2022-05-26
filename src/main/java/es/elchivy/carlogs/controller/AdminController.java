package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.GasolinerosFacadeLocal;
import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
import es.elchivy.carlogs.modelo.Gasolineros;
import es.elchivy.carlogs.modelo.Usuarios;

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
public class AdminController implements Serializable {

    private List<Usuarios> usuarios;

    @EJB
    private UsuariosFacadeLocal ejbUsuarios;

    @PostConstruct
    public void init(){
        usuarios = ejbUsuarios.getAllNotAdmin();

    }

    public List<Usuarios> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(List<Usuarios> gasolineros) {
        this.usuarios = gasolineros;
    }

    public void eliminarUsuario(Usuarios usuario){
        ejbUsuarios.remove(usuario);
        usuarios.remove(usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado", "El usuario ha sido eliminado"));
    }
}
