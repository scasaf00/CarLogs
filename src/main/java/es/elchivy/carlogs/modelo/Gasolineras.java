/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name = "gasolineras")
@NamedQueries({
    @NamedQuery(name = "Gasolineras.findAll", query = "SELECT g FROM Gasolineras g"),
    @NamedQuery(name = "Gasolineras.findById", query = "SELECT g FROM Gasolineras g WHERE g.id = :id"),
    @NamedQuery(name = "Gasolineras.findByNombre", query = "SELECT g FROM Gasolineras g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Gasolineras.findByDireccion", query = "SELECT g FROM Gasolineras g WHERE g.direccion = :direccion")})
public class Gasolineras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gasolinera")
    private Collection<Repostajes> repostajesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gasolineras")
    private Collection<Gasolineros> gasolinerosCollection;

    public Gasolineras() {
    }

    public Gasolineras(Integer id) {
        this.id = id;
    }

    public Gasolineras(Integer id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Collection<Repostajes> getRepostajesCollection() {
        return repostajesCollection;
    }

    public void setRepostajesCollection(Collection<Repostajes> repostajesCollection) {
        this.repostajesCollection = repostajesCollection;
    }

    public Collection<Gasolineros> getGasolinerosCollection() {
        return gasolinerosCollection;
    }

    public void setGasolinerosCollection(Collection<Gasolineros> gasolinerosCollection) {
        this.gasolinerosCollection = gasolinerosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gasolineras)) {
            return false;
        }
        Gasolineras other = (Gasolineras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.elchivy.carlogs.modelo.Gasolineras[ id=" + id + " ]";
    }
    
}
