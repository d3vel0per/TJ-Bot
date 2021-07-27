package gg.discord.tj.bot.command.impl;

import discord4j.core.object.entity.User;
import gg.discord.tj.bot.app.Application;
import gg.discord.tj.bot.command.Command;
import gg.discord.tj.bot.command.CommandExecutionContext;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagCommand
    implements Command
{
    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public Collection<String> getAliases() {
        return List.of("t", "?");
    }

    @Override
    public Mono<Void> onExecute(CommandExecutionContext context)
    {
        Map<String, String> tags = Application.BOT_INSTANCE.getAvailableTags();
        String users = context.message().getUserMentions()
            .toStream()
            .map(User::getMention)
            .collect(Collectors.joining(", "));
        return context.message()
            .getChannel()
            .flatMap(channel -> channel == null ?
                Mono.empty() :
                channel.createMessage(tags.get(context.commandContent().split(" ")[0])
                .replace("{{ user }}", users.isEmpty() ? "" : " " + users)))
            .then();
    }
}
