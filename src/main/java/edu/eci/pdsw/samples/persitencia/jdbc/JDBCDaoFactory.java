/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persitencia.jdbc;

import edu.eci.pdsw.samples.persistencia.DaoFactory;
import edu.eci.pdsw.samples.persistencia.DaoPaciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.persistence.PersistenceException;

/**
 *
 * @author estudiantes
 */
public class JDBCDaoFactory extends DaoFactory {
    
    private static final ThreadLocal<Connection> connectionInstance = new ThreadLocal<Connection>() {
    };

    private static Properties appProperties=null;
    
    public JDBCDaoFactory(Properties appProperties) {
        this.appProperties=appProperties;
    }
    
    
    
    private static Connection openConnection() throws PersistenceException{
            String url=appProperties.getProperty("url");
            String driver=appProperties.getProperty("driver");
            String user=appProperties.getProperty("user");
            String pwd=appProperties.getProperty("pwd");
                        
        try {
            Class.forName(driver);
            Connection _con=DriverManager.getConnection(url,user,pwd);
            _con.setAutoCommit(false);
            return _con;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new PersistenceException("Error on connection opening.",ex);
        }

    }
    
    @Override
    public void beginSession() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){            
                connectionInstance.set(openConnection());
            }
            else{
                throw new PersistenceException("Session was already opened.");
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while opening a JDBC connection.",ex);
        }
        
    }


    
    
    @Override
    public void endSession() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                connectionInstance.get().close();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                connectionInstance.get().commit();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }        
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
                try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                connectionInstance.get().rollback();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }

    @Override
    public DaoPaciente getDaoPaciente() {
        return new JDBCDaoPaciente(connectionInstance.get());
    }
    
}