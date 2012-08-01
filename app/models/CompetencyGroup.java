package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class CompetencyGroup extends Model {
    public String title;
    public String description;
    
    @OneToMany(mappedBy = "competencyGroup", cascade = CascadeType.ALL)
    public List<Competency> competencies;

    @ManyToOne
    public Competency competency;

    public CompetencyGroup(String title, String description, List<Competency> competencies) {
        super();
        this.title = title;
        this.description = description;
        this.competencies = competencies;
    }
}
