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
    public Usuarios validarUsuario(Usuarios usuario) {
        String consulta = "FROM Usuarios u WHERE u.username=:param1 and u.password=:param2";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", usuario.getUsername());
        query.setParameter("param2", usuario.getPassword());
        List<Usuarios> resultado = query.getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Override
    public void insertarUsuario(Usuarios usuario) {
        em.persist(usuario);
    }


    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
}
