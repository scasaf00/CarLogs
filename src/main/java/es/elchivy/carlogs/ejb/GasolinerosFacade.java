/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gasolineros;
import es.elchivy.carlogs.modelo.Repostajes;
import es.elchivy.carlogs.modelo.Usuarios;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author sfcmm
 */
@Stateless
public class GasolinerosFacade extends AbstractFacade<Gasolineros> implements GasolinerosFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GasolinerosFacade() {
        super(Gasolineros.class);
    }

    @Override
    public List<Gasolineros> findAllNoAceptados() {
        //get all the gasolineros that are not accepged
        String consulta = "FROM Gasolineros g WHERE g.aceptado = false";
        Query query = em.createQuery(consulta);
        List<Gasolineros> resultado = query.getResultList();

        return resultado;
    }

    @Override
    public List<Repostajes> getRepostajes(Usuarios user){
        String consulta = "FROM Repostajes r WHERE r.gasolinera = :param1";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", user.getGasolineros().getGasolinera());
        return (List<Repostajes>) query.getResultList();
    }

}
