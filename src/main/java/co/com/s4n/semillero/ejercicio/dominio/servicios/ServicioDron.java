package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Barrio;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Posicion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import io.vavr.control.Try;

public class ServicioDron {

    static public Try<Dron> avanzar(Dron dron, Barrio barrio){
        int x = dron.getPosicion().getX();
        int y = dron.getPosicion().getY();
        switch (dron.getPosicion().getDireccion()) {
            case NORTE:
                y += 1;
                break;
            case ESTE:
                x += 1;
                break;
            case SUR:
                y -= 1;
                break;
            case OESTE:
                x -= 1;
                break;
            default:
                return null;
        }
        Posicion posicion = new Posicion(x,y,dron.getPosicion().getDireccion());
        if(Math.abs(posicion.getX()) > barrio.limite || Math.abs(posicion.getY()) > barrio.limite)
            return Try.failure(new Exception());
        return Try.of(() -> new Dron(dron.getId(),posicion,dron.getEntregas()));
    }

    static public Try<Dron> girarDerecha(Dron dron){
        Direccion d = dron.getPosicion().getDireccion();
        switch (d){
            case NORTE:
                d = Direccion.ESTE;
                break;
            case ESTE:
                d = Direccion.SUR;
                break;
            case SUR:
                d = Direccion.OESTE;
                break;
            case OESTE:
                d = Direccion.NORTE;
                break;
            default:
                break;
        }
        Posicion posicion = new Posicion(dron.getPosicion().getX(),dron.getPosicion().getY(),d);
        return Try.of(() -> new Dron(dron.getId(),posicion,dron.getEntregas()));
    }

    static public Try<Dron> girarIzquierda(Dron dron){
        Direccion d = dron.getPosicion().getDireccion();
        switch (d){
            case NORTE:
                d = Direccion.OESTE;
                break;
            case ESTE:
                d = Direccion.NORTE;
                break;
            case SUR:
                d = Direccion.ESTE;
                break;
            case OESTE:
                d = Direccion.SUR;
                break;
            default:
                break;
        }
        Posicion posicion = new Posicion(dron.getPosicion().getX(),dron.getPosicion().getY(),d);
        return Try.of(() -> new Dron(dron.getId(),posicion,dron.getEntregas()));
    }
}
