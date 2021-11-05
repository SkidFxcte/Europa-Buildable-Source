/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.event.impl.player.EventPlayerDestroyBlock;
import com.europa.api.manager.event.impl.player.EventPlayerJump;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleAnnouncer
extends Module {
    public static String walkMessage;
    public static String placeMessage;
    public static String jumpMessage;
    public static String breakMessage;
    public static String attackMessage;
    public static String eatMessage;
    public static String guiMessage;
    public static int blockBrokeDelay;
    public static int blockPlacedDelay;
    public static int jumpDelay;
    public static int attackDelay;
    public static int eattingDelay;
    public static long lastPositionUpdate;
    public static double lastPositionX;
    public static double lastPositionY;
    public static double lastPositionZ;
    public static double speed;
    public String heldItem = "";
    public int blocksPlaced = 0;
    public int blocksBroken = 0;
    public int eaten = 0;
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.English);
    public ValueBoolean clientSide = new ValueBoolean("Private", "Private", "", true);
    public ValueBoolean walk = new ValueBoolean("Walk", "Walk", "", true);
    public ValueBoolean place = new ValueBoolean("Place", "Place", "", true);
    public ValueBoolean jump = new ValueBoolean("Jump", "Jump", "", true);
    public ValueBoolean breaking = new ValueBoolean("Breaking", "Breaking", "", true);
    public ValueBoolean attack = new ValueBoolean("Attack", "Attack", "", true);
    public ValueBoolean eat = new ValueBoolean("Eat", "Eat", "", true);
    public ValueNumber delay = new ValueNumber("Delay", "Delay", "", 5, 1, 20);

    public ModuleAnnouncer() {
        super("Announcer", "Announcer", "Automatically sends messages in chat saying something that you have done.", ModuleCategory.MISC);
    }

    @Override
    public void onUpdate() {
        ++ModuleAnnouncer.blockBrokeDelay;
        ++ModuleAnnouncer.blockPlacedDelay;
        ++ModuleAnnouncer.jumpDelay;
        ++ModuleAnnouncer.attackDelay;
        ++ModuleAnnouncer.eattingDelay;
        this.heldItem = ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName();
        switch ((modes)this.mode.getValue()) {
            case English: {
                ModuleAnnouncer.walkMessage = "I just walked {blocks} meters thanks to Europa!";
                ModuleAnnouncer.placeMessage = "I just placed {amount} {name} thanks to Europa!";
                ModuleAnnouncer.jumpMessage = "I just jumped thanks to Europa!";
                ModuleAnnouncer.breakMessage = "I just mined {amount} {name} thanks to Europa!";
                ModuleAnnouncer.attackMessage = "I just attacked {name} with a {item} thanks to Europa!";
                ModuleAnnouncer.eatMessage = "I just ate {amount} {name} thanks to Europa!";
                ModuleAnnouncer.guiMessage = "I just opened my advanced GUI thanks to Europa!";
                break;
            }
            case Hebrew: {
                ModuleAnnouncer.walkMessage = "\u05d4\u05e8\u05d2\u05e2 \u05d4\u05dc\u05db\u05ea\u05d9 {blocks} \u05de\u05d8\u05e8\u05d9\u05dd \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                ModuleAnnouncer.placeMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e9\u05de\u05ea\u05d9 {amount} {name} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                ModuleAnnouncer.jumpMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e7\u05e4\u05e6\u05ea\u05d9 \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                ModuleAnnouncer.breakMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e9\u05d1\u05e8\u05ea\u05d9 {amount} {name} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                ModuleAnnouncer.attackMessage = "\u05d4\u05e8\u05d2\u05e2 \u05d4\u05db\u05d9\u05ea\u05d9 \u05d0\u05ea {name} \u05e2\u05dd {item} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                ModuleAnnouncer.eatMessage = "\u05d4\u05e8\u05d2\u05e2 \u05d0\u05db\u05dc\u05ea\u05d9 {amount} {name} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                ModuleAnnouncer.guiMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e4\u05ea\u05d7\u05ea\u05d9 \u05d0\u05ea \u05d4 GUI \u05d4\u05de\u05ea\u05e7\u05d3\u05dd \u05e9\u05dc\u05d9 \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                break;
            }
            case German: {
                ModuleAnnouncer.walkMessage = "Ich bin grade {blocks} meter gelaufen dank Europa!";
                ModuleAnnouncer.placeMessage = "Ich habe grade {amount}{name} plaziert dank Europa!";
                ModuleAnnouncer.jumpMessage = "Ich bin grade gesprungen dank Europa!";
                ModuleAnnouncer.breakMessage = "Ich habe grade {amount}{name} gemined dank Europa!";
                ModuleAnnouncer.attackMessage = "Ich habe grade {name} mit einem {item} attackiert dank Europa!";
                ModuleAnnouncer.eatMessage = "Ich habe grade {amount} {name} gegessen dank Europa!";
                ModuleAnnouncer.guiMessage = "Ich habe grade mein erweitertes GUI ge\u00f6ffnet dank Europa!";
                break;
            }
            case Spanish: {
                ModuleAnnouncer.walkMessage = "Acabo de andar {blocks} metros gracias a Europa!";
                ModuleAnnouncer.placeMessage = "Acabo de colocar {amount} {name} gracias a Europa!";
                ModuleAnnouncer.jumpMessage = "Acabo de saltar gracias a Europa!";
                ModuleAnnouncer.breakMessage = "Acabo de minar {amount} {name} gracias a Europa!";
                ModuleAnnouncer.attackMessage = "Acabo de atacar a {name} con {item} gracias a Europa!";
                ModuleAnnouncer.eatMessage = "Acabo de comer {amount} {name} gracias a Europa!";
                ModuleAnnouncer.guiMessage = "Acabo de abrir mi GUI avanzado gracias a Europa!";
                break;
            }
            case Swag: {
                ModuleAnnouncer.walkMessage = "a young nigga jus stepped {blocks} meters because of muffukin Europa!";
                ModuleAnnouncer.placeMessage = "a young nigga jus placed {amount} {name} because of muffukin Europa!";
                ModuleAnnouncer.jumpMessage = "a young nigga jus jumped because of muffukin Europa!";
                ModuleAnnouncer.breakMessage = "a young nigga jus mined {amount} {name} because of muffukin Europa!";
                ModuleAnnouncer.attackMessage = "a young nigga jus jumped {name} with a muffukin {item} because of muffukin Europa!";
                ModuleAnnouncer.eatMessage = "a young nigga jus smoked {amount} weed because of muffukin Europa!";
                ModuleAnnouncer.guiMessage = "a young nigga just open da muffukin advanced GUI because of muffukin Europa!";
                break;
            }
            case Dutch: {
                ModuleAnnouncer.walkMessage = "Ik heb net {blocks} gelopen door Europa!";
                ModuleAnnouncer.placeMessage = "Ik heb net {amount} {name} geplaatst door Europa!";
                ModuleAnnouncer.jumpMessage = "Ik sprong door Europa!";
                ModuleAnnouncer.breakMessage = "Ik heb {amount} {name} gebroken door Europa!";
                ModuleAnnouncer.attackMessage = "Ik heb zojuist {naam} aangevallen met een {item} dankzij Europa!";
                ModuleAnnouncer.eatMessage = "Ik heb net {amount} {name} gegeten door Europa!";
                ModuleAnnouncer.guiMessage = "Ik heb mijn hackerman menu geopend door Europa!";
                break;
            }
            case Portuguese: {
                ModuleAnnouncer.walkMessage = "Eu acabei de andar {blocks} gracas a Europa!";
                ModuleAnnouncer.placeMessage = "Eu acabei de colocar {amount} {name} gracas a Europa!";
                ModuleAnnouncer.jumpMessage = "Eu acabei de pular gracas a Europa!";
                ModuleAnnouncer.breakMessage = "Eu acabei de minerar {amount} {name} gracas a Europa!";
                ModuleAnnouncer.attackMessage = "Eu acabei de atacar {name} com {item} gracas a Europa!";
                ModuleAnnouncer.eatMessage = "Eu acabei de comer {amount} {name} gracas a Europa!";
                ModuleAnnouncer.guiMessage = "Eu acabei de abrir a minha avan\u00c3§ada GUI gracas a Europa!";
                break;
            }
        }
        if (this.walk.getValue() && ModuleAnnouncer.lastPositionUpdate + ((long)2024053244 ^ 0x78A48A74L) * this.delay.getValue().intValue() < System.currentTimeMillis()) {
            final double d0 = ModuleAnnouncer.lastPositionX - ModuleAnnouncer.mc.player.lastTickPosX;
            final double d2 = ModuleAnnouncer.lastPositionY - ModuleAnnouncer.mc.player.lastTickPosY;
            final double d3 = ModuleAnnouncer.lastPositionZ - ModuleAnnouncer.mc.player.lastTickPosZ;
            ModuleAnnouncer.speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
            if (ModuleAnnouncer.speed > Double.longBitsToDouble(Double.doubleToLongBits(8.014939268986437) ^ 0x7FD007A61EAF721FL) && ModuleAnnouncer.speed <= Double.longBitsToDouble(Double.doubleToLongBits(0.0016552172648570918) ^ 0x7FE8967C014D2E71L)) {
                final String walkAmount = new DecimalFormat("0.00").format(ModuleAnnouncer.speed);
                final Random random = new Random();
                if (this.clientSide.getValue()) {
                    ChatManager.printChatNotifyClient(ModuleAnnouncer.walkMessage.replace("{blocks}", walkAmount));
                }
                else {
                    ModuleAnnouncer.mc.player.sendChatMessage(ModuleAnnouncer.walkMessage.replace("{blocks}", walkAmount));
                }
                ModuleAnnouncer.lastPositionUpdate = System.currentTimeMillis();
                ModuleAnnouncer.lastPositionX = ModuleAnnouncer.mc.player.lastTickPosX;
                ModuleAnnouncer.lastPositionY = ModuleAnnouncer.mc.player.lastTickPosY;
                ModuleAnnouncer.lastPositionZ = ModuleAnnouncer.mc.player.lastTickPosZ;
            }
        }
    }

    @SubscribeEvent
    public void entityUseitem(final LivingEntityUseItemEvent.Finish event) {
        final int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        if (event.getEntity() == ModuleAnnouncer.mc.player) {
            if (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold) {
                ++this.eaten;
                if (ModuleAnnouncer.eattingDelay >= 300 * this.delay.getValue().intValue()) {
                    if (this.eat.getValue()) {
                        if (this.eaten > randomNum) {
                            final Random random = new Random();
                            if (this.clientSide.getValue()) {
                                ChatManager.printChatNotifyClient(ModuleAnnouncer.eatMessage.replace("{amount}", this.eaten + "").replace("{name}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName()));
                            }
                            else {
                                ModuleAnnouncer.mc.player.sendChatMessage(ModuleAnnouncer.eatMessage.replace("{amount}", this.eaten + "").replace("{name}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName()));
                            }
                            this.eaten = 0;
                            ModuleAnnouncer.eattingDelay = 0;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSend(final EventPacket.Send event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && ModuleAnnouncer.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            ++this.blocksPlaced;
            final int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
            if (ModuleAnnouncer.blockPlacedDelay >= 150 * this.delay.getValue().intValue() && this.place.getValue() && this.blocksPlaced > randomNum) {
                final Random random = new Random();
                final String msg = ModuleAnnouncer.placeMessage.replace("{amount}", this.blocksPlaced + "").replace("{name}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName());
                if (this.clientSide.getValue()) {
                    ChatManager.printChatNotifyClient(msg);
                }
                else {
                    ModuleAnnouncer.mc.player.sendChatMessage(msg);
                }
                this.blocksPlaced = 0;
                ModuleAnnouncer.blockPlacedDelay = 0;
            }
        }
    }

    @SubscribeEvent
    public void destroyBlock(final EventPlayerDestroyBlock event) {
        ++this.blocksBroken;
        final int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        if (ModuleAnnouncer.blockBrokeDelay >= 300 * this.delay.getValue().intValue() && this.breaking.getValue() && this.blocksBroken > randomNum) {
            final Random random = new Random();
            final String msg = ModuleAnnouncer.breakMessage.replace("{amount}", this.blocksBroken + "").replace("{name}", ModuleAnnouncer.mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
            if (this.clientSide.getValue()) {
                ChatManager.printChatNotifyClient(msg);
            }
            else {
                ModuleAnnouncer.mc.player.sendChatMessage(msg);
            }
            this.blocksBroken = 0;
            ModuleAnnouncer.blockBrokeDelay = 0;
        }
    }


    @SubscribeEvent
    public void attackEntity(final AttackEntityEvent event) {
        if (this.attack.getValue()) {
            if (!(event.getTarget() instanceof EntityEnderCrystal)) {
                if (ModuleAnnouncer.attackDelay >= 300 * this.delay.getValue().intValue()) {
                    final String msg = ModuleAnnouncer.attackMessage.replace("{name}", event.getTarget().getName()).replace("{item}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName());
                    if (this.clientSide.getValue()) {
                        ChatManager.printChatNotifyClient(msg);
                    }
                    else {
                        ModuleAnnouncer.mc.player.sendChatMessage(msg);
                    }
                    ModuleAnnouncer.attackDelay = 0;
                }
            }
        }
    }


    @SubscribeEvent
    public void onJump(EventPlayerJump eventPlayerJump) {
        if (this.jump.getValue() && jumpDelay >= 300 * this.delay.getValue().intValue()) {
            if (this.clientSide.getValue()) {
                Random random = new Random();
                ChatManager.printChatNotifyClient(jumpMessage);
            } else {
                Random random = new Random();
                ModuleAnnouncer.mc.player.sendChatMessage(jumpMessage);
            }
            jumpDelay = 0;
        }
    }

    @Override
    public void onEnable() {
        this.blocksPlaced = 0;
        this.blocksBroken = 0;
        this.eaten = 0;
        speed = Double.longBitsToDouble(Double.doubleToLongBits(1.0860822019206848E308) ^ 0x7FE3553941D79156L);
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }

    static {
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }

    public static enum modes {
        English,
        Hebrew,
        German,
        Spanish,
        Swag,
        Dutch,
        Portuguese;

    }
}

