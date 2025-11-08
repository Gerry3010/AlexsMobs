package com.github.alexthe666.alexsmobs.enchantment;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Enchantment Registry for Alex's Mobs
 * 
 * NOTE: In Minecraft 1.21, enchantments are data-driven and defined in JSON files.
 * This registry class remains for reference, but actual enchantment definitions
 * will be in data/alexsmobs/enchantment/ as JSON files.
 * 
 * The enchantments registered here serve as holders for code references,
 * but their properties (rarity, cost, max level, etc.) are defined in data files.
 * 
 * Custom behaviors are handled through:
 * - EnchantmentHelper.getItemEnchantmentLevel() checks in entity/item code
 * - Event handlers for custom effects
 * 
 * TODO: Create corresponding JSON files in data/alexsmobs/enchantment/:
 * - straddle_jump.json (allows higher jumps on straddleboard, max level 3)
 * - lavawax.json (fire resistance while riding, max level 1)
 * - serpentfriend.json (bone serpent pacification, max level 1)  
 * - board_return.json (returns board to player on dismount, max level 1)
 */
public class AMEnchantmentRegistry {

    // In 1.21, enchantments are data-driven and defined in JSON files at data/alexsmobs/enchantment/
    // We use ResourceKeys to reference them in code for EnchantmentHelper lookups
    
    // Straddleboard enchantments - properties defined in data/alexsmobs/enchantment/*.json
    // Custom behaviors are implemented in EntityStraddleboard and event handlers
    
    public static final ResourceKey<Enchantment> STRADDLE_JUMP = ResourceKey.create(
        Registries.ENCHANTMENT, 
        new ResourceLocation(AlexsMobs.MODID, "straddle_jump")
    );
    
    public static final ResourceKey<Enchantment> LAVAWAX = ResourceKey.create(
        Registries.ENCHANTMENT, 
        new ResourceLocation(AlexsMobs.MODID, "lavawax")
    );
    
    public static final ResourceKey<Enchantment> SERPENTFRIEND = ResourceKey.create(
        Registries.ENCHANTMENT, 
        new ResourceLocation(AlexsMobs.MODID, "serpentfriend")
    );
    
    public static final ResourceKey<Enchantment> BOARD_RETURN = ResourceKey.create(
        Registries.ENCHANTMENT, 
        new ResourceLocation(AlexsMobs.MODID, "board_return")
    );
}
