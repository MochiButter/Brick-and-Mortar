package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.block.KilnBlock;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        var multipart = MultiPartGenerator.multiPart(ModBlocks.KILN.get());
        Set<String> generatedModels = new HashSet<>();

        for (Direction dir : KilnBlock.FACING.getPossibleValues()) {
            VariantProperties.Rotation yRot = switch (dir) {
                case EAST -> VariantProperties.Rotation.R90;
                case SOUTH -> VariantProperties.Rotation.R180;
                case WEST -> VariantProperties.Rotation.R270;
                default -> VariantProperties.Rotation.R0;
            };

            for (boolean lit : new boolean[]{false, true}) {
                for (boolean open : new boolean[]{false, true}) {
                    for (boolean soul : new boolean[]{false, true}) {

                        String frontTex = "kiln_front";
                        String sideTex = "kiln_side";
                        String topTex = "kiln_top";

                        if (open) {
                            frontTex += "_open";
                            sideTex += "_open";
                        }
                        if (lit) {
                            frontTex += "_on";
                            sideTex += "_on";
                            topTex += "_on";
                            if (soul) {
                                frontTex += "_soul";
                                sideTex += "_soul";
                                topTex += "_soul";
                            }
                        }

                        String modelName = "kiln";
                        if (open) modelName += "_open";
                        if (lit) {
                            modelName += "_on";
                            if (soul) modelName += "_soul";
                        }

                        ResourceLocation modelResLoc = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + modelName);
                        if (generatedModels.add(modelName)) {
                            createKilnModel(blockStateModelGenerator, modelName, frontTex, sideTex, topTex);
                        }

                        multipart.with(
                                Condition.condition()
                                        .term(KilnBlock.FACING, dir)
                                        .term(KilnBlock.LIT, lit)
                                        .term(KilnBlock.OPEN, open)
                                        .term(KilnBlock.SOUL, soul),
                                Variant.variant()
                                        .with(VariantProperties.MODEL, modelResLoc)
                                        .with(VariantProperties.Y_ROT, yRot)
                        );
                    }
                }
            }
        }

        blockStateModelGenerator.blockStateOutput.accept(multipart);
        blockStateModelGenerator.delegateItemModel(ModBlocks.KILN.get(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/kiln"));
    }

    /**
     * Helper method to map custom Kiln textures to Minecraft's standard Furnace-like model template.
     */
    private void createKilnModel(BlockModelGenerators gen, String modelName, String frontTex, String sideTex, String topTex) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.FRONT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + frontTex))
                .put(TextureSlot.SIDE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + sideTex))
                .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + topTex));

        ModelTemplates.CUBE_ORIENTABLE.create(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + modelName),
                mapping,
                gen.modelOutput
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
    }
}