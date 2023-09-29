//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import java.awt.*;

@Module.Declaration(name = "Coordinates", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 0)
public class Coordinates extends HUDModule
{
    BooleanSetting showNetherOverworld;
    BooleanSetting thousandsSeparator;
    IntegerSetting decimalPlaces;
    private final String[] coordinateString;
    @EventHandler
    private final Listener<TickEvent.ClientTickEvent> listener;
    
    public Coordinates() {
        this.showNetherOverworld = this.registerBoolean("Show Nether", true);
        this.thousandsSeparator = this.registerBoolean("Thousands Separator", true);
        this.decimalPlaces = this.registerInteger("Decimal Places", 1, 0, 5);
        this.coordinateString = new String[] { "", "" };
        this.listener = (Listener<TickEvent.ClientTickEvent>)new Listener(event -> {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }
            Entity viewEntity = Coordinates.mc.getRenderViewEntity();
            final EntityPlayerSP player = Coordinates.mc.player;
            if (viewEntity == null) {
                if (player == null) {
                    return;
                }
                viewEntity = (Entity)player;
            }
            final int dimension = viewEntity.dimension;
            this.coordinateString[0] = "XYZ " + this.getFormattedCoords(viewEntity.posX, viewEntity.posY, viewEntity.posZ);
            switch (dimension) {
                case -1: {
                    this.coordinateString[1] = "Overworld " + this.getFormattedCoords(viewEntity.posX * 8.0, viewEntity.posY, viewEntity.posZ * 8.0);
                    break;
                }
                case 0: {
                    this.coordinateString[1] = "Nether " + this.getFormattedCoords(viewEntity.posX / 8.0, viewEntity.posY, viewEntity.posZ / 8.0);
                    break;
                }
            }
        }, new Predicate[0]);
    }
    
    private String getFormattedCoords(final double x, final double y, final double z) {
        return this.roundOrInt(x) + ", " + this.roundOrInt(y) + ", " + this.roundOrInt(z);
    }
    
    private String roundOrInt(final double input) {
        String separatorFormat;
        if (this.thousandsSeparator.getValue()) {
            separatorFormat = ",";
        }
        else {
            separatorFormat = "";
        }
        return String.format('%' + separatorFormat + '.' + this.decimalPlaces.getValue() + 'f', input);
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), new CoordinateLabel(), 9, 1);
    }
    
    private class CoordinateLabel implements HUDList
    {
        @Override
        public int getSize() {
            final EntityPlayerSP player = Coordinates.mc.player;
            final int dimension = (player != null) ? player.dimension : 1;
            if ((boolean)Coordinates.this.showNetherOverworld.getValue() && (dimension == -1 || dimension == 0)) {
                return 2;
            }
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            return Coordinates.this.coordinateString[index];
        }
        
        @Override
        public Color getItemColor(final int index) {
            return new Color(255, 255, 255);
        }
        
        @Override
        public boolean sortUp() {
            return false;
        }
        
        @Override
        public boolean sortRight() {
            return false;
        }
    }
}
