package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.EntityMungus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageMungusBiomeChange(int mungusID, int posX, int posZ, String biomeOption) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageMungusBiomeChange> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "mungus_biome_change"));

    public static final StreamCodec<FriendlyByteBuf, MessageMungusBiomeChange> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            MessageMungusBiomeChange::mungusID,
            ByteBufCodecs.VAR_INT,
            MessageMungusBiomeChange::posX,
            ByteBufCodecs.VAR_INT,
            MessageMungusBiomeChange::posZ,
            ByteBufCodecs.STRING_UTF8,
            MessageMungusBiomeChange::biomeOption,
            MessageMungusBiomeChange::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageMungusBiomeChange message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.mungusID);
                Registry<Biome> registry = player.level().registryAccess().registryOrThrow(Registries.BIOME);
                Biome biome = registry.get(ResourceLocation.parse(message.biomeOption));
                ResourceKey<Biome> resourceKey = registry.getResourceKey(biome).orElse(null);
                Holder<Biome> holder = registry.getHolder(resourceKey).orElse(null);
                if (AMConfig.mungusBiomeTransformationType == 2) {
                    if (entity instanceof EntityMungus && entity.distanceToSqr(message.posX, entity.getY(), message.posZ) < 1000 && biome != null) {
                        LevelChunk chunk = player.level().getChunkAt(new BlockPos(message.posX, 0, message.posZ));
                        int i = QuartPos.fromBlock(chunk.getMinBuildHeight());
                        int k = i + QuartPos.fromBlock(chunk.getHeight()) - 1;
                        int l = Mth.clamp(QuartPos.fromBlock((int)entity.getY()), i, k);
                        int j = chunk.getSectionIndex(QuartPos.toBlock(l));
                        LevelChunkSection section = chunk.getSection(j);
                        if(section != null){
                            PalettedContainer<Holder<Biome>> container = section.getBiomes().recreate();
                            for (int biomeX = 0; biomeX < 4; ++biomeX) {
                                for (int biomeY = 0; biomeY < 4; ++biomeY) {
                                    for (int biomeZ = 0; biomeZ < 4; ++biomeZ) {
                                        container.getAndSetUnchecked(biomeX, biomeY, biomeZ, holder);
                                    }
                                }
                            }
                            section.biomes = container;
                        }
                        AlexsMobs.PROXY.updateBiomeVisuals(message.posX, message.posZ);
                    }
                }
            }
        });
    }
}
