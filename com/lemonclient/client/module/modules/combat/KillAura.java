//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;

@Module.Declaration(name = "KillAura", category = Category.Combat)
public class KillAura extends Module
{
    BooleanSetting players;
    BooleanSetting hostileMobs;
    BooleanSetting passiveMobs;
    ModeSetting itemUsed;
    ModeSetting enemyPriority;
    BooleanSetting silentaura;
    BooleanSetting caCheck;
    BooleanSetting criticals;
    BooleanSetting stopspring;
    BooleanSetting rotation;
    BooleanSetting autoSwitch;
    BooleanSetting swing;
    DoubleSetting switchHealth;
    DoubleSetting range;
    private boolean isAttacking;
    public static boolean SA;
    Pair<Float, Integer> newSlot;
    int temp;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public KillAura() {
        this.players = this.registerBoolean("Players", true);
        this.hostileMobs = this.registerBoolean("Monsters", false);
        this.passiveMobs = this.registerBoolean("Animals", false);
        this.itemUsed = this.registerMode("Item used", (List)Arrays.asList("Sword", "All"), "Sword");
        this.enemyPriority = this.registerMode("Enemy Priority", (List)Arrays.asList("Closest", "Health"), "Closest");
        this.silentaura = this.registerBoolean("SilentAura", false);
        this.caCheck = this.registerBoolean("AC Check", false);
        this.criticals = this.registerBoolean("Criticals", true);
        this.stopspring = this.registerBoolean("Stop Spring", true);
        this.rotation = this.registerBoolean("Rotation", true);
        this.autoSwitch = this.registerBoolean("Switch", false);
        this.swing = this.registerBoolean("Swing", false);
        this.switchHealth = this.registerDouble("Min Switch Health", 0.0, 0.0, 20.0);
        this.range = this.registerDouble("Range", 5.0, 0.0, 10.0);
        this.isAttacking = false;
        this.newSlot = (Pair<Float, Integer>)new Pair((Object)0.0f, (Object)(-1));
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketUseEntity && (boolean)this.criticals.getValue() && ((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && KillAura.mc.player.onGround && this.isAttacking) {
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(KillAura.mc.player.posX, KillAura.mc.player.posY + 0.10000000149011612, KillAura.mc.player.posZ, false));
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(KillAura.mc.player.posX, KillAura.mc.player.posY, KillAura.mc.player.posZ, false));
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (!(boolean)this.silentaura.getValue()) {
            KillAura.SA = false;
        }
        if (KillAura.mc.player == null || !KillAura.mc.player.isEntityAlive()) {
            return;
        }
        final double rangeSq = (double)this.range.getValue() * (double)this.range.getValue();
        final Optional<EntityPlayer> optionalTarget = (Optional<EntityPlayer>)KillAura.mc.world.playerEntities.stream().filter(entity -> !EntityUtil.basicChecksEntity(entity)).filter(entity -> KillAura.mc.player.getDistanceSq(entity) <= rangeSq).filter(this::attackCheck).min(Comparator.comparing(e -> ((String)this.enemyPriority.getValue()).equals("Closest") ? KillAura.mc.player.getDistanceSq(e) : ((EntityLivingBase)e).getHealth()));
        final boolean sword = ((String)this.itemUsed.getValue()).equalsIgnoreCase("Sword");
        final boolean all = ((String)this.itemUsed.getValue()).equalsIgnoreCase("All");
        if (optionalTarget.isPresent()) {
            this.newSlot = this.findSwordSlot();
            this.temp = KillAura.mc.player.inventory.currentItem;
            if ((boolean)this.silentaura.getValue() || this.shouldAttack(sword, all)) {
                if ((int)this.newSlot.getValue() == -1) {
                    return;
                }
                if ((boolean)this.autoSwitch.getValue() && KillAura.mc.player.getHealth() + KillAura.mc.player.getAbsorptionAmount() >= (double)this.switchHealth.getValue() && (int)this.newSlot.getValue() != -1) {
                    KillAura.mc.player.inventory.currentItem = (int)this.newSlot.getValue();
                }
                final Entity target = (Entity)optionalTarget.get();
                if (this.rotation.getValue()) {
                    final Vec2f rotation = RotationUtil.getRotationTo(target.getEntityBoundingBox());
                    final PlayerPacket packet = new PlayerPacket((Module)this, rotation);
                    PlayerPacketManager.INSTANCE.addPacket(packet);
                }
                KillAura.SA = true;
                this.attack(target);
            }
            else {
                KillAura.SA = false;
                KillAura.mc.player.inventory.currentItem = this.temp;
            }
        }
        else {
            KillAura.SA = false;
        }
    }
    
    private Pair<Float, Integer> findSwordSlot() {
        final List<Integer> items = (List<Integer>)InventoryUtil.findAllItemSlots((Class)ItemSword.class);
        final List<ItemStack> inventory = (List<ItemStack>)KillAura.mc.player.inventory.mainInventory;
        float bestModifier = 0.0f;
        int correspondingSlot = -1;
        for (final Integer integer : items) {
            if (integer > 8) {
                continue;
            }
            final ItemStack stack = inventory.get(integer);
            final float modifier = (EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED) + 1.0f) * ((ItemSword)stack.getItem()).getAttackDamage();
            if (modifier <= bestModifier) {
                continue;
            }
            bestModifier = modifier;
            correspondingSlot = integer;
        }
        return (Pair<Float, Integer>)new Pair((Object)bestModifier, (Object)correspondingSlot);
    }
    
    private boolean shouldAttack(final boolean sword, final boolean all) {
        final Item item = KillAura.mc.player.getHeldItemMainhand().getItem();
        return all || (sword && item instanceof ItemSword && !(boolean)this.caCheck.getValue());
    }
    
    private void attack(final Entity e) {
        if (KillAura.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            this.isAttacking = true;
            KillAura.SA = true;
            if (this.stopspring.getValue()) {
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)KillAura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (this.silentaura.getValue()) {
                if ((int)this.newSlot.getValue() == -1) {
                    KillAura.SA = false;
                    return;
                }
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange((int)this.newSlot.getValue()));
            }
            KillAura.mc.playerController.attackEntity((EntityPlayer)KillAura.mc.player, e);
            if (this.silentaura.getValue()) {
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.temp));
            }
            if (this.swing.getValue()) {
                KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            this.isAttacking = false;
        }
    }
    
    private boolean attackCheck(final Entity entity) {
        if ((boolean)this.players.getValue() && entity instanceof EntityPlayer && !SocialManager.isFriend(entity.getName()) && ((EntityPlayer)entity).getHealth() > 0.0f) {
            return true;
        }
        if ((boolean)this.passiveMobs.getValue() && entity instanceof EntityAnimal) {
            return !(entity instanceof EntityTameable);
        }
        return (boolean)this.hostileMobs.getValue() && entity instanceof EntityMob;
    }
}
