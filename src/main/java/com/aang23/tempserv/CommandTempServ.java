package com.aang23.tempserv;

import java.net.InetSocketAddress;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.server.ServerInfo;

import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;
import static net.kyori.adventure.text.event.HoverEvent.showText;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class CommandTempServ implements SimpleCommand {
    static final Component usage = text()
        .append(text()
            .append(text("Usage:", RED))
        )
        .append(space())
        .append(text()
            .append(text("/tempserv add [server] [adress] [port]", AQUA))
            .clickEvent(suggestCommand("tempserv add"))
            .hoverEvent(showText(text("Click to execute", YELLOW)))
        )
        .append(space())
        .append(text()
            .append(text("or", RED))
        )
        .append(space())
        .append(text()
            .append(text("/tempserv del [server]", AQUA))
            .clickEvent(suggestCommand("tempserv del"))
            .hoverEvent(showText(text("Click to execute", YELLOW)))
        )
        .append(space())
        .append(text()
            .append(text("or", RED))
        )
        .append(space())
        .append(text()
            .append(text("/tempserv list", AQUA))
            .clickEvent(suggestCommand("tempserv list"))
            .hoverEvent(showText(text("Click to execute", YELLOW)))
        )
        .build();
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
                        text("Registered server " + 
                            args[1] + " with address " + 
                            args[2] + ":" + 
                            args[3], 
                            GREEN));
            } else if (args[0].equals("del") && args.length == 2) {
                TempServ.server.unregisterServer(TempServ.registeredServers.get(args[1]));
                source.sendMessage(text("Deleted server " + args[1], GREEN));
            } else if (args[0].equals("list")) {
                for (ServerInfo current : TempServ.registeredServers.values()) {
                    source.sendMessage(
                            text("Server " + current.getName() + " : " + current.getAddress().toString(), YELLOW));
                }
            } else
                source.sendMessage(usage);
        } else
            source.sendMessage(usage);
    }
}