import java.util.Scanner;

public class LenguajeAmistoso {

    public static void main(String[] args) {
        //scanner
        Scanner sc = new Scanner(System.in);
        //lo que me pide ingresar por teclado
        System.out.println("introduce el tiempo: ");
        //alamcena el valor introducido por teclado
        int tiempo = sc.nextInt();

        //no puede ser negativo el numero del scanner menor que 0 (<0)
        if (tiempo < 0) {
            System.out.println("no puede ser negativo");

        } else {
            // Calculo las horas a segundos
            int horas = tiempo / 3600;
            // el sobrante con el módulo % del resto para los minutos
            int resto = tiempo % 3600;
            // calculo los minutos tambien a segundos
            int minutos = resto / 60;
            // y el sobrante con el módulo % para los segundos
            int segundos = resto % 60;

            //variable del resultado de la formula anterior
            String resultado ="";
            //horas mayor que cero sale por pantalla y en plural
            if (horas > 0) {
                resultado = resultado + horas + (horas == 1 ? " hora" : " horas");
            }
            //condicion principal si la variable es mayor que cero
            if (minutos > 0) {
                //isEmpty() indica si una cadena está vacía.
                //es un if anidado que devuelve true si la cadena NO esta vacia-->(!resultado.isEmpty())
                //concateno con and para que si no tengo segundos escriba la Y entre horas y minutos y no la coma
                if (!resultado.isEmpty() && segundos == 0) {
                    resultado = resultado + " y ";
                //en cambio si hay segundos, que me escriba la coma.
                }else if(!resultado.isEmpty()){
                    resultado = resultado + ", ";
                }
                resultado = resultado + minutos + (minutos == 1 ? " minuto" : " minutos");
            }
            //idem
            if (segundos > 0) {
                if (!resultado.isEmpty()) {
                    resultado = resultado + " y ";
                }
                resultado = resultado + segundos + (segundos == 1 ? " segundo" : " segundos");
            }
            //si se metio cero por teclado nos salta el mensaje de "ahora"
            if (resultado.isEmpty()) {
                System.out.println("Ahora");
            } else {
                System.out.println(resultado);
            }
        }
    }
}

/*
if (segundos == 0) {
resultado += " y ";
}else{
resultado += ", ";
}
}
*/


/*
            // Calculo las horas
            int horas = tiempo / 3600;
            if (horas != 0) {
                System.out.println("hora " + horas);
                } else {
            }
            // el sobrante con el modulo % del resto para los minutos
            int Resto = tiempo % 3600;
            // calculo los minutos
            int minutos = Resto / 60;
            if (minutos != 0) {
                System.out.println("minutos " + minutos);
            } else {
            }
            // y el sobrante con el modulo para los segundos
            int segundos = Resto % 60;
            if (segundos != 0) {
                System.out.println("segundos " + segundos);
*/
