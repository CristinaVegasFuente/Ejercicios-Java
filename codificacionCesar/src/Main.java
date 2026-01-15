import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //String frase = sc.next();
        sc.useDelimiter("\n");

        String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

        System.out.println("Dame una frase: ");
        String frase = sc.next();

        String texto = codificar(letras, frase);
        System.out.println("Texto codificado: " + texto);


        texto = descodificar(letras, frase);
        System.out.println("Texto descodificado: " + texto);

    }

    public static String codificar(String letras, String texto){

        String textoCodificado = "";

        texto = texto.toUpperCase();

        char caracter;

        for (int i = 0; i < texto.length(); i++){

            caracter = texto.charAt(i);

            int posicion = letras.indexOf(caracter);

            if (posicion == -1) {
                textoCodificado += caracter;
            }else{
                textoCodificado += letras.charAt ((posicion + 3) % letras.length());
            }
        }

        return textoCodificado;

    }
    public static String descodificar(String letras, String texto) {

        String textoDescodificado = "";

        texto = texto.toUpperCase();

        char caracter;

        for (int i = 0; i < texto.length(); i++) {
            caracter = texto.charAt(i);

            int posicion = letras.indexOf(texto);

            if (posicion == -1) {
                textoDescodificado += caracter;
            } else {
                if (posicion - 3 < 0) {
                    textoDescodificado += letras.charAt(letras.length() + (posicion - 3));
                } else {

                    textoDescodificado += letras.charAt((posicion - 3) % letras.length());
                }
            }



        }
        return textoDescodificado;



    }

}