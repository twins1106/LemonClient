//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.mc12;

import com.lukflug.panelstudio.base.*;
import java.util.*;
import org.lwjgl.input.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import javax.imageio.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import java.awt.image.*;
import net.minecraft.client.renderer.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public abstract class GLInterface implements IInterface
{
    private final Stack<Rectangle> clipRect;
    protected boolean clipX;
    
    public GLInterface(final boolean clipX) {
        this.clipRect = new Stack<Rectangle>();
        this.clipX = clipX;
    }
    
    public boolean getModifier(final int modifier) {
        switch (modifier) {
            case 0: {
                return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
            }
            case 1: {
                return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
            }
            case 2: {
                return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
            }
            case 3: {
                return Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220);
            }
            default: {
                return false;
            }
        }
    }
    
    public Dimension getWindowSize() {
        return new Dimension((int)Math.ceil(this.getScreenWidth()), (int)Math.ceil(this.getScreenHeight()));
    }
    
    public void drawString(final Point pos, final int height, final String s, final Color c) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)pos.x, (float)pos.y, 0.0f);
        final double scale = height / (double)Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        GlStateManager.scale(scale, scale, 1.0);
        this.end(false);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, 0.0f, 0.0f, c.getRGB());
        this.begin(false);
        GlStateManager.popMatrix();
    }
    
    public int getFontWidth(final int height, final String s) {
        final double scale = height / (double)Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        return (int)Math.round(Minecraft.getMinecraft().fontRenderer.getStringWidth(s) * scale);
    }
    
    public void fillTriangle(final Point pos1, final Point pos2, final Point pos3, final Color c1, final Color c2, final Color c3) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(4, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)pos1.x, (double)pos1.y, (double)this.getZLevel()).color(c1.getRed() / 255.0f, c1.getGreen() / 255.0f, c1.getBlue() / 255.0f, c1.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)pos2.x, (double)pos2.y, (double)this.getZLevel()).color(c2.getRed() / 255.0f, c2.getGreen() / 255.0f, c2.getBlue() / 255.0f, c2.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)pos3.x, (double)pos3.y, (double)this.getZLevel()).color(c3.getRed() / 255.0f, c3.getGreen() / 255.0f, c3.getBlue() / 255.0f, c3.getAlpha() / 255.0f).endVertex();
        tessellator.draw();
    }
    
    public void drawLine(final Point a, final Point b, final Color c1, final Color c2) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)a.x, (double)a.y, (double)this.getZLevel()).color(c1.getRed() / 255.0f, c1.getGreen() / 255.0f, c1.getBlue() / 255.0f, c1.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)b.x, (double)b.y, (double)this.getZLevel()).color(c2.getRed() / 255.0f, c2.getGreen() / 255.0f, c2.getBlue() / 255.0f, c2.getAlpha() / 255.0f).endVertex();
        tessellator.draw();
    }
    
    public void fillRect(final Rectangle r, final Color c1, final Color c2, final Color c3, final Color c4) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)r.x, (double)(r.y + r.height), (double)this.getZLevel()).color(c4.getRed() / 255.0f, c4.getGreen() / 255.0f, c4.getBlue() / 255.0f, c4.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)(r.y + r.height), (double)this.getZLevel()).color(c3.getRed() / 255.0f, c3.getGreen() / 255.0f, c3.getBlue() / 255.0f, c3.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)r.y, (double)this.getZLevel()).color(c2.getRed() / 255.0f, c2.getGreen() / 255.0f, c2.getBlue() / 255.0f, c2.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)r.x, (double)r.y, (double)this.getZLevel()).color(c1.getRed() / 255.0f, c1.getGreen() / 255.0f, c1.getBlue() / 255.0f, c1.getAlpha() / 255.0f).endVertex();
        tessellator.draw();
    }
    
    public void drawRect(final Rectangle r, final Color c1, final Color c2, final Color c3, final Color c4) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)r.x, (double)(r.y + r.height), (double)this.getZLevel()).color(c4.getRed() / 255.0f, c4.getGreen() / 255.0f, c4.getBlue() / 255.0f, c4.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)(r.y + r.height), (double)this.getZLevel()).color(c3.getRed() / 255.0f, c3.getGreen() / 255.0f, c3.getBlue() / 255.0f, c3.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)r.y, (double)this.getZLevel()).color(c2.getRed() / 255.0f, c2.getGreen() / 255.0f, c2.getBlue() / 255.0f, c2.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)r.x, (double)r.y, (double)this.getZLevel()).color(c1.getRed() / 255.0f, c1.getGreen() / 255.0f, c1.getBlue() / 255.0f, c1.getAlpha() / 255.0f).endVertex();
        tessellator.draw();
    }
    
    public synchronized int loadImage(final String name) {
        try {
            final ResourceLocation rl = new ResourceLocation(this.getResourcePrefix() + name);
            final InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream();
            final BufferedImage image = ImageIO.read(stream);
            final int texture = TextureUtil.glGenTextures();
            TextureUtil.uploadTextureImage(texture, image);
            return texture;
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public void drawImage(final Rectangle r, final int rotation, final boolean parity, final int image, final Color color) {
        if (image == 0) {
            return;
        }
        final int[][] texCoords = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 0, 0 } };
        for (int i = 0; i < rotation % 4; ++i) {
            final int temp1 = texCoords[3][0];
            final int temp2 = texCoords[3][1];
            texCoords[3][0] = texCoords[2][0];
            texCoords[3][1] = texCoords[2][1];
            texCoords[2][0] = texCoords[1][0];
            texCoords[2][1] = texCoords[1][1];
            texCoords[1][0] = texCoords[0][0];
            texCoords[1][1] = texCoords[0][1];
            texCoords[0][0] = temp1;
            texCoords[0][1] = temp2;
        }
        if (parity) {
            int temp3 = texCoords[3][0];
            int temp4 = texCoords[3][1];
            texCoords[3][0] = texCoords[0][0];
            texCoords[3][1] = texCoords[0][1];
            texCoords[0][0] = temp3;
            texCoords[0][1] = temp4;
            temp3 = texCoords[2][0];
            temp4 = texCoords[2][1];
            texCoords[2][0] = texCoords[1][0];
            texCoords[2][1] = texCoords[1][1];
            texCoords[1][0] = temp3;
            texCoords[1][1] = temp4;
        }
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        final FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(4);
        colorBuffer.put(0, color.getRed() / 255.0f);
        colorBuffer.put(1, color.getGreen() / 255.0f);
        colorBuffer.put(2, color.getBlue() / 255.0f);
        colorBuffer.put(3, color.getAlpha() / 255.0f);
        GlStateManager.bindTexture(image);
        GlStateManager.glTexEnv(8960, 8705, colorBuffer);
        GlStateManager.enableTexture2D();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)r.x, (double)(r.y + r.height), (double)this.getZLevel()).tex((double)texCoords[0][0], (double)texCoords[0][1]).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)(r.y + r.height), (double)this.getZLevel()).tex((double)texCoords[1][0], (double)texCoords[1][1]).endVertex();
        bufferbuilder.pos((double)(r.x + r.width), (double)r.y, (double)this.getZLevel()).tex((double)texCoords[2][0], (double)texCoords[2][1]).endVertex();
        bufferbuilder.pos((double)r.x, (double)r.y, (double)this.getZLevel()).tex((double)texCoords[3][0], (double)texCoords[3][1]).endVertex();
        tessellator.draw();
        GlStateManager.disableTexture2D();
    }
    
    protected void scissor(final Rectangle r) {
        if (r == null) {
            GL11.glScissor(0, 0, 0, 0);
            GL11.glEnable(3089);
            return;
        }
        final Point a = this.guiToScreen(r.getLocation());
        final Point b = this.guiToScreen(new Point(r.x + r.width, r.y + r.height));
        if (!this.clipX) {
            a.x = 0;
            b.x = Minecraft.getMinecraft().displayWidth;
        }
        GL11.glScissor(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.abs(b.x - a.x), Math.abs(b.y - a.y));
        GL11.glEnable(3089);
    }
    
    public void window(final Rectangle r) {
        if (this.clipRect.isEmpty()) {
            this.scissor(r);
            this.clipRect.push(r);
        }
        else {
            final Rectangle top = this.clipRect.peek();
            if (top == null) {
                this.scissor(null);
                this.clipRect.push(null);
            }
            else {
                final int x1 = Math.max(r.x, top.x);
                final int y1 = Math.max(r.y, top.y);
                final int x2 = Math.min(r.x + r.width, top.x + top.width);
                final int y2 = Math.min(r.y + r.height, top.y + top.height);
                if (x2 > x1 && y2 > y1) {
                    final Rectangle rect = new Rectangle(x1, y1, x2 - x1, y2 - y1);
                    this.scissor(rect);
                    this.clipRect.push(rect);
                }
                else {
                    this.scissor(null);
                    this.clipRect.push(null);
                }
            }
        }
    }
    
    public void restore() {
        if (!this.clipRect.isEmpty()) {
            this.clipRect.pop();
            if (this.clipRect.isEmpty()) {
                GL11.glDisable(3089);
            }
            else {
                this.scissor(this.clipRect.peek());
            }
        }
    }
    
    public Point screenToGui(final Point p) {
        final int resX = this.getWindowSize().width;
        final int resY = this.getWindowSize().height;
        return new Point(p.x * resX / Minecraft.getMinecraft().displayWidth, resY - p.y * resY / Minecraft.getMinecraft().displayHeight - 1);
    }
    
    public Point guiToScreen(final Point p) {
        final double resX = this.getScreenWidth();
        final double resY = this.getScreenHeight();
        return new Point((int)Math.round(p.x * Minecraft.getMinecraft().displayWidth / resX), (int)Math.round((resY - p.y) * Minecraft.getMinecraft().displayHeight / resY));
    }
    
    protected double getScreenWidth() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth_double();
    }
    
    protected double getScreenHeight() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight_double();
    }
    
    public void begin(final boolean matrix) {
        if (matrix) {
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, this.getScreenWidth(), this.getScreenHeight(), 0.0, -3000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.glLineWidth(2.0f);
        GL11.glPushAttrib(262144);
        GlStateManager.glTexEnvi(8960, 8704, 34160);
        GlStateManager.glTexEnvi(8960, 34161, 8448);
        GlStateManager.glTexEnvi(8960, 34162, 8448);
        GlStateManager.glTexEnvi(8960, 34176, 5890);
        GlStateManager.glTexEnvi(8960, 34192, 768);
        GlStateManager.glTexEnvi(8960, 34184, 5890);
        GlStateManager.glTexEnvi(8960, 34200, 770);
        GlStateManager.glTexEnvi(8960, 34177, 34166);
        GlStateManager.glTexEnvi(8960, 34193, 768);
        GlStateManager.glTexEnvi(8960, 34185, 34166);
        GlStateManager.glTexEnvi(8960, 34201, 770);
    }
    
    public void end(final boolean matrix) {
        GL11.glPopAttrib();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        if (matrix) {
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
        }
    }
    
    protected abstract float getZLevel();
    
    protected abstract String getResourcePrefix();
}
