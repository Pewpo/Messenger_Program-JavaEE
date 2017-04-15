package fi.jamk.chatapp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ChatController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	ApplicationContext context =
    		new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	private MessageDAO messageDAO = (MessageDAO) context.getBean("messageDAO");
	private UserDAO userDAO = (UserDAO) context.getBean("userDAO");

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		User user = new User();
		model.addAttribute("user", user);
		return "home";
	}
	
	//kirjautuminen sisään
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String findThatUser(@ModelAttribute(value="user") @Valid User user, BindingResult result, Model model, HttpServletRequest request) {
		boolean granted;
		if (result.hasErrors()) {
			return "home";
		} try{
			granted = userDAO.loginUser(user);			
				if (!granted){
					model.addAttribute("error", "Username or password was invalid.");
					return "redirect:/";
				}else if (granted){
					Message mess = new Message();
					model.addAttribute("message", mess);
					HttpSession session = request.getSession();
					session.setAttribute("username", user.getNickname());
					return "redirect:/chat";
				}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return "home";
		
	}
	
	// Rekisteröintilomakkeen luominen
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String newRegisterForm(Model model, HttpServletRequest request) {
		User user = new User();
		model.addAttribute("user", user);
		return "register"; 
	}
	
	//Rekisteröityminen
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute(value="user") @Valid User user, BindingResult result, Model model, HttpServletRequest request) {
		boolean registered;
		if (result.hasErrors()) {
			return "register";
		} try{	
			if (user.getNickname().length() >= 5 && user.getPassword().length() >= 5){
				if (user.getPassword().intern() == user.getRepassword().intern()){
					registered = userDAO.registerUser(user);
					if (!registered){
						model.addAttribute("error", "Username already exists!");
						return "redirect:/register";
					}else if (registered){
						model.addAttribute("status", "Registered successfully! Log in.");
						return "redirect:/";
					}
				}else{
					model.addAttribute("error", "Passwords don't match.");
					return "redirect:/register";
				}
			}else{
				model.addAttribute("error", "Username length: 5 characters & Password length: 5 characters");
				return "redirect:/register";
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return "register";
		
	}
	
	// Viestilomakkeen luominen
	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String newForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null){
			model.addAttribute("error", "Login first!");
			return "redirect:/"; 
		}
		Message mes = new Message();
		model.addAttribute("message", mes);
		
		List<Message> allMessages = messageDAO.getAllMessages();
        String messagesAll = "";
        
        for (Message mess : allMessages) {
        	messagesAll += mess.toString(); 
        }
        model.addAttribute("messages", messagesAll);
        
        List<User> allUsers = userDAO.getAllUsers((String)session.getAttribute("username"));
        
        model.addAttribute("users", allUsers);
        
		return "chat"; 
	}
	
	// Viestilomakkeen tietojen ottaminen vastaan
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public String addNew(@RequestParam String action, @ModelAttribute(value="user") User user, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "chat";
		}
		
		/*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		HttpSession session = request.getSession();
		mes.setIduser(1);
		//((String)session.getAttribute("username"));
		mes.setIdchat(1);
		messageDAO.insert(mes);
		*/
		if (action.equals("Log out")){
			HttpSession session = request.getSession();
			if (session != null) {
			    session.invalidate();
			}
			return "redirect:/";
	    }
		if (action.equals("Send")){
			
		}
		
		if (action.equals("Chat")){
			HttpSession session = request.getSession();
			String currentUser = (String)request.getParameter("user");
			session.setAttribute("currentChat", currentUser);
		}
			
		return "redirect:/chat"; 
	}
	
}
