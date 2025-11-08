package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.inventory.MenuTransmutationTable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageTransmuteFromMenu(int playerId, int choice) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageTransmuteFromMenu> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "transmute_from_menu"));

    public static final StreamCodec<FriendlyByteBuf, MessageTransmuteFromMenu> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            MessageTransmuteFromMenu::playerId,
            ByteBufCodecs.VAR_INT,
            MessageTransmuteFromMenu::choice,
            MessageTransmuteFromMenu::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageTransmuteFromMenu message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.getId() == message.playerId && player.containerMenu instanceof MenuTransmutationTable) {
                MenuTransmutationTable table = (MenuTransmutationTable) player.containerMenu;
                table.transmute(player, message.choice);
            }
        });
    }
}
