package svc.rmq.jsonrpc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.googlecode.jsonrpc4j.AnnotationsErrorResolver;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.ProxyUtil;

import svc.rmq.jsonrpc.services.ProducerService;
import svc.rmq.jsonrpc.services.ProducerServiceImpl;

public class JsonRpcServelet extends HttpServlet {
	static final long serialVersionUID = 1L;

	private ProducerService rmqProducerService;
	private Object compositeService;

	private transient JsonRpcServer jsonRpcServer;

	@Override
	public void init() {
		try {
			rmqProducerService = new ProducerServiceImpl();
			compositeService = ProxyUtil.createCompositeServiceProxy(this.getClass().getClassLoader(),
					new Object[] { rmqProducerService }, new Class<?>[] { ProducerService.class }, true);

			jsonRpcServer = new JsonRpcServer(compositeService);
			jsonRpcServer.setErrorResolver(AnnotationsErrorResolver.INSTANCE);
			Logger logger = LoggerFactory.getLogger(JsonRpcServer.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		jsonRpcServer.handle(request, response);
	}
}
