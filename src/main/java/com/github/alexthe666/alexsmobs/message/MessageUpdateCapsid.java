package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.tileentity.TileEntityCapsid;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageUpdateCapsid(long blockPos, ItemStack heldStack) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageUpdateCapsid> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "update_capsid"));

    public static final StreamCodec<FriendlyByteBuf, MessageUpdateCapsid> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG,
            MessageUpdateCapsid::blockPos,
            ItemStack.STREAM_CODEC,
            MessageUpdateCapsid::heldStack,
            MessageUpdateCapsid::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageUpdateCapsid message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.level() != null) {
                BlockPos pos = BlockPos.of(message.blockPos);
                if (player.level().getBlockEntity(pos) instanceof TileEntityCapsid podium) {
                    podium.setItem(0, message.heldStack);
                }
            }
        });
    }
}
