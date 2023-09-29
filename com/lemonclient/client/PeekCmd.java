//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client;

import net.minecraftforge.fml.common.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.nbt.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.module.modules.misc.*;
import com.lemonclient.api.util.misc.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.tileentity.*;

@Mod(modid = "peek", name = "PeekBypass", version = "1", acceptedMinecraftVersions = "[1.12.2]")
public class PeekCmd
{
    public static int metadataTicks;
    public static int guiTicks;
    public static ItemStack shulker;
    public static EntityItem drop;
    public static InventoryBasic toOpen;
    public static Minecraft mc;
    
    @Mod.EventHandler
    public void postinit(final FMLPostInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand((ICommand)new PeekCommand());
    }
    
    public static NBTTagCompound getShulkerNBT(final ItemStack stack) {
        if (PeekCmd.mc.player == null) {
            return null;
        }
        final NBTTagCompound compound = stack.getTagCompound();
        if (compound != null && compound.hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound tags = compound.getCompoundTag("BlockEntityTag");
            if (ModuleManager.getModule("Peek").isEnabled() && ShulkerBypass.shulkers) {
                if (tags.hasKey("Items", 9)) {
                    return tags;
                }
                MessageBus.sendClientDeleteMessage("Shulker is empty!", "Peek", 3);
            }
        }
        return null;
    }
    
    static {
        PeekCmd.metadataTicks = -1;
        PeekCmd.guiTicks = -1;
        PeekCmd.shulker = ItemStack.EMPTY;
        PeekCmd.mc = Minecraft.getMinecraft();
    }
    
    public static class PeekCommand extends CommandBase implements IClientCommand
    {
        public boolean allowUsageWithoutPrefix(final ICommandSender sender, final String message) {
            return false;
        }
        
        public String getName() {
            return "peek";
        }
        
        public String getUsage(final ICommandSender sender) {
            return null;
        }
        
        public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
            if (PeekCmd.mc.player != null && ModuleManager.getModule("Peek").isEnabled() && ShulkerBypass.shulkers) {
                if (!PeekCmd.shulker.isEmpty()) {
                    final NBTTagCompound shulkerNBT = PeekCmd.getShulkerNBT(PeekCmd.shulker);
                    if (shulkerNBT != null) {
                        final TileEntityShulkerBox fakeShulker = new TileEntityShulkerBox();
                        fakeShulker.loadFromNbt(shulkerNBT);
                        String customName = "container.shulkerBox";
                        boolean hasCustomName = false;
                        if (shulkerNBT.hasKey("CustomName", 8)) {
                            customName = shulkerNBT.getString("CustomName");
                            hasCustomName = true;
                        }
                        final InventoryBasic inv = new InventoryBasic(customName, hasCustomName, 27);
                        for (int i = 0; i < 27; ++i) {
                            inv.setInventorySlotContents(i, fakeShulker.getStackInSlot(i));
                        }
                        PeekCmd.toOpen = inv;
                        PeekCmd.guiTicks = 0;
                    }
                }
                else {
                    MessageBus.sendClientDeleteMessage("No shulker detected! please drop and pickup your shulker.", "Peek", 3);
                }
            }
        }
        
        public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
            return true;
        }
    }
}
