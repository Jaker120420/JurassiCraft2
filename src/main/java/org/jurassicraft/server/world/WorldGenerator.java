package org.jurassicraft.server.world;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.FossilizedTrackwayBlock;
import org.jurassicraft.server.block.NestFossilBlock;
import org.jurassicraft.server.block.tree.TreeType;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.base.EntityHandler;
import org.jurassicraft.server.period.TimePeriod;

import java.util.List;
import java.util.Random;

public enum WorldGenerator implements IWorldGenerator {
    INSTANCE;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            this.generateOverworld(world, random, chunkX * 16, chunkZ * 16);
        }
    }

    public void generateOverworld(World world, Random random, int chunkX, int chunkZ) {
        Biome biome = world.getBiomeGenForCoords(new BlockPos(chunkX, 0, chunkZ));

        for (int i = 0; i < world.provider.getHorizon() * 0.0125; i++) {
            int randPosX = chunkX + random.nextInt(16);
            int randPosZ = chunkZ + random.nextInt(16);
            int randPosY = random.nextInt(Math.max(0, world.getTopSolidOrLiquidBlock(new BlockPos(randPosX, 0, randPosZ)).getY() - 10));

            this.generatePetrifiedTree(world, TreeType.values()[random.nextInt(TreeType.values().length)], randPosX, randPosY, randPosZ, random);
        }

        for (int i = 0; i < 32; i++) {
            int randPosX = chunkX + random.nextInt(16);
            int randPosY = random.nextInt(64);
            int randPosZ = chunkZ + random.nextInt(16);

            TimePeriod period = null;

            for (TimePeriod p : TimePeriod.values()) {
                if (randPosY < TimePeriod.getEndYLevel(p) && randPosY > TimePeriod.getStartYLevel(p)) {
                    period = p;

                    break;
                }
            }

            if (period != null) {
                randPosY += random.nextInt(8) - 4;

                List<Dinosaur> dinos = EntityHandler.getDinosaursFromPeriod(period);

                if (dinos != null && dinos.size() > 0) {
                    Dinosaur dinosaur = dinos.get(random.nextInt(dinos.size()));

                    if (dinosaur.shouldRegister()) {
                        int meta = BlockHandler.getMetadata(dinosaur);

                        new WorldGenMinable(BlockHandler.getFossilBlock(dinosaur).getStateFromMeta(meta), 5).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
                    }
                }
            }
        }

        int nestChance = 100;

        if (biome instanceof BiomeHills || biome instanceof BiomeMesa || biome instanceof BiomeDesert) {
            nestChance = 30;
        }

        if (random.nextInt(nestChance) == 0) {
            BlockPos pos = new BlockPos(chunkX + random.nextInt(16), random.nextInt(20) + 30, chunkZ + random.nextInt(16));

            IBlockState nest = BlockHandler.NEST_FOSSIL.getDefaultState().withProperty(NestFossilBlock.VARIANT, NestFossilBlock.Variant.values()[random.nextInt(NestFossilBlock.Variant.values().length)]);
            IBlockState trackway = BlockHandler.FOSSILIZED_TRACKWAY.getDefaultState().withProperty(FossilizedTrackwayBlock.VARIANT, FossilizedTrackwayBlock.TrackwayType.values()[random.nextInt(FossilizedTrackwayBlock.TrackwayType.values().length)]).withProperty(FossilizedTrackwayBlock.FACING, EnumFacing.getHorizontal(random.nextInt(4)));

            int size = random.nextInt(3) + 6;

            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                    BlockPos generationPos = pos.add(x, 0, z);

                    if (!world.isAirBlock(generationPos)) {
                        IBlockState state = null;

                        if (random.nextFloat() < 0.8F) {
                            if (random.nextFloat() < 0.1F) {
                                state = trackway;
                            } else if (random.nextFloat() < 0.6F) {
                                state = Blocks.GRAVEL.getDefaultState();
                            } else {
                                state = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, random.nextBoolean() ? EnumDyeColor.WHITE : EnumDyeColor.SILVER);
                            }
                        }

                        if (state != null) {
                            world.setBlockState(generationPos, state);
                        }
                    }
                }
            }

            for (int i = 0; i < random.nextInt(2) + 1; i++) {
                BlockPos generationPos = pos.add(random.nextInt(size), 0, random.nextInt(size));

                if (!world.isAirBlock(generationPos)) {
                    world.setBlockState(generationPos, nest);
                }
            }
        }

        Predicate<IBlockState> defaultPredicate = BlockMatcher.forBlock(Blocks.STONE);

        this.generateOre(world, chunkX, chunkZ, 20, 8, 3, BlockHandler.AMBER_ORE.getDefaultState(), random, defaultPredicate);
//        generateOre(world, chunkX, chunkZ, 64, 8, 1, BlockHandler.ICE_SHARD.getDefaultState(), random, defaultPredicate);
        this.generateOre(world, chunkX, chunkZ, 128, 32, 10, BlockHandler.GYPSUM_STONE.getDefaultState(), random, defaultPredicate);
    }

    public void generateOre(World world, int chunkX, int chunkZ, int minHeight, int veinsPerChunk, int veinSize, IBlockState state, Random random, Predicate<IBlockState> predicate) {
        WorldGenMinable worldGenMinable = new WorldGenMinable(state, veinSize, predicate);

        for (int i = 0; i < veinsPerChunk; i++) {
            int randPosX = chunkX + random.nextInt(16);
            int randPosY = random.nextInt(minHeight);
            int randPosZ = chunkZ + random.nextInt(16);

            worldGenMinable.generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
        }
    }

    private void generatePetrifiedTree(World world, TreeType treeType, int x, int y, int z, Random rand) {
        float rotX = (float) (rand.nextDouble() * 360.0F);
        float rotY = (float) (rand.nextDouble() * 360.0F) - 180.0F;

        IBlockState state = BlockHandler.PETRIFIED_LOGS.get(treeType).getDefaultState();

        float horizontal = MathHelper.cos(rotX * (float) Math.PI / 180.0F);
        float vertical = MathHelper.sin(rotX * (float) Math.PI / 180.0F);

        float xOffset = -MathHelper.sin(rotY * (float) Math.PI / 180.0F) * horizontal;
        float yOffset = MathHelper.cos(rotY * (float) Math.PI / 180.0F) * horizontal;

        for (int i = 0; i < rand.nextInt(7) + 3; i++) {
            int blockX = x + Math.round(xOffset * i);
            int blockY = y + Math.round(vertical * i);
            int blockZ = z + Math.round(yOffset * i);

            if (blockY > 0 && blockY < 256) {
                BlockPos pos = new BlockPos(blockX, blockY, blockZ);
                Block previousBlock = world.getBlockState(pos).getBlock();

                if (previousBlock != Blocks.BEDROCK && previousBlock != Blocks.AIR) {
                    world.setBlockState(pos, state);
                }
            }
        }
    }
}
