package modelos;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        System.out.println(" ************************************************** ");
        System.out.println(" Bienvenidos a nuestro Conversor de monedas =) ");
        System.out.println(" ¿Que es lo que quieres hacer hoy? ");

        while(!salir){
            System.out.println("--------------------");
            System.out.println("1. Dolar --> Peso argentino");
            System.out.println("2. Peso argentino --> Dolar ");
            System.out.println("3. Dolar -->  Peso colombiano");
            System.out.println("4. Peso colombiano --> Dolar");
            System.out.println("5. Dolar --> Peso chileno ");
            System.out.println("6. Peso chileno --> Dolar");


            System.out.println("Porfavor, Escribe una de las opciones");
            opcion = sn.nextInt();

            switch(opcion){
                case 1:
                    System.out.println("Has seleccionado la opcion 1");
                    break;
                case 2:
                    System.out.println("Has seleccionado la opcion 2");
                    break;
                case 3:
                    System.out.println("Has seleccionado la opcion 3");
                    break;
                case 4:
                    salir=true;
                    break;
                default:
                    System.out.println("Solo números entre 1 y 4");
            }

        }

    }

}
