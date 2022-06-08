package es.elchivy.carlogs.controller;

import es.elchivy.carlogs.ejb.*;
import es.elchivy.carlogs.modelo.*;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

@Named
@ViewScoped
public class GastosController implements Serializable {

    private List<Gastos> gastos;

    private Gastos gasto;

    private Usuarios user;

    private List<String> matriculas;

    private String matricula;

    private List<String> gasolineras;

    private String gasolinera;

    private List<Viajes> viajes;

    private String viaje;

    private String descripcion;

    private Gastos gastoSeleccionado;

    @EJB
    private GastosFacadeLocal ejbGastos;

    @EJB
    private VehiculosFacadeLocal ejbVehiculos;

    @EJB
    private ViajesFacadeLocal ejbViajes;

    @EJB
    private RepostajesFacadeLocal ejbRepostajes;

    @EJB
    private GasolinerasFacadeLocal ejbGasolineras;

    @EJB
    private MantenimientosFacadeLocal ejbMantenimientos;

    @EJB
    private UsuariosFacadeLocal ejbUsuarios;

    @PostConstruct
    public void init(){
        Usuarios usuario = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(!usuario.getTipo().equals("USER")){
            //Redirigir a la página de inicio y mostrar mensaje de error
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tienes permisos", "No tienes permisos para acceder a esta página"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        user = ejbUsuarios.find(user.getUsername());

        viajes = new ArrayList<>( user.getViajesCollection());
        List<Vehiculos> vehiculos = new ArrayList<>();
        vehiculos = ejbUsuarios.getAlLVehiculos(user);
        gastos = new ArrayList<>();
        gastos = ejbGastos.getAllByUser(user);
        matriculas = new ArrayList<>();
        for (Vehiculos v : vehiculos) {
            matriculas.add(v.getMatricula());
        }

        gasolineras = new ArrayList<>();
        List<Gasolineras> gas = ejbGasolineras.findAll();
        for (Gasolineras g : gas) {
            gasolineras.add(g.toString());
        }
        gasto = new Gastos();

        // Objetos Mock para evitar NullPointerException

        Gastos gastoMock = new Gastos();
        Vehiculos vehiculoMock = new Vehiculos();
        Usuarios usuarioMock = new Usuarios();

        usuarioMock.setUsername("mock");
        usuarioMock.setPassword("mock");
        usuarioMock.setTipo("mock");

        vehiculoMock.setMatricula("MOCK");
        vehiculoMock.setUsuario(usuarioMock);
        vehiculoMock.setGastoKm(new BigDecimal(0));
        vehiculoMock.setColor("MOCK");
        vehiculoMock.setModelo("MOCK");
        vehiculoMock.setMarca("MOCK");
        vehiculoMock.setTipoCombustible("MOCK");
        vehiculoMock.setKmActual(new BigDecimal(0));

        gastoMock.setId(0);
        gastoMock.setMatricula(vehiculoMock);
        gastoMock.setFecha(new java.util.Date());
        gastoMock.setTipo("MOCK");
        gastoMock.setPrecio(new BigDecimal(0));
        gastoMock.setKm(new BigDecimal(0));

        this.gastoSeleccionado = gastoMock;
    }

    public List<Gastos> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gastos> gastos) {
        this.gastos = gastos;
    }

    public void setGasto(Gastos gasto) {
        this.gasto = gasto;
    }

    public Gastos getGasto() {
        return gasto;
    }

    public void delete(Gastos gasto) {
        ejbGastos.remove(gasto);
        gastos.remove(gasto);
    }

    public void insertGasto() throws ParseException {
        Vehiculos v = ejbVehiculos.find(this.matricula);
        gasto.setMatricula(v);
        if(this.viaje != null) {
            gasto.setViaje(ejbViajes.findByOrDesDate(this.viaje));
        }
        ejbGastos.create(gasto);
        if(gasto.getTipo().equals("REPOSTAJE")) {
            Gasolineras gasolinera = ejbGasolineras.findByNombreDireccion(this.gasolinera);
            Repostajes repostaje = new Repostajes();
            BigDecimal litros = gasto.getPrecio().divide(BigDecimal.valueOf(getPrecioComb(v.getTipoCombustible(), gasolinera)), 2, RoundingMode.HALF_UP);
            repostaje.setGasolinera(gasolinera);
            repostaje.setGastoId(gasto);
            repostaje.setFecha(gasto.getFecha());
            repostaje.setLitros(litros);
            ejbRepostajes.create(repostaje);
        } else if(gasto.getTipo().equals("MANTENIMIENTO")){
            Mantenimientos mantenimiento = new Mantenimientos();
            mantenimiento.setGastoId(gasto);
            mantenimiento.setFecha(gasto.getFecha());
            mantenimiento.setDescripcion(this.descripcion);
            ejbMantenimientos.create(mantenimiento);
        }
        gastos.add(gasto);
    }

    private float getPrecioComb(String tipoCombustible, Gasolineras gasolinera) {
        if (tipoCombustible.equals("GASOLINA"))
            return gasolinera.getPrecioGasolina();
        else
            return gasolinera.getPrecioGasoil();
    }

    public Usuarios getUser() {
        return user;
    }

    public List<String> getMatriculas() {
        return matriculas;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<Viajes> getViajes() {
        return viajes;
    }

    public void setViaje(String viaje) {
        this.viaje = viaje;
    }

    public String getViaje() {
        return viaje;
    }

    public List<String> getGasolineras() {
        return gasolineras;
    }

    public void setGasolinera(String gasolinera) {
        this.gasolinera = gasolinera;
    }

    public String getGasolinera() {
        return gasolinera;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String masInfo() {
        if(gastoSeleccionado.getTipo().equals("REPOSTAJE")) {
            Repostajes repostajes = (Repostajes) gastoSeleccionado.getRepostajesCollection().toArray()[0];
            return "Litros: " + repostajes.getLitros() + " - Gasolinera: " + repostajes.getGasolinera().toString();
        } else if(gastoSeleccionado.getTipo().equals("MANTENIMIENTO")) {
            Mantenimientos mantenimientos = (Mantenimientos) gastoSeleccionado.getMantenimientosCollection().toArray()[0];
            return "Descripcion: " + mantenimientos.getDescripcion();
        }
        return "";
    }

    public Gastos getGastoSeleccionado() {
        return gastoSeleccionado;
    }

    public void setGastoSeleccionado(Gastos gastoSeleccionado) {
        System.out.println("Seleccionado: " + gastoSeleccionado);
        this.gastoSeleccionado = gastoSeleccionado;
        PrimeFaces.current().executeScript("PF('dlg2').show()");
    }

    public List<Gastos> getGastosReverse(){
        List<Gastos> reverseGastos = this.gastos;
        reverseGastos.sort((o1, o2) -> {
            if (o1.getFecha() == null || o2.getFecha() == null) {
                return 0;
            }
            return o1.getFecha().compareTo(o2.getFecha());
        });
        Collections.reverse(reverseGastos);
        return reverseGastos;
    }
}
