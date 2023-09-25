package bts.sio.webapp.service;

import bts.sio.webapp.model.Olympiade;
import bts.sio.webapp.repository.OlympiadeProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class OlympiadeService {

    @Autowired
    private OlympiadeProxy olympiadeProxy;

    public Olympiade getOlympiade(final int id) {
        return olympiadeProxy.getOlympiade(id);
    }

    public Iterable<Olympiade> getOlympiades() {
        return olympiadeProxy.getOlympiades();
    }

    public void deleteOlympiade(final int id) {
        olympiadeProxy.deleteOlympiade(id);
    }
}
