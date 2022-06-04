/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author scfmm
 */
@Entity
@Table(name = "vehiculos")
@NamedQueries({
        @NamedQuery(name = "Vehiculos.findAll", query = "SELECT v FROM Vehiculos v"),
        @NamedQuery(name = "Vehiculos.findByMatricula", query = "SELECT v FROM Vehiculos v WHERE v.matricula = :matricula"),
        @NamedQuery(name = "Vehiculos.findByMarca", query = "SELECT v FROM Vehiculos v WHERE v.marca = :marca"),
        @NamedQuery(name = "Vehiculos.findByModelo", query = "SELECT v FROM Vehiculos v WHERE v.modelo = :modelo"),
        @NamedQuery(name = "Vehiculos.findByColor", query = "SELECT v FROM Vehiculos v WHERE v.color = :color"),
        @NamedQuery(name = "Vehiculos.findByGastoKm", query = "SELECT v FROM Vehiculos v WHERE v.gastoKm = :gastoKm"),
        @NamedQuery(name = "Vehiculos.findByKmActual", query = "SELECT v FROM Vehiculos v WHERE v.kmActual = :kmActual"),
        @NamedQuery(name = "Vehiculos.findByTipoCombustible", query = "SELECT v FROM Vehiculos v WHERE v.tipoCombustible = :tipoCombustible")})
public class Vehiculos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "color")
    private String color;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "gasto_km")
    private BigDecimal gastoKm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_actual")
    private BigDecimal kmActual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
    @JoinColumn(name = "usuario", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matricula")
    private Collection<Gastos> gastosCollection;

    public Vehiculos() {
    }

    public Vehiculos(String matricula) {
        this.matricula = matricula;
    }

    public Vehiculos(String matricula, String marca, String modelo, String color, BigDecimal gastoKm, BigDecimal kmActual, String tipoCombustible) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.gastoKm = gastoKm;
        this.kmActual = kmActual;
        this.tipoCombustible = tipoCombustible;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getGastoKm() {
        return gastoKm;
    }

    public void setGastoKm(BigDecimal gastoKm) {
        this.gastoKm = gastoKm;
    }

    public BigDecimal getKmActual() {
        return kmActual;
    }

    public void setKmActual(BigDecimal kmActual) {
        this.kmActual = kmActual;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
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
        hash += (matricula != null ? matricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculos)) {
            return false;
        }
        Vehiculos other = (Vehiculos) object;
        return (this.matricula != null || other.matricula == null) && (this.matricula == null || this.matricula.equals(other.matricula));
    }

    @Override
    public String toString() {
        return matricula;
    }

}
