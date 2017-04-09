package fi.jamk.chatapp;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Message mes = new Message(timestamp, "hello", "jannu");
		JdbcMessageDAO mDAO = new JdbcMessageDAO();
		mDAO.insert(mes);
		
		
		
		ApplicationContext context =
    		new ClassPathXmlApplicationContext("Spring-Module.xml");

        MessageDAO messageDAO = (MessageDAO) context.getBean("messageDAO");
        Message message = new Message(timestamp, "hello", "jannu");
        messageDAO.insert(message);

        Message allMessages = (Message) messageDAO.getAllMessages();
        System.out.println(allMessages);

		
		return "home";
	}
	
}
