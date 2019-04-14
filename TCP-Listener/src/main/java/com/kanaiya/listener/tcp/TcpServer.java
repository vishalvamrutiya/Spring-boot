package com.kanaiya.listener.tcp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class TcpServer implements Server, Connection.Listener {
    private static Log logger = LogFactory.getLog(TcpServer.class);

    private ServerSocket serverSocket;
    private volatile boolean isStop;
    private List<Connection> connections = new ArrayList<>();
    private List<Connection.Listener> listeners = new ArrayList<>();

    public void setPort(Integer port) {
        try {
            if (port == null) {
                logger.info("Property tcp.server.port not found. Use default port 1234");
                port = 1234;
            }
            serverSocket = new ServerSocket(port);
            logger.info("Server start at port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("May be port " + port + " busy.");
        }
    }

    @Override
    public int getConnectionsCount() {
        return connections.size();
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (!isStop) {
                try {
                	  logger.info("Received new message from -1" );
                    Socket socket = serverSocket.accept();
                    if (socket.isConnected()) {
                    	 logger.info("Received new message from 0" );

                    	TcpConnection tcpConnection = new TcpConnection(socket);
                    	 logger.info("Received new message from 1" );
                        tcpConnection.start();
                        logger.info("Received new message from 2" );
                        tcpConnection.addListener(this);
                        logger.info("Received new message from 3" );
                        connected(tcpConnection);
                        logger.info("Received new message from 4" );
                        tcpConnection.send("Hello from server".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public void addListener(Connection.Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void messageReceived(Connection connection, Object message) {
    	String str = new String((byte[])message);
        logger.info("Received new message from " + connection.getAddress().getCanonicalHostName());
        logger.info("Class name: " + message.getClass().getCanonicalName() + ", toString: " +str);
        for (Connection.Listener listener : listeners) {
            listener.messageReceived(connection, message);
        }
    }

    @Override
    public void connected(Connection connection) {
        logger.info("New connection! Ip: " + connection.getAddress().getCanonicalHostName() + ".");
        connections.add(connection);
        logger.info("Current connections count: " + connections.size());
        for (Connection.Listener listener : listeners) {
            listener.connected(connection);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        logger.info("Disconnect! Ip: " + connection.getAddress().getCanonicalHostName() + ".");
        connections.remove(connection);
        logger.info("Current connections count: " + connections.size());
        for (Connection.Listener listener : listeners) {
            listener.disconnected(connection);
        }
    }
}
