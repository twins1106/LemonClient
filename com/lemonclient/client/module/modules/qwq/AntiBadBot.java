//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "AntiBadBot", category = Category.qwq)
public class AntiBadBot extends Module
{
    BooleanSetting check;
    @EventHandler
    private final Listener<ClientChatReceivedEvent> listener;
    
    public AntiBadBot() {
        this.check = this.registerBoolean("Server Check", false);
        this.listener = (Listener<ClientChatReceivedEvent>)new Listener(event -> {
            if (AntiBadBot.mc.world == null || AntiBadBot.mc.player == null || AntiBadBot.mc.player.isDead) {
                return;
            }
            if (this.check.getValue()) {
                if (AntiBadBot.mc.getCurrentServerData() == null) {
                    return;
                }
                if (!AntiBadBot.mc.getCurrentServerData().serverIP.equals("5b5t.org")) {
                    return;
                }
            }
            if ((event.getMessage().getUnformattedText().startsWith("moooomoooo joined the game.") && event.getMessage().getUnformattedText().endsWith("moooomoooo joined the game.")) || (event.getMessage().getUnformattedText().startsWith("BibleB0T joined the game.") && event.getMessage().getUnformattedText().endsWith("BibleB0T joined the game."))) {
                MessageBus.sendServerMessage("!online .-.");
            }
        }, new Predicate[0]);
    }
}
