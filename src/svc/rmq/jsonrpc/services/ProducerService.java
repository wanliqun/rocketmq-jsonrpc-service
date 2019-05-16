package svc.rmq.jsonrpc.services;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import svc.rmq.jsonrpc.pojo.ProducerSendResult;

public interface ProducerService {
	@JsonRpcMethod("producer_sendSync")
	ProducerSendResult sendSync(String groupID, String topic, String tag, String body);
}
