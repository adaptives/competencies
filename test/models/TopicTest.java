package models;

import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class TopicTest extends UnitTest {
    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
//        Fixtures.loadModels("data.yml");
    }

    @Test
    public void testCreate() {
    	//create levels
    	Level level1 = new Level("Level I", "Level I Description");
    	level1.save();
    	Level level2 = new Level("Level II", "Level II Description");
    	level2.save();
    	Level level3 = new Level("Level III", "Level III Description");
    	level3.save();
    	
    	//create Topic
    	String topicTitle = "Core CS";
    	String topicDesc = "Core CS Description";
    	String topicResources = "Core CS Resources";
    	Topic topic = new Topic(topicTitle, 
    							topicDesc, 
    							topicResources);
    	topic.levels.add(level1);
    	topic.levels.add(level2);
    	topic.levels.add(level3);
    	topic.save();
    	
    	List<Topic> retreivedTopics = Topic.findAll();
    	assertEquals(1, retreivedTopics.size());
    	Topic retreivedTopic = retreivedTopics.get(0);
    	assertEquals(topicTitle, retreivedTopic.title);
    	assertEquals(topicDesc, retreivedTopic.description);
    	assertEquals(topicResources, retreivedTopic.resources);
    	assertEquals(3, retreivedTopic.levels.size());
    	assertTrue(retreivedTopic.levels.contains(level1));
    	assertTrue(retreivedTopic.levels.contains(level2));
    	assertTrue(retreivedTopic.levels.contains(level3));
    }
    
    @Test(expected=PersistenceException.class)
    public void testCreateUnsuccessfullWithoutRequiredTitle() {
    	
    	Topic topic = new Topic(null, 
    							"Core CS Description", 
    							"Core CS Resources");
    	topic.save();
    }       
    
    @Test
    public void testFindAll() {
        
    }
}
