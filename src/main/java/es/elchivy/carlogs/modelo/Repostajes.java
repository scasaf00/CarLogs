/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author scfmm
 */
@Entity
@Table(name = "repostajes")
@NamedQueries({
        @NamedQuery(name = "Repostajes.findAll", query = "SELECT r FROM Repostajes r"),
        @NamedQuery(name = "Repostajes.findById", query = "SELECT r FROM Repostajes r WHERE r.id = :id"),
        @NamedQuery(name = "Repostajes.findByFecha", query = "SELECT r FROM Repostajes r WHERE r.fecha = :fecha"),
        @NamedQuery(name = "Repostajes.findByLitros", query = "SELECT r FROM Repostajes r WHERE r.litros = :litros")})
public class Repostajes implements Serializable {

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
    @Column(name = "litros")
    private BigDecimal litros;
    @JoinColumn(name = "gasolinera", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Gasolineras gasolinera;
    @JoinColumn(name = "gasto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Gastos gastoId;

    public Repostajes() {
    }

    public Repostajes(Integer id) {
        this.id = id;
    }

    public Repostajes(Integer id, Date fecha, BigDecimal litros) {
        this.id = id;
        this.fecha = fecha;
        this.litros = litros;
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

    public BigDecimal getLitros() {
        return litros;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public Gasolineras getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(Gasolineras gasolinera) {
        this.gasolinera = gasolinera;
    }

    public Gastos getGastoId() {
        return gastoId;
    }

    public void setGastoId(Gastos gastoId) {
        this.gastoId = gastoId;
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
        if (!(object instanceof Repostajes)) {
            return false;
        }
        Repostajes other = (Repostajes) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "es.elchivy.carlogs.modelo.Repostajes[ id=" + id + " ]";
    }

}
