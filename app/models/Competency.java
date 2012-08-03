package models;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;
import utils.StringUtils;

@Entity
public class Competency extends Model {
		
	@Required
	@Column(nullable=false)
    public String title;       
    
    //TODO: Set in the constructor... see BlogPost.java in Sole
	@Column(nullable=false)
    public String sanitizedTitle;
    
    @Lob
    public String description;
    
    @Lob
    public String resources;
    
    @Column(nullable=false)
    public Integer placement;
    
    //TODO: Set database constraints to ensure that this is not nullable
    //TODO: Verify that this level is indeed present in the topic to which this competenct belongs
    @Required
    public Level level;

    @Required
    @ManyToOne
    //TODO: How do we make this required
    public CompetencyGroup competencyGroup;
    
    //TODO: If a Competency has been specified as a prerequsite for this
    //Competency, then the preerquisite must belong to the same CompetencyGroup
    @OneToMany
    public Set<Competency> prerequisites;
    

    public Competency(String title,
    				  String description,  
    				  CompetencyGroup competencyGroup,
    				  Level level,
    				  String resources) {
        super();
        this.title = title;
        this.sanitizedTitle = StringUtils.replaceSpaceWithDashes(title);
        this.description = description;
        this.competencyGroup = competencyGroup;
        this.level = level;
        this.resources = resources;
        this.placement = 0;
        this.prerequisites = new TreeSet<Competency>();
    }

    // TODO: implement compareTo
}
