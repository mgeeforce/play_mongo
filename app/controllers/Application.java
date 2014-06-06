package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.Company;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.*;
import views.html.*;
import static play.data.Form.form;

public class Application extends Controller {
	
	static Form<Company> companyForm = form(Company.class);

    public static Result index() {
        return redirect(routes.Application.companies());
    }
    
    public static Result companies() {
    	return ok(index.render(Company.all(), companyForm));
    }

    public static Result newCompany() {
    	Form<Company> form = companyForm.bindFromRequest();
    	if(form.hasErrors()) {
    		return badRequest(index.render(Company.all(), form));
    	} else {
            Company company = form.get();
            MultipartFormData body = request().body().asMultipartFormData();
            FilePart attachment = body.getFile("attachment");
            if (attachment != null) {
            	File file = attachment.getFile();
            	Path path = Paths.get(file.getAbsolutePath());
            	try {
                    company.attachment = Files.readAllBytes(path);
                    company.mimeType = attachment.getContentType();
            	} catch (Exception e) {
            		Logger.error(e.toString());
            	}
            }
    		//company.mimeType = 
    		Company.create(company);
    		return redirect(routes.Application.companies());
    	}
    	
    }
    
    public static Result deleteCompany(String id) {
    	Company.delete(id);
		return redirect(routes.Application.companies());
    }
    
    public static Result getAttachment(String companyId) {
     	Company company = Company.findById(companyId);
    	ByteArrayInputStream is = new ByteArrayInputStream(company.attachment);
    	return ok(is).as(company.mimeType);
    }
    
}
