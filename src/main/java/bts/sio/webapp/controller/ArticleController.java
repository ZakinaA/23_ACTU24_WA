package bts.sio.webapp.controller;

import bts.sio.webapp.model.*;
import bts.sio.webapp.service.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @GetMapping("/listeArticle")
    public String listeArticle(Model model) {
        Iterable<Article> listArticles = articleservice.getArticles();

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
        return new ModelAndView("redirect:/listeArticle");
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
        return new ModelAndView("redirect:/listeArticle");
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String filename = file.getOriginalFilename();
                // Sauvegardez le fichier dans le répertoire images
                Path path = Paths.get("images/" + filename);
                Files.write(path, bytes);
                // Mettez à jour le modèle Article avec le nom du fichier
                Article article = new Article();
                article.setNomImage(filename);
                articleservice.saveArticle(article);
                return ResponseEntity.ok("File uploaded successfully.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("File upload failed.");
            }
        } else {
            return ResponseEntity.badRequest().body("File not provided for upload.");
        }
    }

}
