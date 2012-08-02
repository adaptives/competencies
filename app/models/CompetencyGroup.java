package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class CompetencyGroup extends Model {
    
	//TODO: Add database constraint to ensure that this cannot be set to null
	@Required
	public String title;
    
	public String description;
     
	public Integer placement;
    
    @ManyToOne
    public Topic topic;
    
    //TODO: order by placement
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
        this.placement = 0;
        this.competencies = new HashSet<Competency>();
        this.prereqisites = new HashSet<CompetencyGroup>();        
    }
}
