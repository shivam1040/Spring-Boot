package com.sts;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model m) {
		m.addAttribute("title", "Home");
		return "home";
	}
	
	@GetMapping("/signup")
	public String signUp(Model m) {
		m.addAttribute("title", "Sign Up");
		m.addAttribute("user", new User()); //sending a blank user obj to get it filled by view
		return "signUp";
	}
	
	@PostMapping("/register") //handler for reciving signup data
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="checkbox", defaultValue = "false") boolean agreement, Model m, HttpSession session) { //this "user" is coming from blank object passed in signup handler which is now filled from view data
		try {
		System.out.println(agreement);
		user.setRole("ROLE_USER"); //remeber to user ROLE_USER for defining standard roles else even after succesful authorization restricted page by spring security wont open
		user.setEnabled(true);
		user.setImageUrl("default");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //encoding password by using spring security, the bean for this is configured in myconfig... class
		m.addAttribute("user", user); //facilitates showing the same entered data in case of redirect to the same page when server side validation fails
		
		if(!agreement)
			System.out.println("agreement false");
		
		if(result.hasErrors()) //doing bean validation that is server side validation
			return "signUp";
		
		System.out.println(user);
		this.userRepository.save(user);
		m.addAttribute("user", new User()); //this will be sent to view in case of success
		session.setAttribute("message", new Message("success signing up", "alert-success")); //sending session obj to view for succes message
		return "signUp";
		}
		catch(Exception e) {
			m.addAttribute("user", user); //same data recieved from view will be sent back in case of error
			session.setAttribute("message", new Message("Error message", "alert-danger")); //sending session obj to view for error message
		}
		return "signUp";
	}
	
	@GetMapping("/login")
	public String customLoginPageSpringSecurity() { //custom login page for spring security
		return "customLoginPageSpringSecurity";
	}
}