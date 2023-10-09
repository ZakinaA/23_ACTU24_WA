package bts.sio.webapp.service;

import bts.sio.webapp.model.Athlete;
import bts.sio.webapp.model.Auteur;
import bts.sio.webapp.model.Pays;
import bts.sio.webapp.model.Sport;
import bts.sio.webapp.repository.AthleteProxy;
import bts.sio.webapp.repository.AuteurProxy;
import bts.sio.webapp.repository.SportProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AuteurService {

    @Autowired
    private AuteurProxy auteurProxy;

    public Auteur getAuteur(final int id) {
        return auteurProxy.getAuteur(id);
    }

    public Iterable<Auteur> getAuteurs() {
        return auteurProxy.getAuteurs();
    }

    public void deleteAuteur(final int id) {
        auteurProxy.deleteAuteur(id);
    }

    public Auteur saveAuteur(Auteur auteur) {
        Auteur savedAuteur;

        // Functional rule : Last name must be capitalized.
        auteur.setNom(auteur.getNom().toUpperCase());

        if(auteur.getId() == null) {
            // If id is null, then it is a new employee.
            savedAuteur = auteurProxy.createAuteur(auteur);
        } else {
            savedAuteur = auteurProxy.updateAuteur(auteur);
        }

        return savedAuteur;
    }
}
