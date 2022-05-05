package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.UsuariosFacade;
import es.elchivy.carlogs.resources.SessionUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    private final UsuariosFacade usuariosFacade = new UsuariosFacade();

    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;

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

    public UsuariosFacade getUsuariosFacade() {
        return usuariosFacade;
    }

    public String loginProject() {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Invalid Login!",
                    "Please Try Again! " + this.getUname()));

            return "login";

    }

    //Validate Login
    public String validateLogin() {
        boolean valid = usuariosFacade.validarUsuario(this.getUname(), this.getPassword());
        if(valid){
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