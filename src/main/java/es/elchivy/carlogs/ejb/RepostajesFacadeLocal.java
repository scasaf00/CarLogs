/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gasolineras;
import es.elchivy.carlogs.modelo.Gastos;
import es.elchivy.carlogs.modelo.Repostajes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface RepostajesFacadeLocal {

    void create(Repostajes repostajes);

    void edit(Repostajes repostajes);

    void remove(Repostajes repostajes);

    Repostajes find(Object id);

    List<Repostajes> findAll();

    List<Repostajes> findRange(int[] range);

    int count();
    
}
