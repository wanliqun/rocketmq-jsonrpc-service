package svc.rmq.jsonrpc.pojo;

import org.apache.rocketmq.client.producer.SendResult;

public class ProducerSendResult {
	public final static int SEND_OK = 0;
	public final static int SEND_FAILED = -1;

	public int status;
	public String message;
	public SendResult data;
	
	public ProducerSendResult(int status, String message, SendResult data) {
		this.status = status < 0 ? -1 : 0;
		this.message = message;
		this.data = data;
	}
}
