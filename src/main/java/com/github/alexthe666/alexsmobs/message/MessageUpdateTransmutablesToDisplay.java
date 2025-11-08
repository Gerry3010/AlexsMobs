package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageUpdateTransmutablesToDisplay(int playerId, ItemStack stack1, ItemStack stack2, ItemStack stack3) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageUpdateTransmutablesToDisplay> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "update_transmutables_to_display"));

    public static final StreamCodec<FriendlyByteBuf, MessageUpdateTransmutablesToDisplay> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            MessageUpdateTransmutablesToDisplay::playerId,
            ItemStack.STREAM_CODEC,
            MessageUpdateTransmutablesToDisplay::stack1,
            ItemStack.STREAM_CODEC,
            MessageUpdateTransmutablesToDisplay::stack2,
            ItemStack.STREAM_CODEC,
            MessageUpdateTransmutablesToDisplay::stack3,
            MessageUpdateTransmutablesToDisplay::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageUpdateTransmutablesToDisplay message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.getId() == message.playerId) {
                AlexsMobs.PROXY.setDisplayTransmuteResult(0, message.stack1);
                AlexsMobs.PROXY.setDisplayTransmuteResult(1, message.stack2);
                AlexsMobs.PROXY.setDisplayTransmuteResult(2, message.stack3);
            }
        });
    }
}
