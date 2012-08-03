package controllers;

import java.util.List;

import models.CompetencyGroup;
import play.mvc.Controller;

public final class CompetencyRestApiController extends Controller {
    public static void competencies() {
        List<CompetencyGroup> groups = CompetencyGroup.all().fetch();
        renderJSON(groups);
    }
}
