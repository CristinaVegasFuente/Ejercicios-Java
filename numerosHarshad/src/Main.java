import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //instancio el scanner
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un numero: ");
        //almaceno el valor en la variable num
        int num = sc.nextInt();

        //valido que sea un numero positivo
        if (num > 0) {
            System.out.println("Numero: " + num);
            // Llama al metodo que esta en la clase Harshad
            //se importan automaticamente con el nombre de la clase
            if (!Harshad.isValid(num)) {
                System.out.println("No es harshad");
            } else {
                System.out.println("Es harshad");
            }
            Harshad.getNext(num);
            System.out.println("Introduce un numero: ");
            int num1 = sc.nextInt();
            //segundo parametro
            System.out.println("cuantos Harshad: ");
            int num2 = sc.nextInt();
            Harshad.getSerie(num1, num2);

            System.out.println("cuantos Harshad a partir de 1: ");
            num1 = sc.nextInt();
            Harshad.getSerie(num1);
        } else {
            System.out.println("Debe ser positivo");
        }

    }
}





/*
//si el numero es 18
//1 primer digito de 18
int x=1;
//8 segundo digito de 18
int y=8;
//booleano? //18 numero completo
int n = 18 % (x+y)== 0;
*/

/*
//bucle for para que itere sobre el numero harshad y me de mas
        for (int numHarshad = 0; numHarshad > numHarshad; numHarshad++){
            System.out.println(numHarshad);
        }
 */