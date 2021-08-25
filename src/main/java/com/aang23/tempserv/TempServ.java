package com.aang23.tempserv;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Plugin(id = "tempserv", 
    name = "TempServ", 
    version = "2.0", 
    description = "A plugin", 
    authors = { "Aang23" })

public class TempServ {
    public static ProxyServer server;
    public static Logger logger;

    public static Map<String, ServerInfo> registeredServers = new HashMap<String, ServerInfo>();

    @Inject
    public TempServ(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
		server.getCommandManager().register("tempserv", new CommandTempServ());
    }
}
