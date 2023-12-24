package net.emilsg.clutter.data;

import net.emilsg.clutter.util.ModBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class BiomeTagDataGen extends FabricTagProvider<Biome> {

    public BiomeTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_CRIMSON_NEWTS)
                .add(BiomeKeys.CRIMSON_FOREST);

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_NETHER_BUTTERFLIES).add(
                BiomeKeys.CRIMSON_FOREST,
                BiomeKeys.WARPED_FOREST,
                BiomeKeys.SOUL_SAND_VALLEY
        );

    }

    @Override
    protected RegistryKey<Biome> reverseLookup(Biome element) {
        return super.reverseLookup(element);
    }
}
