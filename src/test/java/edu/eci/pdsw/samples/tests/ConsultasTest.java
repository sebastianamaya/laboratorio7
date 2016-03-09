/*
 * Copyright (C) 2015 hcadavid
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
import java.sql.Date;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class ConsultasTest {
    
    public ConsultasTest() {
    }
    
    @Before
    public void setUp() {
    }
        
   
    @Test
    public void registroConsultaTest() throws ExcepcionServiciosPacientes{
         Paciente pac = new Paciente(666, "cc", "jose", new Date (1996,9,26));
         Consulta consu = new Consulta(new Date(2016,3,8), "colicos");
         pac.getConsultas().add(consu);
         ServiciosPacientes servipac = ServiciosPacientes.getInstance();
         servipac.registrarNuevoPaciente(pac);
         servipac.agregarConsultaAPaciente(666, "cc", consu);
         Paciente prueba= servipac.consultarPaciente(666, "cc");
         assertTrue("La cantidad de consultas debe ser la misma registrada y la misma del paciente", prueba.getConsultas().size()==pac.getConsultas().size());
         Iterator<Consulta> iConsultas= prueba.getConsultas().iterator();
         Iterator<Consulta> iPac= pac.getConsultas().iterator();
         while (iConsultas.hasNext() &&iPac.hasNext()){
             assertTrue("La consulta debe ser la misma", iConsultas.next().getId()== iPac.next().getId());
         }
    }
   
}
