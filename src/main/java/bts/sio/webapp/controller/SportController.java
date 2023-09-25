package bts.sio.webapp.controller;

import bts.sio.webapp.model.Athlete;
import bts.sio.webapp.model.Pays;
import bts.sio.webapp.model.Sport;
import bts.sio.webapp.service.AthleteService;
import bts.sio.webapp.service.OlympiadeService;
import bts.sio.webapp.service.PaysService;
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
public class SportController {


    @Autowired
    private SportService sportService;

    @Autowired
    private PaysService paysService;


    @GetMapping("/createSport")
    public String createSport(Model model) {
        Sport a = new Sport();
        model.addAttribute("sport", a);

        Iterable<Pays> listPays = paysService.getLesPays();
        model.addAttribute("listPays", listPays);

        return "athlete/formNewAthlete";
    }

    @GetMapping("/deleteSport/{id}")
    public ModelAndView deleteSport(@PathVariable("id") final int id) {
        sportService.deleteSport(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveSport")
    public ModelAndView saveSport(@ModelAttribute Sport sport) {
        System.out.println("controller save=" + sport.getNom());
        if(sport.getId() != null) {
            Sport current = sportService.getSport(sport.getId());
            sport.setNom(current.getNom());
        }
        sportService.saveSport(sport);
        return new ModelAndView("redirect:/");
    }
}
