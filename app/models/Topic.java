package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Topic extends Model {
    public String title;
    public String description;

    @ManyToOne
    public Competency competency;

    public Topic(String title, String description, Competency competency) {
        super();
        this.title = title;
        this.description = description;
        this.competency = competency;
    }
}
