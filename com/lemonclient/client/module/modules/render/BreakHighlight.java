//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.world.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.play.server.*;

@Module.Declaration(name = "BreakHighlight", category = Category.Render)
public class BreakHighlight extends Module
{
    BooleanSetting cancelAnimation;
    IntegerSetting range;
    IntegerSetting playerRange;
    BooleanSetting showProgress;
    ColorSetting nameColor;
    ModeSetting renderType;
    ColorSetting color;
    IntegerSetting alpha;
    IntegerSetting width;
    HashMap<EntityPlayer, renderBlock> list;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    private final Listener<DrawBlockDamageEvent> drawBlockDamageEventListener;
    
    public BreakHighlight() {
        this.cancelAnimation = this.registerBoolean("No Animation", true);
        this.range = this.registerInteger("Range", 64, 0, 256);
        this.playerRange = this.registerInteger("Player Range", 16, 0, 64);
        this.showProgress = this.registerBoolean("Show Progress", false);
        this.nameColor = this.registerColor("Name Color", new GSColor(255, 255, 255));
        this.renderType = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both"), "Both");
        this.color = this.registerColor("Color", new GSColor(0, 255, 0, 255));
        this.alpha = this.registerInteger("Alpha", 100, 0, 255);
        this.width = this.registerInteger("Width", 1, 0, 5);
        this.list = new HashMap<EntityPlayer, renderBlock>();
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketBlockBreakAnim) {
                final SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
                final BlockPos blockPos = packet.getPosition();
                if (BreakHighlight.mc.player.getDistanceSq(blockPos) > (int)this.range.getValue() * (int)this.range.getValue()) {
                    return;
                }
                final EntityPlayer entityPlayer = (EntityPlayer)BreakHighlight.mc.world.getEntityByID(packet.getBreakerId());
                if (entityPlayer == null) {
                    return;
                }
                if (this.list.containsKey(entityPlayer)) {
                    if (this.isPos2(this.list.get(entityPlayer).pos, blockPos)) {
                        return;
                    }
                    this.list.replace(entityPlayer, new renderBlock(blockPos, entityPlayer));
                }
                else {
                    this.list.put(entityPlayer, new renderBlock(blockPos, entityPlayer));
                }
            }
        }, new Predicate[0]);
        this.drawBlockDamageEventListener = (Listener<DrawBlockDamageEvent>)new Listener(event -> {
            if (this.cancelAnimation.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (BreakHighlight.mc.player == null || BreakHighlight.mc.world == null) {
            this.list.clear();
            return;
        }
        final List<EntityPlayer> playerList = (List<EntityPlayer>)BreakHighlight.mc.world.playerEntities;
        for (final EntityPlayer player : playerList) {
            if (this.list.containsKey(player)) {
                final BlockPos pos = this.list.get(player).pos;
                if (BreakHighlight.mc.world.getBlockState(pos).getBlockHardness((World)BreakHighlight.mc.world, pos) < 0.0f) {
                    this.list.remove(player);
                }
                else {
                    if (BreakHighlight.mc.player.getDistanceSq(pos) > (int)this.range.getValue() * (int)this.range.getValue()) {
                        continue;
                    }
                    if (player.getDistanceSq(pos) > (int)this.playerRange.getValue() * (int)this.playerRange.getValue()) {
                        this.list.remove(player);
                    }
                    else {
                        this.list.get(player).update();
                    }
                }
            }
        }
    }
    
    public static GSColor getRainbowColor(final int damage) {
        return GSColor.fromHSB((1 + damage * 32) % 11520 / 11520.0f, 1.0f, 1.0f);
    }
    
    private void renderBox(final BlockPos blockPos, final float mineDamage, final EntityPlayer player) {
        final AxisAlignedBB getSelectedBoundingBox = new AxisAlignedBB(blockPos);
        final Vec3d getCenter = getSelectedBoundingBox.getCenter();
        final float prognum = mineDamage * 100.0f;
        final String[] name = { player.getName() };
        if (this.showProgress.getValue()) {
            final String[] progress = { String.format("%.1f", prognum) };
            RenderUtil.drawNametag(blockPos.getX() + 0.5, blockPos.getY() + 0.39, blockPos.getZ() + 0.5, progress, getRainbowColor((int)prognum), 1);
            RenderUtil.drawNametag(blockPos.getX() + 0.5, blockPos.getY() + 0.61, blockPos.getZ() + 0.5, name, new GSColor(this.nameColor.getColor(), 255), 1);
        }
        else {
            RenderUtil.drawNametag(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, name, new GSColor(this.nameColor.getColor(), 255), 1);
        }
        this.renderESP(new AxisAlignedBB(getCenter.x, getCenter.y, getCenter.z, getCenter.x, getCenter.y, getCenter.z).grow((getSelectedBoundingBox.minX - getSelectedBoundingBox.maxX) * 0.5 * MathHelper.clamp(mineDamage, 0.0f, 1.0f), (getSelectedBoundingBox.minY - getSelectedBoundingBox.maxY) * 0.5 * MathHelper.clamp(mineDamage, 0.0f, 1.0f), (getSelectedBoundingBox.minZ - getSelectedBoundingBox.maxZ) * 0.5 * MathHelper.clamp(mineDamage, 0.0f, 1.0f)));
    }
    
    private void renderESP(final AxisAlignedBB axisAlignedBB) {
        final GSColor fillColor = new GSColor(this.color.getValue(), (int)this.alpha.getValue());
        final GSColor outlineColor = new GSColor(this.color.getValue(), 255);
        final String s = (String)this.renderType.getValue();
        switch (s) {
            case "Fill": {
                RenderUtil.drawBox(axisAlignedBB, true, 0.0, fillColor, 63);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(axisAlignedBB, (double)(int)this.width.getValue(), outlineColor);
                break;
            }
            default: {
                RenderUtil.drawBox(axisAlignedBB, true, 0.0, fillColor, 63);
                RenderUtil.drawBoundingBox(axisAlignedBB, (double)(int)this.width.getValue(), outlineColor);
                break;
            }
        }
    }
    
    public ItemStack getEfficientItem(final IBlockState blockState, final BlockPos pos) {
        int n = -1;
        double n2 = 0.0;
        for (int i = 0; i < 9; ++i) {
            if (!BreakHighlight.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                float getDestroySpeed = BreakHighlight.mc.player.inventory.getStackInSlot(i).getDestroySpeed(blockState);
                if (getDestroySpeed > 1.0f) {
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, BreakHighlight.mc.player.inventory.getStackInSlot(i)) > 0) {
                        getDestroySpeed += (float)(StrictMath.pow(EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, BreakHighlight.mc.player.inventory.getStackInSlot(i)), 2.0) + 1.0);
                    }
                    if (getDestroySpeed > n2) {
                        n2 = getDestroySpeed;
                        n = i;
                    }
                }
            }
        }
        if (n != -1) {
            return BreakHighlight.mc.player.inventory.getStackInSlot(n);
        }
        return BreakHighlight.mc.player.inventory.getStackInSlot(this.findItem(pos));
    }
    
    public float getDestroySpeed(final IBlockState blockState, final BlockPos pos) {
        float n = 1.0f;
        if (this.getEfficientItem(blockState, pos) != null && !this.getEfficientItem(blockState, pos).isEmpty()) {
            n *= this.getEfficientItem(blockState, pos).getDestroySpeed(blockState);
        }
        return n;
    }
    
    public float getDigSpeed(final IBlockState blockState, final BlockPos pos) {
        float destroySpeed = this.getDestroySpeed(blockState, pos);
        if (destroySpeed > 1.0f) {
            final ItemStack efficientItem = this.getEfficientItem(blockState, pos);
            final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, efficientItem);
            if (getEnchantmentLevel > 0 && !efficientItem.isEmpty()) {
                destroySpeed += (float)(StrictMath.pow(getEnchantmentLevel, 2.0) + 1.0);
            }
        }
        if (BreakHighlight.mc.player.isPotionActive(MobEffects.HASTE)) {
            destroySpeed *= 1.0f + (Objects.requireNonNull(BreakHighlight.mc.player.getActivePotionEffect(MobEffects.HASTE)).getAmplifier() + 1) * 0.2f;
        }
        if (BreakHighlight.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float n = 0.0f;
            switch (Objects.requireNonNull(BreakHighlight.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE)).getAmplifier()) {
                case 0: {
                    n = 0.3f;
                    break;
                }
                case 1: {
                    n = 0.09f;
                    break;
                }
                case 2: {
                    n = 0.0027f;
                    break;
                }
                default: {
                    n = 8.1E-4f;
                    break;
                }
            }
            destroySpeed *= n;
        }
        if (BreakHighlight.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)BreakHighlight.mc.player)) {
            destroySpeed /= 5.0f;
        }
        if (!BreakHighlight.mc.player.onGround) {
            destroySpeed /= 5.0f;
        }
        return Math.max(destroySpeed, 0.0f);
    }
    
    public float getBlockStrength(final IBlockState blockState, final BlockPos blockPos) {
        final float getBlockHardness = blockState.getBlockHardness((World)BreakHighlight.mc.world, blockPos);
        if (getBlockHardness < 0.0f) {
            return 0.0f;
        }
        return this.getDigSpeed(blockState, blockPos) / getBlockHardness / ((12000 * Minecraft.debugFPS == 0) ? 1 : Minecraft.debugFPS);
    }
    
    public int findItem(final BlockPos pos) {
        if (pos == null) {
            return BreakHighlight.mc.player.inventory.currentItem;
        }
        return findBestTool(pos, BreakHighlight.mc.world.getBlockState(pos));
    }
    
    public static int findBestTool(final BlockPos pos, final IBlockState state) {
        int result = BreakHighlight.mc.player.inventory.currentItem;
        if (state.getBlockHardness((World)BreakHighlight.mc.world, pos) > 0.0f) {
            double speed = getSpeed(state, BreakHighlight.mc.player.getHeldItemMainhand());
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = BreakHighlight.mc.player.inventory.getStackInSlot(i);
                final double stackSpeed = getSpeed(state, stack);
                if (stackSpeed > speed) {
                    speed = stackSpeed;
                    result = i;
                }
            }
        }
        return result;
    }
    
    public static double getSpeed(final IBlockState state, final ItemStack stack) {
        final double str = stack.getDestroySpeed(state);
        final int effect = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        return Math.max(str + ((str > 1.0) ? (effect * effect + 1.0) : 0.0), 0.0);
    }
    
    class renderBlock
    {
        private final BlockPos pos;
        private final EntityPlayer player;
        private float damage;
        
        public renderBlock(final BlockPos pos, final EntityPlayer player) {
            this.pos = pos;
            this.player = player;
            this.damage = 0.0f;
        }
        
        void update() {
            if (this.damage >= 1.0f) {
                this.damage = 1.0f;
            }
            else {
                this.damage += BreakHighlight.this.getBlockStrength(BreakHighlight.mc.world.getBlockState(this.pos), this.pos);
            }
            BreakHighlight.this.renderBox(this.pos, this.damage, this.player);
        }
    }
}
