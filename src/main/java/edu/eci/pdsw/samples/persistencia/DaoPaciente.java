/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistencia;

import edu.eci.pdsw.samples.entities.Paciente;
import javax.persistence.PersistenceException;

/**
 *
 * @author 2098654
 */
public interface DaoPaciente {

    
    public Paciente load(int id, String tipoid) throws PersistenceException;
    
    public void save(Paciente p) throws PersistenceException;
    
    public void update(Paciente p) throws PersistenceException;
    
    
}
