package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Topic extends Model implements Comparable {
    
	@Required
	@Column(nullable=false)
	public String title;
    
	@Lob
	public String description;
    
	public Integer placement;

    //TODO: order by placement
    @OneToMany(cascade=CascadeType.ALL, mappedBy="topic")
    public Set<CompetencyGroup> competencyGroups;
    
    @OneToMany
    public Set<Topic> prerequisites;
    
    @Lob
    public String resources;
    
    //TODO: Set database constraints to ensure that this is not nullable
    //@Column(nullable=false) does not seem to work
    //@Required
    @OneToMany    
    public Set<Level> levels;

    public Topic(String title, 
    			 String description,
    			 String resources) {
        super();
        this.title = title;
        this.description = description;
        this.resources = resources;
        this.placement = 0;
        this.competencyGroups = new HashSet<CompetencyGroup>();
        this.prerequisites = new HashSet<Topic>();
        this.levels = new HashSet<Level>();
    }

	@Override
	public int compareTo(Object o) {
		Topic other = (Topic)o;
		return this.placement - other.placement;
	}
    
    
}
