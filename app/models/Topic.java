package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Topic extends Model implements Comparable {
    
	@Required
	@Column(nullable=false)
	public String title;
    
	@Lob
	public String description;
    
	@Column(nullable=false)
	public Integer placement;

    //TODO: order by placement
    @OneToMany(cascade=CascadeType.ALL, mappedBy="topic")
    @OrderBy("placement")
    public Set<CompetencyGroup> competencyGroups;
    
    @OneToMany
    public Set<Topic> prerequisites;
    
    @Lob
    public String resources;
    
    //TODO: Set database constraints to ensure that this is not nullable
    @Column(nullable=false)
    @Required
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
        this.competencyGroups = new TreeSet<CompetencyGroup>();
        this.prerequisites = new TreeSet<Topic>();
        this.levels = new TreeSet<Level>();
    }

    @Override
	public int compareTo(Object o) {
		Topic other = (Topic)o;
		//we do not want to return a 0 if both the placement values are the same
		//because that will make the TreeSet think these are equal objects and
		//one of them will not be added. If placements are equal then we 
		//compare by title
		if(this.placement == other.placement) {
			return this.title.compareTo(other.title);
		} else {
			return this.placement - other.placement;
		}
	}
}
