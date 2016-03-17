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
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
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
    
    // Atributos
    private ArrayList<Consulta> listconsultas = new ArrayList<Consulta>();
    private ServiciosPacientes sp = ServiciosPacientes.getInstance();
    private ArrayList<Paciente> listpacientes;
    private Paciente actual_paciente;
    
    // Constructor
    public RegistroConsultaBean(){
        listpacientes = new ArrayList<Paciente>();
        listpacientes.add(new Paciente(123, "CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01")));
        listpacientes.add(new Paciente(321, "CC", "Maria Rodriguez", java.sql.Date.valueOf("2000-01-01")));
        listpacientes.add(new Paciente(875, "CC", "Pedro Martine", java.sql.Date.valueOf("1956-05-01")));
        actual_paciente = new Paciente(87, "CC", "prueba", new java.sql.Date(1996, 02, 12));
    }
    //Paciente
    private int id;
    private String tipo_id;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;

    // Consulta
    private Date fechaConsulta;
    private String descripcion;
    private ArrayList<Consulta> consultas_pacientes;
    private ArrayList<Consulta> consultas_depaciente;
    
    //Metodos
    // Paciente
    public ArrayList<Paciente> getListaPacientes(){
        return listpacientes;
    }
    
    public void setListaPacientes(ArrayList<Paciente> pac){
        this.listpacientes = pac;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    
    public void setFechaNacimiento(Date fecha) {
        this.fechaNacimiento = fecha;
    }
    
    public void registrarPaciente() throws ExcepcionServiciosPacientes{
        Paciente p = new Paciente(id, tipo_id, nombre+" "+apellido, new java.sql.Date(fechaNacimiento.getTime()));
        sp.registrarNuevoPaciente(p);
        listpacientes.add(p);
    }
    
    public Paciente consultarPaciente(int id, String tipo_id) throws ExcepcionServiciosPacientes{
        return sp.consultarPaciente(id, tipo_id);
    }
        
    // Consultas
    public ArrayList<Consulta> getConsultas(){
        return listconsultas;
    }
    
    public void setConsultas(ArrayList<Consulta> con){
        this.listconsultas = con;
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
    
        
    public void registrarConsulta() throws ExcepcionServiciosPacientes{
        Consulta con= new Consulta(new java.sql.Date(fechaConsulta.getTime()),descripcion);
        sp.agregarConsultaAPaciente(actual_paciente.getId(), actual_paciente.getTipo_id(),  con);
        listconsultas.add(con);
    }
    
    public Paciente getPacienteActual(){
        return actual_paciente;
    }
    
    public void setPacienteActual(Paciente actual){
        this.actual_paciente = actual;
    }
    
    public String navegacion(Paciente p){
        this.actual_paciente = p;
        return "registroconsultas.xhtml";
    }
    
    public ArrayList<Consulta> getConsultasPaciente(){
        consultas_depaciente = new ArrayList();
        Set<Consulta> temporal = actual_paciente.getConsultas();
        for (Iterator<Consulta> iterator = temporal.iterator(); iterator.hasNext();) {
            Consulta next = iterator.next();
            consultas_depaciente.add(next);
        }
        return consultas_depaciente;
    }
    
    public void setConsultasPaciente(ArrayList<Consulta> c){
        this.consultas_depaciente = c;
    }
}
