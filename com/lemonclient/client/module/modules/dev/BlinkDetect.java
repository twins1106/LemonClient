//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.concurrent.*;
import java.util.function.*;
import net.minecraft.client.*;
import com.lemonclient.api.event.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.client.entity.*;

@Module.Declaration(name = "BlinkDetect", category = Category.Dev)
public class BlinkDetect extends Module
{
    DoubleSetting Scaling;
    private final HashMap<EntityPlayer, Long> lastMovePacketTimes;
    private final List<EntityPlayer> blinkers;
    @EventHandler
    private final Listener<EventPlayerUpdate> onUpdate;
    @EventHandler
    private final Listener<RenderEvent> OnRenderGameOverlay;
    @EventHandler
    private final Listener<PacketEvent.Receive> onServerPacket;
    
    public BlinkDetect() {
        this.Scaling = this.registerDouble("Scaling", 3.0, 1.0, 10.0);
        this.lastMovePacketTimes = new HashMap<EntityPlayer, Long>();
        this.blinkers = new CopyOnWriteArrayList<EntityPlayer>();
        this.onUpdate = (Listener<EventPlayerUpdate>)new Listener(event -> {
            final long now = System.currentTimeMillis();
            this.blinkers.removeIf(p -> p == null || p.getDistance((Entity)BlinkDetect.mc.player) > 50.0f);
            this.lastMovePacketTimes.keySet().removeIf(Objects::isNull);
            long lastTime;
            final long n;
            long diff;
            BlinkDetect.mc.world.playerEntities.forEach(p -> {
                if (!(p instanceof EntityPlayerSP)) {
                    if (this.lastMovePacketTimes.containsKey(p)) {
                        lastTime = this.lastMovePacketTimes.get(p);
                        diff = n - lastTime;
                        if (diff >= 1000L) {
                            if (!this.blinkers.contains(p)) {
                                this.blinkers.add(p);
                            }
                        }
                        else {
                            this.blinkers.remove(p);
                        }
                    }
                    else {
                        this.lastMovePacketTimes.put(p, n);
                    }
                }
            });
        }, new Predicate[0]);
        this.OnRenderGameOverlay = (Listener<RenderEvent>)new Listener(event -> {
            if (BlinkDetect.mc.world == null || BlinkDetect.mc.renderEngine == null || BlinkDetect.mc.getRenderManager().options == null) {
                return;
            }
            for (final EntityPlayer player : this.blinkers) {
                final Entity entity2 = BlinkDetect.mc.getRenderViewEntity();
                if (entity2 == null) {
                    return;
                }
                Vec3d pos = MathUtil.interpolateEntityClose((Entity)player, event.getPartialTicks());
                final double n = pos.x;
                double distance = pos.y + 0.65;
                final double n2 = pos.z;
                final double n3 = distance + (player.isSneaking() ? 0.0 : 0.07999999821186066) - 0.3;
                pos = MathUtil.interpolateEntityClose(entity2, event.getPartialTicks());
                final double posX = entity2.posX;
                final double posY = entity2.posY;
                final double posZ = entity2.posZ;
                entity2.posX = pos.x;
                entity2.posY = pos.y;
                entity2.posZ = pos.z;
                distance = entity2.getDistance(n, distance, n2);
                double scale = 0.04;
                if (distance > 0.0) {
                    scale = 0.02 + (double)this.Scaling.getValue() / 1000.0 * distance;
                }
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.enablePolygonOffset();
                GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
                GlStateManager.disableLighting();
                GlStateManager.translate((float)n, (float)n3 + 1.4f, (float)n2);
                final float n4 = -BlinkDetect.mc.getRenderManager().playerViewY;
                final float n5 = 1.0f;
                final float n6 = 0.0f;
                GlStateManager.rotate(n4, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(BlinkDetect.mc.getRenderManager().playerViewX, (BlinkDetect.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
                GlStateManager.scale(-scale, -scale, scale);
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                final String nameTag = "possibly blinking!";
                final float width = getStringWidth(nameTag) / 2.0f;
                final float height = getStringHeight();
                GlStateManager.enableBlend();
                GlStateManager.disableBlend();
                drawStringWithShadow(nameTag, -width + 1.0f, -height + 3.0f, 16711680);
                GlStateManager.pushMatrix();
                GlStateManager.popMatrix();
                GlStateManager.enableDepth();
                GlStateManager.disableBlend();
                GlStateManager.disablePolygonOffset();
                GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                GlStateManager.popMatrix();
                entity2.posX = posX;
                entity2.posY = posY;
                entity2.posZ = posZ;
            }
        }, new Predicate[0]);
        this.onServerPacket = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getEra() != LemonClientEvent.Era.PRE || BlinkDetect.mc.world == null) {
                return;
            }
            if (event.getPacket() instanceof SPacketEntity.S15PacketEntityRelMove) {
                final SPacketEntity.S15PacketEntityRelMove packet = (SPacketEntity.S15PacketEntityRelMove)event.getPacket();
                final Entity en = packet.getEntity((World)BlinkDetect.mc.world);
                if (en instanceof EntityPlayer) {
                    if (packet.getX() == 0 && packet.getY() == 0 && packet.getZ() == 0 && packet.getPitch() == 0 && packet.getPitch() == 0) {
                        return;
                    }
                    this.lastMovePacketTimes.put((EntityPlayer)en, System.currentTimeMillis());
                }
            }
            else if (event.getPacket() instanceof SPacketEntityHeadLook) {
                final SPacketEntityHeadLook packet2 = (SPacketEntityHeadLook)event.getPacket();
                final Entity en = packet2.getEntity((World)BlinkDetect.mc.world);
                if (en instanceof EntityPlayer) {
                    this.lastMovePacketTimes.put((EntityPlayer)en, System.currentTimeMillis());
                }
            }
            else if (event.getPacket() instanceof SPacketAnimation) {
                final SPacketAnimation packet3 = (SPacketAnimation)event.getPacket();
                final Entity en = BlinkDetect.mc.world.getEntityByID(packet3.getEntityID());
                if (en instanceof EntityPlayer) {
                    this.lastMovePacketTimes.put((EntityPlayer)en, System.currentTimeMillis());
                }
            }
            else if (event.getPacket() instanceof SPacketBlockBreakAnim) {
                final SPacketBlockBreakAnim packet4 = (SPacketBlockBreakAnim)event.getPacket();
                final Entity en = BlinkDetect.mc.world.getEntityByID(packet4.getBreakerId());
                if (en instanceof EntityPlayer) {
                    this.lastMovePacketTimes.put((EntityPlayer)en, System.currentTimeMillis());
                }
            }
        }, new Predicate[0]);
    }
    
    public static float drawStringWithShadow(final String p_Name, final float p_X, final float p_Y, final int p_Color) {
        return (float)Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(p_Name, p_X, p_Y, p_Color);
    }
    
    public static float getStringWidth(final String p_Name) {
        return (float)Minecraft.getMinecraft().fontRenderer.getStringWidth(p_Name);
    }
    
    public static float getStringHeight() {
        return (float)Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }
}
