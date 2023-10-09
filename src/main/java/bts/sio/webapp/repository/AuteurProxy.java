package bts.sio.webapp.repository;

import bts.sio.webapp.CustomProperties;
import bts.sio.webapp.model.Athlete;
import bts.sio.webapp.model.Auteur;
import bts.sio.webapp.model.Pays;
import bts.sio.webapp.model.Sport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class AuteurProxy {

    @Autowired
    private CustomProperties props;

    /**
     * Get all auteurs
     * @return An iterable of all athlete
     */

    public void deleteAuteur(int id) {
        String baseApiUrl = props.getApiUrl();
        String deleteAuteurUrl = baseApiUrl + "/auteur/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteAuteurUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Auteur call " + response.getStatusCode().toString());
    }

    public Auteur updateAuteur(Auteur e) {
        String baseApiUrl = props.getApiUrl();
        String updateAuteurUrl = baseApiUrl + "/auteur/" + e.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Auteur> request = new HttpEntity<Auteur>(e);
        ResponseEntity<Auteur> response = restTemplate.exchange(
                updateAuteurUrl,
                HttpMethod.PUT,
                request,
                Auteur.class);

        log.debug("Update Auteur call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Auteur createAuteur(Auteur s) {

        String baseApiUrl = props.getApiUrl();
        String createSportUrl = baseApiUrl + "/auteur";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Auteur> request = new HttpEntity<Auteur>(s);
        ResponseEntity<Auteur> response = restTemplate.exchange(
                createSportUrl,
                HttpMethod.POST,
                request,
                Auteur.class);

        log.debug("Create Auteur call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Iterable<Auteur> getAuteurs() {

        String baseApiUrl = props.getApiUrl();
        String getAuteursUrl = baseApiUrl + "/auteurs";
        System.out.println("url=" + getAuteursUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Auteur>> response = restTemplate.exchange(
                getAuteursUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Auteur>>() {}
        );

        log.debug("Get Auteurs call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Auteur getAuteur(int id) {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/auteur/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Auteur> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Auteur.class
        );

        log.debug("Get Auteur call " + response.getStatusCode().toString());

        return response.getBody();
    }

}
