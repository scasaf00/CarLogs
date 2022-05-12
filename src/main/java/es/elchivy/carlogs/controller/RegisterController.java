package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
import es.elchivy.carlogs.modelo.Usuarios;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@ManagedBean(name = "registerController")
@ViewScoped
public class RegisterController implements Serializable {

    @EJB
    private UsuariosFacadeLocal ejb;

    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{registerController.uname}")
    private String uname;

    @ManagedProperty("#{registerController.password}")
    private String password;

    @ManagedProperty("#{registerController.userType}")
    private String userType;

    @ManagedProperty("#{registerController.gasolinera}")
    private String gasolinera;

    public String getUname() {
        return this.uname;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return this.userType;
    }

    public String getGasolinera() {return this.gasolinera;}

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setGasolinera(String gasolinera) {this.gasolinera = gasolinera;}

    public void insertUser() {

        Usuarios user = new Usuarios();
        user.setUsername(uname);
        user.setPassword(password);
        user.setTipo(userType);
        System.out.println(user);
        ejb.insertarUsuario(user);

    }
}