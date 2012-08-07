package models;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;
import utils.StringUtils;

@Entity
public class CompetencyGroup extends Model implements Comparable {
    
	//TODO: Add database constraint to ensure that this cannot be set to null
	@Required
    @Column(nullable=false)
    public String title;
	
	//TODO: Test
	@Column(nullable=false)
    public String sanitizedTitle;

	@Lob
	public String description;

    @Column(nullable=false)
    public Integer placement;
    
    @ManyToOne
    public Topic topic;
    
    //TODO: order by placement
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competencyGroup")
    public Set<Competency> competencies;

    // Note: The CompetencyGroup should not depend on itself.

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
        this.sanitizedTitle = StringUtils.replaceSpaceWithDashes(title);
        this.description = description;
        this.resources = resources;
        this.placement = 0;
        this.competencies = new TreeSet<Competency>();
        this.prereqisites = new TreeSet<CompetencyGroup>();        
    }

    public static CompetencyGroup fetchBySanitizedTitle(String sanitizedTitle) {
    	String query = "select cg from CompetencyGroup cg where cg.sanitizedTitle = ?";
    	return CompetencyGroup.find(query, sanitizedTitle).first();
    }
    
    public List<Competency> fetchCompetenciesForLevel(Level level) {
    	String query = "select c from CompetencyGroup cg join cg.competencies as c where cg.id = ? and c.level.id = ?";
    	return Competency.find(query, this.id, level.id).fetch();
    }

    public static CompetencyGroup findByTitle(String title) {
        String query = "select g from CompetencyGroup g where g.title = ?";
        return find(query, title).first();
    }

	@Override
	public int compareTo(Object o) {
		CompetencyGroup other = (CompetencyGroup)o;
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
	
	@Override
    public String toString() {
    	return this.id + " : " + this.title;
    }
}
