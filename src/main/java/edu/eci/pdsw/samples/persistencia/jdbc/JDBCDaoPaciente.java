/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistencia.jdbc;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistencia.DaoPaciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.PersistenceException;

/**
 *
 * @author 2098654
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen " +
                "from PACIENTES as pac inner join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id "
                + "where pac.id=? and pac.tipo_id=?");
            
            ps.setInt(1, idpaciente);
            ps.setString(2, tipoid);
            ResultSet rs=ps.executeQuery();
            
            Paciente p;
            Consulta c;
            if (rs.next()){
                
                p = new Paciente(idpaciente,tipoid,rs.getString("nombre"),rs.getDate("fecha_nacimiento"));
                c=new Consulta(rs.getDate("fecha_y_hora"),rs.getString("resumen"));
                c.setId(rs.getInt("idCONSULTAS"));
                p.getConsultas().add(c);
            }            
            else{
                throw new PersistenceException("No row with the given id:"+idpaciente);
            }

            while (rs.next()){
                
                c=new Consulta(rs.getDate("fecha_y_hora"),rs.getString("resumen"));
                c.setId(rs.getInt("idCONSULTAS"));
                p.getConsultas().add(c);
            }            
            
            return p;
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }

    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("insert into PACIENTES (id,tipo_id,nombre,fecha_nacimiento) values (?,?,?,?)");
            ps.setInt(1, p.getId());
            ps.setString(2, p.getTipo_id());
            ps.setString(3, p.getNombre());
            ps.setDate(4, p.getFechaNacimiento());            
            ps.execute();
            
            ps = con.prepareStatement("insert into CONSULTAS (fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                     
            for (Consulta c:p.getConsultas()){
                ps.setDate(1, c.getFechayHora());
                ps.setString(2, c.getResumen());
                ps.setInt(3, p.getId());
                ps.setString(4, p.getTipo_id());
                ps.execute();
                
                ResultSet keys=ps.getGeneratedKeys();
                while(keys.next()){
                    c.setId(keys.getInt(1));
                }
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("insert into CONSULTAS (fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            for (Consulta c:p.getConsultas()){
                
                if (c.getId()==-1){
                    ps.setDate(1, c.getFechayHora());
                    ps.setString(2, c.getResumen());
                    ps.setInt(3, p.getId());
                    ps.setString(4, p.getTipo_id());
                    ps.execute();

                    ResultSet keys=ps.getGeneratedKeys();
                    
                    while(keys.next()){
                        c.setId(keys.getInt(1));
                    }
                }
            }
            
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }        
    }
    
}
