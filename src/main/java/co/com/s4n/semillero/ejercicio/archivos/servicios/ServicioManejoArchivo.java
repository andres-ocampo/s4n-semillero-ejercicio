package co.com.s4n.semillero.ejercicio.archivos.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import java.nio.charset.Charset;
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

    public static  Try<List<String>> leerArchivo(){
        Path path = Paths.get("src/main/resources/in.txt");
        Try<List<String>> archivo = Try.of(() -> Files.lines(path).collect(List.collector()).filter(a -> a.matches("[A|I|D]+")));
        Validation<Seq<String>, String> res = validar(archivo);
        return res.isValid() ? archivo : Try.failure(new Exception());
    }


    public static Try<String> escribirArchivo(List<Dron> reporteDrones){
        Path path = Paths.get("src/main/resources/out.txt");
        return Try.of(() -> {
            List<String> line = List.of();
            line = line.append("== Reporte de entregas ==");
            for (Dron dron : reporteDrones) {
                line = line.append("(" + dron.getPosicion().getX()
                        + ", " + dron.getPosicion().getY()
                        + ") dirección "
                        + ServicioManejoArchivo.enumToString(dron.getPosicion().getDireccion()));
            }
            Files.write(path, line, Charset.defaultCharset());
            return "Archivo escrito con éxito";
        });
    }

    private static String enumToString(Direccion d){
        switch (d){
            case NORTE: return "Norte";
            case OESTE: return "Oeste";
            case SUR:   return "Sur";
            case ESTE:  return "Este";
        }
        return "";
    }
}
