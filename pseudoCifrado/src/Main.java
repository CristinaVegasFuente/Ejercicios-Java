import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("introduce los numeros: ");


        String cadena = sc.nextLine();
        System.out.print("introduce las repeticiones: ");

        int repeticiones = sc.nextInt();
        System.out.println(" ");

        //metodoB
        String cifradoRepeticiones = encrypt.metodoB(cadena, repeticiones);
        System.out.println(" ");

        //metodoC
        encrypt.metodoC(cifradoRepeticiones, repeticiones);
    }
}
/*
public static void main(String [] args) {
        String nomApe = "cristina vegas";
        String nombre = nomApe.substring(0, 8);
        System.out.println(nombre);
        String apellido = nomApe.substring(8).trim();
        System.out.println(apellido);
    }
 */
/*
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        String myTarget = "012345";
        for (int i = 0; i < myTarget.length(); i++) {
            for (int j = i + 1; j <= myTarget.length(); j++) {
                System.out.println(myTarget.substring(i, j));
            }
        }
    }
}
 */

/*
// La entrada se lee como un String para conservar los ceros iniciales.
        String numero = "012345";
        try {
            int n = Integer.parseInt(numero);


            int pares = 0;
            int impares = 0;
            for (int i = 0; i < numero.length(); i++) {
                System.out.print(numero.charAt(i));

                if (n % 2 == 0) {
                    pares++;

                }if (n % 2 == 0) {
                    impares++;
                }
            }
            System.out.println(" ");
            System.out.println("numeros pares: " + pares);
            System.out.println("numeros impares: " + impares);

        //lo he añadido de forma automayica porque si no me daba error
        } finally {

        }
 */
/*    // La entrada se lee como un String para conservar los ceros iniciales.
        String numeroComoCadena;

        //try para el Scanner.
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("descomponer numero");
            System.out.print("itroduce un número: ");
            //next() en lugar de nextInt() para leer la cadena.
            numeroComoCadena = entrada.next();

            System.out.println("El número descompuesto:");
            //int i = 0 inicializa con valor cero que es la posicion del primer digito
            //i < numeroComoCadena.length();condicion de la longitud de la cadena
            //i++ El incremento
            int digitoNumerico = 0;
            char digitoChar = 0;
            int pares = 0;
            int impares = 0;
            for (int i = 0; i < numeroComoCadena.length(); i++) {
                //numeroComoCadena.charAt(i) recoge el digito que se encuentra en la posición i o sea 0
                //acuerdate del orden tia!!!!
                //digitoChar mantiene el valor
                digitoChar = numeroComoCadena.charAt(i);
                digitoNumerico = Character.getNumericValue(digitoChar);
                System.out.print(digitoNumerico + " ");

                if (digitoChar % 2 == 0) {
                    pares++;

                }if (digitoChar % 2 != 0){
                    impares++;
                }
            }

            System.out.println("numeros pares: " + pares);
            System.out.println("numeros impares: " + impares);
        }*/



        /*int numero = 0, numDescompuesto = 0;
        char p;

        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println("Descomponer por digitos");
            System.out.println("introduce un numero: ");
            numero = entrada.nextInt();
            //cierra el scanner!!!! acuerdate de ponerlo tia
            entrada.close();
            //convierte el valor de p a numero
            String cadena = String.valueOf(numero);

            for (int i=0; i<cadena.length();i++){
                p=cadena.charAt(i);
                //convierte valor de p a numerico
                numDescompuesto =Character.getNumericValue(p);
                System.out.print(" " + numDescompuesto + " ");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/


/*  CUENTA LOS DIGITOS PARES QUE HAY EN UN NUMERO, PROBLEMA!!! QUE NO ME LEE EL CERO
    PODRIA SER UNA OPCION

        long numero;
        int longitud;
        int digito;
        int pares=0;

        Scanner teclado = new Scanner(System.in);
        System.out.println("introduce un numero para descifrar: ");
        numero = teclado.nextLong();
        System.out.println("numero: " + numero);

        //por ejemplo 012345 se suma 1 porque empieza por 0
        //recuerda cris el orden que empieza por 0
        //aqui se va dividiendo por 10 para hayar cada digito
        longitud = (int) Math.log10(numero)+1;
        //012345 tiene longitu 6 digitos
        //digito es 012345/10*longitud-1 =5 y tenemos el digito 1
        //para ver si es par tenemos que /2=0

        for (int i = 1; i <= longitud; i++) {
            digito = (int) (numero/Math.pow(10, longitud -i));
                    if (digito %2==0){
                        pares++;
                    }
        }//fin for

        System.out.printf("el numero %d contiene %d digitos pares y %d digitos impares.%n",numero, pares, longitud-pares);*/


    /*    int par;
        int impar;

        Scanner sc = new Scanner(System.in);
        System.out.println("introduce un numero para descifrar: ");
        int num = sc.nextInt();
        System.out.println("numero: " + num);


        if (num % 2 == 0)
            impar = num - 1;
        else
            impar = num - 2;

        for (int i = impar; i > (impar - 20); i = i - 2)*/



        /*System.out.println( pares + impares );*/



/*
        if (s %2==0){
            System.out.println("numeros pares: ");
        }else{
            System.out.println("numeros impares: ");
        }
 */

/*
Integer.parseInt("Cadena") es un metodo de clase Integer,
la cual como su nombre indica, parsea (convierte, transforma) una cadena
pasada como parámetro y te devuelve un número de tipo int (no confundir con
Integer.valueOf).
 */


/*
        //metodoC main
        String descifrado = encrypt.metodoC(cifradoRepeticiones, repeticiones);
        System.out.println("descifrado: " + descifrado);
        */

