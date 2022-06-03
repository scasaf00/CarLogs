package es.elchivy.carlogs.controller;

import com.google.common.hash.Hashing;
import es.elchivy.carlogs.ejb.UsuariosFacade;
import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.resources.SessionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@ManagedBean(name = "loginController")
@Named
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private UsuariosFacadeLocal ejb;

    private Usuarios user;

    @PostConstruct
    public void init(){
        user = new Usuarios();
    }

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios usuario) {
        this.user = usuario;
    }

    //Validate Login
    public String validateLogin() {
        user.setPassword(Hashing.crc32().hashString(user.getPassword(), StandardCharsets.UTF_8).toString());
        user = ejb.validarUsuario(user);
        System.out.println(user);

        if(user != null){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",user);

            String rutaIndex = "";

            switch(user.getTipo()){
                case "ADMIN":
                    rutaIndex = "/private/admin/indexAdmin.xhtml?faces-redirect=true";
                    break;
                case "USER":
                    rutaIndex = "/private/user/indexUser.xhtml?faces-redirect=true";
                    break;
                case "GASOLINERO":
                    if(user.getGasolineros().getAceptado()){
                        rutaIndex = "/private/gasolinero/indexGasolinero.xhtml";
                    }else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El gasolinero no ha sido aceptado"));
                    }
                    break;

                default:
                    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Usuario no es correcto"));
            }

            return rutaIndex;
        }

        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Usuario incorrecto"));

        return "";
    }
}