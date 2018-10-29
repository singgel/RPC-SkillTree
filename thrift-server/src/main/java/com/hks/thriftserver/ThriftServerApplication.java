package com.hks.thriftserver;

import com.hks.thriftapi.service.Hello;
import com.hks.thriftserver.service.HelloServiceImpl;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThriftServerApplication {

	public static void main(String[] args) {
		try {
			TNonblockingServerSocket socket = new TNonblockingServerSocket(7911);
			Hello.Processor processor = new Hello.Processor(new HelloServiceImpl());
			TNonblockingServer.Args arg = new TNonblockingServer.Args(socket);
			arg.protocolFactory(new TBinaryProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			TServer server = new TNonblockingServer(arg);
			server.serve();
			SpringApplication.run(ThriftServerApplication.class, args);
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}
