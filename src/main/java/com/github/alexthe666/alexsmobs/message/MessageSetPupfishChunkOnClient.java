package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageSetPupfishChunkOnClient(int chunkX, int chunkZ) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSetPupfishChunkOnClient> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "set_pupfish_chunk_on_client"));

    public static final StreamCodec<FriendlyByteBuf, MessageSetPupfishChunkOnClient> STREAM_CODEC = StreamCodec.composite(
            StreamCodec.INT,
            MessageSetPupfishChunkOnClient::chunkX,
            StreamCodec.INT,
            MessageSetPupfishChunkOnClient::chunkZ,
            MessageSetPupfishChunkOnClient::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageSetPupfishChunkOnClient message, IPayloadContext context) {
        context.enqueueWork(() -> {
            AlexsMobs.PROXY.setPupfishChunkForItem(message.chunkX, message.chunkZ);
        });
    }
}
