package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	List<Topic> topics = Topic.findAll();
    	for(Topic topic : topics) {
    		System.out.println("Topic '" + topic + "' has " + topic.competencyGroups.size() + " competency groups");
    	}
        render(topics);
    }
    
    public static void competencyGroup(String sanitizedTitle) {
    	CompetencyGroup competencyGroup = CompetencyGroup.fetchBySanitizedTitle(sanitizedTitle);
    	notFoundIfNull(competencyGroup);
    	render(competencyGroup);
    }

}