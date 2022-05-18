/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sergio
 */
@Embeddable
public class GasolinerosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "gasolinera")
    private int gasolinera;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usuario")
    private String usuario;

    public GasolinerosPK() {
    }

    public GasolinerosPK(int gasolinera, String usuario) {
        this.gasolinera = gasolinera;
        this.usuario = usuario;
    }

    public int getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(int gasolinera) {
        this.gasolinera = gasolinera;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gasolinera;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GasolinerosPK)) {
            return false;
        }
        GasolinerosPK other = (GasolinerosPK) object;
        if (this.gasolinera != other.gasolinera) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.elchivy.carlogs.modelo.GasolinerosPK[ gasolinera=" + gasolinera + ", usuario=" + usuario + " ]";
    }
    
}
