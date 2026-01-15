public class encrypt {

    //metodoA descompone el numero en pares e impares
    public static String metodoA(String cadenaCifrada) {
        //la convierto en un array numerico
        char[] cadenaNum = cadenaCifrada.toCharArray();
        //variables vacias
        String pares = "";
        String impares = "";
        String n = "";
        //bucle que recorre la cadenaCifrada
        //el length automatiza la largura de la cadenaCifrada que se mete por teclado
        for (int i = 0; i < cadenaNum.length; i++) {
            //character.isDigit busca numeros
            if (Character.isDigit(cadenaNum[i])) {
                n = n + cadenaNum[i];
            }
        }

        //bucle para hallar los pares e impares
        for (int x = 0; x < cadenaNum.length; x++) {
            //entre par y de resto 0 es la condicion
            if (x % 2 == 0) {
                pares = pares + cadenaNum[x];
                //si no cumple, es impar
            } else {
                impares = impares + cadenaNum[x];
            }
        }
        //orden de salida,
        //se puede cambiar y que salgan los pares antes y a correr
        String resultadoMetodoA = impares + pares;
        return resultadoMetodoA;


    }

    //metodoB que repite el cifrado x veces
    public static String metodoB(String cadenasNuevas, int numRepeticiones) {
        //la condicion es que sea un numero entero/positivo
        if (numRepeticiones <= 0) {
            //devuelve la cadenasNuevas
            return cadenasNuevas;
        }
        //asigna valor a la variable
        String resultadoMetodoB = cadenasNuevas;
        //el resultadoMetodoB lo mete en el bucle hasta cumplir las x veces de repeticion
        for (int i = 0; i < numRepeticiones; i++) {
            resultadoMetodoB = metodoA(resultadoMetodoB);
            //despues de iterar en el metodoA
            //esta línea printa el numero de repeticiones sumando 1 a i para que no comience en 0
            //y sacael valor de la cadena una vez añadido el metodoA
            System.out.println("repeticion " + (i + 1) + ": " + resultadoMetodoB);
        }
        return resultadoMetodoB;
    }

    public static String metodoC(String cadenaDescifrada, int repeticiones) {
        //asigno valor a la variable resultadoC con la cadena resultante
        String resultadoMetodoC = cadenaDescifrada;

        for (int x = 0; x < repeticiones; x++) {
            //la variable de numero cifrado length para evitar limite de digios
            int numeroCifrado = resultadoMetodoC.length();
            int mitad = numeroCifrado / 2;
            String impares = resultadoMetodoC.substring(0, mitad);
            String pares = resultadoMetodoC.substring(mitad);
            String resultado = "";

            //empieza contando desde 0
            //revisa que i es mas pequeño que el numero de letras pares
            //al final suma 1 a la i
            for (int i = 0; i < pares.length(); i++) {
                //si quedan letras en la lista impares
                if (impares.length() >= i + 1) {
                    //si es si cada letra toma su posicion en su lista correspondiente
                    //y las suma a la lista resultado
                    //el i,i+1 coge del codigo la posicion y se la suma a pares y a impares
                    resultado = resultado + pares.substring(i, i + 1) + impares.substring(i, i + 1);
                    //si la lista impares ya no tiene mas letras en esa posicion, la añade a pares
                } else {
                    //pares.substring(i) lo que hace es que si una cadena es mas larga que la otra la suma a pares
                    resultado = resultado + pares.substring(i);
                }
            }

            System.out.println(resultado);
            resultadoMetodoC = resultado;
        }
        return resultadoMetodoC;
    }
}