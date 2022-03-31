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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Sergio
 */
@Entity
@Table(name = "gastos")
@NamedQueries({
    @NamedQuery(name = "Gastos.findAll", query = "SELECT g FROM Gastos g"),
    @NamedQuery(name = "Gastos.findById", query = "SELECT g FROM Gastos g WHERE g.id = :id"),
    @NamedQuery(name = "Gastos.findByFecha", query = "SELECT g FROM Gastos g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "Gastos.findByPrecio", query = "SELECT g FROM Gastos g WHERE g.precio = :precio"),
    @NamedQuery(name = "Gastos.findByKm", query = "SELECT g FROM Gastos g WHERE g.km = :km"),
    @NamedQuery(name = "Gastos.findByTipo", query = "SELECT g FROM Gastos g WHERE g.tipo = :tipo")})
public class Gastos implements Serializable {

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
    @Column(name = "precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km")
    private BigDecimal km;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoId")
    private Collection<Mantenimientos> mantenimientosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoId")
    private Collection<Repostajes> repostajesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoId")
    private Collection<Viajes> viajesCollection;
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    @ManyToOne(optional = false)
    private Vehiculos matricula;

    public Gastos() {
    }

    public Gastos(Integer id) {
        this.id = id;
    }

    public Gastos(Integer id, Date fecha, BigDecimal precio, BigDecimal km, String tipo) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.km = km;
        this.tipo = tipo;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getKm() {
        return km;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Collection<Mantenimientos> getMantenimientosCollection() {
        return mantenimientosCollection;
    }

    public void setMantenimientosCollection(Collection<Mantenimientos> mantenimientosCollection) {
        this.mantenimientosCollection = mantenimientosCollection;
    }

    public Collection<Repostajes> getRepostajesCollection() {
        return repostajesCollection;
    }

    public void setRepostajesCollection(Collection<Repostajes> repostajesCollection) {
        this.repostajesCollection = repostajesCollection;
    }

    public Collection<Viajes> getViajesCollection() {
        return viajesCollection;
    }

    public void setViajesCollection(Collection<Viajes> viajesCollection) {
        this.viajesCollection = viajesCollection;
    }

    public Vehiculos getMatricula() {
        return matricula;
    }

    public void setMatricula(Vehiculos matricula) {
        this.matricula = matricula;
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
        if (!(object instanceof Gastos)) {
            return false;
        }
        Gastos other = (Gastos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.elchivy.carlogs.modelo.Gastos[ id=" + id + " ]";
    }
    
}
