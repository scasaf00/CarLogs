package es.elchivy.carlogs.controller;


import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
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
    public void init() {
        Usuarios usuario = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(!usuario.getTipo().equals("ADMIN")){
            //Redirigir a la página de inicio y mostrar mensaje de error
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tienes permisos", "No tienes permisos para acceder a esta página"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
