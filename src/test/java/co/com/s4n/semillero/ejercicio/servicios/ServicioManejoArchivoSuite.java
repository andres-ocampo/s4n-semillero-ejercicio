package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServicioManejoArchivo.class)
public class ServicioManejoArchivoSuite {

    @Test
    public void testLeerPowerMock(){
        PowerMockito.mockStatic(ServicioManejoArchivo.class);
        PowerMockito.when(ServicioManejoArchivo.leerArchivoVeinteDrones())
                .thenReturn(List.of(Try.of(()->List.of("AAI"))));
        ServicioManejoArchivo.leerArchivoVeinteDrones().forEach(a->assertTrue(a.isSuccess()));
        assertEquals(List.of(Try.of(()->List.of("AAI"))), ServicioManejoArchivo.leerArchivoVeinteDrones());
    }

    @Test
    public void testEscribirPowerMock(){
        List<List<Dron>> drons = List.of(List.of(new Dron()));
        PowerMockito.mockStatic(ServicioManejoArchivo.class);
        PowerMockito.when(ServicioManejoArchivo.escribirArchivo(drons))
                .thenReturn(Try.of(()->"(1, 1) dirección Norte"));
        assertTrue(ServicioManejoArchivo.escribirArchivo(drons).isSuccess());
        assertEquals(Try.of(()->"(1, 1) dirección Norte"), ServicioManejoArchivo.escribirArchivo(drons));
    }


    @Test
    public void testLeer(){
        List<Try<List<String>>> list = ServicioManejoArchivo.leerArchivoVeinteDrones();
        list.forEach(a -> assertTrue(a.isSuccess()));
        assertFalse(list.isEmpty());
    }

    @Test
    public void testEscribir(){
        List<List<Dron>> drons = List.of(List.of(new Dron(
                1,new Posicion(1,1,Direccion.ESTE),List.of(new Entrega()))));
        Try<String> f = ServicioManejoArchivo.escribirArchivo(drons);
        assertTrue(f.isSuccess());
    }
}
