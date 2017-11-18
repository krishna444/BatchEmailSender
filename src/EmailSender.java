import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Standard class to read csv file and sending email to everyone in the file
 * 
 * @author Krishna Paudel
 */
class EmailSender {
	private static final Logger LOGGER = Logger.getLogger(EmailSender.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.INFO);
	}

	//Waiting time is email service is not ready to accept a task
	private static final int WAIT_TIME=200;
	
	private EmailService emailService;
	private String fileLocation;

	/**
	 * Create an instance of email sender
	 * 
	 * @param fileLocation
	 *            Location of file
	 */
	public EmailSender(String fileLocation) {
		this.fileLocation = fileLocation;
		this.emailService = new EmailService();
	}

	/**
	 * Starts email sending, which basically start service and the service
	 * executes the email sending jobs
	 */
	public void send() throws FileNotFoundException, IOException,InterruptedException	 {		
		// Read the file
		File file = new File(this.fileLocation);
		if (!file.exists()) {
			throw new FileNotFoundException("File does not exist! Please provide a valid file!");
		}
		LOGGER.fine("Starting...");
		this.emailService.start();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			// Discard first header line
			reader.readLine();
			String line = "";
			while ((line=reader.readLine()) != null) {
				String[] usersInfo = line.split(";");
				//check if service is ready to execute task, otherwise wait for some time 
				while(!this.emailService.isReady()){
					Thread.sleep(WAIT_TIME);
				}	
				this.emailService.sendEMail(usersInfo[0], usersInfo[1], usersInfo[2]);
				
				
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			//Close
			if (reader != null)
				try {
					reader.close();
					this.emailService.stop();
				} catch (Exception ex) {
					//
				}
		}

	}

	/**
	 * Entry point of the program
	 * @param args CSV file name
	 */
	public static void main(String... args) {
		if (args.length == 0) {
			System.out.println("Please provide the CSV file name as parameter! ");
			System.out.println("Exiting...");
			return;
		}
		long tic = System.currentTimeMillis();

		EmailSender emailSender = new EmailSender(args[0]);
		try {
			emailSender.send();
		} catch (Exception ex) {
			LOGGER.severe("ERROR while sending email. Please check the file is CORRECT");
			LOGGER.severe("CAUSE:" + ex.getMessage());
		}
		long duration = (System.currentTimeMillis() - tic) / 1000;
		LOGGER.info("Processing took " + duration + " seconds");

	}

}