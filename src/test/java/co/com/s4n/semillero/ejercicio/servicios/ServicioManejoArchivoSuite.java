package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServicioManejoArchivoSuite {


    @Test
    public void testLeer(){
        List<String> strings = ServicioManejoArchivo.leerArchivo();
        assertFalse(strings.isEmpty());
    }

    @Test
    public void testEscribir(){
        Dron dron = new Dron(1,new Posicion(1,1,Direccion.NORTE),List.empty());
        List<Dron> drons = List.of(dron, dron);
        Try<String> f = ServicioManejoArchivo.escribirArchivo(drons);
        assertTrue(f.isSuccess());

    }
}
