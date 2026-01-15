import java.util.Scanner;

public class Harshad {

    // primer metodo
    public static boolean isValid(int num) {
        // Descompongo el numero introducido por scanner en decenas
        int decenas = num / 10;
        // despues en unidades
        int unidades = num % 10;
        //variable numHarshad y uso el operador del resto
        int numHarshad = num % (decenas + unidades);
        //devuelve numero harshad true
        return numHarshad ==0;
    }

    //segundo metodo
    public static void getNext(int num) {
        do {
            //si meto un numero X me sale el siguiente ++
            num++;
            if (isValid(num)) {
                System.out.println("el siguiente harshad es: " + num);
            }
        } while (!isValid(num));
    }

    //tercer metodo
    public static void getSerie(int num1,int num2) {
        //harshad + el siguiente harshad
        int numHarshad = num1 + 1;
        //comienza en cero
        int contador = 0;
        System.out.println("Serie de Harshads empieza en:" + num1);
        //busca numeros hasta encontrar la cantidad que le he metido por teclado

        while (contador < num2) {
            //hace la validacion
            int decenas = numHarshad / 10;
            int unidades = numHarshad % 10;
            if ((decenas + unidades) == 0) {
                //si pasa la validacion se suma y vuelve a empezar hasta que recolecte los harshad que le he pedido por teclado en la variable num2
                numHarshad++;
                continue;
            }
            //valida que el resto de cero
            int resto = numHarshad % (decenas + unidades);
            //si da cero
            if (resto == 0) {
                isValid(numHarshad);
                System.out.println("Harshad: " + numHarshad);
                //suma hasta que llegue al numero introducido por teclado
                contador++;
            }
            //suma los harshads
            numHarshad++;
        }
    }

    public static void getSerie(int num1) {
        //harshad + el siguiente harshad
        int numHarshad = 1;
        //comienza en cero
        int contador = 0;
        System.out.println("Serie de Harshads empieza en:" + 1);
        //busca numeros hasta encontrar la cantidad que le he metido por teclado

        while (contador < num1) {
            //hace la validacion
            int decenas = numHarshad / 10;
            int unidades = numHarshad % 10;
            if ((decenas + unidades) == 0) {
                //si pasa la validacion se suma y vuelve a empezar hasta que recolecte los harshad que le he pedido por teclado en la variable num2
                numHarshad++;
                continue;
            }
            //valida que el resto de cero
            int resto = numHarshad % (decenas + unidades);
            //si da cero
            if (resto == 0) {
                isValid(numHarshad);
                System.out.println("Harshad: " + numHarshad);
                //suma hasta que llegue al numero introducido por teclado
                contador++;
            }
            //suma los harshads
            numHarshad++;
        }
    }
}

















/*
    //tercer metodo
    public static void getSerie(int num) {
        int numHarshad = 1;
        Li

        while (numHarshad <= num) {

            isValid(num);

            numHarshad++;
        }

        System.out.println();
    }
*/




/*
//segundo metodo
    public static void getNext(int num) {

        int numHarshad = num;

        while (numHarshad <= 3) {

            System.out.println(numHarshad);

            isValid(num + 1);

            numHarshad++;
        }
    }
 */


        /*
        int numHarshad;

        while(numHarshad){

            isValid(num);
            System.out.println(numHarshad);
            numHarshad++;

        }
    }*/


