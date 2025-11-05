package com.github.alexthe666.alexsmobs.enchantment;
import net.minecraft.core.registries.Registries;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

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

    public static final DeferredRegister<Enchantment> DEF_REG = DeferredRegister.create(Registries.ENCHANTMENT, AlexsMobs.MODID);

    // Note: These registrations are placeholders. Actual enchantment definitions must be in data files.
    // The following commented code shows the old 1.20.1 style registration:
    /*
    public static final DeferredHolder<Enchantment, Enchantment> STRADDLE_JUMP = DEF_REG.register("straddle_jump", 
        () -> new StraddleJumpEnchantment(Enchantment.Rarity.COMMON, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    public static final DeferredHolder<Enchantment, Enchantment> STRADDLE_LAVAWAX = DEF_REG.register("lavawax", 
        () -> new StraddleEnchantment(Enchantment.Rarity.UNCOMMON, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    public static final DeferredHolder<Enchantment, Enchantment> STRADDLE_SERPENTFRIEND = DEF_REG.register("serpentfriend", 
        () -> new StraddleEnchantment(Enchantment.Rarity.RARE, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    public static final DeferredHolder<Enchantment, Enchantment> STRADDLE_BOARDRETURN = DEF_REG.register("board_return", 
        () -> new StraddleEnchantment(Enchantment.Rarity.UNCOMMON, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    */
    
    // TODO: Uncomment and implement once data files are created
    // For now, enchantments are disabled to allow compilation
}
