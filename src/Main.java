import api.ConectarApi;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import modelos.Excepciones;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        // Mapeo de opciones del menú a códigos de moneda (se usa para origen y destino)
        Map<Integer, String> monedaOpciones = new HashMap<>();
        monedaOpciones.put(1, "USD");
        monedaOpciones.put(2, "JPY");
        monedaOpciones.put(3, "EUR");
        monedaOpciones.put(4, "AUD");
        monedaOpciones.put(5, "GBP");
        monedaOpciones.put(6, "CLP");

        System.out.println(" ************************************************** ");
        System.out.println(" Bienvenidos a nuestro Conversor de monedas =) ");
        System.out.println(" ¿Que es lo que quieres hacer hoy? ");


        while (!salir) {
            System.out.println("--------------------");
            System.out.println("1. Dolar (USD)      --> A otras monedas ");
            System.out.println("2. Yen Japones (YPY)--> A otras monedas ");
            System.out.println("3.  Euro (EUR)      -->  A otras monedas");
            System.out.println("4. Dolar Australiano(AUD) --> A otras monedas ");
            System.out.println("5. Libra Esterlina (GBP)  --> A otras monedas ");
            System.out.println("6. Peso chileno (CLP)     --> A otras monedas");
            System.out.println("7. Salir");

            System.out.println("Por favor, escribe una de las opciones o un código de moneda (ejemplo: USD): ");

            // Leer la entrada del usuario
            String entrada = sn.next().toUpperCase();

            // Validar si es un número (opción del menú) o un código de moneda
            if (esNumero(entrada)) {
                opcion = Integer.parseInt(entrada);

                if (opcion >= 1 && opcion <= 6) {
                    String monedaOrigen = monedaOpciones.get(opcion);
                    System.out.println("Has seleccionado: " + monedaOrigen);
                    convertirMoneda(monedaOrigen, sn, monedaOpciones);
                } else if (opcion == 7) {
                    System.out.println("¡Gracias por usar el conversor! Hasta pronto.");
                    salir = true;
                } else {
                    System.out.println("Opción inválida. Por favor, elige entre 1 y 7 o escriba la moneda (ej:USD) .");
                }
            } else {
                // El usuario introdujo un código de moneda
                if (monedaOpciones.containsValue(entrada)) {
                    System.out.println("Has seleccionado la moneda: " + entrada);
                    convertirMoneda(entrada, sn, monedaOpciones);
                } else {
                    System.out.println("Código de moneda no válido. Inténtalo nuevamente.");
                }
            }
        }
    }

    // metodo para validar si una entrada es un numero
    private static boolean esNumero(String entrada) {
        try {
            Integer.parseInt(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //metodo para convertir monedas
    private static void convertirMoneda(String monedaOrigen, Scanner sn, Map<Integer, String> monedaOpciones) {
        System.out.println("Elige la moneda de destino (1: USD, 2: JPY, 3: EUR, 4: AUD, 5: GBP, 6: CLP): ");
        int opcionDestino = sn.nextInt();


        System.out.print("Introduce el monto a convertir: ");
        try {
            double cantidad = sn.nextDouble();
            ConectarApi conectarApi = new ConectarApi();

            if (monedaOpciones.containsKey(opcionDestino)) {
                String monedaDestino = monedaOpciones.get(opcionDestino);
                System.out.println("Has seleccionado la moneda de destino: " + monedaDestino);
                System.out.println("Convirtiendo " + cantidad + " " + monedaOrigen + " a " + monedaDestino + "...");

                // Obtener tasas de cambio desde la API (monedaOrigen)
                JsonObject datosJson = conectarApi.obtenerMoneda(monedaOrigen);

                // Comprobar si la respuesta es válida
                if (datosJson != null) {
                    // Obtener el objeto de tasas de conversión
                    JsonElement conversionRatesElement = datosJson.get("conversion_rates");

                    // Verificar que conversionRates es un objeto antes de convertirlo
                    if (conversionRatesElement != null && conversionRatesElement.isJsonObject()) {
                        JsonObject conversionRates = conversionRatesElement.getAsJsonObject();

                        // Verificar si la moneda de destino existe en las tasas de conversión
                        if (conversionRates.has(monedaDestino)) {
                            double tasa = conversionRates.get(monedaDestino).getAsDouble();
                            double resultado = cantidad * tasa;

                            System.out.printf("Resultado: %.2f %s = %.2f %s\n", cantidad, monedaOrigen, resultado, monedaDestino);
                        } else {
                            System.out.println("La moneda de destino no fue encontrada en las tasas de conversión.");
                        }
                    } else {
                        System.out.println("Las tasas de conversión no están disponibles o tienen un formato incorrecto.");
                    }
                } else {
                    System.out.println("Error al obtener las tasas de cambio.");
                }

            } else {
                System.out.println("Opción de moneda de destino no válida.");
            }

        } catch (Exception e) {
            System.out.println("Hubo un error al realizar la conversión: " + e.getMessage());
        }
    }
}