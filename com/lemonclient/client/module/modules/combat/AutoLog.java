//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.function.*;

@Module.Declaration(name = "AutoLog", category = Category.Combat)
public class AutoLog extends Module
{
    IntegerSetting tots;
    IntegerSetting hp;
    
    public AutoLog() {
        this.tots = this.registerInteger("Totems", 1, 0, 36);
        this.hp = this.registerInteger("Health", 12, 0, 36);
    }
    
    public void onUpdate() {
        if (AutoLog.mc.player.getHealth() + AutoLog.mc.player.getAbsorptionAmount() > (int)this.hp.getValue() && AutoLog.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum() < (int)this.tots.getValue()) {
            AutoLog.mc.player.connection.getNetworkManager().handleDisconnection();
        }
    }
}
