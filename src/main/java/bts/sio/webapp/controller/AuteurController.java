package bts.sio.webapp.controller;

import bts.sio.webapp.model.*;
import bts.sio.webapp.service.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class AuteurController {


    @Autowired
    private AuteurService auteurService;

    @Autowired
    private PaysService paysService;

    @GetMapping("/homeAuteur")
    public String homeAuteur(Model model) {
        Iterable<Auteur> listAuteurs = auteurService.getAuteurs();
        model.addAttribute("auteurs", listAuteurs);
        return "/auteur/homeAuteur";
    }


    @GetMapping("/createAuteur")
    public String createAuteur(Model model) {
        Auteur a = new Auteur();
        model.addAttribute("auteur", a);

        return "auteur/formNewAuteur";
    }

    @GetMapping("/deleteAuteur/{id}")
    public ModelAndView deleteAuteur(@PathVariable("id") final int id) {
        auteurService.deleteAuteur(id);
        return new ModelAndView("redirect:/homeAuteur");
    }

    @PostMapping("/saveAuteur")
    public ModelAndView saveAuteur(@ModelAttribute Auteur auteur) {
        System.out.println("controller save=" + auteur.getNom());
        if(auteur.getId() != null) {
            Auteur current = auteurService.getAuteur(auteur.getId());
        }
        auteurService.saveAuteur(auteur);
        return new ModelAndView("redirect:/homeAuteur");
    }

    @GetMapping("/updateAuteur/{id}")
    public String updateAuteur(@PathVariable("id") final int id, Model model) {
        Auteur a = auteurService.getAuteur(id);
        model.addAttribute("auteur", a);

        return "auteur/formUpdateAuteur";
    }

}
