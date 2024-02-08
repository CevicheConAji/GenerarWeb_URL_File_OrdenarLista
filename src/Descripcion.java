public class Descripcion {
    private String clase;
    private String alias;
    private String nreal;
    private String texto;

    public Descripcion(String clase, String alias, String nreal, String texto) {
        super();
        this.clase = clase;
        this.alias = alias;
        this.nreal = nreal;
        this.texto = texto;
    }


    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNreal() {
        return nreal;
    }

    public void setNreal(String nreal) {
        this.nreal = nreal;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Descripcion [clase=" + clase + ", alias=" + alias + ", nreal=" + nreal + ", texto=" + texto + "]";
    }

}