package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.UsuariosFacade;
import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.resources.SessionUtils;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private UsuariosFacadeLocal ejb;

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{loginController.password}")
    private String password;
    @ManagedProperty(value = "#{loginController.uname}")
    private String uname;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public UsuariosFacadeLocal getUsuariosFacade() {
        return ejb;
    }

    //Validate Login
    public String validateLogin() {
        Usuarios user = ejb.find(uname);
        if(ejb.validarUsuario(user) != null){
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", this.getUname());
            return "?"; //TODO: redirecr a algun lao
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
            return "index";
        }
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "index";

    }
}