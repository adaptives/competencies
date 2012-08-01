package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Competency extends Model {
    public String title;
    public String description;

    @OneToMany(mappedBy = "competency", cascade = CascadeType.ALL)
    public List<Topic> topics;

    @ManyToOne
    public CompetencyGroup competencyGroup;

    public Competency(String title, String description, List<Topic> topics, CompetencyGroup competencyGroup) {
        super();
        this.title = title;
        this.description = description;
        this.topics = topics;
        this.competencyGroup = competencyGroup;
    }
}
