# rocketmq-jsonrpc-service
json rpc service for rocketmq message publishing

#rpcservice server command line:
java -jar rmq.jar -n namesrv:9876 -p 8088

Option "-n (--nameserver)" is required
 -n (--nameserver) VAL : nameserver address
 -p (--port) N         : listenning port, default 8088

# client requesting:
curl -X POST \
  http://127.0.0.1:8088/rmq \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 8730c9ae-818d-4de1-a07f-77fbc403eb54' \
  -H 'cache-control: no-cache' \
  -d '{"jsonrpc":"2.0", "id":"10", "method":"producer_sendSync", "params":["group", "topic", "tag", "body"]}'
