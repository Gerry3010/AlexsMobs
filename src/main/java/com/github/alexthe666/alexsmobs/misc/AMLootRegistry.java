package com.github.alexthe666.alexsmobs.misc;
import net.minecraft.core.registries.Registries;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;

public class AMLootRegistry {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> DEF_REG = DeferredRegister.create(Neonet.minecraft.core.registries.BuiltInRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AlexsMobs.MODID);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<BananaLootModifier>> BANANA_DROP = DEF_REG.register("banana_drop", BananaLootModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<BlossomLootModifier>> BLOSSOM_DROP = DEF_REG.register("blossom_drop", BlossomLootModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<AncientDartLootModifier>> ANCIENT_DART = DEF_REG.register("ancient_dart", AncientDartLootModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<PigshoesLootModifier>> PIGSHOES = DEF_REG.register("pigshoes", PigshoesLootModifier.CODEC);
}
