package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageSendVisualFlagFromServer(int entityID, int flag) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSendVisualFlagFromServer> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "send_visual_flag_from_server"));

    public static final StreamCodec<FriendlyByteBuf, MessageSendVisualFlagFromServer> STREAM_CODEC = StreamCodec.composite(
            StreamCodec.INT,
            MessageSendVisualFlagFromServer::entityID,
            StreamCodec.INT,
            MessageSendVisualFlagFromServer::flag,
            MessageSendVisualFlagFromServer::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageSendVisualFlagFromServer message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.entityID);
                AlexsMobs.PROXY.processVisualFlag(entity, message.flag);
            }
        });
    }
}
