/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.api.utilities.world.BlockUtils;
import com.europa.api.utilities.world.HoleUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class ModuleHoleESP
extends Module {
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.Normal);
    public ValueNumber height = new ValueNumber("MainHeight", "MainHeight", "", Double.longBitsToDouble(Double.doubleToLongBits(7.8620089267176585) ^ 0x7FEF72B277D4731AL), Double.longBitsToDouble(Double.doubleToLongBits(-5.5422599329261075) ^ 0x7FE62B4630176528L), Double.longBitsToDouble(Double.doubleToLongBits(0.6271762968980095) ^ 0x7FEC11D406801B56L));
    public ValueNumber outlineWidth = new ValueNumber("OutlineWidth", "OutlineWidth", "", Double.longBitsToDouble(Double.doubleToLongBits(0.21026312703273062) ^ 0x7FCAE9E6F31483C7L), Double.longBitsToDouble(Double.doubleToLongBits(3.474827062334602) ^ 0x7FEBCC72217FDCEAL), Double.longBitsToDouble(Double.doubleToLongBits(1.936454765162052) ^ 0x7FEAFBB7FDE8DEB7L));
    public ValueBoolean doubles = new ValueBoolean("Doubles", "Doubles", "", true);
    public ValueNumber range = new ValueNumber("Range", "Range", "", 8, 0, 10);
    public ValueColor obsidian = new ValueColor("ObiColor", "ObiColor", "", new Color(255, 0, 0, 120));
    public ValueColor bedrock = new ValueColor("BrockColor", "BrockColor", "", new Color(0, 255, 0, 120));
    public ValueColor doubleColor = new ValueColor("DoubleColor", "DoubleColor", "", new Color(100, 0, 255, 120));
    public ValueColor obsidianOL = new ValueColor("ObiOutlineColor", "ObiOutlineColor", "", new Color(255, 0, 0, 255));
    public ValueColor bedrockOL = new ValueColor("BrockOutlineColor", "BrockOutlineColor", "", new Color(0, 255, 0, 255));
    public ValueColor doubleOL = new ValueColor("DoubleOutlineColor", "DoubleOutlineColor", "", new Color(100, 0, 255, 255));
    public List<HoleInfo> hole = new ArrayList<HoleInfo>();

    public ModuleHoleESP() {
        super("HoleESP", "Hole ESP", "Draws an ESP on holes that you can get into.", ModuleCategory.RENDER);
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        if (ModuleHoleESP.mc.player == null || ModuleHoleESP.mc.world == null) {
            return;
        }
        RenderUtils.camera.setPosition(Objects.requireNonNull(ModuleHoleESP.mc.getRenderViewEntity()).posX, ModuleHoleESP.mc.getRenderViewEntity().posY, ModuleHoleESP.mc.getRenderViewEntity().posZ);
        ArrayList<HoleInfo> currentHoles = new ArrayList<HoleInfo>();
        currentHoles.addAll(this.hole);
        currentHoles.forEach(this::lambda$onRender3D$0);
    }

    @Override
    public void onUpdate() {
        if (ModuleHoleESP.mc.player == null || ModuleHoleESP.mc.world == null) {
            return;
        }
        ArrayList<HoleInfo> holes = new ArrayList<HoleInfo>();
        for (BlockPos potentialHole : BlockUtils.getNearbyBlocks((EntityPlayer)ModuleHoleESP.mc.player, this.range.getValue().intValue(), false)) {
            if (!(ModuleHoleESP.mc.world.getBlockState(potentialHole).getBlock() instanceof BlockAir)) continue;
            if (HoleUtils.isBedrockHole(potentialHole)) {
                holes.add(new HoleInfo(this, potentialHole, Type.Bedrock, 0, 0));
            } else if (HoleUtils.isObiHole(potentialHole)) {
                holes.add(new HoleInfo(this, potentialHole, Type.Obsidian, 0, 0));
            }
            if (!this.doubles.getValue()) continue;
            if (HoleUtils.isDoubleBedrockHoleX(potentialHole.west()) || HoleUtils.isDoubleObsidianHoleX(potentialHole.west())) {
                holes.add(new HoleInfo(this, potentialHole.west(), Type.Double, 1, 0));
                continue;
            }
            if (!HoleUtils.isDoubleBedrockHoleZ(potentialHole.north())) {
                if (!HoleUtils.isDoubleObsidianHoleZ(potentialHole.north())) continue;
            }
            holes.add(new HoleInfo(this, potentialHole.north(), Type.Double, 0, 1));
        }
        this.hole.clear();
        this.hole.addAll(holes);
    }

    /*
     * WARNING - void declaration
     */
    public void renderHole(BlockPos blockPos, Type type, double d, double d2) {
        block1: {
            Color color;
            void type2;
            void width;
            void length;
            void hole;
            AxisAlignedBB box = new AxisAlignedBB((double)hole.getX() - ModuleHoleESP.mc.getRenderManager().viewerPosX, (double)hole.getY() - ModuleHoleESP.mc.getRenderManager().viewerPosY, (double)hole.getZ() - ModuleHoleESP.mc.getRenderManager().viewerPosZ, (double)(hole.getX() + 1) - ModuleHoleESP.mc.getRenderManager().viewerPosX + length, (double)hole.getY() + this.height.getValue().doubleValue() - ModuleHoleESP.mc.getRenderManager().viewerPosY, (double)hole.getZ() + width + Double.longBitsToDouble(Double.doubleToLongBits(25.803604906891547) ^ 0x7FC9CDB90D1A011BL) - ModuleHoleESP.mc.getRenderManager().viewerPosZ);
            Color color2 = type2 == Type.Bedrock ? this.bedrockOL.getValue() : (color = type2 == Type.Obsidian ? this.obsidianOL.getValue() : this.doubleOL.getValue());
            if (this.mode.getValue().equals((Object)modes.Normal)) {
                RenderUtils.drawBlockOutline(box, color, this.outlineWidth.getValue().floatValue());
                color = type2 == Type.Bedrock ? this.bedrock.getValue() : (type2 == Type.Obsidian ? this.obsidian.getValue() : this.doubleColor.getValue());
                RenderUtils.drawBoxBB(box.shrink(Double.longBitsToDouble(Double.doubleToLongBits(1010.6878854844731) ^ 0x7FEFF7CD2A1AD9A4L)), color);
            }
            if (!this.mode.getValue().equals((Object)modes.Glow)) break block1;
            RenderUtils.drawGradientBlockOutline(box, new Color(0, 0, 0, 0), color, this.outlineWidth.getValue().floatValue());
            color = type2 == Type.Bedrock ? this.bedrock.getValue() : (type2 == Type.Obsidian ? this.obsidian.getValue() : this.doubleColor.getValue());
            RenderUtils.drawOpenGradientBoxBB(box.shrink(Double.longBitsToDouble(Double.doubleToLongBits(3872.007843348089) ^ 0x7FDA3AE5440B1711L)), color, new Color(0, 0, 0, 0), false);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void lambda$onRender3D$0(HoleInfo holeInfo) {
        void holeInfo2;
        this.renderHole(holeInfo2.pos, holeInfo2.type, holeInfo2.length, holeInfo2.width);
    }

    public class HoleInfo {
        public BlockPos pos;
        public Type type;
        public int length;
        public int width;
        public ModuleHoleESP this$0;

        public HoleInfo(ModuleHoleESP this$0, BlockPos pos, Type type, int length, int width) {
            this.this$0 = this$0;
            this.pos = pos;
            this.type = type;
            this.length = length;
            this.width = width;
        }
    }

    public static enum Type {
        Obsidian,
        Bedrock,
        Double;

    }

    public static enum modes {
        Normal,
        Glow;

    }
}

