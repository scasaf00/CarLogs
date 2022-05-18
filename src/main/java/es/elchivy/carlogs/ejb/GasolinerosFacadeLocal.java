/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gasolineros;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface GasolinerosFacadeLocal {

    void create(Gasolineros gasolineros);

    void edit(Gasolineros gasolineros);

    void remove(Gasolineros gasolineros);

    Gasolineros find(Object id);

    List<Gasolineros> findAll();

    List<Gasolineros> findRange(int[] range);

    int count();
    
}
