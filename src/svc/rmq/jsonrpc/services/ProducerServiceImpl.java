package svc.rmq.jsonrpc.services;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import svc.rmq.jsonrpc.config.Config;
import svc.rmq.jsonrpc.pojo.ProducerSendResult;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class ProducerServiceImpl implements ProducerService {
	
	@Override
	public ProducerSendResult sendSync(String groupID, String topic, String tag, String body) {
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
		        .withinRange('0', 'z')
		        .filteredBy(LETTERS, DIGITS)
		        .build();
		String instName = generator.generate(8);
		
		DefaultMQProducer producer = new DefaultMQProducer(groupID);
        producer.setNamesrvAddr(Config.getNameserver());
        producer.setInstanceName(instName);
		
        ProducerSendResult pdcSendResult = null;
        try {
        	producer.start();
        	Message msg = new Message(topic, tag, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        	SendResult sendResult = producer.send(msg);
	        
	        pdcSendResult = new ProducerSendResult(ProducerSendResult.SEND_OK, null, sendResult);
        } catch (Exception e) {
            e.printStackTrace();
            pdcSendResult = new ProducerSendResult(ProducerSendResult.SEND_FAILED, e.getMessage(), null);
        } finally {
        	producer.shutdown();
        }
        
        return pdcSendResult;
	}
}
