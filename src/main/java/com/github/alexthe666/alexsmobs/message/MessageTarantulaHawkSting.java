package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.EntityTarantulaHawk;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageTarantulaHawkSting(int hawk, int spider) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageTarantulaHawkSting> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "tarantula_hawk_sting"));

    public static final StreamCodec<FriendlyByteBuf, MessageTarantulaHawkSting> STREAM_CODEC = StreamCodec.composite(
            StreamCodec.INT,
            MessageTarantulaHawkSting::hawk,
            StreamCodec.INT,
            MessageTarantulaHawkSting::spider,
            MessageTarantulaHawkSting::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MessageTarantulaHawkSting message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.hawk);
                Entity spider = player.level().getEntity(message.spider);
                if (entity instanceof EntityTarantulaHawk && spider instanceof LivingEntity && ((LivingEntity) spider).getMobType() == MobCategory.CREATURE) {
                    ((LivingEntity) spider).addEffect(new MobEffectInstance(AMEffectRegistry.DEBILITATING_STING.get(), EntityTarantulaHawk.STING_DURATION));
                }
            }
        });
    }
}
