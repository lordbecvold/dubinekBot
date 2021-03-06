package me.lordbecvold.bot.command.commands;

import me.lordbecvold.bot.command.ICommand;
import me.lordbecvold.bot.utils.service.ServerStatusUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class ServerStatusCommand implements ICommand {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        // Set msg
        EmbedBuilder usage = new EmbedBuilder();
        usage.setColor(Color.DARK_GRAY);
        usage.setTitle("Server Status");

        // Set status of tor server
        if (ServerStatusUtils.ifServiceRunning("ts3server")) {
            usage.addField("TeamSpeak server", "Online", false);
        } else {
            usage.addField("TeamSpeak server", "Offline", false);
        }

        // Set status of web server
        if (ServerStatusUtils.ifServiceRunning("apache2")) {
            usage.addField("Web server", "Online", false);
        } else {
            usage.addField("Web server", "Offline", false);
        }

        // Set status of database server
        if (ServerStatusUtils.ifServiceRunning("mysql") || ServerStatusUtils.ifServiceRunning("mariadb")) {
            usage.addField("Database server", "Online", false);
        } else {
            usage.addField("Database server", "Offline", false);
        }

        // Set status of mincraft server
        if (ServerStatusUtils.hostAvailabilityCheck("185.252.233.220", 25565)) {
            usage.addField("Minecraft server", "Online", false);
        } else {
            usage.addField("Minecraft server", "Offline", false);
        }

        // Set footer
        usage.setFooter(event.getAuthor().getAsTag() + " use this command", event.getAuthor().getAvatarUrl());

        // Send msg to discord channel
        event.getChannel().sendMessageEmbeds(usage.build()).queue();
    }

    @Override
    public String getCommand() {
        return "serverstatus";
    }

    @Override
    public String getHelp() {
        return "Show server status";
    }
}
