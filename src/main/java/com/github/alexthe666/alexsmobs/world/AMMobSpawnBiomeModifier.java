package com.github.alexthe666.alexsmobs.world;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AMMobSpawnBiomeModifier implements BiomeModifier {
    private static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<? extends BiomeModifier>> SERIALIZER = DeferredHolder.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "am_mob_spawns"));

    public AMMobSpawnBiomeModifier() {
    }

    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            AMWorldRegistry.addBiomeSpawns(biome, builder);
        }
    }

    public Codec<? extends BiomeModifier> codec() {
        return (Codec)SERIALIZER.get();
    }

    public static Codec<AMMobSpawnBiomeModifier> makeCodec() {
        return Codec.unit(AMMobSpawnBiomeModifier::new);
    }
}
