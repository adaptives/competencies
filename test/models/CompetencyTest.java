package models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public final class CompetencyTest extends UnitTest {

    private Level level;
    private CompetencyGroup group;

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();

        level = new Level("Level I", "Level I description");
        level.save();

        group = new CompetencyGroup("Group 1", "Group 1 description", "Group 1 resources");
        group.save();
    }

    @Test
    public void testSaveAndFindByTitle() {
        Competency competency = new Competency("Competency 1", "Competency 1 description", group, level, "Competency 1 resources");
        competency.save();

        Competency found = Competency.findByTitle("Competency 1");

        Assert.assertNotNull(found);
        Assert.assertEquals("Competency 1", found.title);
        Assert.assertEquals("competency-1", found.sanitizedTitle);
        Assert.assertEquals("Competency 1 description", found.description);
        Assert.assertEquals("Competency 1 resources", found.resources);
        Assert.assertNotNull(found.level);
        Assert.assertEquals("Level I", found.level.title);
        Assert.assertNotNull(found.competencyGroup);
        Assert.assertEquals("Group 1", found.competencyGroup.title);
    }

    @Test
    public void testCompetencyConstraints() {
        try {
            Competency competency = new Competency(null, "Competency 1 description", group, level, "Competency 1 resources");
            // We may want to throw some specific exception here like
            // IllegalArgumentException. Also, this exception is originating
            // from the model as part of sanitization of the title.
        } catch (NullPointerException npe) {
            // Expected.
        }
    }
}
