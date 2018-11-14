package com.hks.springthriftcommon.thrift;

import com.hks.springthriftcommon.zookeeper.ThriftServerAddressRegister;
import com.hks.springthriftcommon.zookeeper.ThriftServerIpResolve;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 *
 * 服务端注册服务工厂
 */
public class ThriftServiceServerFactory implements InitializingBean {

    //服务注册本机端口
    private Integer port = 8299;
    //优先级
    private Integer weight = 1;
    //服务实现类
    private Object service;
    //服务版本号
    private String version;
    //服务注册
    private ThriftServerAddressRegister thriftServerAddressRegister;
    //解析本机IP
    private ThriftServerIpResolve thriftServerIpResolve;

    private ServerThread serverThread;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    class ServerThread extends Thread {
        private TServer server;

        ServerThread(TProcessor processor, int port) throws Exception {
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
            TProcessorFactory processorFactory = new TProcessorFactory(processor);
            tArgs.processorFactory(processorFactory);
            tArgs.transportFactory(new TFramedTransport.Factory());
            tArgs.protocolFactory(new TBinaryProtocol.Factory(true, true));
            server = new TThreadedSelectorServer(tArgs);
        }

        @Override
        public void run() {
            try {
                //启动服务
                server.serve();
            } catch (Exception e) {
                //
            }
        }

        public void stopServer() {
            server.stop();
        }
    }

    public void close() {
        serverThread.stopServer();
    }
}
