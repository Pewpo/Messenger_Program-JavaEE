package fi.jamk.chatapp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	@RequestMapping(value = "chat", method = RequestMethod.GET)
	public String newForm(Model model) {
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
		if (request.getSession() != null){
			String username = mes.getUser();
			session.setAttribute("UserName", username);
		}else{
			mes.setUser((String)request.getAttribute("UserName"));
		}	
		
		mes.setTimestamp(timestamp);
		messageDAO.insert(mes);
		
		return "redirect:/chat"; 
	}
		//kirjautuminen sis‰‰n
		@RequestMapping(value = "/", method = RequestMethod.POST)
		public String findThatUser(@ModelAttribute(value="user") User newuser, BindingResult result, Model model, HttpServletRequest request) {
			boolean answer = false;
			if (result.hasErrors()) {
				return "home";
			} try{
				System.out.println(answer);
				answer = messageDAO.findUser(newuser.getNickname(), newuser.getPassword());
				System.out.println(answer);
				if(answer == false){
					return "home";
				}
				else if (answer == true){
					model.addAttribute("username", newuser.getNickname());
					return "chat";
				} 
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			return "home";
			
		}
	
}
