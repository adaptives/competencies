package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	List<Topic> topics = Topic.findAll();
        render(topics);
    }
    
    public static void competencyGroup(String sanitizedTitle) {
    	CompetencyGroup competencyGroup = CompetencyGroup.fetchBySanitizedTitle(sanitizedTitle);
    	notFoundIfNull(competencyGroup);
    	render(competencyGroup);
    }
    
    public static void competency(String sanitizedTitle) {
    	Competency competency = Competency.fetchBySanitizedTitle(sanitizedTitle);
    	notFoundIfNull(competency);
    	render(competency);
    }

}