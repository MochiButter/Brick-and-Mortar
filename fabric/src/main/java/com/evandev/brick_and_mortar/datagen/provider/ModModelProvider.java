package com.evandev.brick_and_mortar.datagen.provider;

import com.evandev.brick_and_mortar.Constants;
import com.evandev.brick_and_mortar.block.KilnBlock;
import com.evandev.brick_and_mortar.registry.ModBlocks;
import com.evandev.brick_and_mortar.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        var multipart = MultiPartGenerator.multiPart(ModBlocks.KILN.get());
        java.util.Set<String> generatedModels = new java.util.HashSet<>();

        for (Direction dir : KilnBlock.FACING.getPossibleValues()) {
            VariantProperties.Rotation yRot = switch (dir) {
                case EAST -> VariantProperties.Rotation.R90;
                case SOUTH -> VariantProperties.Rotation.R180;
                case WEST -> VariantProperties.Rotation.R270;
                default -> VariantProperties.Rotation.R0;
            };

            for (boolean lit : new boolean[]{false, true}) {
                for (boolean soul : new boolean[]{false, true}) {
                    for (boolean frontOpen : new boolean[]{false, true}) {
                        for (boolean leftOpen : new boolean[]{false, true}) {
                            for (boolean backOpen : new boolean[]{false, true}) {
                                for (boolean rightOpen : new boolean[]{false, true}) {
                                    String frontTex = getTexture("kiln_front", frontOpen, lit, soul);
                                    String leftTex = getTexture("kiln_side", leftOpen, lit, soul);
                                    String backTex = getTexture("kiln_side", backOpen, lit, soul);
                                    String rightTex = getTexture("kiln_side", rightOpen, lit, soul);
                                    String topTex = getTexture("kiln_top", false, lit, soul);
                                    String modelName = "kiln";
                                    if (frontOpen) modelName += "_f";
                                    if (leftOpen) modelName += "_l";
                                    if (backOpen) modelName += "_b";
                                    if (rightOpen) modelName += "_r";
                                    if (lit) {
                                        modelName += "_on";
                                        if (soul) modelName += "_soul";
                                    }

                                    ResourceLocation modelResLoc = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + modelName);

                                    if (generatedModels.add(modelName)) {
                                        TextureMapping mapping = new TextureMapping()
                                                .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + frontTex))
                                                .put(TextureSlot.DOWN, ResourceLocation.withDefaultNamespace("block/bricks"))
                                                .put(TextureSlot.UP, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + topTex))
                                                .put(TextureSlot.NORTH, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + frontTex))
                                                .put(TextureSlot.SOUTH, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + backTex))
                                                .put(TextureSlot.EAST, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + leftTex))
                                                .put(TextureSlot.WEST, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + rightTex));
                                        ModelTemplates.CUBE.create(modelResLoc, mapping, gen.modelOutput);
                                    }

                                    multipart.with(
                                            Condition.condition()
                                                    .term(KilnBlock.FACING, dir)
                                                    .term(KilnBlock.LIT, lit)
                                                    .term(KilnBlock.SOUL, soul)
                                                    .term(KilnBlock.OPEN_FRONT, frontOpen)
                                                    .term(KilnBlock.OPEN_LEFT, leftOpen)
                                                    .term(KilnBlock.OPEN_BACK, backOpen)
                                                    .term(KilnBlock.OPEN_RIGHT, rightOpen),
                                            Variant.variant()
                                                    .with(VariantProperties.MODEL, modelResLoc)
                                                    .with(VariantProperties.Y_ROT, yRot)
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }

        gen.blockStateOutput.accept(multipart);
        gen.delegateItemModel(ModBlocks.KILN.get(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/kiln"));

        for (var family : ModBlocks.FAMILIES) {
            ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/bricks/" + family.base().getId().getPath());
            TextureMapping mapping = TextureMapping.cube(textureLoc);

            gen.createTrivialBlock(family.base().get(), mapping, ModelTemplates.CUBE_ALL);

            ResourceLocation stairsBase = ModelTemplates.STAIRS_STRAIGHT.create(family.stairs().get(), mapping, gen.modelOutput);
            ResourceLocation stairsInner = ModelTemplates.STAIRS_INNER.createWithSuffix(family.stairs().get(), "_inner", mapping, gen.modelOutput);
            ResourceLocation stairsOuter = ModelTemplates.STAIRS_OUTER.createWithSuffix(family.stairs().get(), "_outer", mapping, gen.modelOutput);
            gen.blockStateOutput.accept(BlockModelGenerators.createStairs(family.stairs().get(), stairsInner, stairsBase, stairsOuter));

            ResourceLocation slabBase = ModelTemplates.SLAB_BOTTOM.create(family.slab().get(), mapping, gen.modelOutput);
            ResourceLocation slabTop = ModelTemplates.SLAB_TOP.createWithSuffix(family.slab().get(), "_top", mapping, gen.modelOutput);
            gen.blockStateOutput.accept(BlockModelGenerators.createSlab(family.slab().get(), slabBase, slabTop, ModelLocationUtils.getModelLocation(family.base().get())));

            if (family.wall() != null) {
                ResourceLocation wallPost = ModelTemplates.WALL_POST.createWithSuffix(family.wall().get(), "_post", mapping, gen.modelOutput);
                ResourceLocation wallSide = ModelTemplates.WALL_LOW_SIDE.createWithSuffix(family.wall().get(), "_side", mapping, gen.modelOutput);
                ResourceLocation wallSideTall = ModelTemplates.WALL_TALL_SIDE.createWithSuffix(family.wall().get(), "_side_tall", mapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createWall(family.wall().get(), wallPost, wallSide, wallSideTall));
                gen.delegateItemModel(family.wall().get(), ModelTemplates.WALL_INVENTORY.createWithSuffix(family.wall().get(), "_inventory", mapping, gen.modelOutput));
            }

            if (family.pillar() != null) {
                ResourceLocation pillarSide = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/bricks/" + family.pillar().getId().getPath());
                ResourceLocation pillarTop = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/bricks/" + family.pillar().getId().getPath() + "_top");

                TextureMapping pillarMapping = TextureMapping.column(pillarSide, pillarTop);
                ResourceLocation pillarModel = ModelTemplates.CUBE_COLUMN.create(family.pillar().get(), pillarMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(family.pillar().get(), pillarModel));
            }
        }
    }

    private String getTexture(String base, boolean open, boolean lit, boolean soul) {
        String tex = base;
        if (open) tex += "_open";
        if (lit) {
            tex += "_on";
            if (soul) tex += "_soul";
        }
        return tex;
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (var itemObj : ModItems.ALL_BRICK_ITEMS) {
            itemModelGenerator.generateFlatItem(itemObj.get(), ModelTemplates.FLAT_ITEM);
        }

        for (var itemObj : ModItems.ALL_CHORUS_ITEMS) {
            itemModelGenerator.generateFlatItem(itemObj.get(), ModelTemplates.FLAT_ITEM);
        }
    }
}