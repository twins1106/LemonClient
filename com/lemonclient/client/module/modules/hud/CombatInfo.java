//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.render.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import com.lemonclient.client.module.modules.combat.*;
import com.lemonclient.client.module.*;
import net.minecraft.item.*;
import java.util.function.*;
import net.minecraft.client.entity.*;
import com.lemonclient.api.util.player.social.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.combat.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import java.awt.*;
import com.lemonclient.client.module.modules.dev.*;

@Module.Declaration(name = "CombatInfo", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 150)
public class CombatInfo extends HUDModule
{
    ModeSetting infoType;
    ColorSetting color1;
    ColorSetting color2;
    private final InfoList list;
    private static final BlockPos[] surroundOffset;
    private static final String[] hoosiersModules;
    private static final String[] hoosiersNames;
    
    public CombatInfo() {
        this.infoType = this.registerMode("Type", (List)Arrays.asList("Cyber", "Hoosiers"), "Hoosiers");
        this.color1 = this.registerColor("On", new GSColor(0, 255, 0, 255));
        this.color2 = this.registerColor("Off", new GSColor(255, 0, 0, 255));
        this.list = new InfoList();
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), this.list, 9, 1);
    }
    
    public void onRender() {
        final AutoCrystalRewrite autoCrystal = (AutoCrystalRewrite)ModuleManager.getModule((Class)AutoCrystalRewrite.class);
        this.list.totems = CombatInfo.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum() + ((CombatInfo.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) ? 1 : 0);
        this.list.players = (EntityOtherPlayerMP)CombatInfo.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityOtherPlayerMP).filter(entity -> !SocialManager.isFriend(entity.getName())).filter(e -> CombatInfo.mc.player.getDistance(e) <= (double)autoCrystal.placeRange.getValue()).map(entity -> entity).min(Comparator.comparing(cl -> CombatInfo.mc.player.getDistance(cl))).orElse(null);
        this.list.renderLby = false;
        final List<EntityPlayer> entities = new ArrayList<EntityPlayer>((Collection<? extends EntityPlayer>)CombatInfo.mc.world.playerEntities.stream().filter(entityPlayer -> !SocialManager.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
        for (final EntityPlayer e2 : entities) {
            int i = 0;
            for (final BlockPos add : CombatInfo.surroundOffset) {
                ++i;
                final BlockPos o = new BlockPos(e2.getPositionVector().x, e2.getPositionVector().y, e2.getPositionVector().z).add(add.getX(), add.getY(), add.getZ());
                if (CombatInfo.mc.world.getBlockState(o).getBlock() == Blocks.OBSIDIAN) {
                    if (i == 1 && CrystalUtil.canPlaceCrystal(o.north(1).down(), (boolean)autoCrystal.newPlace.getValue())) {
                        this.list.lby = true;
                        this.list.renderLby = true;
                    }
                    else if (i == 2 && CrystalUtil.canPlaceCrystal(o.east(1).down(), (boolean)autoCrystal.newPlace.getValue())) {
                        this.list.lby = true;
                        this.list.renderLby = true;
                    }
                    else if (i == 3 && CrystalUtil.canPlaceCrystal(o.south(1).down(), (boolean)autoCrystal.newPlace.getValue())) {
                        this.list.lby = true;
                        this.list.renderLby = true;
                    }
                    else if (i == 4 && CrystalUtil.canPlaceCrystal(o.west(1).down(), (boolean)autoCrystal.newPlace.getValue())) {
                        this.list.lby = true;
                        this.list.renderLby = true;
                    }
                }
                else {
                    this.list.lby = false;
                    this.list.renderLby = true;
                }
            }
        }
    }
    
    private static int getPing() {
        int p = -1;
        if (CombatInfo.mc.player == null || CombatInfo.mc.getConnection() == null || CombatInfo.mc.getConnection().getPlayerInfo(CombatInfo.mc.player.getName()) == null) {
            p = -1;
        }
        else {
            p = CombatInfo.mc.getConnection().getPlayerInfo(CombatInfo.mc.player.getName()).getResponseTime();
        }
        return p;
    }
    
    static {
        surroundOffset = new BlockPos[] { new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
        hoosiersModules = new String[] { "AutoCrystal", "KillAura", "Surround", "AutoTrap", "SelfTrap" };
        hoosiersNames = new String[] { "AC", "KA", "SU", "AT", "ST" };
    }
    
    private class InfoList implements HUDList
    {
        public int totems;
        public EntityOtherPlayerMP players;
        public boolean renderLby;
        public boolean lby;
        
        private InfoList() {
            this.totems = 0;
            this.players = null;
            this.renderLby = false;
            this.lby = false;
        }
        
        @Override
        public int getSize() {
            if (((String)CombatInfo.this.infoType.getValue()).equals("Hoosiers")) {
                return CombatInfo.hoosiersModules.length;
            }
            if (((String)CombatInfo.this.infoType.getValue()).equals("Cyber")) {
                return this.renderLby ? 6 : 5;
            }
            return 0;
        }
        
        @Override
        public String getItem(final int index) {
            if (((String)CombatInfo.this.infoType.getValue()).equals("Hoosiers")) {
                if (ModuleManager.isModuleEnabled(CombatInfo.hoosiersModules[index])) {
                    return CombatInfo.hoosiersNames[index] + ": ON";
                }
                return CombatInfo.hoosiersNames[index] + ": OFF";
            }
            else {
                if (!((String)CombatInfo.this.infoType.getValue()).equals("Cyber")) {
                    return "";
                }
                if (index == 0) {
                    return "Lemon Client v0.0.6";
                }
                if (index == 1) {
                    return "HTR";
                }
                if (index == 2) {
                    return "PLR";
                }
                if (index == 3) {
                    return "" + this.totems;
                }
                if (index == 4) {
                    return "PING " + getPing();
                }
                return "LBY";
            }
        }
        
        @Override
        public Color getItemColor(final int index) {
            final AutoCrystalRewrite autoCrystal = (AutoCrystalRewrite)ModuleManager.getModule((Class)AutoCrystalRewrite.class);
            if (((String)CombatInfo.this.infoType.getValue()).equals("Hoosiers")) {
                if (ModuleManager.isModuleEnabled(CombatInfo.hoosiersModules[index])) {
                    return (Color)CombatInfo.this.color1.getValue();
                }
                return (Color)CombatInfo.this.color2.getValue();
            }
            else {
                if (!((String)CombatInfo.this.infoType.getValue()).equals("Cyber")) {
                    return new Color(255, 255, 255);
                }
                boolean on = false;
                if (index == 0) {
                    on = true;
                }
                else if (index == 1) {
                    if (this.players != null) {
                        on = (CombatInfo.mc.player.getDistance((Entity)this.players) <= (double)autoCrystal.breakRange.getValue());
                    }
                }
                else if (index == 2) {
                    if (this.players != null) {
                        on = (CombatInfo.mc.player.getDistance((Entity)this.players) <= (double)autoCrystal.placeRange.getValue());
                    }
                }
                else if (index == 3) {
                    on = (this.totems > 0 && (ModuleManager.isModuleEnabled((Class)LemonOffHand.class) || ModuleManager.isModuleEnabled((Class)NewOffHand.class) || ModuleManager.isModuleEnabled((Class)NewOffHand2.class)));
                }
                else if (index == 4) {
                    on = (getPing() <= 100);
                }
                else {
                    on = this.lby;
                }
                if (on) {
                    return (Color)CombatInfo.this.color1.getValue();
                }
                return (Color)CombatInfo.this.color2.getValue();
            }
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
