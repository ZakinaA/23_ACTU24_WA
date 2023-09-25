package bts.sio.webapp.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Olympiade {

    private Integer id;
    private String numero;
    private String annee;
    private String ville ;
    private Pays pays;

}
