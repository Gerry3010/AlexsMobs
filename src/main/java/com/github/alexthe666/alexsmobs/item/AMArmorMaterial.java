package com.github.alexthe666.alexsmobs.item;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import com.github.alexthe666.alexsmobs.AlexsMobs;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

// In 1.21, ArmorMaterial is a record, not an interface
// This class now wraps the creation and access of ArmorMaterial holders
public class AMArmorMaterial {

    protected static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final Holder<ArmorMaterial> holder;
    private Supplier<Ingredient> repairIngredient = () -> Ingredient.EMPTY;

    public AMArmorMaterial(String name, int durability, int[] damageReduction, int encantability, SoundEvent sound, float toughness) {
        this(name, durability, damageReduction, encantability, sound, toughness, 0.0F);
    }

    public AMArmorMaterial(String name, int durability, int[] damageReduction, int encantability, SoundEvent sound, float toughness, float knockbackResist) {
        this.name = name;
        
        // Create defense map
        EnumMap<ArmorItem.Type, Integer> defenseMap = new EnumMap<>(ArmorItem.Type.class);
        defenseMap.put(ArmorItem.Type.BOOTS, damageReduction[3]);
        defenseMap.put(ArmorItem.Type.LEGGINGS, damageReduction[2]);
        defenseMap.put(ArmorItem.Type.CHESTPLATE, damageReduction[1]);
        defenseMap.put(ArmorItem.Type.HELMET, damageReduction[0]);
        defenseMap.put(ArmorItem.Type.BODY, damageReduction[1]); // Use chestplate value for body
        
        // Register the armor material
        ResourceLocation id = new ResourceLocation(AlexsMobs.MODID, name);
        ArmorMaterial material = new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, MAX_DAMAGE_ARRAY[3] * durability);
                map.put(ArmorItem.Type.LEGGINGS, MAX_DAMAGE_ARRAY[2] * durability);
                map.put(ArmorItem.Type.CHESTPLATE, MAX_DAMAGE_ARRAY[1] * durability);
                map.put(ArmorItem.Type.HELMET, MAX_DAMAGE_ARRAY[0] * durability);
                map.put(ArmorItem.Type.BODY, MAX_DAMAGE_ARRAY[1] * durability);
            }),
            encantability,
            Holder.direct(sound),
            () -> this.repairIngredient.get(),
            List.of(new ArmorMaterial.Layer(id)),
            toughness,
            knockbackResist
        );
        
        this.holder = Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, id, material);
    }


    public Holder<ArmorMaterial> getHolder() {
        return holder;
    }
    
    public String getName() {
        return name;
    }

    public void setRepairMaterial(Ingredient ingredient) {
        this.repairIngredient = () -> ingredient;
    }

}
