//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command;

import net.minecraft.client.*;
import java.lang.annotation.*;

public abstract class Command
{
    protected static final Minecraft mc;
    private final String name;
    private final String[] alias;
    private final String syntax;
    
    public Command() {
        this.name = this.getDeclaration().name();
        this.alias = this.getDeclaration().alias();
        this.syntax = this.getDeclaration().syntax();
    }
    
    private Declaration getDeclaration() {
        return this.getClass().getAnnotation(Declaration.class);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSyntax() {
        return CommandManager.getCommandPrefix() + this.syntax;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public abstract void onCommand(final String p0, final String[] p1, final boolean p2);
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface Declaration {
        String name();
        
        String syntax();
        
        String[] alias();
    }
}
