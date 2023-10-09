package bts.sio.webapp.controller;

import bts.sio.webapp.model.*;
import bts.sio.webapp.service.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class ArticleController {

    @Autowired
    private AthleteService athleteservice;

    @Autowired
    private ArticleService articleservice;

    @Autowired
    private EpreuveService epreuveservice;

    @Autowired
    private SportService sportService;

    @Autowired
    private PaysService paysService;

    @Autowired
    private AuteurService auteurService;

    @GetMapping("/homeArticle")
    public String homeArticle(Model model) {
        Iterable<Article> listArticles = articleservice.getArticles();
        model.addAttribute("articles", listArticles);
        return "/article/homeArticle";
    }

    @GetMapping("/ConsulterArticle/{id}")
    public String consulterArticle(@PathVariable("id") final int id, Model model) {
        Article article = articleservice.getArticle(id);
        model.addAttribute("article", article);
        return "article/consulterArticle";
    }

    @GetMapping("/createArticle")
    public String createArticle(Model model) {
        Article a = new Article();
        model.addAttribute("article", a);

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

    @PostMapping("/saveArticle")
    public ModelAndView saveArticle(@ModelAttribute Article article) {
        System.out.println("controller save=" + article.getTitre());
        if(article.getId() != null) {
            Article current = articleservice.getArticle(article.getId());
            article.setTitre(current.getTitre());
        }
        articleservice.saveArticle(article);
        return new ModelAndView("redirect:/homeArticle");
    }

    @GetMapping("/updateArticle/{id}")
    public String updateArticle(@PathVariable("id") final int id, Model model) {
        Article a = articleservice.getArticle(id);
        model.addAttribute("article", a);

        Iterable<Athlete> listAthlete = athleteservice.getAthletes();
        model.addAttribute("listAthlete", listAthlete);

        Iterable<Pays> listPays = paysService.getLesPays();
        model.addAttribute("listPays", listPays);

        Iterable<Auteur> listAuteur = auteurService.getAuteurs();
        model.addAttribute("listAuteur", listAuteur);

        return "article/formUpdateArticle";
    }

    @GetMapping("/deleteArticle/{id}")
    public ModelAndView deleteArticle(@PathVariable("id") final int id) {
        articleservice.deleteArticle(id);
        return new ModelAndView("redirect:/homeArticle");
    }

}
