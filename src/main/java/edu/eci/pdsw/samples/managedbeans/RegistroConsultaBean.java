/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hcadavid
 * @author Kevin S. Sanchez - Sebastian Amaya
 */
@ManagedBean (name = "beanConsultas")
@SessionScoped

public class RegistroConsultaBean implements Serializable{
    
    // Aributos
    private ArrayList<Consulta> listconsultas = new ArrayList<Consulta>();
    private ServiciosPacientes sp = ServiciosPacientes.getInstance();
    private ArrayList<Paciente> listpacientes = new ArrayList<>();
    
    //Paciente
    private int id;
    private String tipo_id;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;

   
    private Date fechaConsulta;
    private String descripcion;
    
    //Metodos
    public ArrayList<Paciente> getListaPacientes(){
        return listpacientes;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public java.sql.Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    // Revisar Fecha de Nacimiento
    public void setFechaNacimiento(Date fecha) {;
        int dia = fecha.getDay();
        int mes = fecha.getMonth();
        int year = fecha.getYear();
        java.sql.Date temporal = new java.sql.Date(dia, mes, year);
        this.fechaNacimiento = temporal;
    }
    
    public ArrayList<Paciente> getPacientes(){
        return listpacientes;
    }
    
    public ArrayList<Consulta> getConsultas(){
        return listconsultas;
    }
    
    public void registrarPaciente() throws ExcepcionServiciosPacientes{
        listpacientes.add(new Paciente(123, "CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01")));
        Paciente p = new Paciente(id, tipo_id, nombre+" "+apellido, fechaNacimiento);
        sp.registrarNuevoPaciente(p);
        listpacientes.add(p);
    }
    
    public Paciente consultarPaciente(int id, String tipo_id) throws ExcepcionServiciosPacientes{
        return sp.consultarPaciente(id, tipo_id);
    }
    
    
    public void registrarConsulta() throws ExcepcionServiciosPacientes{
       
        Consulta con= new Consulta(fechaConsulta,descripcion);
        sp.agregarConsultaAPaciente( id, tipo_id,  con);
        listconsultas.add(con);
    }
     public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
        
        
    }

    public String getDescripcion() {
        return descripcion;
    }

    //consulta
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
