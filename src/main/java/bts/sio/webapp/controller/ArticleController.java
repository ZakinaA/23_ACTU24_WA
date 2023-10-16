package bts.sio.webapp.controller;

import bts.sio.webapp.model.*;
import bts.sio.webapp.service.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Data
@Controller
public class ArticleController {

    @Autowired
    private AthleteService athleteservice;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EpreuveService epreuveservice;

    @Autowired
    private SportService sportService;

    @Autowired
    private PaysService paysService;

    @Autowired
    private AuteurService auteurService;

    @GetMapping("/listeArticle")
    public String listeArticle(Model model) {
        Iterable<Article> listArticles = articleService.getArticles();

        List<Article> articles = new ArrayList<>();
        listArticles.forEach(articles::add);

        articles.sort(Comparator.comparing(Article::getDate)
                .thenComparing(Article::getHeure)
                .reversed());

        model.addAttribute("articles", articles);
        return "/article/listeArticle";

    }

    @GetMapping("/consulterArticle/{id}")
    public String consulterArticle(@PathVariable("id") final int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article/consulterArticle";
    }

    @GetMapping("/createArticle")
    public String createArticle(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);

        Iterable<Athlete> listAthlete = athleteservice.getAthletes();
        model.addAttribute("listAthlete", listAthlete);

        Iterable<Sport> listSport = sportService.getSports();
        model.addAttribute("listSport", listSport);

        Iterable<Pays> listPays = paysService.getLesPays();
        model.addAttribute("listPays", listPays);

        Iterable<Auteur> listAuteur = auteurService.getAuteurs();
        model.addAttribute("listAuteur", listAuteur);

        return "article/formNewArticle";
    }

    @GetMapping("/deleteArticle/{id}")
    public ModelAndView deleteArticle(@PathVariable("id") final int id) {
        articleService.deleteArticle(id);
        return new ModelAndView("redirect:/listeArticle");
    }

    /**@PostMapping("/saveArticle")
    public ModelAndView saveArticle(@ModelAttribute Article article) {
        System.out.println("controller save=" + article.getTitre());

        if (article.getId() != null) {
            Article current = articleService.getArticle(article.getId());
        }

        articleService.saveArticle(article);
        return new ModelAndView("redirect:/listeArticle");
    }


    @GetMapping("/updateArticle/{id}")
    public String updateArticle(@PathVariable("id") final int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);

        Iterable<Athlete> listAthlete = athleteservice.getAthletes();
        model.addAttribute("listAthlete", listAthlete);

        Iterable<Sport> listSport = sportService.getSports();
        model.addAttribute("listSport", listSport);

        Iterable<Pays> listPays = paysService.getLesPays();
        model.addAttribute("listPays", listPays);

        Iterable<Auteur> listAuteur = auteurService.getAuteurs();
        model.addAttribute("listAuteur", listAuteur);

        return "article/formUpdateArticle";
    }*/

    /**@PostMapping("/saveArticle")
    public ModelAndView saveArticle(@ModelAttribute Article article) {
        System.out.println("controller save=" + article.getTitre());
        if(article.getId() != null) {
            Article current = articleService.getArticle(article.getId());
        }
        articleService.saveArticle(article);
        return new ModelAndView("redirect:/listeArticle");
    }*/

    @PostMapping("/saveArticle")
    public ModelAndView saveArticle(@ModelAttribute Article article) {
        System.out.println("Controller save article=" + article.getTitre());
        System.out.println("Controller - L'ID est " + article.getId());
        if (article.getId() != null) {
            Article current = articleService.getArticle(article.getId());
            current.setTitre(article.getTitre());
        }
        System.out.println(("test1"));
        System.out.println("Controller 2 - L'ID est " + article.getId());
        articleService.saveArticle(article);
        System.out.println(("test2"));
        return new ModelAndView("redirect:/listeArticle");
    }


    @GetMapping("/updateArticle/{id}")
    public String updateArticle(@PathVariable("id") final int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);

        Iterable<Athlete> listAthlete = athleteservice.getAthletes();
        model.addAttribute("listAthlete", listAthlete);

        Iterable<Pays> listPays = paysService.getLesPays();
        model.addAttribute("listPays", listPays);

        Iterable<Auteur> listAuteur = auteurService.getAuteurs();
        model.addAttribute("listAuteur", listAuteur);

        return "article/formUpdateArticle";
    }

}
