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
public class Topic extends Model {
    public String title;
    public String description;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="topic")
    public Set<CompetencyGroup> competencyGroups;
    
    @OneToMany
    public Set<Topic> prerequisites;
    
    @Lob
    public String resources;

    public Topic(String title, 
    			 String description,
    			 String resources) {
        super();
        this.title = title;
        this.description = description;
        this.resources = resources;
        this.competencyGroups = new TreeSet<CompetencyGroup>();
        this.prerequisites = new TreeSet<Topic>();
    }
}
