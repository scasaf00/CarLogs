/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Viajes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sfcmm
 */
@Stateless
public class ViajesFacade extends AbstractFacade<Viajes> implements ViajesFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ViajesFacade() {
        super(Viajes.class);
    }

    @Override
    public Viajes findByOrDesDate(String viaje){
        String[] viajeArray = viaje.split(" - ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = sdf.parse(viajeArray[2]);
            String consulta = "SELECT v FROM Viajes v WHERE v.origen = :origen AND v.destino = :destino AND v.fecha = :fecha";
            return (Viajes) em.createQuery(consulta)
                    .setParameter("origen", viajeArray[0])
                    .setParameter("destino", viajeArray[1])
                    .setParameter("fecha", date)
                    .getSingleResult();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
