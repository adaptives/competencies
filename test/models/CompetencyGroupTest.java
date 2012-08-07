package models;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public final class CompetencyGroupTest extends UnitTest {
    private Topic java;
    private Level level1;
    private Level level2;
    private Level level3;

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        level1 = new Level("Level I", "Level I description");
        level2 = new Level("Level II", "Level II description");
        level3 = new Level("Level III", "Level III description");
        level1.save();
        level2.save();
        level3.save();

        java = new Topic("Java", "Java programming language.", "Sample resources");
        java.levels.add(level1);
        java.levels.add(level2);
        java.levels.add(level3);
        java.save();
    }

    @Test
    public void testCreateAndFindByTitle() {
        CompetencyGroup collections = new CompetencyGroup("Collections", "Java Collections API", "Collection resources");
        collections.topic = java;
        collections.save();

        Competency c1 = new Competency("Collections API", "Collections API Basics", collections, level1, "c1 resources");
        Competency c2 = new Competency("List", "Understanding  Lits", collections, level2, "c2 resources");
        Competency c3 = new Competency("Sets", "Understanding Sets", collections, level3, "c3 resources");

        collections.competencies.add(c1);
        collections.competencies.add(c2);
        collections.competencies.add(c3);

        collections.save();

        CompetencyGroup found = CompetencyGroup.findByTitle("Collections");
        Assert.assertNotNull(found);
        Assert.assertEquals("Collections", found.title);
        Assert.assertEquals("Java Collections API", found.description);
        Assert.assertEquals("Collection resources", found.resources);
        Assert.assertNotNull(found.topic);
        Assert.assertEquals("Java", found.topic.title);
        Assert.assertNotNull(found.competencies);
        Assert.assertEquals(3, found.competencies.size());

        Assert.assertTrue(containsCompetencyWithTitle("Collections API", found.competencies));
        Assert.assertTrue(containsCompetencyWithTitle("List", found.competencies));
        Assert.assertTrue(containsCompetencyWithTitle("Sets", found.competencies));
    }

    private boolean containsCompetencyWithTitle(String title, Set<Competency> competencies) {
        for (Competency c : competencies) {
            if (title.equals(c.title)) {
                return true;
            }
        }

        return false;
    }
}
