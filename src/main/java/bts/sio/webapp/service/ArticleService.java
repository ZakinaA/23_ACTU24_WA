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

        if(article.getId() == null) {
            // Si l'ID est vide, créé un nouvel employé
            savedArticle = articleProxy.createArticle(article);
        } else {
            // Sinon, mets à jour l'ID les données de l'ID existante
            savedArticle = articleProxy.updateArticle(article);
        }

        return savedArticle;
    }

}
