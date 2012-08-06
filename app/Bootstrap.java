import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import models.Level;
import models.Topic;

import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.DataUtils;
import utils.ParseException;


@OnApplicationStart
public class Bootstrap extends Job {
	
	public void doJob() {
		System.out.println("In onApplicationStart Job");
		if(Play.mode == Play.Mode.DEV && Topic.findAll().size() == 0) {
			System.out.println("Populating the database");
			
			//create levels
			Level level1 = new Level("Level I", "Level I Description");
			level1.placement = 1;
			level1.save();
			Level level2 = new Level("Level II", "Level II Description");
			level2.placement = 2;
			level2.save();
			Level level3 = new Level("Level III", "Level III Description");
			level3.placement = 3;
			level3.save();
			
			String fName = "default-data.csv";
			ClassLoader loader = DataUtils.class.getClassLoader(); 
			InputStream is = loader.getResourceAsStream(fName);
			//create string from file
			StringBuffer sBuff = new StringBuffer();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(is));
				String line = null;
				while((line = reader.readLine()) != null) {
					sBuff.append(line + "\n");
				}
				
				String csvString = sBuff.toString();
				Topic topic = DataUtils.parseCSV("Core Java", 0, csvString);
				System.out.println("Saving topics");
				topic.levels.add(level1);
				topic.levels.add(level2);
				topic.levels.add(level3);
				topic.save();
			} catch(IOException ioe) {
				System.out.println("caught IOException while parsing default data " + ioe);
			} catch(ParseException pe) {
				System.out.println("caught ParseException while parsing default data " + pe);
			} finally {
				if(reader != null) {
					try { reader.close(); } catch(IOException ioe) {}
				}
			}
		}
	}

	private void createLevels() { 
		
	}
}
