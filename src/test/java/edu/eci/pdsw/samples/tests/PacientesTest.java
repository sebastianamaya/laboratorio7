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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientesTest {
    
    public PacientesTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void registroPacienteTest() throws ExcepcionServiciosPacientes{
        Paciente pac=new Paciente(666, "cc", "jose", new Date (1996,9,26));
        Set<Consulta> consultas= new LinkedHashSet<Consulta>();
        Consulta consu=new Consulta(new Date(2016,3,8), "colicos");
        consultas.add(consu);
        pac.setConsultas(consultas);
        ServiciosPacientes servipac = ServiciosPacientes.getInstance();
        servipac.registrarNuevoPaciente(pac);
        Paciente prueba=servipac.consultarPaciente(666, "cc");
        assertTrue("El id del paciente debe ser igual ", pac.getId()==prueba.getId());
        assertTrue("El nombre del paciente debe ser igual ", pac.getNombre()==prueba.getNombre());
        

    }
    
    
}
