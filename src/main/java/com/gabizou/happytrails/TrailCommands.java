package com.gabizou.happytrails;

import static org.spongepowered.api.command.args.GenericArguments.choices;
import static org.spongepowered.api.command.args.GenericArguments.firstParsing;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.ChildCommandElementExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.function.Function;
import java.util.stream.Collectors;

public class TrailCommands {
    static final String INDENT = "    ";
    static final String LONG_INDENT = INDENT + INDENT;

    static final Text INDENT_TEXT = Text.of(INDENT);
    static final Text NEWLINE_TEXT = Text.NEW_LINE;
    static final Text SEPARATOR_TEXT = Text.of(", ");
    static final Text LIST_ITEM_TEXT = Text.of(TextColors.DARK_AQUA, "- ");
    static final Text UNKNOWN = Text.of("UNKNOWN");

    public static CommandSpec getCommand() {
        final ChildCommandElementExecutor flagChildren = new ChildCommandElementExecutor(null);
        final ChildCommandElementExecutor nonFlagChildren = new ChildCommandElementExecutor(flagChildren);
        nonFlagChildren.register(getSetTrailCommand(), "set", "setTrail", "settrail");
        nonFlagChildren.register(getRemoveTrailCommand(), "reset", "resetTrail", "resetTrail");
        return CommandSpec.builder()
            .description(Text.of("HappyTrails command"))
            .extendedDescription(Text.of("commands:\n",
                INDENT, title("set"), LONG_INDENT, "set a trail to you as a player"))
            .arguments(firstParsing(nonFlagChildren))
            .executor(nonFlagChildren)
            .build();
    }

    private static CommandSpec getSetTrailCommand() {
        return CommandSpec.builder()
            .permission("happytrails.command.set")
            .description(Text.of("sets a trail to you as a player"))
            .arguments(choices(Text.of("trail"), () -> TrailRegistry.getInstance().getAll().stream().map(Trail::getId).collect(Collectors.toList()), Function.identity()))
            .executor(((src, args) -> {
                if (!(src instanceof Player)) {
                    return CommandResult.success();
                }
                final String trailId = args.<String>getOne("trail").get();
                final Trail trail = TrailRegistry.getInstance().getById(trailId).orElseGet(() -> TrailRegistry.getInstance().getDefaultTrail());
                HappyTrails.getInstance().setPlayer((Player) src, trail);
                return CommandResult.success();
            })).build();
    }

    private static CommandSpec getRemoveTrailCommand() {
        return CommandSpec.builder()
            .permission("happytrails.command.reset")
            .description(Text.of("Resets your trail"))
            .executor((src, args) -> {
                if (!(src instanceof Player)) {
                    return CommandResult.empty();
                }
                HappyTrails.getInstance().removePlayer(((Player) src));
                return CommandResult.success();
            })
            .build();
    }

    private static CommandSpec getAddTrailCommand() {
        return CommandSpec.builder()
            .permission("happytrails.command.add")
            .description(Text.of("Adds a new trail"))
            .executor((src, args) -> {
                if (!(src instanceof Player)) {
                    return CommandResult.success();
                }
                Inventory.builder()
                    .of(InventoryArchetypes.CHEST)
                    .property("title", new InventoryTitle(Text.of(TextColors.AQUA, "Create a Trail")))

            })


    }

    static Text title(String title) {
        return Text.of(TextColors.BLUE, title);
    }

}
