package com.kanaiya.listener.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kanaiya.tcp-server")
public class TcpServerProperties {

    private int port;

    private boolean autostart;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getAutostart() {
        return autostart;
    }

    public void setAutostart(boolean autoStart) {
        this.autostart = autoStart;
    }
}
