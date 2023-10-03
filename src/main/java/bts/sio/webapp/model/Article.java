package bts.sio.webapp.model;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class Article {

    private Integer id;

    private String titre;

    private String soustitre;

    private String contenu;

    private String image;

    private LocalDate date;

    private LocalTime heure;

    private String auteur;

    private Athlete athlete;

}
