package bts.sio.webapp.service;

import bts.sio.webapp.model.Article;
import bts.sio.webapp.model.Epreuve;
import bts.sio.webapp.repository.ArticleProxy;
import bts.sio.webapp.repository.EpreuveProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@Service
public class ArticleService {

    @Autowired
    private ArticleProxy articleProxy;

    public Article getArticle(final int id) {
        return articleProxy.getArticle(id);
    }

    public Iterable<Article> getArticles() {
        return articleProxy.getArticles();
    }

    public void deleteArticle(final int id) {
        articleProxy.deleteArticle(id);
    }

    public Article saveArticle(Article article) {
        Article savedArticle;

        // Functional rule : Last name must be capitalized.
        article.setTitre(article.getTitre().toUpperCase());

        if(article.getId() == null) {
            // If id is null, then it is a new employee.
            savedArticle = articleProxy.createArticle(article);
        } else {
            savedArticle = articleProxy.updateArticle(article);
        }

        return savedArticle;
    }


    public List<Article> chercherArticlesParMotCle(String motCle) {
        Iterable<Article> articles = getArticles(); // Récupérer les articles via articleProxy

        List<Article> articlesTrouves = new ArrayList<>();

        for (Article article : articles) {
            if (article.getTitre().toLowerCase().contains(motCle.toLowerCase()) ||
                    article.getPays().getNom().toLowerCase().contains(motCle.toLowerCase()) ||
                    article.getAthlete().getNom().toLowerCase().contains(motCle.toLowerCase()) ||
                    article.getAthlete().getSport().getNom().toLowerCase().contains(motCle.toLowerCase())) {
                articlesTrouves.add(article);
            }
        }

        // Triez les articles par date et heure en utilisant le comparateur
        articlesTrouves.sort(Comparator.comparing(Article::getDate)
                .thenComparing(Article::getHeure)
                .reversed());

        return articlesTrouves;
    }
}
