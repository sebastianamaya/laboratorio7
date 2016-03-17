/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistencia;

import edu.eci.pdsw.samples.persitencia.jdbc.JDBCDaoFactory;
import edu.eci.pdsw.samples.persitencia.jdbc.JDBCDaoFactory;
import java.util.Properties;
import javax.persistence.PersistenceException;

/**
 *
 * @author 2098654
 */
public abstract class DaoFactory {

    protected DaoFactory() {
    }

    private static DaoFactory instance = null;

    public static DaoFactory getInstance(Properties appProperties) {
        if (instance == null) {
            instance = new JDBCDaoFactory(appProperties);        
        }
        return instance;
    }

    public abstract void beginSession() throws PersistenceException;

    public abstract DaoPaciente getDaoPaciente();

    public abstract void commitTransaction() throws PersistenceException;

    public abstract void rollbackTransaction() throws PersistenceException;

    public abstract void endSession() throws PersistenceException;
}
