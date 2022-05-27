/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gasolineras;
import es.elchivy.carlogs.modelo.Gastos;
import es.elchivy.carlogs.modelo.Repostajes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Sergio
 */
@Stateless
public class RepostajesFacade extends AbstractFacade<Repostajes> implements RepostajesFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepostajesFacade() {
        super(Repostajes.class);
    }
    
}
