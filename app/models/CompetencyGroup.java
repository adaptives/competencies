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
public class CompetencyGroup extends Model {
    public String title;
    public String description;
    
    @ManyToOne
    public Topic topic;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competencyGroup")
    public Set<Competency> competencies;
    
    //TODO: If a CompetencyGroup has been specified as a prerequsite for this
    //CompetencyGroup, then the preerquisite must belong to the same topic
    @OneToMany
    public Set<CompetencyGroup> prereqisites;
    
    @Lob
    public String resources;

    public CompetencyGroup(String title, 
    					   String description,
    					   String resources) {
        super();
        this.title = title;
        this.description = description;
        this.resources = resources;
        this.competencies = new TreeSet<Competency>();
        this.prereqisites = new TreeSet<CompetencyGroup>();
    }
}
