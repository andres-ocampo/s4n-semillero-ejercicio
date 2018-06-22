package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServicioManejoArchivo.class)
public class ServicioManejoArchivoSuite {

    @Test
    public void testLeerPowerMock(){
        PowerMockito.mockStatic(ServicioManejoArchivo.class);
        PowerMockito.when(ServicioManejoArchivo.leerArchivo())
                .thenReturn(Try.of(()->List.of("AAI")));
        assertTrue(ServicioManejoArchivo.leerArchivo().isSuccess());
        assertEquals(Try.of(()->List.of("AAI")), ServicioManejoArchivo.leerArchivo());
    }

    @Test
    public void testEscribirPowerMock(){
        List<Dron> drons = List.of(new Dron());
        PowerMockito.mockStatic(ServicioManejoArchivo.class);
        PowerMockito.when(ServicioManejoArchivo.escribirArchivo(drons))
                .thenReturn(Try.of(()->"(1, 1) dirección Norte"));
        assertTrue(ServicioManejoArchivo.escribirArchivo(drons).isSuccess());
        assertEquals(Try.of(()->"(1, 1) dirección Norte"), ServicioManejoArchivo.escribirArchivo(drons));
    }


    @Test
    public void testLeer(){
        Try<List<String>> list = ServicioManejoArchivo.leerArchivo();
        assertTrue(list.isSuccess());
        assertFalse(list.isEmpty());
    }

    @Test
    public void testEscribir(){
        Dron dron = new Dron(1,new Posicion(1,1,Direccion.NORTE),List.empty());
        List<Dron> drons = List.of(dron, dron);
        Try<String> f = ServicioManejoArchivo.escribirArchivo(drons);
        assertTrue(f.isSuccess());

    }
}
