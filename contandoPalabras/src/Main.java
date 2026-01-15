import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        //scanner
        Scanner sc = new Scanner(System.in);
        //lo que me pide ingresar por teclado
        System.out.print("introduce el texto: ");
        //alamcena el valor introducido por teclado
        String frase= sc.nextLine();
        System.err.println(frase);

        //variable frase introducida y predefinida
        //String frase = "That's the password: 'PASSWORD 123'!, gritó el Agente Especial.\nAsí que huí.";


        //inicia el Map para almacenar las palabras que estan predefinidas en la variable "frase"
        //o introducidas por scanner
        Map<String, Integer> mapa = new HashMap<>();


        //recorre la frase
        //y lo convierte en un array/lista de palabras
        //usa split para descomponer la variable frase por espacios " "
        String[] arrayPalabras = frase.split("[ \\n]");


        //recorre el array para rellenar el mapa
        //variable palabras
        //arraypalabras
        for (String palabras : arrayPalabras) {
            //elimina caracteres especiales con replaceAll
            //quita el salto de linea con \\n
            palabras = palabras.replaceAll("[\",?!¿¡:.;-]", "").toLowerCase();


            //metodo starts.With que es: si empieza con comilla simple la elimina con replace
            if (palabras.startsWith("'")) {
                palabras = palabras.replace("'", "");
            }
            //este metodo es end.With que es: si termina con comilla simple la elimina
            if (palabras.endsWith("'")) {
                palabras = palabras.replace("'", "");
            }


            //este es el contador de palabras de la frase introducida
            if (mapa.containsKey(palabras)) {
                //variable que hace el conteo de las palabras para añadirlas al mapa y sumarlas si estan repes
                int contador = mapa.get(palabras);
                //suma las palabras repes si son iguales ya que las he pasado a mayusculas todas
                mapa.put(palabras, contador + 1);
            } else {
                //las que no son repes las añade igualmente pero sin sumarlas
                mapa.put(palabras, 1);
            }
        }

        System.out.println("El mapeo para este subtítulo sería: ");
        //itera por el mapa
        for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
            System.out.println("" + entrada.getKey() + ": " + entrada.getValue());

        }

    }
}


/*
ReadSymptomDataFromFile list = new ReadSymptomDataFromFile("Project02Eclipse\\symptoms.txt");
        Map<String, Integer> listSort = new TreeMap<>();
        List<String> test = list.getSymptoms();
//para convertir una lista en un mapa uso el for para el conteo y para las repes
        for (String word : test) {
            if (!listSort.containsKey(word)) {
                listSort.put(word, 1);
            } else {
                listSort.put(word, listSort.get(word) + 1);
            }
        }
*/
        
/*
String x = cadena.replaceFirst("^ *\"", "") // Comilla doble al inicio
                     .replaceFirst("\" *$", "") // Comilla doble al final
                     .replaceAll("''", "'")     // Doble Comilla sencilla
                     .replaceAll("\"\"", "\"")  // Doble Comilla doble
                     ;
*/

/*
String cadena= " \"esto es \'\' una \"\" prueba\" ";

    //Elimina espacios en los extremos de la cadena.
    cadena = cadena.trim();
    //Elimina primer comilla doble.
    if(cadena.startsWith("\"")){
        cadena = cadena.substring(1, cadena.length());
    }
    //Elimina ultima comilla doble
    if(cadena.endsWith("\"")){
        cadena = cadena.substring(0,cadena.length()-1);
    }
    //Reemplaza dobles apariciones por solo una.
    String resultado = cadena.replace("\'\'","\'").replace("\"\"","\"");
    System.out.println(resultado);
 */

