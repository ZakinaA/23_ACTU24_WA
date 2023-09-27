package bts.sio.webapp.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Epreuve {

    private Integer id;
    private String nom;
    private String genre;
    private Athlete athlete;
    private Sport sport;
}
