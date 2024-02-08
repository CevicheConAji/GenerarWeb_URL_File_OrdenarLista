import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *Aplicación para procesar ficheros de texto (superheroes.txt) y (superheroescondrstrange.txt) que genera
 * 2 páginas web con información e imágenes que obtiene de esta web (<a href="http://mural.uv.es/franpevi/index.html">...</a>)
 * @author Piero Zavala Chira
 * @version 1.0
 */
public class Main {


    public static void main(String[] args) {
        ArrayList<Imagen> imagenes = new ArrayList<>();
        ArrayList<Descripcion> descripciones = new ArrayList<>();

        URL url = null;

        try {
            url = new URL("http://mural.uv.es/franpevi/index.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        BufferedReader in;

        try {
            InputStream inputStream = url.openStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            String inpuntLine;

            while ((inpuntLine = in.readLine()) != null) {

                //trim elimina los espacios
                inpuntLine = inpuntLine.trim();

                if (inpuntLine.contains("img src")) {
                    String[] partes = inpuntLine.split("class=");

                    Imagen imagen = new Imagen(partes[1].substring(1, 5), partes[0].substring(10, partes[0].length() - 2));

                    imagenes.add(imagen);

                }

            }
            System.out.println("\n");
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File entrada = new File("src/superheroes.txt");

        Scanner sc = null;

        try {
            sc = new Scanner(entrada);

            while (sc.hasNextLine()) {

                //Se guarda en el Array de partes separado por (;)

                String[] partes = sc.nextLine().split(";");
                Descripcion descripcion = new Descripcion(partes[0], partes[1], partes[2], partes[3]);
                descripciones.add(descripcion);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        imprimirListaImagenes(imagenes);
        System.out.println("\n");

        imprimirListaDescripcion(descripciones);
        System.out.println("\n");

        //Ordenar ArrayList de imagenes
        imagenes = ordenarListaImagenes(imagenes);

        imprimirListaImagenes(imagenes);

        generar_web(descripciones, imagenes, 'M');
        generar_web(descripciones, imagenes, 'D');
    }

    /**
     * t1 > t2 = 1
     * t1 < t2 = -1
     * t1 == t2 = 0
     */
    public static ArrayList<Imagen> ordenarListaImagenes(ArrayList<Imagen> lista) {
        ArrayList<Imagen> resultado = new ArrayList<>();
        String min;
        int posMin;
        while (!lista.isEmpty()) {
            min = lista.get(0).getClase();
            posMin = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getClase().compareTo(min) < 0) {
                    min = lista.get(i).getClase();
                    posMin = i;
                }

            }
            resultado.add(lista.get(posMin));
            lista.remove(posMin);
        }
        return resultado;
    }

    public static void imprimirListaImagenes(ArrayList<Imagen> imagenes) {

        for (Imagen imagen :
                imagenes) {
            System.out.println(imagen);
        }
    }

    public static void imprimirListaDescripcion(ArrayList<Descripcion> descripciones) {

        for (Descripcion d :
                descripciones) {
            System.out.println(d);
        }
    }

    public static void generar_web(ArrayList<Descripcion> descripcion, ArrayList<Imagen> imagen, char tipo) {
        ArrayList<Descripcion> desc_ok = new ArrayList<Descripcion>();
        ArrayList<Imagen> img_ok = new ArrayList<Imagen>();

        File file_salida ;
        FileWriter file = null;
        String titulo = "pagina web de superheroes de ";
        if (tipo == 'M') {
            file_salida = new File("marvel.html");
            titulo = titulo + "Marvel";
        } else {
            file_salida = new File("dcweb.html");
            titulo = titulo + "DC";
        }

        try {
            file_salida.createNewFile();
            file = new FileWriter(file_salida);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < imagen.size(); i++) {
            if (imagen.get(i).getClase().charAt(0) == tipo) {
                img_ok.add(imagen.get(i));
            }
        }

        for (int i = 0; i < descripcion.size(); i++) {
            if (descripcion.get(i).getClase().charAt(0) == tipo) {
                desc_ok.add(descripcion.get(i));
            }
        }

        try {
            creacion_web(titulo, file, desc_ok, img_ok);
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }

    }

    public static void creacion_web(String titulo, FileWriter file, ArrayList<Descripcion> desc_ok, ArrayList<Imagen> img_ok) throws IOException {

        String ruta_img;

        file.write("<html>\n");
        file.write("<head>\n");
        file.write("<title>" + titulo + "</title>\n");
        file.write("<head>\n");
        file.write("<body>\n");
        file.write("<h1>" + titulo + "<h1>\n");
        file.write("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#000000\">\n");
        file.write("<tr>\n");
        file.write("<th>Nombre</th>\n");
        file.write("<th>Id Secreta</th>\n");
        file.write("<th>Descripcion</th>\n");
        file.write("<th>Foto</th>\n");

        for (Descripcion descripcion : desc_ok) {
            ruta_img = "";
            file.write("<tr>\n");
            file.write("<td>" + descripcion.getAlias() + "</td>\n");
            file.write("<td>" + descripcion.getNreal() + "</td>\n");
            file.write("<td>" + descripcion.getTexto() + "</td>\n");

            for (Imagen imagen : img_ok) {
                if (descripcion.getClase().equals(imagen.getClase())) {
                    ruta_img = "\"" + imagen.getRuta() + "\"";
                    break;
                }
            }
            file.write("<td>" + "<img src=" + ruta_img + ">" + "</td>\n");
            file.write("</tr>\n");

        }
        file.write("</table>\n");
        file.write("</body>\n");
        file.write("</html>\n");


    }

}