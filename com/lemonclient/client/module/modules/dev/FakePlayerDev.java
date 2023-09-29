//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import net.minecraft.item.*;
import java.util.function.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.util.*;
import com.lemonclient.client.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;

@Module.Declaration(name = "FakePlayerDev", category = Category.Dev)
public class FakePlayerDev extends Module
{
    private final ItemStack[] armors;
    StringSetting nameFakePlayer;
    BooleanSetting copyInventory;
    BooleanSetting playerStacked;
    BooleanSetting onShift;
    BooleanSetting simulateDamage;
    IntegerSetting vulnerabilityTick;
    IntegerSetting resetHealth;
    IntegerSetting tickRegenVal;
    IntegerSetting startHealth;
    ModeSetting moving;
    DoubleSetting speed;
    DoubleSetting range;
    BooleanSetting followPlayer;
    BooleanSetting resistance;
    BooleanSetting pop;
    int incr;
    boolean beforePressed;
    ArrayList<playerInfo> listPlayers;
    movingManager manager;
    @EventHandler
    private final Listener<PacketEvent.Receive> packetReceiveListener;
    
    public FakePlayerDev() {
        this.armors = new ItemStack[] { new ItemStack((Item)Items.DIAMOND_BOOTS), new ItemStack((Item)Items.DIAMOND_LEGGINGS), new ItemStack((Item)Items.DIAMOND_CHESTPLATE), new ItemStack((Item)Items.DIAMOND_HELMET) };
        this.nameFakePlayer = this.registerString("Name FakePlayer", "NotLazyOfLazys");
        this.copyInventory = this.registerBoolean("Copy Inventory", false);
        this.playerStacked = this.registerBoolean("Player Stacked", false);
        this.onShift = this.registerBoolean("On Shift", false);
        this.simulateDamage = this.registerBoolean("Simulate Damage", false);
        this.vulnerabilityTick = this.registerInteger("Vulnerability Tick", 4, 0, 10);
        this.resetHealth = this.registerInteger("Reset Health", 10, 0, 36);
        this.tickRegenVal = this.registerInteger("Tick Regen", 4, 0, 30);
        this.startHealth = this.registerInteger("Start Health", 20, 0, 30);
        this.moving = this.registerMode("Moving", (List)Arrays.asList("None", "Line", "Circle", "Random"), "None");
        this.speed = this.registerDouble("Speed", 0.36, 0.0, 4.0);
        this.range = this.registerDouble("Range", 3.0, 0.0, 14.0);
        this.followPlayer = this.registerBoolean("Follow Player", true);
        this.resistance = this.registerBoolean("Resistance", true);
        this.pop = this.registerBoolean("Pop", true);
        this.listPlayers = new ArrayList<playerInfo>();
        this.manager = new movingManager();
        this.packetReceiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (this.simulateDamage.getValue()) {
                final Packet<?> packet = (Packet<?>)event.getPacket();
                if (packet instanceof SPacketSoundEffect) {
                    final SPacketSoundEffect packetSoundEffect = (SPacketSoundEffect)packet;
                    if (packetSoundEffect.getCategory() == SoundCategory.BLOCKS && packetSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                        for (final Entity entity : new ArrayList<Entity>(FakePlayerDev.mc.world.loadedEntityList)) {
                            if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ()) <= 36.0) {
                                for (final EntityPlayer entityPlayer : FakePlayerDev.mc.world.playerEntities) {
                                    if (entityPlayer.getName().split(this.nameFakePlayer.getText()).length == 2) {
                                        final Optional<playerInfo> temp = this.listPlayers.stream().filter(e -> e.name.equals(entityPlayer.getName())).findAny();
                                        if (!temp.isPresent()) {
                                            continue;
                                        }
                                        if (!temp.get().canPop()) {
                                            continue;
                                        }
                                        final float damage = DamageUtil.calculateDamage(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ(), (Entity)entityPlayer);
                                        if (damage > entityPlayer.getHealth()) {
                                            entityPlayer.setHealth((float)(int)this.resetHealth.getValue());
                                            if (this.pop.getValue()) {
                                                FakePlayerDev.mc.effectRenderer.emitParticleAtEntity((Entity)entityPlayer, EnumParticleTypes.TOTEM, 30);
                                                FakePlayerDev.mc.world.playSound(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
                                            }
                                            LemonClient.EVENT_BUS.post((Object)new TotemPopEvent((Entity)entityPlayer));
                                        }
                                        else {
                                            entityPlayer.setHealth(entityPlayer.getHealth() - damage);
                                        }
                                        temp.get().tickPop = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.incr = 0;
        this.beforePressed = false;
        if (FakePlayerDev.mc.player == null || FakePlayerDev.mc.player.isDead) {
            this.disable();
            return;
        }
        if (!(boolean)this.onShift.getValue()) {
            this.spawnPlayer();
        }
    }
    
    void spawnPlayer() {
        final EntityOtherPlayerMP clonedPlayer = new EntityOtherPlayerMP((World)FakePlayerDev.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), this.nameFakePlayer.getText()));
        clonedPlayer.copyLocationAndAnglesFrom((Entity)FakePlayerDev.mc.player);
        clonedPlayer.rotationYawHead = FakePlayerDev.mc.player.rotationYawHead;
        clonedPlayer.rotationYaw = FakePlayerDev.mc.player.rotationYaw;
        clonedPlayer.rotationPitch = FakePlayerDev.mc.player.rotationPitch;
        clonedPlayer.setGameType(GameType.SURVIVAL);
        clonedPlayer.setHealth((float)(int)this.startHealth.getValue());
        FakePlayerDev.mc.world.addEntityToWorld(-1234 + this.incr, (Entity)clonedPlayer);
        ++this.incr;
        if (this.copyInventory.getValue()) {
            clonedPlayer.inventory.copyInventory(FakePlayerDev.mc.player.inventory);
        }
        else if (this.playerStacked.getValue()) {
            for (int i = 0; i < 4; ++i) {
                final ItemStack item = this.armors[i];
                item.addEnchantment((i == 3) ? Enchantments.BLAST_PROTECTION : Enchantments.PROTECTION, 4);
                clonedPlayer.inventory.armorInventory.set(i, (Object)item);
            }
        }
        if (this.resistance.getValue()) {
            clonedPlayer.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 123456789, 0));
        }
        clonedPlayer.onEntityUpdate();
        this.listPlayers.add(new playerInfo(clonedPlayer.getName()));
        if (!((String)this.moving.getValue()).equals("None")) {
            this.manager.addPlayer(clonedPlayer.entityId, (String)this.moving.getValue(), (double)this.speed.getValue(), ((String)this.moving.getValue()).equals("Line") ? this.getDirection() : -1, (double)this.range.getValue(), (boolean)this.followPlayer.getValue());
        }
    }
    
    public void onUpdate() {
        if ((boolean)this.onShift.getValue() && FakePlayerDev.mc.gameSettings.keyBindSneak.isPressed() && !this.beforePressed) {
            this.beforePressed = true;
            this.spawnPlayer();
        }
        else {
            this.beforePressed = false;
        }
        for (int i = 0; i < this.listPlayers.size(); ++i) {
            if (this.listPlayers.get(i).update()) {
                final int finalI = i;
                final Optional<EntityPlayer> temp = (Optional<EntityPlayer>)FakePlayerDev.mc.world.playerEntities.stream().filter(e -> e.getName().equals(this.listPlayers.get(finalI).name)).findAny();
                if (temp.isPresent() && temp.get().getHealth() < 20.0f) {
                    temp.get().setHealth(temp.get().getHealth() + 1.0f);
                }
            }
        }
        this.manager.update();
    }
    
    int getDirection() {
        int yaw = (int)RotationUtil.normalizeAngle(FakePlayerDev.mc.player.getPitchYaw().y);
        if (yaw < 0) {
            yaw += 360;
        }
        yaw += 22;
        yaw %= 360;
        return yaw / 45;
    }
    
    public void onDisable() {
        if (FakePlayerDev.mc.world != null) {
            for (int i = 0; i < this.incr; ++i) {
                FakePlayerDev.mc.world.removeEntityFromWorld(-1234 + i);
            }
        }
        this.listPlayers.clear();
        this.manager.remove();
    }
    
    class playerInfo
    {
        final String name;
        int tickPop;
        int tickRegen;
        
        public playerInfo(final String name) {
            this.tickPop = -1;
            this.tickRegen = 0;
            this.name = name;
        }
        
        boolean update() {
            if (this.tickPop != -1 && ++this.tickPop >= (int)FakePlayerDev.this.vulnerabilityTick.getValue()) {
                this.tickPop = -1;
            }
            if (++this.tickRegen >= (int)FakePlayerDev.this.tickRegenVal.getValue()) {
                this.tickRegen = 0;
                return true;
            }
            return false;
        }
        
        boolean canPop() {
            return this.tickPop == -1;
        }
    }
    
    static class movingPlayer
    {
        private final int id;
        private final String type;
        private final double speed;
        private final int direction;
        private final double range;
        private final boolean follow;
        int rad;
        
        public movingPlayer(final int id, final String type, final double speed, final int direction, final double range, final boolean follow) {
            this.rad = 0;
            this.id = id;
            this.type = type;
            this.speed = speed;
            this.direction = Math.abs(direction);
            this.range = range;
            this.follow = follow;
        }
        
        void move() {
            final Entity player = FakePlayerDev.mc.world.getEntityByID(this.id);
            if (player != null) {
                final String type = this.type;
                switch (type) {
                    case "Line": {
                        double posX = this.follow ? FakePlayerDev.mc.player.posX : player.posX;
                        double posY = this.follow ? FakePlayerDev.mc.player.posY : player.posY;
                        double posZ = this.follow ? FakePlayerDev.mc.player.posZ : player.posZ;
                        switch (this.direction) {
                            case 0: {
                                posZ += this.speed;
                                break;
                            }
                            case 1: {
                                posX -= this.speed / 2.0;
                                posZ += this.speed / 2.0;
                                break;
                            }
                            case 2: {
                                posX -= this.speed / 2.0;
                                break;
                            }
                            case 3: {
                                posZ -= this.speed / 2.0;
                                posX -= this.speed / 2.0;
                                break;
                            }
                            case 4: {
                                posZ -= this.speed;
                                break;
                            }
                            case 5: {
                                posX += this.speed / 2.0;
                                posZ -= this.speed / 2.0;
                                break;
                            }
                            case 6: {
                                posX += this.speed;
                                break;
                            }
                            case 7: {
                                posZ += this.speed / 2.0;
                                posX += this.speed / 2.0;
                                break;
                            }
                        }
                        if (BlockUtil.getBlock(posX, posY, posZ) instanceof BlockAir) {
                            for (int i = 0; i < 5 && BlockUtil.getBlock(posX, posY - 1.0, posZ) instanceof BlockAir; --posY, ++i) {}
                        }
                        else {
                            for (int i = 0; i < 5 && !(BlockUtil.getBlock(posX, posY, posZ) instanceof BlockAir); ++posY, ++i) {}
                        }
                        player.setPositionAndUpdate(posX, posY, posZ);
                        break;
                    }
                    case "Circle": {
                        final double posXCir = Math.cos(this.rad / 100.0) * this.range + FakePlayerDev.mc.player.posX;
                        final double posZCir = Math.sin(this.rad / 100.0) * this.range + FakePlayerDev.mc.player.posZ;
                        double posYCir = FakePlayerDev.mc.player.posY;
                        if (BlockUtil.getBlock(posXCir, posYCir, posZCir) instanceof BlockAir) {
                            for (int j = 0; j < 5 && BlockUtil.getBlock(posXCir, posYCir - 1.0, posZCir) instanceof BlockAir; --posYCir, ++j) {}
                        }
                        else {
                            for (int j = 0; j < 5 && !(BlockUtil.getBlock(posXCir, posYCir, posZCir) instanceof BlockAir); ++posYCir, ++j) {}
                        }
                        player.setPositionAndUpdate(posXCir, posYCir, posZCir);
                        this.rad += (int)(this.speed * 10.0);
                        break;
                    }
                }
            }
        }
    }
    
    static class movingManager
    {
        private final ArrayList<movingPlayer> players;
        
        movingManager() {
            this.players = new ArrayList<movingPlayer>();
        }
        
        void addPlayer(final int id, final String type, final double speed, final int direction, final double range, final boolean follow) {
            this.players.add(new movingPlayer(id, type, speed, direction, range, follow));
        }
        
        void update() {
            this.players.forEach(movingPlayer::move);
        }
        
        void remove() {
            this.players.clear();
        }
    }
}
