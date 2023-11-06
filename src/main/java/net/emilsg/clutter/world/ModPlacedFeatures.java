package net.emilsg.clutter.world;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> SILVER_ORE_PLACED_KEY = registerKey("silver_ore_placed");
    public static final RegistryKey<PlacedFeature> BLACKSTONE_SULPHUR_ORE_PLACED_KEY = registerKey("blackstone_sulphur_ore_placed");
    public static final RegistryKey<PlacedFeature> BASALT_SULPHUR_ORE_PLACED_KEY = registerKey("basalt_sulphur_ore_placed");
    public static final RegistryKey<PlacedFeature> ONYX_GEODE_PLACED_KEY = registerKey("onyx_geode_placed");
    public static final RegistryKey<PlacedFeature> CATTAILS_PLACED_KEY = registerKey("cattails_placed");
    public static final RegistryKey<PlacedFeature> LUSH_MOSS_PLACED_KEY = registerKey("lush_moss_placed");
    public static final RegistryKey<PlacedFeature> GIANT_LILY_PAD_SEEDLING_PLACED_KEY = registerKey("giant_lily_pad_seedling_placed");
    public static final RegistryKey<PlacedFeature> SMALL_LILY_PADS_PLACED_KEY = registerKey("small_lily_pads_placed");
    public static final RegistryKey<PlacedFeature> SCULK_MUSHROOM_PLACED_KEY = registerKey("sculk_mushroom_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, SILVER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILVER_ORE_KEY), ModOrePlacement.modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(32))));
        register(context, BLACKSTONE_SULPHUR_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACKSTONE_SULPHUR_ORE_KEY), ModOrePlacement.modifiersWithCount(10, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
        register(context, BASALT_SULPHUR_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BASALT_SULPHUR_ORE_KEY), ModOrePlacement.modifiersWithCount(7, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
        register(context, ONYX_GEODE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ONYX_GEODE_KEY), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(10), YOffset.belowTop(20)), BiomePlacementModifier.of());

        register(context, SCULK_MUSHROOM_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SCULK_MUSHROOM_KEY), RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, CATTAILS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CATTAILS_KEY), RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(60), YOffset.fixed(62)), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, LUSH_MOSS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LUSH_MOSS_KEY), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, GIANT_LILY_PAD_SEEDLING_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.GIANT_LILY_PAD_SEEDLING_KEY), RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, SMALL_LILY_PADS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_LILY_PADS_KEY), RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }


    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Clutter.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}