package utilidades;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Entrada {

    private static final Scanner entrada = new Scanner(System.in);

    public static int ingresarEntero(int min, int max)
    {
        int num = 0;
        boolean error = false;
        do{
            error = false;
            try{
                num = entrada.nextInt();
                if(num < min || num > max)
                {
                    System.err.println("ERROR. El valor ingresado debe estar entre " + min + " y " + max);
                    error = true;
                }
            } catch (InputMismatchException e) {
                System.err.println("ERROR. El valor ingresado debe ser un numero entero");
                error = true;
            } finally {
                entrada.nextLine();
                if(error)
                {
                    System.out.print("Ingrese otro valor: ");
                }
            }
        }while(error);
        return num;
    }

    public static String ingresarTexto()
    {
        return entrada.nextLine();
    }
}
