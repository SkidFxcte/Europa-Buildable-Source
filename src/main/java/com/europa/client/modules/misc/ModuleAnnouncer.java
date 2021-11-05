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
        double d3;
        double d2;
        double d0;
        ++blockBrokeDelay;
        ++blockPlacedDelay;
        ++jumpDelay;
        ++attackDelay;
        ++eattingDelay;
        this.heldItem = ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName();
        switch (1.$SwitchMap$com$europa$client$modules$misc$ModuleAnnouncer$modes[((modes)this.mode.getValue()).ordinal()]) {
            case 1: {
                walkMessage = "I just walked {blocks} meters thanks to Europa!";
                placeMessage = "I just placed {amount} {name} thanks to Europa!";
                jumpMessage = "I just jumped thanks to Europa!";
                breakMessage = "I just mined {amount} {name} thanks to Europa!";
                attackMessage = "I just attacked {name} with a {item} thanks to Europa!";
                eatMessage = "I just ate {amount} {name} thanks to Europa!";
                guiMessage = "I just opened my advanced GUI thanks to Europa!";
                break;
            }
            case 2: {
                walkMessage = "\u05d4\u05e8\u05d2\u05e2 \u05d4\u05dc\u05db\u05ea\u05d9 {blocks} \u05de\u05d8\u05e8\u05d9\u05dd \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                placeMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e9\u05de\u05ea\u05d9 {amount} {name} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                jumpMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e7\u05e4\u05e6\u05ea\u05d9 \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                breakMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e9\u05d1\u05e8\u05ea\u05d9 {amount} {name} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                attackMessage = "\u05d4\u05e8\u05d2\u05e2 \u05d4\u05db\u05d9\u05ea\u05d9 \u05d0\u05ea {name} \u05e2\u05dd {item} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                eatMessage = "\u05d4\u05e8\u05d2\u05e2 \u05d0\u05db\u05dc\u05ea\u05d9 {amount} {name} \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                guiMessage = "\u05d4\u05e8\u05d2\u05e2 \u05e4\u05ea\u05d7\u05ea\u05d9 \u05d0\u05ea \u05d4 GUI \u05d4\u05de\u05ea\u05e7\u05d3\u05dd \u05e9\u05dc\u05d9 \u05ea\u05d5\u05d3\u05d5\u05ea \u05dcEuropa!";
                break;
            }
            case 3: {
                walkMessage = "Ich bin grade {blocks} meter gelaufen dank Europa!";
                placeMessage = "Ich habe grade {amount}{name} plaziert dank Europa!";
                jumpMessage = "Ich bin grade gesprungen dank Europa!";
                breakMessage = "Ich habe grade {amount}{name} gemined dank Europa!";
                attackMessage = "Ich habe grade {name} mit einem {item} attackiert dank Europa!";
                eatMessage = "Ich habe grade {amount} {name} gegessen dank Europa!";
                guiMessage = "Ich habe grade mein erweitertes GUI ge\u00f6ffnet dank Europa!";
                break;
            }
            case 4: {
                walkMessage = "Acabo de andar {blocks} metros gracias a Europa!";
                placeMessage = "Acabo de colocar {amount} {name} gracias a Europa!";
                jumpMessage = "Acabo de saltar gracias a Europa!";
                breakMessage = "Acabo de minar {amount} {name} gracias a Europa!";
                attackMessage = "Acabo de atacar a {name} con {item} gracias a Europa!";
                eatMessage = "Acabo de comer {amount} {name} gracias a Europa!";
                guiMessage = "Acabo de abrir mi GUI avanzado gracias a Europa!";
                break;
            }
            case 5: {
                walkMessage = "a young nigga jus stepped {blocks} meters because of muffukin Europa!";
                placeMessage = "a young nigga jus placed {amount} {name} because of muffukin Europa!";
                jumpMessage = "a young nigga jus jumped because of muffukin Europa!";
                breakMessage = "a young nigga jus mined {amount} {name} because of muffukin Europa!";
                attackMessage = "a young nigga jus jumped {name} with a muffukin {item} because of muffukin Europa!";
                eatMessage = "a young nigga jus smoked {amount} weed because of muffukin Europa!";
                guiMessage = "a young nigga just open da muffukin advanced GUI because of muffukin Europa!";
                break;
            }
            case 6: {
                walkMessage = "Ik heb net {blocks} gelopen door Europa!";
                placeMessage = "Ik heb net {amount} {name} geplaatst door Europa!";
                jumpMessage = "Ik sprong door Europa!";
                breakMessage = "Ik heb {amount} {name} gebroken door Europa!";
                attackMessage = "Ik heb zojuist {naam} aangevallen met een {item} dankzij Europa!";
                eatMessage = "Ik heb net {amount} {name} gegeten door Europa!";
                guiMessage = "Ik heb mijn hackerman menu geopend door Europa!";
                break;
            }
            case 7: {
                walkMessage = "Eu acabei de andar {blocks} gracas a Europa!";
                placeMessage = "Eu acabei de colocar {amount} {name} gracas a Europa!";
                jumpMessage = "Eu acabei de pular gracas a Europa!";
                breakMessage = "Eu acabei de minerar {amount} {name} gracas a Europa!";
                attackMessage = "Eu acabei de atacar {name} com {item} gracas a Europa!";
                eatMessage = "Eu acabei de comer {amount} {name} gracas a Europa!";
                guiMessage = "Eu acabei de abrir a minha avan\u00c3\u00a7ada GUI gracas a Europa!";
            }
        }
        if (this.walk.getValue() && lastPositionUpdate + ((long)2024053244 ^ 0x78A48A74L) * (long)this.delay.getValue().intValue() < System.currentTimeMillis() && !((speed = Math.sqrt((d0 = lastPositionX - ModuleAnnouncer.mc.player.lastTickPosX) * d0 + (d2 = lastPositionY - ModuleAnnouncer.mc.player.lastTickPosY) * d2 + (d3 = lastPositionZ - ModuleAnnouncer.mc.player.lastTickPosZ) * d3)) <= Double.longBitsToDouble(Double.doubleToLongBits(8.014939268986437) ^ 0x7FD007A61EAF721FL)) && !(speed > Double.longBitsToDouble(Double.doubleToLongBits(0.0016552172648570918) ^ 0x7FE8967C014D2E71L))) {
            String walkAmount = new DecimalFormat("0.00").format(speed);
            Random random = new Random();
            if (this.clientSide.getValue()) {
                ChatManager.printChatNotifyClient(walkMessage.replace("{blocks}", walkAmount));
            } else {
                ModuleAnnouncer.mc.player.sendChatMessage(walkMessage.replace("{blocks}", walkAmount));
            }
            lastPositionUpdate = System.currentTimeMillis();
            lastPositionX = ModuleAnnouncer.mc.player.lastTickPosX;
            lastPositionY = ModuleAnnouncer.mc.player.lastTickPosY;
            lastPositionZ = ModuleAnnouncer.mc.player.lastTickPosZ;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void entityUseitem(LivingEntityUseItemEvent.Finish finish) {
        void event;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        if (event.getEntity() == ModuleAnnouncer.mc.player && (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold)) {
            ++this.eaten;
            if (eattingDelay >= 300 * this.delay.getValue().intValue()) {
                if (this.eat.getValue()) {
                    if (this.eaten > randomNum) {
                        Random random = new Random();
                        if (this.clientSide.getValue()) {
                            ChatManager.printChatNotifyClient(eatMessage.replace("{amount}", this.eaten + "").replace("{name}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName()));
                        } else {
                            ModuleAnnouncer.mc.player.sendChatMessage(eatMessage.replace("{amount}", this.eaten + "").replace("{name}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName()));
                        }
                        this.eaten = 0;
                        eattingDelay = 0;
                    }
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onSend(EventPacket.Send send) {
        void event;
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && ModuleAnnouncer.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            ++this.blocksPlaced;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
            if (blockPlacedDelay >= 150 * this.delay.getValue().intValue() && this.place.getValue() && this.blocksPlaced > randomNum) {
                Random random = new Random();
                String msg = placeMessage.replace("{amount}", this.blocksPlaced + "").replace("{name}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName());
                if (this.clientSide.getValue()) {
                    ChatManager.printChatNotifyClient(msg);
                } else {
                    ModuleAnnouncer.mc.player.sendChatMessage(msg);
                }
                this.blocksPlaced = 0;
                blockPlacedDelay = 0;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void destroyBlock(EventPlayerDestroyBlock eventPlayerDestroyBlock) {
        ++this.blocksBroken;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        if (blockBrokeDelay >= 300 * this.delay.getValue().intValue() && this.breaking.getValue() && this.blocksBroken > randomNum) {
            void event;
            Random random = new Random();
            String msg = breakMessage.replace("{amount}", this.blocksBroken + "").replace("{name}", ModuleAnnouncer.mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
            if (this.clientSide.getValue()) {
                ChatManager.printChatNotifyClient(msg);
            } else {
                ModuleAnnouncer.mc.player.sendChatMessage(msg);
            }
            this.blocksBroken = 0;
            blockBrokeDelay = 0;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void attackEntity(AttackEntityEvent attackEntityEvent) {
        void event;
        if (this.attack.getValue() && !(event.getTarget() instanceof EntityEnderCrystal)) {
            if (attackDelay >= 300 * this.delay.getValue().intValue()) {
                String msg = attackMessage.replace("{name}", event.getTarget().getName()).replace("{item}", ModuleAnnouncer.mc.player.getHeldItemMainhand().getDisplayName());
                if (this.clientSide.getValue()) {
                    ChatManager.printChatNotifyClient(msg);
                } else {
                    ModuleAnnouncer.mc.player.sendChatMessage(msg);
                }
                attackDelay = 0;
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

