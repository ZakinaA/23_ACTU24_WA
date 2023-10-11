package bts.sio.webapp.service;

import bts.sio.webapp.model.Article;
import bts.sio.webapp.model.Epreuve;
import bts.sio.webapp.repository.ArticleProxy;
import bts.sio.webapp.repository.EpreuveProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        article.setTitre(article.getTitre().toUpperCase());
        article.setSoustitre(article.getTitre());
        article.setContenu(article.getContenu());
        article.setImage(article.getImage());
        article.setDate(article.getDate());
        article.setHeure(article.getHeure());
        article.setAthlete(article.getAthlete());
        article.setPays(article.getPays());
        article.setAuteur(article.getAuteur());

        if(article.getId() == null) {
            // If id is null, then it is a new employee.
            savedArticle = articleProxy.createArticle(article);
        } else {
            savedArticle = articleProxy.updateArticle(article);
        }

        return savedArticle;
    }

}
