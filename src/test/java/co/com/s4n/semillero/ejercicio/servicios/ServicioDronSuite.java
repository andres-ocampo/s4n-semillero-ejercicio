package co.com.s4n.semillero.ejercicio.servicios;


import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ServicioDronSuite {


    @PrepareForTest(ServicioDron.class)
    @Test
    public void testAvanzarStatic(){
        Dron dron = Mockito.mock(Dron.class);
        PowerMockito.mockStatic(ServicioDron.class);
        PowerMockito.when(ServicioDron.avanzar(dron))
                .thenReturn(Try.of(()->new Dron(1,new Posicion(1,1,Direccion.ESTE),List.empty())));

        assertTrue(ServicioDron.avanzar(dron).isSuccess());
    }

    @Test
    public void testAvanzar(){
        Dron dron = new Dron(1,new Posicion(0,0,Direccion.NORTE),List.empty());
        Try<Dron> dron2 = ServicioDron.avanzar(dron);
        assertEquals(dron2.get().getPosicion().getY(), 1);
    }

    @Test
    public void testGirarDerecha(){
        Dron dron = new Dron(1,new Posicion(0,0,Direccion.NORTE),List.empty());
        Try<Dron> dron2 = ServicioDron.girarDerecha(dron);
        assertEquals(dron2.get().getPosicion().getDireccion(), Direccion.ESTE);
    }

    @Test
    public void testGirarIzquierda(){
        Dron dron = new Dron(1,new Posicion(1,1,Direccion.NORTE),List.empty());
        Try<Dron> dron2 = ServicioDron.girarIzquierda(dron);
        assertEquals(dron2.get().getPosicion().getDireccion(), Direccion.OESTE);
    }

}
