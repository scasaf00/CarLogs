/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Usuarios;

import javax.ejb.EJB;
import javax.ejb.Stateful;
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
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "CarLogsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Usuarios> validarUsuario(Usuarios usuario) {
        String consulta = "SELECT u FROM Usuarios u WHERE u.username = :usuario and u.password = :password";
        Query query = em.createQuery(consulta);
        query.setParameter("usuario", usuario.getUsername());
        query.setParameter("password", usuario.getPassword());
        List<Usuarios> lista = query.getResultList();
        return lista;

    }

    @Override
    public void insertarUsuario(Usuarios usuario) {
        em.persist(usuario);
    }


    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
}
