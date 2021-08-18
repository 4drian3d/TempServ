package com.aang23.tempserv;

import java.net.InetSocketAddress;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.server.ServerInfo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CommandTempServ implements SimpleCommand {

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length > 0) {
            if (args[0].equals("add") && args.length == 4) {
                ServerInfo toRegister = new ServerInfo(args[1],
                        new InetSocketAddress(args[2], Integer.parseInt(args[3])));
                TempServ.registeredServers.put(args[1], toRegister);
                TempServ.server.registerServer(toRegister);

                source.sendMessage(
                        Component.text("Registered server " + 
                            args[1] + " with adress " + 
                            args[2] + ":" + 
                            args[3], 
                        NamedTextColor.GREEN));
            } else if (args[0].equals("del") && args.length == 2) {
                TempServ.server.unregisterServer(TempServ.registeredServers.get(args[1]));
                source.sendMessage(Component.text("Deleted server " + 
                    args[1], 
                    NamedTextColor.GREEN));
            } else if (args[0].equals("list")) {
                for (ServerInfo current : TempServ.registeredServers.values()) {
                    source.sendMessage(
                            Component.text("Server " + current.getName() + " : " + current.getAddress().toString(), 
                                NamedTextColor.YELLOW));
                }
            } else
                source.sendMessage(Component.text("Usage : /tempserv add [name] [adress] [port] or /tempserv del [name] or /tempserv list", 
                    NamedTextColor.RED));
        } else
            source.sendMessage(Component.text("Usage : /tempserv add [name] [adress] [port] or /tempserv del [name] or /tempserv list", 
                NamedTextColor.RED));
    }
}