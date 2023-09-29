//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.event.events.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import com.lemonclient.api.util.render.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

@Module.Declaration(name = "PopChams", category = Category.Render)
public class PopChams extends Module
{
    ModeSetting particles;
    BooleanSetting soundParticles;
    IntegerSetting lifeTime;
    IntegerSetting repeted;
    BooleanSetting followPlayer;
    BooleanSetting player;
    IntegerSetting range;
    ModeSetting chamsPopType;
    ColorSetting chamsColor;
    IntegerSetting wireFramePop;
    BooleanSetting gradientAlpha;
    ModeSetting Movement;
    DoubleSetting yMovement;
    IntegerSetting life;
    private int fpNum;
    ArrayList<Entity> toSpawn;
    ArrayList<particleContinue> particleList;
    @EventHandler
    private final Listener<TotemPopEvent> totemPopEventListener;
    @EventHandler
    private final Listener<RenderEntityEvent.Head> renderEntityHeadEventListener;
    ArrayList<playerChams> listPlayers;
    @EventHandler
    private final Listener<RenderEntityEvent.Return> renderEntityReturnEventListener;
    
    public PopChams() {
        this.particles = this.registerMode("Particles", (List)this.getParticlesList(), "None");
        this.soundParticles = this.registerBoolean("Sound Particles", false, () -> !((String)this.particles.getValue()).equals("None"));
        this.lifeTime = this.registerInteger("LifeTime", 30, 0, 200, () -> !((String)this.particles.getValue()).equals("None"));
        this.repeted = this.registerInteger("Repeted", 0, 0, 10, () -> !((String)this.particles.getValue()).equals("None"));
        this.followPlayer = this.registerBoolean("Follow Player", false);
        this.player = this.registerBoolean("Player", true);
        this.range = this.registerInteger("Range", 100, 10, 260);
        this.chamsPopType = this.registerMode("Chams Type Pop", (List)Arrays.asList("Color", "WireFrame"), "WireFrame");
        this.chamsColor = this.registerColor("Chams Color", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.wireFramePop = this.registerInteger("WireFrame Pop", 4, 0, 10);
        this.gradientAlpha = this.registerBoolean("Gradient Alpha", true);
        this.Movement = this.registerMode("Movement", (List)Arrays.asList("None", "Heaven", "Hell"), "None");
        this.yMovement = this.registerDouble("Y Movement", 0.2, 0.0, 1.0, () -> !((String)this.Movement.getValue()).equals("None"));
        this.life = this.registerInteger("Time", 100, 10, 300);
        this.fpNum = 0;
        this.toSpawn = new ArrayList<Entity>();
        this.particleList = new ArrayList<particleContinue>();
        this.totemPopEventListener = (Listener<TotemPopEvent>)new Listener(event -> {
            if (event.getEntity() != null) {
                if (this.player.getValue()) {
                    this.toSpawn.add(event.getEntity());
                }
                if (!((String)this.particles.getValue()).equals("None")) {
                    final EnumParticleTypes part = EnumParticleTypes.getByName((String)this.particles.getValue());
                    if (part != null) {
                        final Entity entityPlayer = event.getEntity();
                        this.particleList.add(new particleContinue(entityPlayer.entityId, part));
                    }
                }
            }
        }, new Predicate[0]);
        this.renderEntityHeadEventListener = (Listener<RenderEntityEvent.Head>)new Listener(event -> {
            if (event.getType() == RenderEntityEvent.Type.COLOR && ((String)this.chamsPopType.getValue()).equalsIgnoreCase("Texture")) {
                return;
            }
            if (event.getType() == RenderEntityEvent.Type.TEXTURE && (((String)this.chamsPopType.getValue()).equalsIgnoreCase("Color") || ((String)this.chamsPopType.getValue()).equalsIgnoreCase("WireFrame"))) {
                return;
            }
            if (event.getEntity() == null || event.getEntity().getName().length() > 0) {
                return;
            }
            if (PopChams.mc.player == null || PopChams.mc.world == null) {
                return;
            }
            final Entity entity1 = event.getEntity();
            if (entity1.getDistance((Entity)PopChams.mc.player) > (int)this.range.getValue()) {
                return;
            }
            if (entity1 instanceof EntityPlayer && entity1 != PopChams.mc.player) {
                this.renderChamsPopPre(entity1);
            }
        }, new Predicate[0]);
        this.listPlayers = new ArrayList<playerChams>();
        this.renderEntityReturnEventListener = (Listener<RenderEntityEvent.Return>)new Listener(event -> {
            if (event.getType() == RenderEntityEvent.Type.COLOR && ((String)this.chamsPopType.getValue()).equalsIgnoreCase("Texture")) {
                return;
            }
            if (event.getType() == RenderEntityEvent.Type.TEXTURE && (((String)this.chamsPopType.getValue()).equalsIgnoreCase("Color") || ((String)this.chamsPopType.getValue()).equalsIgnoreCase("WireFrame"))) {
                return;
            }
            if (PopChams.mc.player == null || PopChams.mc.world == null) {
                return;
            }
            final Entity entity1 = event.getEntity();
            if (entity1.getDistance((Entity)PopChams.mc.player) > (int)this.range.getValue()) {
                return;
            }
            if (entity1 instanceof EntityPlayer && entity1 != PopChams.mc.player) {
                this.renderChamsPopPost();
            }
            if (entity1.getName().equals("")) {
                PopChams.mc.world.removeEntityFromWorld(entity1.getEntityId());
            }
            final int a = 0;
        }, new Predicate[0]);
    }
    
    List<String> getParticlesList() {
        final List<String> output = new ArrayList<String>();
        output.add("None");
        output.addAll(EnumParticleTypes.getParticleNames());
        return output;
    }
    
    public void onWorldRender(final RenderEvent event) {
        for (int i = 0; i < this.particleList.size(); ++i) {
            if (this.particleList.get(i).update()) {
                this.particleList.remove(i);
                --i;
            }
        }
    }
    
    public void onUpdate() {
        if (PopChams.mc.world == null || PopChams.mc.player == null) {
            this.toSpawn.clear();
        }
        this.toSpawn.removeIf(this::spawnPlayer);
        for (int i = 0; i < this.listPlayers.size(); ++i) {
            if (this.listPlayers.get(i).onUpdate()) {
                try {
                    PopChams.mc.world.removeEntityFromWorld(this.listPlayers.get(i).id);
                }
                catch (NullPointerException ex) {}
                this.listPlayers.remove(i);
                --i;
            }
            else {
                this.spawnPlayer(this.listPlayers.get(i).id, this.listPlayers.get(i).coordinates);
            }
        }
    }
    
    boolean spawnPlayer(final Entity entity) {
        if (entity != null) {
            double movement = 0.0;
            final String s = (String)this.Movement.getValue();
            switch (s) {
                case "Heaven": {
                    movement = (double)this.yMovement.getValue();
                    break;
                }
                case "Hell": {
                    movement = -(double)this.yMovement.getValue();
                    break;
                }
            }
            this.listPlayers.add(new playerChams(-1235 - this.fpNum, (int)this.life.getValue(), new double[] { entity.posX, entity.posY, entity.posZ }, movement));
            ++this.fpNum;
        }
        return true;
    }
    
    void spawnPlayer(final int num, final double[] positions) {
        final EntityOtherPlayerMP clonedPlayer = new EntityOtherPlayerMP((World)PopChams.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), ""));
        clonedPlayer.setPosition(positions[0], positions[1], positions[2]);
        clonedPlayer.setGameType(GameType.SPECTATOR);
        clonedPlayer.isSpectator();
        clonedPlayer.setHealth(20.0f);
        clonedPlayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 100, false, false));
        PopChams.mc.world.addEntityToWorld(num, (Entity)clonedPlayer);
    }
    
    private void renderChamsPopPre(final Entity player) {
        int alpha = this.chamsColor.getColor().getAlpha();
        if (this.gradientAlpha.getValue()) {
            final Optional<playerChams> prova = this.listPlayers.stream().filter(e -> e.id == player.entityId).findAny();
            if (prova.isPresent()) {
                alpha = prova.get().returnGradient();
            }
        }
        if (alpha < 0) {
            alpha = 0;
        }
        final String s = (String)this.chamsPopType.getValue();
        switch (s) {
            case "Color": {
                ChamsUtil.createColorPre(new GSColor(this.chamsColor.getColor(), alpha), true);
                break;
            }
            case "WireFrame": {
                ChamsUtil.createWirePre(new GSColor(this.chamsColor.getColor(), alpha), (int)this.wireFramePop.getValue(), true);
                break;
            }
        }
    }
    
    private void renderChamsPopPost() {
        final String s = (String)this.chamsPopType.getValue();
        switch (s) {
            case "Color": {
                ChamsUtil.createColorPost(true);
                break;
            }
            case "WireFrame": {
                ChamsUtil.createWirePost(true);
                break;
            }
        }
    }
    
    class particleContinue
    {
        int id;
        EnumParticleTypes part;
        int tick;
        double posX;
        double posY;
        double posZ;
        
        public particleContinue(final int id, final EnumParticleTypes part) {
            this.tick = 0;
            this.posX = 0.0;
            this.posY = 0.0;
            this.posZ = 0.0;
            this.id = id;
            this.part = part;
            try {
                this.posX = PopChams.mc.world.getEntityByID(id).posX;
                this.posY = PopChams.mc.world.getEntityByID(id).posY;
                this.posZ = PopChams.mc.world.getEntityByID(id).posZ;
            }
            catch (NullPointerException ex) {}
        }
        
        public boolean update() {
            if (this.tick++ > (int)PopChams.this.repeted.getValue()) {
                return true;
            }
            final Entity entityPlayer = PopChams.mc.world.getEntityByID(this.id);
            if (entityPlayer != null) {
                try {
                    if (PopChams.this.followPlayer.getValue()) {
                        PopChams.mc.effectRenderer.emitParticleAtEntity(entityPlayer, this.part, (int)PopChams.this.lifeTime.getValue());
                    }
                    else {
                        final EntityOtherPlayerMP test = new EntityOtherPlayerMP((World)PopChams.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), "oky"));
                        test.setPosition(this.posX, this.posY, this.posZ);
                        PopChams.mc.effectRenderer.emitParticleAtEntity((Entity)test, this.part, (int)PopChams.this.lifeTime.getValue());
                    }
                    if (PopChams.this.soundParticles.getValue()) {
                        PopChams.mc.world.playSound(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, SoundEvents.ITEM_TOTEM_USE, entityPlayer.getSoundCategory(), 1.0f, 1.0f, false);
                    }
                }
                catch (ReportedException ex) {}
            }
            return false;
        }
    }
    
    static class playerChams
    {
        private int tick;
        private final int finalTick;
        private final int id;
        final double[] coordinates;
        final double movement;
        
        public playerChams(final int id, final int finalTick, final double[] coordinates, final double movement) {
            this.id = id;
            this.finalTick = finalTick;
            this.tick = 0;
            this.coordinates = coordinates;
            this.movement = movement;
        }
        
        public boolean onUpdate() {
            final double[] coordinates = this.coordinates;
            final int n = 1;
            coordinates[n] += this.movement;
            return this.tick++ > this.finalTick;
        }
        
        public int returnGradient() {
            return 250 - (int)(this.tick / (float)this.finalTick * 250.0f);
        }
    }
}
