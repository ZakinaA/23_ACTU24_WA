package bts.sio.webapp.service;

import bts.sio.webapp.model.Athlete;
import bts.sio.webapp.model.Pays;
import bts.sio.webapp.model.Sport;
import bts.sio.webapp.repository.AthleteProxy;
import bts.sio.webapp.repository.SportProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Data
@Service
public class SportService {

    @Autowired
    private SportProxy sportProxy;

    public Sport getSport(final int id) {
        return sportProxy.getSport(id);
    }

    public Iterable<Sport> getSports() {
        return sportProxy.getSports();
    }

    public void deleteSport(final int id) {
        sportProxy.deleteSport(id);
    }

    public Sport saveSport(Sport sport) {
        Sport savedSport;

        // Functional rule : Last name must be capitalized.
        sport.setNom(sport.getNom().toUpperCase());

        if(sport.getId() == null) {
            // If id is null, then it is a new employee.
            savedSport = sportProxy.createSport(sport);
        } else {
            savedSport = sportProxy.updateSport(sport);
        }

        return savedSport;
    }

}
