package models;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Competency extends Model {
    public String title;
    public String description;

    @ManyToOne
    public CompetencyGroup competencyGroup;
    
    //TODO: If a Competency has been specified as a prerequsite for this
    //Competency, then the preerquisite must belong to the same CompetencyGroup
    @OneToMany
    public Set<Competency> prerequisites;
    
    @Lob
    public String resources;

    public Competency(String title, 
    				  String description,  
    				  CompetencyGroup competencyGroup,
    				  String resources) {
        super();
        this.title = title;
        this.description = description;
        this.competencyGroup = competencyGroup;
        this.resources = resources;
        this.prerequisites = new TreeSet<Competency>();
    }
}
