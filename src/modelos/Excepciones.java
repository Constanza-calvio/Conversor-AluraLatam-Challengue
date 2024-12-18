package modelos;

public class Excepciones extends Exception {

    public Excepciones(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    // Constructor con solo mensaje
    public Excepciones(String mensaje) {
        super(mensaje);
    }

}
