package cr.ac.tec.ecohydro.servicio;
// aca tambien los chunches de la api q hay q buscar

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServicioClima {
    private String ubicacion;
    private HttpClient cliente = HttpClient.newHttpClient();

    public double[] consultarClima(String ubicacion){

        ubicacion = URLEncoder.encode(ubicacion, StandardCharsets.UTF_8).replace("+", "%20");

        // saca la latitude y longitude de la api de geocoding
        String urlGeocoding = "https://geocoding-api.open-meteo.com/v1/search?name=" + ubicacion + "&count=1&language=es";

        double latitude = 0.0;
        double longitude = 0.0;

        HttpRequest requestGeocoding = HttpRequest.newBuilder()
            .uri(URI.create(urlGeocoding))
            .GET()
            .build();

        try {
        HttpResponse<String> respuestaGeocoding = cliente.send(requestGeocoding, HttpResponse.BodyHandlers.ofString());
        
        JSONObject jsonRespuestaGeocoding = new JSONObject(respuestaGeocoding.body());
            JSONArray resultados = jsonRespuestaGeocoding.getJSONArray("results");
            JSONObject resultado = resultados.getJSONObject(0);
            latitude = resultado.getDouble("latitude");
            longitude = resultado.getDouble("longitude");
        } catch (IOException | InterruptedException | JSONException e) {
            System.out.println("Error al consultar el clima: " + e.getMessage());
            return null;
        }

        // saca la temperatura y humedad de la api de forecast
        try {
        String urlForecast = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m&timezone=auto";

        HttpRequest requestForecast = HttpRequest.newBuilder()
            .uri(URI.create(urlForecast))
            .GET()
            .build();

        HttpResponse<String> respuestaForecast = cliente.send(requestForecast, HttpResponse.BodyHandlers.ofString());
            
            JSONObject jsonRespuestaForecast = new JSONObject(respuestaForecast.body());
            JSONObject pronostico = jsonRespuestaForecast.getJSONObject("current");

            double temperatura = pronostico.getDouble("temperature_2m");
            double humedad = pronostico.getDouble("relative_humidity_2m");

            return new double[]{temperatura, humedad};
        } catch (IOException | InterruptedException | JSONException e) {
            System.out.println("Error al consultar el clima: " + e.getMessage());
            return null;
        }
    }

  

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    
}
