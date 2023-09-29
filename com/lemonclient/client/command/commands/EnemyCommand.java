//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "Enemy", syntax = "enemy list/add/del [player]", alias = { "enemy", "enemies", "e" })
public class EnemyCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        if (main.equalsIgnoreCase("list")) {
            MessageBus.sendClientPrefixMessage("Enemies: " + SocialManager.getEnemiesByName() + "!");
            return;
        }
        final String value = message[1];
        if (main.equalsIgnoreCase("add") && !SocialManager.isEnemy(value)) {
            if (SocialManager.isOnFriendList(value)) {
                MessageBus.sendClientDeleteMessage(value + " is already your enemy.", "Enemy", 3000);
            }
            else {
                SocialManager.addEnemy(value);
                MessageBus.sendClientDeleteMessage("Added enemy: " + value, "Enemy", 3000);
            }
        }
        else if (main.equalsIgnoreCase("del") && SocialManager.isEnemy(value)) {
            if (SocialManager.isOnFriendList(value)) {
                SocialManager.delEnemy(value);
                MessageBus.sendClientDeleteMessage("Deleted enemy: " + value, "Enemy", 3000);
            }
            else {
                MessageBus.sendClientDeleteMessage(value + " isn't your enemy.", "Enemy", 3000);
            }
        }
    }
}
