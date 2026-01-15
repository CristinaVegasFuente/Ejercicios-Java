import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //SCANNER
        Scanner sc = new Scanner(System.in);
        System.out.print("introduce un numero: ");
        int num = sc.nextInt();
        System.out.println("la cadena más larga de sumandos consecutivos es :");
        //inicializo la variable suma para que los numeros primos se sumen de forma consecutiva
        //ESTO PARA QUITAR?????????
        //int suma = 0;
        //ARRAYLIST PARA HACER UNA LISTA CONSECUTIVA PRIMER BLOQUE
        //inicializo el arraya lista de primos
        ArrayList<Integer> listaPrimos = new ArrayList<>();
        //BUCLE PARA BUSCAR LOS NUMEROS PRIMOS
        //indice i desde el 1 hasta el valor que meta por teclado
        //si meto un 5 iterare con los valores 1,2,3,4 y 5 si meyo un 5
        //ahora tengo que ver si iiiiiiiii es primo
        int contador = 0;
        for (int i = 1; i <= num; i++) {
            //variable contador
            contador = 0;
            //segundo for que va desde j=1 y va a ir iterando mientras j sea menor o igual a i
            //i es el valor que tenemos que ver si es primo
            for (int j = 1; j <= i; j++) {
                //condicion de si el resto de i con j es igual a cero
                //si es verdadero sumo uno al contador
                if (i % j == 0)
                    contador++;

            }
            //pregunto si solo hay dos divisores para que sea primo, por el mismo o por uno
            if (contador == 2) {
                //añado los numeros primos al array
                listaPrimos.add(i);
            }
        }
        //LOGICA PARA SUMAR LA CADENA DE PRIMOS QUE ME DA EL PRIMER BLOQUE
        //****************************************************************
        //NUEVA VARIABLE PARA CADENA DE PRIMOS CONSECUTIVOS cuantos
        int cadenaPrimos = 0;
        //******************************************************************
        //NUEVA VARIABLE DE SUMA DE PRIMOS CONSECUTIVOS valor
        int sumaPrimos = 0;
        //********************************************************
        //NUEVO ARRAY PARA GUARDAR LAS SUMAS
        ArrayList<Integer> listaSumaPrimos = new ArrayList<>();

        //itero por el arraylist de primos i
        for (int i = 0; i < listaPrimos.size(); i++) {
            //inicializo la suma a cero
            int suma = 0;
            //segundo bucle para la suma de los numeros primos j
            for (int j = i; j < listaPrimos.size(); j++) {
                //voy acumulando la suma
                suma = suma + listaPrimos.get(j);
                //condicion salir si la suma es mayor que el numero metido por teclado
                if (suma >= num)
                    break;
                //comprobacion de resultado primo
                int resultadoPrimo = 0;
                //mismo bucle de buscar primo pero para el resultado de la suma
                for (int k = 1; k <= suma; k++) {
                    if (suma % k == 0) resultadoPrimo++;
                }
                if (resultadoPrimo == 2) {


                    //cuantos primos hay sumados
                    int cantidadPrimos = j - i + 1;
                    //verifica que cantidadPrimos es una cadena mas larga que cadenaPrimos
                    if (cantidadPrimos > cadenaPrimos) {
                        //actializo
                        cadenaPrimos = cantidadPrimos;
                        //guardo la suma total de la nueva cadena
                        sumaPrimos = suma;
                        //lista completa de primos, desde el índice i hasta el índice j incluido por el +1
                        listaSumaPrimos = new ArrayList<>(listaPrimos.subList(i, j + 1));
                    }
                }
            }
        }
        System.out.println(listaSumaPrimos);
        System.out.print("suma = " + sumaPrimos);

    }
}