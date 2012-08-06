package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import models.Competency;
import models.CompetencyGroup;
import models.Level;
import models.Topic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class DataUtilsTest extends UnitTest {

	private List<Level> levels = new ArrayList<Level>();
	
	@Before
	public void setUp() {
		Fixtures.deleteAllModels();
		
		//create levels
    	Level level1 = new Level("Level I", "Level I Description");
    	level1.placement = 1;
    	level1.save();
    	this.levels.add(level1);
    	Level level2 = new Level("Level II", "Level II Description");
    	level2.placement = 2;
    	level2.save();
    	this.levels.add(level2);
    	Level level3 = new Level("Level III", "Level III Description");
    	level3.placement = 3;
    	level3.save();
    	this.levels.add(level3);
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testParseCSV() throws Exception {
	
	String csv = ", Level I, Level II, Level III,\n" +
				     "sg1, c111, c112, c113,\n" +
				     ", c121, c122, c123,\n" +
				     ", c131, c132, c133,\n" +
				     "sg2, c211, c212, c213,\n" +
				     ", c221, c222, c223,\n" +
				     "sg3, c311, c312, c313,\n";
		
		Topic topic = DataUtils.parseCSV("test topic", 0, csv);
		assertNotNull(topic);
		assertEquals(3, topic.competencyGroups.size());
		for(CompetencyGroup competencyGroup : topic.competencyGroups) {
			if(competencyGroup.title.equals("sg1")) {				
				assertEquals(9, competencyGroup.competencies.size());
				for(Competency competency : competencyGroup.competencies) {
					if(competency.title.equals("c111") || 
					   competency.title.equals("c121") || 
					   competency.title.equals("c131")) {
						assertEquals(this.levels.get(0).title, competency.level.title);
					} else if(competency.title.equals("c112") || 
							   competency.title.equals("c122") || 
							   competency.title.equals("c132")) {
						assertEquals(this.levels.get(1).title, competency.level.title);
					} else if(competency.title.equals("c113") || 
							   competency.title.equals("c123") || 
							   competency.title.equals("c133")) {
						assertEquals(this.levels.get(2).title, competency.level.title);
					} else {
						fail("Competency " + competency + " should not belong to this competency group" + competencyGroup);
					}
				}
			} else if (competencyGroup.title.equals("sg2")) {				
				assertEquals(6, competencyGroup.competencies.size());
				for(Competency competency : competencyGroup.competencies) {
					if(competency.title.equals("c211") ||  
					   competency.title.equals("c221")) {
						assertEquals(this.levels.get(0).title, competency.level.title);
					} else if(competency.title.equals("c212") ||  
							   competency.title.equals("c222")) {
						assertEquals(this.levels.get(1).title, competency.level.title);
					} else if(competency.title.equals("c213") ||  
							   competency.title.equals("c223")) {
						assertEquals(this.levels.get(2).title, competency.level.title);
					} else {
						fail("Competenct " + competency + " should not belong to this competency group" + competencyGroup);
					}
				}
			} else if(competencyGroup.title.equals("sg3")) {				
				assertEquals(3, competencyGroup.competencies.size());
				for(Competency competency : competencyGroup.competencies) {
					if(competency.title.equals("c311")) {  				
						assertEquals(this.levels.get(0).title, competency.level.title);
					} else if(competency.title.equals("c312")) {  
						assertEquals(this.levels.get(1).title, competency.level.title);
					} else if(competency.title.equals("c313")) {
						assertEquals(this.levels.get(2).title, competency.level.title);
					} else {
						fail("Competenct " + competency + " should not belong to this competency group" + competencyGroup);
					}
				}
			}
		}
	}
	
	@Test
	public void testParseCSVAndSaveTopics() throws Exception {
	
	String csv = ", Level I, Level II, Level III,\n" +
				     "sg1, c111, c112, c113,\n" +
				     ", c121, c122, c123,\n" +
				     ", c131, c132, c133,\n" +
				     "sg2, c211, c212, c213,\n" +
				     ", c221, c222, c223,\n" +
				     "sg3, c311, c312, c313,\n";
		
		Topic topic = DataUtils.parseCSV("test topic", 0, csv);
		for(Level level : this.levels) {
			topic.levels.add(level);
		}
		topic.save();
	}
	
	
	@Test
	public void testParseCSVFromFile() throws Exception {
	
	InputStream is = DataUtilsTest.class.getClassLoader().getResourceAsStream("default-data.csv");	
	String csv = getFileAsSting(is);
		
		Topic topic = DataUtils.parseCSV("test topic", 0, csv);
		assertNotNull(topic);
		assertEquals(24, topic.competencyGroups.size());	
	}
			

	@Test(expected=ParseException.class)
	public void testParseCSVUnsuccessfullNoLevelsDefined() throws Exception {
	String csv =     ", , , ," +
				     "sg1, c111, c112, c113," +
				     ", c121, c122, c123," +
				     ", c131, c132, c133," +
				     "sg2, c211, c212, c213," +
				     ", c211, c212, c213," +
				     "sg3, c311, c312, c313,";
		
		Topic topic = DataUtils.parseCSV("test topic", 0, csv);
		assertNotNull(topic);
	}
	
	@Test(expected=ParseException.class)
	public void testParseCSVUnsuccessfullIncorrectLevels() throws Exception {
	String csv =     ",Level 6 , , ," +
				     "sg1, c111, c112, c113," +
				     ", c121, c122, c123," +
				     ", c131, c132, c133," +
				     "sg2, c211, c212, c213," +
				     ", c211, c212, c213," +
				     "sg3, c311, c312, c313,";
		
		Topic topic = DataUtils.parseCSV("test topic", 0, csv);
		assertNotNull(topic);
	}
	
	private String getFileAsSting(InputStream is) throws IOException {
		StringBuffer sBuff = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));			
			String line = null;
			while((line = reader.readLine()) != null) {
				sBuff.append(line + "\n");
			}
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		return sBuff.toString();
	}
}
