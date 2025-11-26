package com.github.alexthe666.alexsmobs.client.render.layer;

import com.github.alexthe666.alexsmobs.client.model.ModelMimicube;
import com.github.alexthe666.alexsmobs.client.render.RenderMimicube;
import com.github.alexthe666.alexsmobs.entity.EntityMimicube;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class LayerMimicubeHelmet extends RenderLayer<EntityMimicube, ModelMimicube> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private final HumanoidModel defaultBipedModel;
    private final RenderMimicube renderer;

    public LayerMimicubeHelmet(RenderMimicube render, EntityRendererProvider.Context renderManagerIn) {
        super(render);
        this.renderer = render;
        defaultBipedModel = new HumanoidModel(renderManagerIn.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
    }

    public static ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @javax.annotation.Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        // In 1.21, ArmorMaterial is a Holder - get the key location
        net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial> materialHolder = item.getMaterial();
        ResourceLocation materialId = materialHolder.unwrapKey().orElseThrow().location();
        
        // Construct texture path: namespace:textures/models/armor/name_layer_1.png
        String suffix = type == null ? "" : ("_" + type);
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
            materialId.getNamespace(),
            "textures/models/armor/" + materialId.getPath() + "_layer_1" + suffix + ".png"
        );
        
        return texture;
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityMimicube cube, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.pushPose();
        ItemStack itemstack = cube.getItemBySlot(EquipmentSlot.HEAD);
        float helmetSwap = Mth.lerp(partialTicks, cube.prevHelmetSwapProgress, cube.helmetSwapProgress) * 0.2F;
        if (itemstack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem) itemstack.getItem();
            if (armoritem.getEquipmentSlot() == EquipmentSlot.HEAD) {
                HumanoidModel a = defaultBipedModel;
                a = getArmorModelHook(cube, itemstack, EquipmentSlot.HEAD, a);
                boolean notAVanillaModel = a != defaultBipedModel;

                this.setModelSlotVisible(a, EquipmentSlot.HEAD);
                boolean flag = false;
                this.renderer.getModel().root.translateAndRotate(matrixStackIn);
                this.renderer.getModel().innerbody.translateAndRotate(matrixStackIn);
                matrixStackIn.translate(0,  notAVanillaModel ? 0.25F : -0.75F, 0F);
                matrixStackIn.scale(1F + 0.3F * (1 - helmetSwap), 1F + 0.3F * (1 - helmetSwap), 1F + 0.3F * (1 - helmetSwap));
                boolean flag1 = itemstack.hasFoil();
                int clampedLight = helmetSwap > 0 ? (int) (-100 * helmetSwap) : packedLightIn;
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(360 * helmetSwap));
                if (itemstack.has(net.minecraft.core.component.DataComponents.DYED_COLOR)) { // Check for dyed color component
                    int i = itemstack.get(net.minecraft.core.component.DataComponents.DYED_COLOR).rgb();
                    float f = (float) (i >> 16 & 255) / 255.0F;
                    float f1 = (float) (i >> 8 & 255) / 255.0F;
                    float f2 = (float) (i & 255) / 255.0F;
                    renderArmor(cube, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(cube, itemstack, EquipmentSlot.HEAD, null), notAVanillaModel);
                    renderArmor(cube, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(cube, itemstack, EquipmentSlot.HEAD, "overlay"), notAVanillaModel);
                } else {
                    renderArmor(cube, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(cube, itemstack, EquipmentSlot.HEAD, null), notAVanillaModel);
                }

            }
        }
        matrixStackIn.popPose();
    }

    private void renderArmor(EntityMimicube entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        if(notAVanillaModel){
            renderer.getModel().copyPropertiesTo(modelIn);
            modelIn.body.y = 0;
            modelIn.head.setPos(0.0F, 1.0F, 0.0F);
            modelIn.hat.y = 0;
            modelIn.head.xRot = renderer.getModel().body.xRot;
            modelIn.head.yRot = renderer.getModel().body.yRot;
            modelIn.head.zRot = renderer.getModel().body.zRot;
            modelIn.head.x = renderer.getModel().body.x;
            modelIn.head.y = renderer.getModel().body.y;
            modelIn.head.z = renderer.getModel().body.z;
            modelIn.hat.copyFrom(modelIn.head);
            modelIn.body.copyFrom(modelIn.head);
        }
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    protected void setModelSlotVisible(HumanoidModel p_188359_1_, EquipmentSlot slotIn) {
        this.setModelVisible(p_188359_1_);
        switch (slotIn) {
            case HEAD:
                p_188359_1_.head.visible = true;
                p_188359_1_.hat.visible = true;
                break;
            case CHEST:
                p_188359_1_.body.visible = true;
                p_188359_1_.rightArm.visible = true;
                p_188359_1_.leftArm.visible = true;
                break;
            case LEGS:
                p_188359_1_.body.visible = true;
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
                break;
            case FEET:
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
        }
    }

    protected void setModelVisible(HumanoidModel model) {
        model.setAllVisible(false);

    }


    protected HumanoidModel<?> getArmorModelHook(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
        try{
            Model basicModel = net.neoforged.neoforge.client.ClientHooks.getArmorModel(entity, itemStack, slot, model);
            return basicModel instanceof HumanoidModel ? (HumanoidModel<?>) basicModel : model;
        }catch (Exception e){
            return model;
        }
    }
}
