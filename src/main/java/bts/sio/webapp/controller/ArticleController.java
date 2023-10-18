package bts.sio.webapp.controller;

import bts.sio.webapp.model.*;
import bts.sio.webapp.service.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

    @GetMapping("/listeArticles")
    public String listeArticles(Model model) {
        Iterable<Article> listArticles = articleService.getArticles();

        List<Article> articles = new ArrayList<>();
        listArticles.forEach(articles::add);

        articles.sort(Comparator.comparing(Article::getDate)
                .thenComparing(Article::getHeure)
                .reversed());

        model.addAttribute("articles", articles);
        return "article/listeArticles";

    }

    @GetMapping("/filtreArticle")
    public String listeArticles(@RequestParam("athleteId") Long athleteId, Model model) {
        Iterable<Article> listArticles = articleService.getArticles();

        List<Article> articles = new ArrayList<>();
        listArticles.forEach(articles::add);

        // Filtrer les articles en fonction de l'ID de l'athlète (convert Long to Integer)
        articles = articles.stream()
                .filter(article -> article.getAthlete().getId().intValue() == athleteId.intValue())
                .sorted(Comparator.comparing(Article::getDate)
                        .thenComparing(Article::getHeure)
                        .reversed())
                .collect(Collectors.toList());

        model.addAttribute("articles", articles);
        return "article/listeArticles";
    }


    @GetMapping("/articles/chercher")
    public String chercherArticle(@RequestParam("motCle") String motCle, Model model) {
        List<Article> articlesTrouves = articleService.chercherArticlesParMotCle(motCle);
        model.addAttribute("articles", articlesTrouves);
        return "article/chercherArticle"; // Nom de la vue pour afficher les résultats
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
        return new ModelAndView("redirect:/listeArticles");
    }

    @PostMapping("/saveArticle")
    public ModelAndView saveArticle(@ModelAttribute Article article) {
        if (article.getId() != null) {
            Article current = articleService.getArticle(article.getId());
            current.setTitre(article.getTitre());
        }
        articleService.saveArticle(article);
        return new ModelAndView("redirect:/listeArticles");
    }


    @GetMapping("/updateArticle/{id}")
    public String updateArticle(@PathVariable("id") final int id, Model model) {

        System.out.println("La date est " + articleService.getArticle(id).getDate());
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

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/images/article";

    @GetMapping("/uploadimage") public String displayUploadForm() {
        return "redirect:/listeArticles";
    }

    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "redirect:/listeArticles";
    }
}
