package es.elchivy.carlogs.controller;

import com.google.common.hash.Hashing;
import es.elchivy.carlogs.ejb.GasolinerasFacadeLocal;
import es.elchivy.carlogs.ejb.GasolinerosFacadeLocal;
import es.elchivy.carlogs.ejb.UsuariosFacadeLocal;
import es.elchivy.carlogs.modelo.Gasolineras;
import es.elchivy.carlogs.modelo.Gasolineros;
import es.elchivy.carlogs.modelo.Usuarios;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;


@Named
@ViewScoped
public class RegisterController implements Serializable {

    @EJB
    private UsuariosFacadeLocal ejbUser;

    @EJB
    private GasolinerosFacadeLocal ejbGasolinero;

    @EJB
    private GasolinerasFacadeLocal ejbGasolinera;

    private Usuarios user;
    private Gasolineras gasolinera;

    private String password;

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public Gasolineras getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(Gasolineras gasolinera) {
        this.gasolinera = gasolinera;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PostConstruct
    public void init(){
        user = new Usuarios();
        gasolinera = new Gasolineras();
    }

    public void insertUser() {
        if (user.getPassword().equals(password)) {
            if (user.getTipo().equals("GASOLINERO")) {
                if (ejbGasolinera.exist(gasolinera)){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La gasolinera ya existe"));
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
                    }catch (Exception e){
                        System.out.println("Error");
                    }
                }else {
                    ejbGasolinera.create(gasolinera);
                    Gasolineros gasolinero = new Gasolineros();
                    gasolinero.setGasolinera(ejbGasolinera.findGasolinera(gasolinera));
                    gasolinero.setUsuario(user.getUsername());
                    gasolinero.setUsuarios(user);
                    user.setGasolineros(gasolinero);
                }
            }

            boolean dup = false;
            try {
                user.setPassword(Hashing.crc32().hashString(user.getPassword(), StandardCharsets.UTF_8).toString());
                ejbUser.create(user);
            } catch (Exception e) {
                dup = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario ya existe"));
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                }catch(Exception ex){
                    System.out.println("Error");
                }
            }

            if (!dup) {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                if(user.getTipo().equals("GASOLINERO")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario gasolinero registrado correctamente, espere a que el administrador lo apruebe"));
                }else {
                    //Return to login page and show message
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario registrado correctamente"));
                }
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                }
                catch (Exception e) {
                    System.out.println("Error");
                }
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden"));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
            }catch (Exception ex){
                System.out.println("Error");
            }
        }

    }
}