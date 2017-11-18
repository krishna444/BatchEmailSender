import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a job to send email. This job is executed from Email Service
 * 
 * @author Krishna Paudel
 */
class SendEmailJob implements Runnable {	 
	private static final Logger LOGGER = Logger.getLogger(SendEmailJob.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.INFO);
	}
	
	private String email;
	private String firstName;
	private String lastName;

	/**
	 * Email Send Job Constructor
	 * 
	 * @param email
	 *            Email Address
	 * @param firstName
	 *            First Name
	 * @param lastName
	 *            Last Name
	 */
	public SendEmailJob(String email, String firstName, String lastName) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Actual email sending is carried out here(Need to be implemented)<br>	
	 * Assumption: sending takes 500 milliseconds
	 */
	@Override
	public void run() {
		try {
			// implement email sending here.			
			Thread.sleep(500);			
			LOGGER.info("SUCCESS: Email sent to "+this.firstName+" "+this.lastName);		
		} catch (Exception ex) {
			LOGGER.severe("FAILURE: Error while sending email to " + this.email);
		}

	}
}