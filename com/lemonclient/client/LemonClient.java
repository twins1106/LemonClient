//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client;

import net.minecraftforge.fml.common.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.client.settings.*;
import com.lemonclient.api.util.font.*;
import com.lemonclient.client.clickgui.*;
import com.lemonclient.api.util.log4j.*;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.client.registry.*;
import java.awt.*;
import com.lemonclient.api.config.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.command.*;
import com.lemonclient.client.manager.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.nio.*;
import com.lemonclient.api.util.misc.*;
import java.io.*;
import org.apache.logging.log4j.*;
import me.zero.alpine.bus.*;
import java.util.*;

@Mod(modid = "lemonclient", name = "Lemon Client", version = "v0.0.6")
public class LemonClient
{
    public static final String MODNAME = "Lemon Client";
    public static final String MODID = "lemonclient";
    public static final String MODVER = "v0.0.6";
    public static final Logger LOGGER;
    public static final EventBus EVENT_BUS;
    public static List<String> hwidList;
    public static ServerUtil serverUtil;
    public static SpeedUtil speedUtil;
    public static Runtime runtime;
    public static KeyBinding PacketXPBind;
    public static KeyBinding AutoStringBind;
    public static KeyBinding ForcePush;
    @Mod.Instance
    public static LemonClient INSTANCE;
    public CFontRenderer cFontRenderer;
    public LemonClientGUI gameSenseGUI;
    
    @Mod.EventHandler
    public void construct(final FMLConstructionEvent event) {
        try {
            Fixer.disableJndiManager();
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Fixer.doRuntimeTest(event.getModLog());
    }
    
    public LemonClient() {
        LemonClient.INSTANCE = this;
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Display.setTitle("Lemon Client v0.0.6");
        setWindowIcon();
        LemonClient.LOGGER.info("Starting up Lemon Client v0.0.6!");
        this.startClient();
        LemonClient.LOGGER.info("Finished initialization for Lemon Client v0.0.6!");
        LemonClient.AutoStringBind = new KeyBinding("AutoString", 0, "Lemon Client");
        LemonClient.PacketXPBind = new KeyBinding("PacketXP", 0, "Lemon Client");
        LemonClient.ForcePush = new KeyBinding("ForceHolePush", 0, "Lemon Client");
        ClientRegistry.registerKeyBinding(LemonClient.AutoStringBind);
        ClientRegistry.registerKeyBinding(LemonClient.PacketXPBind);
        ClientRegistry.registerKeyBinding(LemonClient.ForcePush);
    }
    
    private void startClient() {
        this.cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
        LoadConfig.init();
        ModuleManager.init();
        CommandManager.init();
        ManagerLoader.init();
        this.gameSenseGUI = new LemonClientGUI();
        CapeUtil.init();
        LoadConfig.init();
        LemonClient.serverUtil = new ServerUtil();
        LemonClient.speedUtil = new SpeedUtil();
    }
    
    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (final InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/lemonclient/icons/icon-16x.png");
                 final InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/lemonclient/icons/icon-32x.png")) {
                final ByteBuffer[] icons = { IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x) };
                Display.setIcon(icons);
            }
            catch (Exception ex) {}
        }
    }
    
    static {
        LOGGER = LogManager.getLogger("Lemon Client");
        EVENT_BUS = (EventBus)new EventManager();
        LemonClient.hwidList = new ArrayList<String>();
        LemonClient.runtime = Runtime.getRuntime();
    }
}
