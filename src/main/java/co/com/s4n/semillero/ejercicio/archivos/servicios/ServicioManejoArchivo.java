package co.com.s4n.semillero.ejercicio.archivos.servicios;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServicioManejoArchivo {

    private static Validation<String, String> tryEsExitoso(Try<List<String>> archivo) {
        if(archivo.isFailure()) return Validation.invalid("No se pudo leer el archivo");
        else return Validation.valid("Archivo leído con éxito");
    }

    private static Validation<String, String> archivoNoEstaVacio(Try<List<String>> archivo) {
        if(archivo.getOrElse(List.of()).size() > 0) return Validation.valid("El archivo no está vacío");
        else return Validation.invalid("El archivo está vacío");
    }

    private static Validation<Seq<String>, String> validar(Try<List<String>> archivo) {
        return Validation
                .combine(tryEsExitoso(archivo),
                        archivoNoEstaVacio(archivo))
                .ap((x,y)->"");
    }

    public static List<String> leerArchivo(){
        Path path = Paths.get("/home/s4n/in.txt");
        Try<List<String>> archivo = Try.of(() -> Files.lines(path).collect(List.collector()));
        Validation<Seq<String>, String> res = validar(archivo);
        return res.isValid() ? archivo.getOrElse(List.of()) : List.of();
    }


}
