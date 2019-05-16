package svc.rmq.jsonrpc.cmd;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * This class handles the programs arguments.
 */
public class CommandLineValues {
    @Option(name = "-n", aliases = { "--nameserver" }, required = true,
            usage = "nameserver address")
    private String nameServer;
    
    @Option(name = "-p", aliases = { "--port" }, required = false,
    		usage = "listenning port")
    private int port = 8088;
    
    private boolean errorFree = false;

    public CommandLineValues(String... args) {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);
        try {
            parser.parseArgument(args);
            /*
            if (!getSource().isFile()) {
                throw new CmdLineException(parser, "--input is no valid input file.");
            }
            */

            errorFree = true;
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    /**
     * Returns whether the parameters could be parsed without an
     * error.
     *
     * @return true if no error occurred.
     */
    public boolean isErrorFree() {
        return errorFree;
    }

    /**
	 * @return the nameServer
	 */
	public String getNameServer() {
		return nameServer;
	}
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
}