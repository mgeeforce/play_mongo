package models;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import controllers.MorphiaController;
import play.Logger;
import play.data.validation.Constraints.Required;


@Entity
public class Company {
	
	@Id
	public ObjectId id;
	
	@Required
	public String name;
	
	public byte[] attachment;
	
	public String mimeType;
	
	public boolean hasAttachment() {
		return (attachment!=null);
	}
	
	public static void create(Company company) {
		MorphiaController.datastore.save(company);		
	}
	
	public static void delete(String id) {
		Company company = MorphiaController.datastore.find(Company.class).field("_id").equal(new ObjectId(id)).get();
		if (company != null) {
			MorphiaController.datastore.delete(company);
		} else {
			Logger.debug("No company with ID "+id+" found.");
		}
	}
	
	public static List<Company> all() {
		return MorphiaController.datastore.find(Company.class).asList();
	}
	
	public static Company findById(String id) {
		Company company = MorphiaController.datastore.find(Company.class).field("_id").equal(new ObjectId(id)).get();
		return company;
	}

}
