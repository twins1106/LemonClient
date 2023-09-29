//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.function.*;
import com.mojang.realmsclient.gui.*;

@Module.Declaration(name = "32kTotem", category = Category.Combat)
public class Anti32kTotem extends Module
{
    IntegerSetting slot;
    
    public Anti32kTotem() {
        this.slot = this.registerInteger("Slot", 1, 1, 9);
    }
    
    public void onUpdate() {
        if ((!(Anti32kTotem.mc.currentScreen instanceof GuiContainer) || Anti32kTotem.mc.currentScreen instanceof GuiInventory) && Anti32kTotem.mc.player.inventory.getStackInSlot((int)this.slot.getValue() - 1).getItem() != Items.TOTEM_OF_UNDYING) {
            int i = 9;
            while (i < 36) {
                if (Anti32kTotem.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    if ((int)this.slot.getValue() == 1) {
                        Anti32kTotem.mc.playerController.windowClick(Anti32kTotem.mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, (EntityPlayer)Anti32kTotem.mc.player);
                        break;
                    }
                    Anti32kTotem.mc.playerController.windowClick(0, i, 0, ClickType.SWAP, (EntityPlayer)Anti32kTotem.mc.player);
                    Anti32kTotem.mc.playerController.windowClick(0, (int)this.slot.getValue() + 35, 0, ClickType.SWAP, (EntityPlayer)Anti32kTotem.mc.player);
                    Anti32kTotem.mc.playerController.windowClick(0, i, 0, ClickType.SWAP, (EntityPlayer)Anti32kTotem.mc.player);
                    break;
                }
                else {
                    ++i;
                }
            }
        }
    }
    
    public String getHudInfo() {
        int totems = Anti32kTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (Anti32kTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        return "[" + ChatFormatting.WHITE + "Totem " + totems + ChatFormatting.GRAY + "]";
    }
}
