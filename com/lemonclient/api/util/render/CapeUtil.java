//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.render;

import java.net.*;
import java.io.*;
import java.util.*;

public class CapeUtil
{
    private static final List<UUID> uuids;
    
    public static void init() {
        try {
            final URL capesList = new URL("https://raw.githubusercontent.com/OaDwH/CapeUUID/main/list.txt");
            final BufferedReader in = new BufferedReader(new InputStreamReader(capesList.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                CapeUtil.uuids.add(UUID.fromString(inputLine));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean hasCape(final UUID id) {
        return CapeUtil.uuids.contains(id);
    }
    
    static {
        uuids = new ArrayList<UUID>();
    }
}
