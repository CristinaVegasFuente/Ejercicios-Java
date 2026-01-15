//importado el scanner
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//declaración del ENUM
//mayusculas y las constantes separadas por comas
//correccion de uno a dos valores por el tema de x e y
enum Coordenadas {
    N(0, 1),
    S(0, -1),
    E(1, 0),
    W(-1, 0);

    //su valor solo se puede asignar una vez a través del constructor
    private final int x;
    private final int y;

    //de este constructor
    Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //getter
    public int getX() {
        return x;
    }
    //getter
    public int getY() {
        return y;
    }
}

public class caminando {
    public static void main(String[] args) {
        //inicializado el scanner
        Scanner sc = new Scanner(System.in);

        String letras;
        int minutos;
        //primera validacion para las letras de las coordenadas
        System.out.println("Introduce las coordenadas (N, S, E, W): ");
        while (true) {
            letras = sc.nextLine();
            //toUpperCase mayusculas/minusculas
            //[NSEW]* unicamente esas letras y * para varias letras
            //esto lo tengo que volver a ubicar en la linea 83 porque no lo almacena nada mas que aqui
            if (letras.toUpperCase().matches("[NSEW]*")) {
                //si lo cumple se sale del bucle
                break;
            } else {
                System.out.println("Error");
            }
        }
        //segunda validacion para numeros enteros
        System.out.println("Introduce los minutos: ");
        while (true) {
            //numeros enteros
            if (sc.hasNextInt()) {
                minutos = sc.nextInt();
                sc.nextLine();
                break;
            //da fallo al ingresar cualquier otro caracter que no sea numerico
            } else {
                System.out.println("Errror");
                sc.next();
            }
        }

        boolean puntoPartida = pp(letras);
        //el booleano que da true o false segun la ruta que introduzca por teclado
        if (puntoPartida) {
            //esto seria true
            System.out.println("bien");
        } else {
            //esto evidentemente es false
            System.out.println("maaaaaal");
        }
    }

    //pp es punto de partida
    public static boolean pp(String letras) {
        //las variables x & y comienzan en la posicion 0 del recorrido
        int x = 0;
        int y = 0;

        //toCharArray() convierte la cadena en un array
        //meto el .toUpperCase para que me lea minusulas para el array
        for (char direccion : letras.toUpperCase().toCharArray()) {
            //valueof transforma el valor que obtiene a un string
            Coordenadas coordenada = Coordenadas.valueOf(String.valueOf(direccion));
            //suma el valor de la coordenada.getX() a la variable x
            x = x + coordenada.getX();
            //idem con la y
            y = y + coordenada.getY();
        }

        System.out.println("cordenada x: " + x);
        System.out.println("cordenada y: " + y);
        System.out.println();
        //devuelve el punto de partida
        //explicitamente tiene que ser igual a cero ambas variables ==0
        return x == 0 && y == 0;
    }
}

/*
// Definición de la clase enum para los días de la semana,
// ahora con dos valores personalizados
enum DiaDeLaSemana {
    LUNES("Día laboral", 8),
    MARTES("Día laboral", 8),
    MIERCOLES("Día laboral", 8),
    JUEVES("Día laboral", 8),
    VIERNES("Día laboral", 7), // Por ejemplo, un viernes con menos horas
    SABADO("Fin de semana", 0),
    DOMINGO("Fin de semana", 0);

    // Variables de instancia para guardar las propiedades
    private final String tipoDia;
    private final int horasLaborales;

    // Constructor privado para inicializar ambos valores
    DiaDeLaSemana(String tipoDia, int horasLaborales) {
        this.tipoDia = tipoDia;
        this.horasLaborales = horasLaborales;
    }

    // Metodo público para obtener el tipo de día
    public String getTipoDia() {
        return tipoDia;
    }

    // Metodo público para obtener las horas laborales
    public int getHorasLaborales() {
        return horasLaborales;
    }
}

public class EjemploEnumConDosValores {
    public static void main(String[] args) {
        // Declarar una variable de tipo enum
        DiaDeLaSemana hoy = DiaDeLaSemana.MARTES;

        // Acceder a los dos valores del enum
        System.out.println("Hoy es " + hoy + ".");
        System.out.println("Es un " + hoy.getTipoDia() + " y tiene " + hoy.getHorasLaborales() + " horas laborales.");

        // Iterar sobre todos los valores del enum y mostrar sus descripciones
        System.out.println("\nResumen de la semana:");
        for (DiaDeLaSemana dia : DiaDeLaSemana.values()) {
            System.out.println("- " + dia + ": " + dia.getTipoDia() + ", " + dia.getHorasLaborales() + " horas.");
        }
    }
}*/

/*
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

//clase split
public class Main {
    public static void main(String[] args) {

        //divide en formato lista
        String mensaje = "harry|mesa|12000|en mora";
        //importante poner las barras para que reconozca los pay (caracteres especiales)
        String [] datos = mensaje.split("\\|");
        for(int i = 0; datos.length > i; i++){
            System.err.println("dato en posicion ["+i+"]: "+datos[i]);
        }

        //tiene en cuenta caracteres especiales
        String correo = "theharrycode@gmail.com";
        //genera error porque eso retorna y posicion 0
        System.err.println("El correo antes del @ es: " +correo.split("@")[0]);


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


    }
}
 */

/*
        String cadena = "That's the password: 'PASSWORD 123'!\","+" gritó el Agente Especial.\\nAsí que huí";
        String textoSeparado[]=cadena.split(",");
        for (int postS = 0; postS < textoSeparado.length; postS++);
        System.out.println(" "+textoSeparado[postS]);*/




/*
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe una frase:");
        String frase = sc.nextLine();*/





/*
System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {

            System.out.println("i = " + i);
        }
 */