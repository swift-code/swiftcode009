package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import forms.login;
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

    @Inject
    ObjectMapper objectMapper;

    public Result signUp(){
        Form<signup> signupForm = formFactory.form(signup.class).bindFromRequest();
        if(signupForm.hasErrors()){
            return ok(signupForm.errorsAsJson());
        }
        Profile profile = new Profile(signupForm.data().get("firstName"),signupForm.data().get("lastName"));
        Profile.db().save(profile);
        User user = new User(signupForm.data().get("email"),signupForm.data().get("password"));
        user.profile= profile;
        User.db().save(user);

        return ok((JsonNode) objectMapper.valueToTree(user));
    }

    public Result login(){
        Form<login> loginForm = formFactory.form(login.class).bindFromRequest();
        if(loginForm.hasErrors()){
            return ok(loginForm.errorsAsJson());
        }
        return ok();
    }

  }
