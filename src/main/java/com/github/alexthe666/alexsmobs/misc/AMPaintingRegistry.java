package com.github.alexthe666.alexsmobs.misc;
import net.minecraft.core.registries.Registries;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AMPaintingRegistry {
    public static final DeferredRegister<PaintingVariant> DEF_REG = DeferredRegister.create(Registries.PAINTING_VARIANT, AlexsMobs.MODID);

    public static final DeferredHolder<PaintingVariant> NFT = DEF_REG.register("nft", () -> new PaintingVariant(32, 32));
    public static final DeferredHolder<PaintingVariant> DOG_POKER = DEF_REG.register("dog_poker", () -> new PaintingVariant(32, 16));
}
