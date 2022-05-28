/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gastos;
import es.elchivy.carlogs.modelo.Usuarios;
import es.elchivy.carlogs.modelo.Vehiculos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sfcmm
 */
@Stateless
public class GastosFacade extends AbstractFacade<Gastos> implements GastosFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GastosFacade() {
        super(Gastos.class);
    }

    @Override
    public List<Gastos> getAllByUser(Usuarios usuario) {

        Collection<Vehiculos> vehiculos = usuario.getVehiculosCollection();

        String consulta = "FROM Gastos g WHERE g.matricula IN :param1";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", vehiculos);

        return query.getResultList();
    }
    
}
