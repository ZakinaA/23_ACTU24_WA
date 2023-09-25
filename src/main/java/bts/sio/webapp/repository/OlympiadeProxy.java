package bts.sio.webapp.repository;

import bts.sio.webapp.CustomProperties;
import bts.sio.webapp.model.Athlete;
import bts.sio.webapp.model.Olympiade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OlympiadeProxy {

    @Autowired
    private CustomProperties props;

    /**
     * Get all olympiades
     * @return An iterable of all athlete
     */
    public Iterable<Olympiade> getOlympiades() {

        String baseApiUrl = props.getApiUrl();
        String getOlympiadesUrl = baseApiUrl + "/olympiades";
        System.out.println("url=" + getOlympiadesUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Olympiade>> response = restTemplate.exchange(
                getOlympiadesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Olympiade>>() {}
        );

        log.debug("Get Olympiades call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Olympiade getOlympiade(int id) {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/olympiade/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Olympiade> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Olympiade.class
        );

        log.debug("Get Olympiade call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public void deleteOlympiade(int id) {
        String baseApiUrl = props.getApiUrl();
        String deleteOlympiadeUrl = baseApiUrl + "/olympiade/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteOlympiadeUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Olympiade call " + response.getStatusCode().toString());
    }
}
