public class Main {
    public static void main(String[] args) {
        //declaracion de la ip en base 256
        String ip = "128.32.10.1";

        //con el split quito los puntos entre los octetos
        String[] octetos = ip.split( "\\." );

        //array de octetos
        int[] ipOctetos = new int[] {
                //128
                Integer.parseInt( octetos[ 0 ] ),
                //32
                Integer.parseInt( octetos[ 1 ] ),
                //10
                Integer.parseInt( octetos[ 2 ] ),
                //1
                Integer.parseInt( octetos[ 3 ] )
        };

        //formula para pasarlos a decimal con potencias de 256
        //uso long en la variable para que no me de problemas con el decimal en positivo o negativo
        long decimal =  ( ipOctetos[ 0 ] * (long)Math.pow( 256, 3 ) ) +
                        ( ipOctetos[ 1 ] * (long)Math.pow( 256, 2 ) ) +
                        ( ipOctetos[ 2 ] * 256 ) +
                        ( ipOctetos[ 3 ] );

        System.out.println("Ip en decimales: " + decimal);


        //metodo para variable int capee el signo negativo
        //System.out.println(Integer.toUnsignedLong(decimal));
        //metodo para pasar los int de decimales a binarios sin bucles ni nada
        //System.out.println(Integer.toBinaryString(decimal));

        String binario = "";
        //bucle con la formula para hacerl binario el decimal que me ha dado las potencias
        while (decimal > 0) {
            long resto = decimal % 2;
            binario = resto + binario;
            decimal = decimal/2;

        }

        System.out.println("Ip en binario: " + binario);

    }
}

//paso el decimal negativo a decimal positivo
//int decimalNegativo = decimal;
//int decimalPositivo = Math.abs(decimalNegativo);

/*

public class Main {
    public static void main(String[] args) {

        //como no entra en el rango de int sale en negativo
        System.out.print("Prueba 1 : ");
        int n1 = (128 << 24) | (32 << 16) | (10 << 8) | 1;
        System.out.println(n1);

        //capeo el overflow con Integer.toUnsignedLong
        int n2 = (128 << 24) | (32 << 16) | (10 << 8) | 1;
        System.out.print("Prueba 2 : ");
        System.out.println(Integer.toUnsignedLong(n2));

        //>>> empuja los bloques hacia la derecha
        //&0xFF garantiza que cada trozo ocupa solo 8 bits sin arrastrar signos
        int a = (n2 >>> 24) &0xFF;
        int b = (n2 >>> 16) &0xFF;
        int c = (n2 >>> 8) &0xFF;
        int d = n2 &0xFF;

        System.out.println("Ip: " + a + "." + b + "." + c + "." + d);

    }
}
 */

/*
//prueba overflow
        byte n0 = (byte) 128;
        System.out.println("prueba 0: "+ n0);
 */