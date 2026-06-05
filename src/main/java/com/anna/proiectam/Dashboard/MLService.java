package com.anna.proiectam.Dashboard;

import com.anna.proiectam.FactoriZilnici.FactoriZilnici;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class MLService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FLASK_URL = "http://127.0.0.1:5000/predictie";

    public record PredictieML(double scor, String tipZi) {}

    public PredictieML getPredictie(FactoriZilnici factori) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("ore_somn", factori.getOreSomn());
            body.put("minute_somn", factori.getMinuteSomn());
            body.put("stres", factori.getStres());
            body.put("activitate_fizica", factori.getActivitateFizica());
            body.put("activitate_sociala", factori.getActivitateSociala());
            body.put("mancare", factori.getMancare());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    FLASK_URL, request, Map.class);

            Map<String, Object> result = response.getBody();
            double scor = ((Number) result.get("scor")).doubleValue();
            String tipZi = (String) result.get("tip_zi");

            return new PredictieML(scor, tipZi);

        } catch (Exception e) {
            // Daca Flask nu e pornit, returnam null
            return null;
        }
    }
}