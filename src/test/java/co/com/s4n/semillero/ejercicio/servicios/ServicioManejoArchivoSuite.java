package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.archivos.servicios.ServicioManejoArchivo;
import io.vavr.collection.List;
import org.junit.Test;

public class ServicioManejoArchivoSuite {


    @Test
    public void testLeer(){
        List<String> strings = ServicioManejoArchivo.leerArchivo();
        strings.forEach(System.out::println);
    }
}
