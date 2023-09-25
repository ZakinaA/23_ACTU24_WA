package bts.sio.webapp.model;
import lombok.Data;

import java.util.ArrayList;
@Data
public class Sport {
    private Integer id;
    private String nom;
    private String descriptif;
    private ArrayList<Sport> lesSports ;
}
