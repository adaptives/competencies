package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Level extends Model implements Comparable {
	
	//TODO: Add database constraint to ensure that this cannot be set to null
	@Required
	public String title;
	
	public String description;
	
	public Integer placement;
	
	public Level(String title,
				 String description) {
		this.title = title;
		this.description = description;
		this.placement = 0;
	}

	@Override
	public int compareTo(Object o) {
		Level other = (Level)o;
		return this.placement - other.placement;
	}
}
