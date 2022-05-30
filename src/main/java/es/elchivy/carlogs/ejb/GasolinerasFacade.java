/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gasolineras;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sfcmm
 */
@Stateless
public class GasolinerasFacade extends AbstractFacade<Gasolineras> implements GasolinerasFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GasolinerasFacade() {
        super(Gasolineras.class);
    }

    @Override
    public Gasolineras findGasolinera(Gasolineras gasolineras) {
        //find gasolinera by nombre and direccion
        String consulta = "SELECT g FROM Gasolineras g WHERE g.nombre = :nombre AND g.direccion = :direccion";
        Query q = em.createQuery(consulta);
        q.setParameter("nombre", gasolineras.getNombre());
        q.setParameter("direccion", gasolineras.getDireccion());
        System.out.println("Gasolinera: " + q.getSingleResult());
        return (Gasolineras) q.getSingleResult();
    }
    
}
