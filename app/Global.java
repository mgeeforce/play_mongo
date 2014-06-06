import java.net.UnknownHostException;

import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;

import play.GlobalSettings;
import play.Logger;


import controllers.MorphiaController;

public class Global extends GlobalSettings {

	@Override
	public void onStart(play.Application arg0) {
		super.beforeStart(arg0);
		Logger.debug("** onStart **"); 
		try {
			MorphiaController.mongo = new Mongo("127.0.0.1", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MorphiaController.morphia = new Morphia();
		MorphiaController.datastore = MorphiaController.morphia.createDatastore(MorphiaController.mongo, "play_morphia");
		MorphiaController.datastore.ensureIndexes();   
		MorphiaController.datastore.ensureCaps();  

		Logger.debug("** Morphia datastore: " + MorphiaController.datastore.getDB());
	}
}