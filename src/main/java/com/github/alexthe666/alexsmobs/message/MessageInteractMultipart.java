package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageInteractMultipart(int parent, boolean offhand) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageInteractMultipart> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "interact_multipart"));

    public static final StreamCodec<FriendlyByteBuf, MessageInteractMultipart> STREAM_CODEC = StreamCodec.composite(
            StreamCodec.INT,
            MessageInteractMultipart::parent,
            StreamCodec.BOOL,
            MessageInteractMultipart::offhand,
            MessageInteractMultipart::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageInteractMultipart message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.level() != null) {
                Entity parent = player.level().getEntity(message.parent);
                if (player.distanceTo(parent) < 20 && parent instanceof Mob) {
                    player.interactOn(parent, message.offhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
                }
            }
        });
    }
}
