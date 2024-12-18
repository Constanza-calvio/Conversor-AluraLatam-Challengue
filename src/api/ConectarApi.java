package api;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import modelos.Excepciones;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ConectarApi{
    private static final String Api_Key = "4aa01cf7876da051c8e1d0bf";
    private static final String Api_Url = "https://v6.exchangerate-api.com/v6/";

    //conexion con la api

    public JsonObject obtenerMoneda(String monedaOrigen) throws Excepciones {
        try {
            String apiUrl = Api_Url + Api_Key + "/latest/" + monedaOrigen;
            URI direccion = URI.create(apiUrl);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Convertir la respuesta a JsonObject para facilitar su uso
            return JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (IOException | InterruptedException e) {
            throw new Excepciones("Error al realizar la solicitud a la API", e);
        }

    }

    @SerializedName("base_code") // Corresponde a "base_code" en el JSON
    private String baseCode;

    @SerializedName("conversion_rates") // Corresponde a "conversion_rates" en el JSON
    private Map<String, Double> conversionRates;


   //METODO PARA TENER TASA DE CONVERSION DE MONEDAS

    public double obtenerTasaConversion(String monedaOrigen, String monedaDestino) throws Excepciones {
        JsonObject respuestaJson = obtenerMoneda(monedaOrigen);

        // Verificar si la respuesta contiene la clave "conversion_rates"
        if (respuestaJson.has("conversion_rates")) {
            JsonObject conversionRatesObj = respuestaJson.getAsJsonObject("conversion_rates");

            // Verificar si existe la clave de la moneda destino
            if (conversionRatesObj.has(monedaDestino)) {
                return conversionRatesObj.get(monedaDestino).getAsDouble();
            } else {
                throw new Excepciones("Moneda de destino no encontrada en las tasas de conversión.");
            }
        } else {
            throw new Excepciones("No se pudo obtener las tasas de conversión.");
        }
    }

    // Getters
    public String getBaseCode() {
        return baseCode;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }
}
