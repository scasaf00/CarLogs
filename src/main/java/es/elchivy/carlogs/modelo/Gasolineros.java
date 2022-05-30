/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author scfmm
 */
@Entity
@Table(name = "gasolineros")
@NamedQueries({
        @NamedQuery(name = "Gasolineros.findAll", query = "SELECT g FROM Gasolineros g"),
        @NamedQuery(name = "Gasolineros.findByUsuario", query = "SELECT g FROM Gasolineros g WHERE g.usuario = :usuario"),
        @NamedQuery(name = "Gasolineros.findByAceptado", query = "SELECT g FROM Gasolineros g WHERE g.aceptado = :aceptado")})
public class Gasolineros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aceptado")
    private boolean aceptado;
    @JoinColumn(name = "gasolinera", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Gasolineras gasolinera;
    @JoinColumn(name = "usuario", referencedColumnName = "username", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuarios usuarios;

    public Gasolineros() {
    }

    public Gasolineros(String usuario) {
        this.usuario = usuario;
    }

    public Gasolineros(String usuario, boolean aceptado) {
        this.usuario = usuario;
        this.aceptado = aceptado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public Gasolineras getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(Gasolineras gasolinera) {
        this.gasolinera = gasolinera;
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
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gasolineros)) {
            return false;
        }
        Gasolineros other = (Gasolineros) object;
        return (this.usuario != null || other.usuario == null) && (this.usuario == null || this.usuario.equals(other.usuario));
    }

    @Override
    public String toString() {
        return "Gasolineros{ " + "usuario=" + usuario + ", aceptado=" + aceptado + ", gasolinera=" + gasolinera + ", usuarios=" + usuarios + '}';
    }

}
