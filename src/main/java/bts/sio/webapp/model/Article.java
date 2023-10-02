package bts.sio.webapp.model;

import lombok.Data;


@Data
public class Article {

    private Integer id;

    private String titre;

    private String contenu;

    private String auteur;

    private Athlete athlete;

}
