package com.github.fefo.worldreset.messages;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

import java.time.Duration;

import static com.github.fefo.worldreset.util.Utils.longDuration;
import static com.github.fefo.worldreset.util.Utils.shortDuration;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

public interface Message {

  Component PREFIX =
      text()
          .color(GRAY)
          .append(text('['),
                  text("WR", GOLD, BOLD),
                  text(']'))
          .build();

  Args1<Plugin> PLUGIN_INFO = plugin ->
      text()
          .color(YELLOW)
          .append(text("WorldReset", GOLD),
                  space(),
                  text("by Fefo"),
                  text(" - ", GRAY),
                  text('v'),
                  text(plugin.getDescription().getVersion()));

  Args0 NO_PERMISSION = () ->
      prefixed()
          .append(text("You are not allowed to run this command", RED));

  Args0 CONSOLE_INCOMPLETE_COMMAND = () ->
      prefixed()
          .append(text("Please provide a world from console", RED));

  Args2<String, Duration> SCHEDULED_SUCCESSFULLY = (world, interval) ->
      prefixed()
          .color(GRAY)
          .append(text("World reset scheduled successfully"),
                  text()
                      .append(join(space(),
                                   text("World"),
                                   text(world, AQUA),
                                   text("will reset every"),
                                   text(shortDuration(interval), GREEN)
                                       .hoverEvent(showText(text(longDuration(interval)))))));

  Args1<String> UNSCHEDULED_SUCCESSFULLY = world ->
      prefixed()
          .color(GRAY)
          .append(join(space(),
                       text("World"),
                       text(world, AQUA),
                       text("has been unscheduled for reset")));

  Args1<String> WASNT_SCHEDULED = world ->
      prefixed()
          .color(GRAY)
          .append(join(space(),
                       text("World"),
                       text(world, AQUA),
                       text("was not scheduled")));

  Args1<String> RESETTING_NOW = world ->
      prefixed()
          .color(GRAY)
          .append(join(space(),
                       text("Resetting"),
                       text(world, AQUA),
                       text("now!")));

  Args0 LIST_SCHEDULED_RESETS_TITLE = () ->
      prefixed()
          .append(text("Worlds scheduled to reset:", WHITE),
                  text()
                      .color(GRAY)
                      .append(join(text(" - "),
                                   text("world"),
                                   text("next reset"),
                                   text("interval"))));

  Args3<String, Duration, Duration> LIST_SCHEDULED_RESETS_ELEMENT = (world, until, interval) ->
      prefixed()
          .append(join(text(" - ", GRAY),
                       text(world, AQUA),
                       text(shortDuration(until), GREEN)
                           .hoverEvent(showText(text(longDuration(until), WHITE))),
                       text(shortDuration(interval), GREEN)
                           .hoverEvent(showText(text(longDuration(interval), WHITE)))));

  Args0 LIST_SCHEDULED_RESETS_NO_ELEMENT = () ->
      prefixed()
          .append(text("There are no scheduled resets", GRAY));

  Args0 ERROR_WHILE_SAVING = () ->
      prefixed()
          .color(RED)
          .append(text("There was an error while saving scheduled data"),
                  text("Please check console for any errors"));

  Args0 USAGE_TITLE = () ->
      prefixed()
          .append(text("Usage(s):", WHITE));

  Args1<String> USAGES_COMMAND = usage ->
      text()
          .color(RED)
          .append(text('/'),
                  text(usage))
          .hoverEvent(showText(text()
                                   .append(text("Click to run: ", WHITE),
                                           text('/', GRAY),
                                           text(usage, GRAY))))
          .clickEvent(suggestCommand('/' + usage));

  Args1<String> UNKNOWN_WORLD = unknownWorld ->
      prefixed()
          .color(RED)
          .append(join(space(),
                       text("No world for name"),
                       text(unknownWorld, AQUA),
                       text("was found")));

  Args1<String> COMMAND_ERROR = error ->
      prefixed()
          .append(text(error, RED));

  static TextComponent.Builder prefixed() {
    return TextComponent.ofChildren(PREFIX, space()).toBuilder().resetStyle();
  }

  @FunctionalInterface
  interface Args0 {

    default void sendMessage(final Audience audience) {
      audience.sendMessage(build());
    }

    default String legacy() {
      return legacySection().serialize(build().asComponent());
    }

    ComponentLike build();
  }

  @FunctionalInterface
  interface Args1<T> {

    default void sendMessage(final Audience audience, final T t) {
      audience.sendMessage(build(t));
    }

    default String legacy(final T t) {
      return legacySection().serialize(build(t).asComponent());
    }

    ComponentLike build(T t);
  }

  @FunctionalInterface
  interface Args2<T, S> {

    default void sendMessage(final Audience audience, final T t, final S s) {
      audience.sendMessage(build(t, s));
    }

    default String legacy(final T t, final S s) {
      return legacySection().serialize(build(t, s).asComponent());
    }

    ComponentLike build(T t, S s);
  }

  @FunctionalInterface
  interface Args3<T, S, R> {

    default void sendMessage(final Audience audience, final T t, final S s, final R r) {
      audience.sendMessage(build(t, s, r));
    }

    default String legacy(final T t, final S s, final R r) {
      return legacySection().serialize(build(t, s, r).asComponent());
    }

    ComponentLike build(T t, S s, R r);
  }

  @FunctionalInterface
  interface Args4<T, S, R, Q> {

    default void sendMessage(final Audience audience, final T t, final S s, final R r, final Q q) {
      audience.sendMessage(build(t, s, r, q));
    }

    default String legacy(final T t, final S s, final R r, final Q q) {
      return legacySection().serialize(build(t, s, r, q).asComponent());
    }

    ComponentLike build(T t, S s, R r, Q q);
  }

  @FunctionalInterface
  interface Args5<T, S, R, Q, P> {

    default void sendMessage(
        final Audience audience, final T t, final S s, final R r, final Q q, final P p) {
      audience.sendMessage(build(t, s, r, q, p));
    }

    default String legacy(final T t, final S s, final R r, final Q q, final P p) {
      return legacySection().serialize(build(t, s, r, q, p).asComponent());
    }

    ComponentLike build(T t, S s, R r, Q q, P p);
  }
}
