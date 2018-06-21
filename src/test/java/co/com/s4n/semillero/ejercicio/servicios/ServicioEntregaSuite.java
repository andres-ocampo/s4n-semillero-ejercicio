package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Almuerzo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioEntrega;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServicioEntregaSuite {

    @Test
    public void testRealizarEntrega(){
        Dron dron = new Dron(1,new Posicion(0,0,Direccion.NORTE),List.empty());
        Entrega entrega = new Entrega(true,new Almuerzo(1,"Arroz"),"AAAAIAAD".toCharArray());
        Try<Dron> dronOption = ServicioEntrega.realizarEntrega(dron, entrega);
        ServicioEntrega.cargarRuta();
        assertEquals(Direccion.NORTE, dronOption.getOrElse(dron).getPosicion().getDireccion());
        assertEquals(-2, dronOption.getOrElse(dron).getPosicion().getX());
        assertEquals(4, dronOption.getOrElse(dron).getPosicion().getY());
    }
}
