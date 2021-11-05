/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.world.HoleUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ModuleAnchor
extends Module {
    public ValueNumber height = new ValueNumber("Height", "Height", "", 3, 1, 5);
    public ValueBoolean doubles = new ValueBoolean("Doubles", "Doubles", "", false);
    public ValueBoolean drop = new ValueBoolean("Drop", "Drop", "", false);
    public ValueNumber speed = new ValueNumber("DropSpeed", "DropSpeed", "", Double.longBitsToDouble(Double.doubleToLongBits(5.6987914496487955) ^ 0x7FE6CB8FFC5BDD31L), Double.longBitsToDouble(Double.doubleToLongBits(4.975246101536091) ^ 0x7FAA7F3F7067010FL), Double.longBitsToDouble(Double.doubleToLongBits(1.8960016847361254) ^ 0x7FEA5605DCD1A417L));
    public ValueBoolean pitchD = new ValueBoolean("PitchDepend", "PitchDepend", "", false);
    public ValueNumber pitch = new ValueNumber("Pitch", "Pitch", "", 60, 0, 90);
    public Vec3d Center = Vec3d.ZERO;

    public ModuleAnchor() {
        super("Anchor", "Anchor", "Automatically stops player movement when hovering a hole.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        block8: {
            block7: {
                if (ModuleAnchor.mc.world == null || ModuleAnchor.mc.player == null || ModuleAnchor.mc.player.isInWater() || ModuleAnchor.mc.player.isInLava()) break block7;
                if (!ModuleAnchor.mc.player.isOnLadder()) break block8;
            }
            return;
        }
        for (int i = 0; i < this.height.getValue().intValue(); ++i) {
            if (this.pitchD.getValue() && !(ModuleAnchor.mc.player.rotationPitch >= (float)this.pitch.getValue().intValue())) {
                return;
            }
            if (!HoleUtils.isHole(this.getPlayerPos().down(i))) {
                if (!HoleUtils.isDoubleHole(this.getPlayerPos().down(i)) || !this.doubles.getValue()) continue;
            }
            this.Center = this.getCenter(ModuleAnchor.mc.player.posX, ModuleAnchor.mc.player.posY, ModuleAnchor.mc.player.posZ);
            double XDiff = Math.abs(this.Center.x - ModuleAnchor.mc.player.posX);
            double ZDiff = Math.abs(this.Center.z - ModuleAnchor.mc.player.posZ);
            if (XDiff <= Double.longBitsToDouble(Double.doubleToLongBits(117.46318390069519) ^ 0x7FE4C43D578FF8DAL) && ZDiff <= Double.longBitsToDouble(Double.doubleToLongBits(145.1346023634388) ^ 0x7FDBBDD730040479L)) {
                this.Center = Vec3d.ZERO;
                continue;
            }
            double MotionX = this.Center.x - ModuleAnchor.mc.player.posX;
            double MotionZ = this.Center.z - ModuleAnchor.mc.player.posZ;
            ModuleAnchor.mc.player.motionX = MotionX / Double.longBitsToDouble(Double.doubleToLongBits(0.027934141819797694) ^ 0x7F9C9AC4863B3EDFL);
            ModuleAnchor.mc.player.motionZ = MotionZ / Double.longBitsToDouble(Double.doubleToLongBits(0.38746982320790874) ^ 0x7FD8CC4E3AB75A07L);
            if (!this.drop.getValue()) continue;
            ModuleAnchor.mc.player.motionY -= (double)this.speed.getValue().floatValue();
        }
    }

    /*
     * WARNING - void declaration
     */
    public Vec3d getCenter(double d, double d2, double d3) {
        double x = Math.floor((double)d) + Double.longBitsToDouble(Double.doubleToLongBits(10.889939534281096) ^ 0x7FC5C7A627964F3FL);
        double y = Math.floor((double)d2);
        double z = Math.floor((double)d3) + Double.longBitsToDouble(Double.doubleToLongBits(16.861404934570704) ^ 0x7FD0DC8508A6981DL);
        return new Vec3d(x, y, z);
    }

    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(ModuleAnchor.mc.player.posX), Math.floor(ModuleAnchor.mc.player.posY), Math.floor(ModuleAnchor.mc.player.posZ));
    }
}

