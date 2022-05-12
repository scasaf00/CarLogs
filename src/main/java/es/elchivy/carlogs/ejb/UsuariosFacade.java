/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sergio
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public boolean validarUsuario(String username , String password) {
        String consulta = "FROM Usuarios u WHERE u.username = :param1 AND u.password = :param2";
        Query q = em.createQuery(consulta);
        q.setParameter("param1", username);
        q.setParameter("param2", password);
        return q.getResultList().size() > 0;
    }

    @Override
    public void insertarUsuario(Usuarios usuario) {
        em.persist(usuario);
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
}
