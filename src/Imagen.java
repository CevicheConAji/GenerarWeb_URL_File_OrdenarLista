public class Imagen {
    private String clase;
    private String ruta;

    public Imagen(String clase, String ruta) {
        super();
        this.clase = clase;
        this.ruta = ruta;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Imagen [clase=" + clase + ", ruta=" + ruta + "]";
    }


}