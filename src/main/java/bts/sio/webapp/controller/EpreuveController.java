package bts.sio.webapp.controller;

import bts.sio.webapp.model.Epreuve;
import bts.sio.webapp.service.AthleteService;
import bts.sio.webapp.service.EpreuveService;
import bts.sio.webapp.service.SportService;
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
public class EpreuveController {


    @Autowired
    private AthleteService athleteservice;

    @Autowired
    private EpreuveService epreuveservice;

    @Autowired
    private SportService sportService;



    @GetMapping("/listerEpreuve")
    public String listerEpreuve(Model model) {
        Iterable<Epreuve> listEpreuves = epreuveservice.getEpreuves();
        model.addAttribute("epreuves", listEpreuves);
        return "/epreuve/listerEpreuve";
    }

    @GetMapping("/ConsulterEpreuve/{id}")
    public String consulterEpreuve(@PathVariable("id") final int id, Model model) {
        Epreuve epreuve = epreuveservice.getEpreuve(id);
        model.addAttribute("epreuve", epreuve);
        return "epreuve/consulterEpreuve";
    }


    @GetMapping("/createEpreuve")
    public String createEpreuve(Model model) {
        Epreuve a = new Epreuve();
        model.addAttribute("epreuve", a);

        return "epreuve/formNewEpreuve";
    }

    @GetMapping("/updateEpreuve/{id}")
    public String updateEpreuve(@PathVariable("id") final int id, Model model) {
        Epreuve a = epreuveservice.getEpreuve(id);
        model.addAttribute("epreuve", a);

        return "epreuve/formUpdateEpreuve";
    }

    @GetMapping("/deleteEpreuve/{id}")
    public ModelAndView deleteEpreuve(@PathVariable("id") final int id) {
        epreuveservice.deleteEpreuve(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveEpreuve")
    public ModelAndView saveEpreuve(@ModelAttribute Epreuve epreuve) {
        System.out.println("controller save=" + epreuve.getNom());
        if(epreuve.getId() != null) {
            Epreuve current = epreuveservice.getEpreuve(epreuve.getId());
            epreuve.setNom(current.getNom());
        }
        epreuveservice.saveEpreuve(epreuve);
        return new ModelAndView("redirect:/");
    }
}
