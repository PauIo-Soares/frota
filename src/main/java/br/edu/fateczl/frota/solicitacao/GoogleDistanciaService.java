package br.edu.fateczl.frota.solicitacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GoogleDistanciaService {

    @Value("${google.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";

    public double calcularDistanciaEmKm(String cepOrigem, String cepDestino) {

        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("origins", cepOrigem).queryParam("destinations", cepDestino).queryParam("mode", "driving").queryParam("key", apiKey).toUriString();

        DistanciaMatrixResponse response = restTemplate.getForObject(url, DistanciaMatrixResponse.class);

        if (response == null || response.rows().isEmpty() || response.rows().get(0).elements().isEmpty()) {
            throw new RuntimeException("Erro ao obter distância da API do Google.");
        }

        long distanciaMetros = response.rows().get(0).elements().get(0).distance().value();

        return distanciaMetros / 1000.0;
    }
}