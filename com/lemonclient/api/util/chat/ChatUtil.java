//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.chat;

import java.util.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.text.*;
import java.util.concurrent.*;
import java.util.function.*;
import net.minecraft.client.gui.*;

public class ChatUtil extends SubscriberImpl
{
    private static final Map<Integer, Map<String, Integer>> message_ids;
    private static final SkippingCounter counter;
    
    public void clear() {
        if (BurrowUtil.mc.ingameGUI != null) {
            ChatUtil.message_ids.values().forEach(m -> m.values().forEach(id -> BurrowUtil.mc.ingameGUI.getChatGUI().deleteChatLine((int)id)));
        }
        ChatUtil.message_ids.clear();
        ChatUtil.counter.reset();
    }
    
    public static void sendMessage(final String message) {
        sendMessage(message, 0);
    }
    
    public static void sendClientMessage(final String append, final String modulename) {
        sendDeleteMessage(append, modulename, 1000);
    }
    
    public static void sendMessage(final String message, final int id) {
        sendComponent((ITextComponent)new TextComponentString((message == null) ? "null" : message), id);
    }
    
    public static void sendComponent(final ITextComponent component) {
        sendComponent(component, 0);
    }
    
    public static void sendComponent(final ITextComponent c, final int id) {
        applyIfPresent(g -> g.printChatMessageWithOptionalDeletion(c, id));
    }
    
    public void sendDeleteMessageScheduled(final String message, final String uniqueWord, final int senderID) {
        final Integer id = ChatUtil.message_ids.computeIfAbsent(Integer.valueOf(senderID), v -> new ConcurrentHashMap()).computeIfAbsent(uniqueWord, v -> ChatUtil.counter.next());
        BurrowUtil.mc.addScheduledTask(() -> sendMessage(message, id));
    }
    
    public static void sendDeleteMessage(final String message, final String uniqueWord, final int senderID) {
        final Integer id = ChatUtil.message_ids.computeIfAbsent(Integer.valueOf(senderID), v -> new ConcurrentHashMap()).computeIfAbsent(uniqueWord, v -> ChatUtil.counter.next());
        sendMessage(message, id);
    }
    
    public void deleteMessage(final String uniqueWord, final int senderID) {
        final Map<String, Integer> map = ChatUtil.message_ids.get(senderID);
        if (map != null) {
            final Integer id = map.remove(uniqueWord);
            if (id != null) {
                deleteMessage(id);
            }
        }
    }
    
    public static void deleteMessage(final int id) {
        applyIfPresent(g -> g.deleteChatLine(id));
    }
    
    public static void applyIfPresent(final Consumer<GuiNewChat> consumer) {
        final GuiNewChat chat = getChatGui();
        if (chat != null) {
            consumer.accept(chat);
        }
    }
    
    public static GuiNewChat getChatGui() {
        if (BurrowUtil.mc.ingameGUI != null) {
            return BurrowUtil.mc.ingameGUI.getChatGUI();
        }
        return null;
    }
    
    static {
        message_ids = new ConcurrentHashMap<Integer, Map<String, Integer>>();
        counter = new SkippingCounter(1337, i -> i != -1);
    }
}
