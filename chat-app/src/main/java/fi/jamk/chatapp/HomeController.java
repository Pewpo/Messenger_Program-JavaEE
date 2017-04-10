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

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	ApplicationContext context =
    		new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	private MessageDAO messageDAO = (MessageDAO) context.getBean("messageDAO");
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		User user = new User();
		model.addAttribute("user", user);
		/*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		

     
        Message message = new Message(timestamp, "hello", "jannu");
        //messageDAO.insert(message);

        List<Message> allMessages = messageDAO.getAllMessages();
        String messagesAll = "";
        
        for (Message mes : allMessages) {
        	messagesAll += mes.toString(); 
        }
        model.addAttribute("messages", messagesAll);
		*/
		return "home";
	}
	
	
	// Lomakkeen luominen
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
		return "chat"; 
	}
	
	// Lomakkeen tietojen ottaminen vastaan
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public String addNew(@ModelAttribute(value="message") Message mes, BindingResult result, HttpServletRequest request) {

		// tallennetaan lomakkeelta luettu kone
		if (result.hasErrors()) {
			return "chat";
		}
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		HttpSession session = request.getSession();
		mes.setUser((String)session.getAttribute("username"));
		mes.setTimestamp(timestamp);
		messageDAO.insert(mes);
		
		return "redirect:/chat"; 
	}
	//kirjautuminen sisään
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String findThatUser(@ModelAttribute(value="user") @Valid User user, BindingResult result, Model model, HttpServletRequest request) {
		boolean exist = false;
		if (result.hasErrors()) {
			return "home";
		} try{
			//System.out.println(answer);
			exist = messageDAO.findUser(user.getNickname(), user.getPassword());
			System.out.println(user.getNickname() + "tätällöfsd");
			
			//if (user.getNickname() !=  && user.getPassword() != null){
				
				if (exist == false){
					model.addAttribute("error", "Username or password was invalid.");
					return "redirect:/";
				}else if (exist == true){
					Message mess = new Message();
					model.addAttribute("message", mess);
					HttpSession session = request.getSession();
					session.setAttribute("username", user.getNickname());
					return "redirect:/chat";
				}
				//model.addAttribute("error", "Fill fields first.");
				//return "redirect:/";
			//}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return "home";
		
	}

}
