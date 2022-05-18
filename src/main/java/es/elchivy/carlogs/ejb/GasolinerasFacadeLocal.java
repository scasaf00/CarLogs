/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.elchivy.carlogs.ejb;

import es.elchivy.carlogs.modelo.Gasolineras;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio
 */
@Local
public interface GasolinerasFacadeLocal {

    void create(Gasolineras gasolineras);

    void edit(Gasolineras gasolineras);

    void remove(Gasolineras gasolineras);

    Gasolineras find(Object id);

    List<Gasolineras> findAll();

    List<Gasolineras> findRange(int[] range);

    int count();
    
}
