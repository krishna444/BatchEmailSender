import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is Email Service which is responsible for executing email sending jobs.
 * We can add other jobs also
 * 
 * @author Krishna Paudel
 */
class EmailService {
	private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.INFO);
	}

	private static final int QUEUE_LIMIT = 1000000;
	private static final int CORE_POOL_SIZE = 20;
	private static final int MAXIMUM_POOL_SIZE = 2000;	

	private ThreadPoolExecutor executor;

	public EmailService() {
		LOGGER.fine("Service Initializating....");
	}

	/**
	 * Starts the service
	 */
	public void start() {
		LOGGER.fine("Service Starting...");
		BlockingQueue<Runnable> threadPool = new LinkedBlockingQueue<Runnable>();
		this.executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
				threadPool);
	}

	/**
	 * Checks if the server is ready for new task execution
	 * 
	 * @return true if server is ready, false otherwise
	 */
	public boolean isReady() {
		return this.executor.getQueue().size() <= QUEUE_LIMIT;
	}

	/**
	 * Performs email sending job
	 * 
	 * @param email
	 *            Email Address
	 * @param firstName
	 *            First Name
	 * @param lastName
	 *            Last Name
	 * @return true if can be sent, false if queue is full
	 */
	public void sendEMail(String email, String firstName, String lastName) {
		this.executor.execute(new SendEmailJob(email, firstName, lastName));

	}

	/**
	 * Stops the service
	 */
	public void stop() throws InterruptedException {
		LOGGER.fine("Stopping");
		this.executor.shutdown();
		this.executor.awaitTermination(1, TimeUnit.DAYS);
		LOGGER.fine("Service Stopped.");
	}

}