/*
        //mapeo
        Map<Integer, String> criptomonedas = new HashMap<>();
        criptomonedas.put(1, "a");
        criptomonedas.put(2, "b");
        criptomonedas.put(3, "c");
        criptomonedas.put(4, "d");
        criptomonedas.put(5, "e");
        //criptomonedas.keyset es para el integer
        //"values es para los valores
        //"get metemos el parametro que queramos sacar
        //"size para decirnos cuanto hay
        //"contains value para ver si existe en el map devuelve true o false
        //"equals para ver igualdades
        System.out.println("Claves: " + criptomonedas);
        //quitamos el valor 2
        criptomonedas.remove(2,"b");
        System.out.println("Claves: " +criptomonedas);
        */

        /*
        //tiene en cuenta caracteres especiales
        String correo = "theharrycode@gmail.com";
        //genera error porque eso retorna y posicion 0
        System.err.println("El correo antes del @ es: " +correo.split("@")[0]);
        */
        /*
        //cuenta las palabras
        String s1 = "Hola mundo en Java";
        String[] a1;
        //separa por espacions
        a1 = s1.split(" ");
        System.out.println("la cadena tiene "+a1.length+ " palabras");
        //menciona el posicionamiento de la palabra en este caso la primera palabra es hola
        System.out.println("primera palabra de la cadena es: "+a1[0]);

        String s2 = "Juan,02,04,1995";
        String[] a2;

        a2 = s2.split(",");
        System.out.println("El nombre es: "+a2[0]);
        System.out.println("Dia de nacimiento: "+a2[1]);
        System.out.println("Mes de nacimiento: "+a2[2]);
        System.out.println("Año de nacimiento: "+a2[3]);
        */
        /*
        String[] palabras = {
                "manzana",
                "casa",
                "perrito",
                "alabaricoque"
        };
        //para concatenarlas uso un stream
        var res = Arrays.stream(palabras)
                //el proposito del collector estransformarlo en un unico valor final
                //el resultado es un joining para que lo una
                //puedo usar delimitadores como comas
                //prefijos como "palabras: "
                //un sufijo "." por ejemplo un punto final
                .collect(Collectors.joining(", ", "palabras: ", ".")
                );
        System.out.println(res);

        //buscar palabras
        String x = "hola que haces.";
        //split
        String[] arreglo = x.split(" ");
        //recorre  la cadena
        for (int i = 0; i < arreglo.length; i++){
            //busca la palabra en concreto
            if(arreglo[i].equals("hola")){
                //la printa
                System.out.println("La palabra existe en la cadena: " +arreglo[i]);
            }
        }
        //esto es lo mismo que las lineas 61-66 el mismo bucle
        for(String cadena : arreglo){
            if(cadena.equals("hola")){
                //la printa
                System.out.println("La palabra existe en la cadena: " +cadena);
            }
        }

        String nombre1= "Alex";
        //equals es sensible a mayus y minus
        //equalsIgnoreCase no diferencia entre mayusculas y minusculas
        //al se boolean tiene que devolver true o false, en este caso true
        //boolean resultado = nombre1.equalsIgnoreCase("alex"); //comentado para evitar errores
        //esto devuelve la longitud que es 4 letras
        int resultado = nombre1.length();
        System.out.println(resultado);

        //indica el orden en el qye esta la h, en este caso el 0
        //h=0, o=1, l=2, a=3
        String word = "hola";
        int result1 = word.indexOf("h");
        System.out.println(result1);


        //contador de palabras repetidas
        Scanner sc = new Scanner(System.in);
        //contador de palabras que empieza en 0
        int cont = 0;
        System.out.println("mete una cadena de texto: ");
        String texto = sc.nextLine();
        System.out.println(texto);
        System.out.println("palabra a buscar?");
        String palabraBuscada = sc.nextLine();
        //contar en un texto cuantas palabras hay repes
        int pb = texto.indexOf(palabraBuscada);
        while(pb!=-1) { //lo encuentro o no lo encuentro
            //sigo buscando
            cont++; //cuento
            pb++;
            pb = texto.indexOf(palabraBuscada, pb); //nuevamente la busqueda sobre el texto
        }
        System.out.println("cantidad de palabras: " +cont);
    */

/*
        String cadena = "That's the password: 'PASSWORD 123'!\","+" gritó el Agente Especial.\\nAsí que huí";
        String textoSeparado[]=cadena.split(",");
        for (int postS = 0; postS < textoSeparado.length; postS++);
        System.out.println(" "+textoSeparado[postS]);*/

/*
System.out.printf("Hello and welcome!");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
 */
/*
//interesante
//separa la cadena por trocitos que empiezan por mayusculas
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        // the compiled pattern can be re-used, are thread-safe
        // and thus can be static final
        Pattern p = Pattern.compile("([A-Z][a-z]?[0-9]*)+?");

        // Per molecule (well, string) a matcher must be obtained.
        Matcher m = p.matcher("HeKiLH2B6");
        while(m.find()) {
            System.out.println(m.group());
        }
    }
}
 */