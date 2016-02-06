package xpd.mavenagent;

import shared.AgentProperties;
import shared.CommonProperties;
import shared.ResultQueue;
import shared.TaskQueue;
import threads.ThreadManager;

/**
 * Hello world!
 *
 */
public class agent 
{
	/**
	 * 
	 * @param args of the <b>function</b> main test javadoc e!
	 * @return nothing at all
	 */
    public static void main( String[] args ) {
    	CommonProperties.init();
    	AgentProperties.init();
		ResultQueue.init();
		TaskQueue.init();
		
		final ThreadManager m = ThreadManager.getInstance();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				m.end();				
			}
		});
		
		m.begin();
    }
}
