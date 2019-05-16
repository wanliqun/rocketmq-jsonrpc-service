package svc.rmq.jsonrpc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import svc.rmq.jsonrpc.cmd.CommandLineValues;
import svc.rmq.jsonrpc.config.Config;
import svc.rmq.jsonrpc.servlet.JsonRpcServelet;

public class RPCServiceMain {
	
	// entry point
	public static void main(String[] args) throws Exception {
		// parse command line
		CommandLineValues values = new CommandLineValues(args);
        CmdLineParser parser = new CmdLineParser(values);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.exit(1);
        }
        
        Config.setNameserver(values.getNameServer());
        Config.setPort(values.getPort());
		
        // start rpc service 
		try (RPCServiceImpl svcImpl = new RPCServiceImpl()) {
			svcImpl.startup();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	@SuppressWarnings("WeakerAccess")
	static class RPCServiceImpl implements AutoCloseable {
		private Server jetty;
		private String servletPathSpec;
		ServletHolder servletHolder;
		
		RPCServiceImpl() {
			servletPathSpec = "rmq";
		}
		
		public void startup() throws Exception {
			System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
			System.setProperty("org.eclipse.jetty.LEVEL", "INFO");
			// StdErrLog logger = new StdErrLog();
			// Log.setLog(logger);
			// logger.setLevel(StdErrLog.LEVEL_INFO);
			
			jetty = new Server(Config.getPort());
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			context.setContextPath("/");
			jetty.setHandler(context);
			
			servletHolder = context.addServlet(JsonRpcServelet.class, "/" + servletPathSpec);
			
			jetty.start();
			jetty.join();
		}
		
		@Override
		public void close() throws Exception {
			jetty.stop();
		}
	}
}
