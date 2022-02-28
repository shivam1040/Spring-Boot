package com.sts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

//this controller is for user related pages
@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private OrderAndPaymentStatusRepository orderAndPaymentStatusRepository;
	
	@ModelAttribute
	public void addCommonData(Model m, Principal principal) { //this is method for sending common data releated to user on all sub pages of user and to all handlers of usercontroller
		String name=principal.getName(); //principal interface of spring security will get the logging in user's username or email in this case
		User user=userRepository.getUserByUsername(name); //getting data of username from DB by Userrepositry methods
		//System.out.println(user);
		//System.out.println(name);
		m.addAttribute("user", user);
	}
	
	@RequestMapping("/dashboard")
	public String index(Model m, Principal principal) {
		
		return "dashboard";
	}
	
	
	
	@GetMapping("/add")
	public String addContactForm(Model m) {
		m.addAttribute("title", "Add Contact");
		m.addAttribute("contact", new Contact()); //sending blank contact object for adding contact data on view page
		return "addContact";
	}
	
	@PostMapping("/addProcess")
	public String addProcess(@ModelAttribute Contact contact, @RequestParam("pImage") MultipartFile multipartFile, Principal principal, HttpSession httpSession) { //processing recieved add contact data from view
		
		if(multipartFile.isEmpty()) { //image upload block
			
		}
		else {
			contact.setImage(multipartFile.getOriginalFilename()); //setting image var to file name, so that DB can have filename and contents are accessed from static resource 
			try {
				File file=new ClassPathResource("static/").getFile(); //setting savepath folder in current project directory
				Path path=Paths.get(file.getAbsolutePath()+File.separator+multipartFile.getOriginalFilename()); //getting actual file path
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); //saving the file in set directory
				System.out.println("uploaded"); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		String user=principal.getName(); //getting current looged in user
		User user1=this.userRepository.getUserByUsername(user);
		user1.getContacts().add(contact); //saving the contact
		contact.setUsers(user1); //setting user so that hibernate will do auto mapping with foreign keys
		this.userRepository.save(user1); //saving contact related to user
		//System.out.println(contact);
		httpSession.setAttribute("message", new Message("addded", "success"));
		return "addContact";
	}
	
	//first page starts from zero
	@GetMapping("/show/{page}") //showing contacts controller with pagination in pages of 5 contacts/page
	public String show(@PathVariable("page") int page, Model m, Principal principal) { //pathvariable takes the page number here
		m.addAttribute("title", "View Contacts");
		String username=principal.getName();
		User user=this.userRepository.getUserByUsername(username);
		Pageable pageable=PageRequest.of(page, 5); //creating a pagination requirement, and the parameters passed are page number and number of contacts per page
		Page<Contact> contacts=this.contactRepository.findContactsByUser(user.getId(), pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		return "showContact";
	}
	
	@RequestMapping("/contact/{cId}") //handler for showing contact details when one clicks on email
	public String showContactDetails(@PathVariable("cId") int cId, Model m) {
		m.addAttribute("title", "Contact Details");
		Optional<Contact> contact=this.contactRepository.findById(cId); //getting optional obj from db
		Contact contact1=contact.get(); //getting contact entity from optional obj
		m.addAttribute("contact", contact1);
		return "contactDetails";
	}
	
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") int cId, Model m, HttpSession httpSession) {
		Optional<Contact> optional=this.contactRepository.findById(cId);
		Contact contact=optional.get();
		this.contactRepository.delete(contact);
		httpSession.setAttribute("message", new Message("deleted", "success"));
		return "redirect:/user/show/0";
	}
	
	@PostMapping("/update/{cId}")
	public String updateContact(@PathVariable("cId") int cId, Model m) {
		m.addAttribute("title", "Update Contact");
		Optional<Contact> optional=this.contactRepository.findById(cId);
		Contact contact=optional.get();
		m.addAttribute("contact" ,contact);
		return "updateContact";
	}
	
	@PostMapping("/updateProcess") //controller for processing recieved update data from view
	public String updateProcess(@ModelAttribute Contact contact, @RequestParam("pImage") MultipartFile multipartFile, Model m, HttpSession httpSession, Principal principal) throws IOException {
		
if(!multipartFile.isEmpty()) { //image upload block
	
			File deleteOld=new ClassPathResource("static/").getFile(); //deleting current image before updating new
			deleteOld=new File(deleteOld, contact.getImage());
			deleteOld.delete();
			
			contact.setImage(multipartFile.getOriginalFilename()); //setting image var to file name, so that DB can have filename and contents are accessed from static resource 
			try {
				File file=new ClassPathResource("static/").getFile(); //setting savepath folder in current project directory
				Path path=Paths.get(file.getAbsolutePath()+File.separator+multipartFile.getOriginalFilename()); //getting actual file path
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); //saving the file in set directory
				System.out.println("uploaded"); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	System.out.println(contact.getcId());
	User user=this.userRepository.getUserByUsername(principal.getName()); //since contact recived has no value for user in it so we need to get the current user explicitly and set it to user var of contact for systematic update else updated data wont be displayed since the updated contact in db has no user_id/user linked it wit
	contact.setUsers(user);
		this.contactRepository.save(contact);
		httpSession.setAttribute("message", new Message("updated", "success"));
		
		
		return "redirect:/user/contact/"+contact.getcId();
	}
	
	@GetMapping("/profile") //handler for viewing user profile, all user data coming from common handler
	public String profile(Model m) {
		m.addAttribute("title", "Profile");
		return "profile";
	}
	
	@GetMapping("/settings")
	public String settings(Model m) {
		m.addAttribute("title", "Settings");
		return "settings";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("old") String old, @RequestParam("new") String newP, Principal principal, HttpSession httpSession) {
		String username=principal.getName();
		User user=this.userRepository.getUserByUsername(username);
		httpSession.setAttribute("message", new Message("unchanged", "alert-error"));
		if(this.bCryptPasswordEncoder.matches(old, user.getPassword())) {
			user.setPassword(this.bCryptPasswordEncoder.encode(newP));
			this.userRepository.save(user);
			httpSession.setAttribute("message", new Message("changed", "success"));
		}
		return "redirect:/user/dashboard";
	}
	
	@PostMapping("/createOrder")
	@ResponseBody //creating handler for payment url set in JS
	public String createOrderForPayment(@RequestBody Map<String, Object> data, Principal principal) { //Map will map the data according to key value as recieved from view through post data in JSON format in our case
		System.out.println(data);
		int amount=Integer.parseInt(data.get("amount").toString());
		Order order=null;
		try { //in java 11 var  can be used instead of classname
			var client=new RazorpayClient("rzp_test_BNKwffUSPA4Af0", "Qg4u0ujcVHqQe8MPHL7coNcy"); //pasting payment gateway id and secret, razorpay for creating entrypoint for order creation on payment gateway side
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("amount", amount*100); //passing required parametes by payment gateway in json form
			jsonObject.put("currency", "INR");
			jsonObject.put("receipt", "tet");
			order=client.Orders.create(jsonObject); //creating order on the payment gateway side and getting it back
			System.out.println(order);
			
			
			OrderAndPaymentStatus orderAndPaymentStatus= new OrderAndPaymentStatus();
			orderAndPaymentStatus.setAmount(order.get("amount").toString());
			orderAndPaymentStatus.setStatus("created");
			orderAndPaymentStatus.setUser(this.userRepository.getUserByUsername(principal.getName()));
			orderAndPaymentStatus.setReceipt(order.get("receipt"));
			orderAndPaymentStatus.setOrderId(order.get("id")); //getting data from order object recieved from payment gateway
			this.orderAndPaymentStatusRepository.save(orderAndPaymentStatus);
			System.out.println(orderAndPaymentStatus);
			
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order.toString();
	}
	
	@PostMapping("/updateOrder") //this handler is for saving the final payment status to DB
	public ResponseEntity<?> updateOrderStatus(@RequestBody Map<String, Object> data){
		OrderAndPaymentStatus orderAndPaymentStatus=this.orderAndPaymentStatusRepository.findByOrderId(data.get("orderId").toString());
		orderAndPaymentStatus.setPaymentId(data.get("paymentId").toString());
		orderAndPaymentStatus.setStatus(data.get("status").toString());
		System.out.println(orderAndPaymentStatus);
		this.orderAndPaymentStatusRepository.save(orderAndPaymentStatus);
		return ResponseEntity.ok(Map.of("message", "updated"));
	}
} 	