/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name = "gasolineros")
@NamedQueries({
    @NamedQuery(name = "Gasolineros.findAll", query = "SELECT g FROM Gasolineros g"),
    @NamedQuery(name = "Gasolineros.findByGasolinera", query = "SELECT g FROM Gasolineros g WHERE g.gasolinerosPK.gasolinera = :gasolinera"),
    @NamedQuery(name = "Gasolineros.findByUsuario", query = "SELECT g FROM Gasolineros g WHERE g.gasolinerosPK.usuario = :usuario"),
    @NamedQuery(name = "Gasolineros.findByAceptado", query = "SELECT g FROM Gasolineros g WHERE g.aceptado = :aceptado")})
public class Gasolineros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GasolinerosPK gasolinerosPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aceptado")
    private boolean aceptado;
    @JoinColumn(name = "gasolinera", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Gasolineras gasolineras;
    @JoinColumn(name = "usuario", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Gasolineros() {
    }

    public Gasolineros(GasolinerosPK gasolinerosPK) {
        this.gasolinerosPK = gasolinerosPK;
    }

    public Gasolineros(GasolinerosPK gasolinerosPK, boolean aceptado) {
        this.gasolinerosPK = gasolinerosPK;
        this.aceptado = aceptado;
    }

    public Gasolineros(int gasolinera, String usuario) {
        this.gasolinerosPK = new GasolinerosPK(gasolinera, usuario);
    }

    public GasolinerosPK getGasolinerosPK() {
        return gasolinerosPK;
    }

    public void setGasolinerosPK(GasolinerosPK gasolinerosPK) {
        this.gasolinerosPK = gasolinerosPK;
    }

    public boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public Gasolineras getGasolineras() {
        return gasolineras;
    }

    public void setGasolineras(Gasolineras gasolineras) {
        this.gasolineras = gasolineras;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gasolinerosPK != null ? gasolinerosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gasolineros)) {
            return false;
        }
        Gasolineros other = (Gasolineros) object;
        return (this.gasolinerosPK != null || other.gasolinerosPK == null) && (this.gasolinerosPK == null || this.gasolinerosPK.equals(other.gasolinerosPK));
    }

    @Override
    public String toString() {
        return "es.elchivy.mavenproject1.Gasolineros[ gasolinerosPK=" + gasolinerosPK + " ]";
    }
    
}
