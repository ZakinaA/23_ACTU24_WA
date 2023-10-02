package bts.sio.webapp.repository;

import bts.sio.webapp.CustomProperties;
import bts.sio.webapp.model.Article;
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
public class ArticleProxy {

    @Autowired
    private CustomProperties props;

    public Iterable<Article> getArticles() {

        String baseApiUrl = props.getApiUrl();
        String getArticlesUrl = baseApiUrl + "/articles";
        System.out.println("url=" + getArticlesUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Article>> response = restTemplate.exchange(
                getArticlesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Article>>() {}
        );

        log.debug("Get Articles call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Article getArticle(int id) {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/article/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Article> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Article.class
        );

        log.debug("Get Article call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Article createArticle(Article a) {

        String baseApiUrl = props.getApiUrl();
        String createArticleUrl = baseApiUrl + "/article";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Article> request = new HttpEntity<Article>(a);
        ResponseEntity<Article> response = restTemplate.exchange(
                createArticleUrl,
                HttpMethod.POST,
                request,
                Article.class);

        log.debug("Create Article call " + response.getStatusCode().toString());

        return response.getBody();
    }


    public Article updateArticle(Article e) {
        String baseApiUrl = props.getApiUrl();
        String updateArticleUrl = baseApiUrl + "/article/" + e.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Article> request = new HttpEntity<Article>(e);
        ResponseEntity<Article> response = restTemplate.exchange(
                updateArticleUrl,
                HttpMethod.PUT,
                request,
                Article.class);

        log.debug("Update Article call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public void deleteArticle(int id) {
        String baseApiUrl = props.getApiUrl();
        String deleteArticleUrl = baseApiUrl + "/article/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteArticleUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Article call " + response.getStatusCode().toString());
    }

}
