package controllers;

import forms.signup;
import models.Profile;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;

/**
 * Created by lubuntu on 8/21/16.
 */
public class Application extends Controller {
    @Inject
    FormFactory formFactory;


    public Result signUp(){
        Form<signup> signupForm = formFactory.form(signup.class).bindFromRequest();
        if(signupForm.hasErrors()){
            return ok(signupForm.errorsAsJson());
        }
        Profile profile = new Profile(signupForm.data().get("firstName"),signupForm.data().get("lastName"));
        profile.db().save(profile);
        User user = new User(signupForm.data().get("email"),signupForm.data().get("password"));
        user.profile= profile;
        user.db().save(user);




        return ok();
    }

  }
