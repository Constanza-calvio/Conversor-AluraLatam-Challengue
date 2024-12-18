package api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public record TestApi() {
    public static void main(String[] args) {
        // Instanciar la clase ConectarApi
        ConectarApi conectarApi = new ConectarApi();

        // La moneda que quieres consultar (por ejemplo, USD)
        String monedaOrigen = "USD";

        try {
            JsonObject datosJson = conectarApi.obtenerMoneda(monedaOrigen);
            // Hacer pretty print de la respuesta JSON
            String prettyJson = new GsonBuilder().setPrettyPrinting().create().toJson(datosJson);

            // Mostrar los datos completos obtenidos de la API
            System.out.println("Respuesta de la API: ");
            System.out.println(prettyJson);

            // También puedes extraer las tasas de conversión si la API devuelve ese dato
            if (datosJson.has("conversion_rates")) {
                JsonObject conversionRates = datosJson.getAsJsonObject("conversion_rates");

                System.out.println("Tasas de conversión: " + conversionRates);
            } else {
                System.out.println("No se encontraron tasas de conversión.");
            }

        } catch (Exception e) {
            // Captura cualquier error
            System.out.println("Error al obtener datos de la API: " + e.getMessage());
        }
    }
}

