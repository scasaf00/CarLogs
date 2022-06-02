/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author scfmm
 */
@Entity
@Table(name = "viajes")
@NamedQueries({
        @NamedQuery(name = "Viajes.findAll", query = "SELECT v FROM Viajes v"),
        @NamedQuery(name = "Viajes.findById", query = "SELECT v FROM Viajes v WHERE v.id = :id"),
        @NamedQuery(name = "Viajes.findByFecha", query = "SELECT v FROM Viajes v WHERE v.fecha = :fecha"),
        @NamedQuery(name = "Viajes.findByDistancia", query = "SELECT v FROM Viajes v WHERE v.distancia = :distancia"),
        @NamedQuery(name = "Viajes.findByOrigen", query = "SELECT v FROM Viajes v WHERE v.origen = :origen"),
        @NamedQuery(name = "Viajes.findByDestino", query = "SELECT v FROM Viajes v WHERE v.destino = :destino")})
public class Viajes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "distancia")
    private BigDecimal distancia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "origen")
    private String origen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "destino")
    private String destino;
    @OneToMany(mappedBy = "viaje")
    private Collection<Gastos> gastosCollection;

    public Viajes() {
    }

    public Viajes(Integer id) {
        this.id = id;
    }

    public Viajes(Integer id, Date fecha, BigDecimal distancia, String origen, String destino) {
        this.id = id;
        this.fecha = fecha;
        this.distancia = distancia;
        this.origen = origen;
        this.destino = destino;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Collection<Gastos> getGastosCollection() {
        return gastosCollection;
    }

    public void setGastosCollection(Collection<Gastos> gastosCollection) {
        this.gastosCollection = gastosCollection;
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
        if (!(object instanceof Viajes)) {
            return false;
        }
        Viajes other = (Viajes) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return origen + " - " + destino;
    }

}
