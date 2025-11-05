# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

Alex's Mobs is a Minecraft mod that adds 80+ new creatures to the game. This is a NeoForge/Forge mod for Minecraft 1.20.1 written in Java, using the Gradle build system.

**Version:** 1.22.9  
**Minecraft Version:** 1.20.1  
**Forge Version:** 47.1.65  
**Required Dependencies:** Citadel (2.6.0+)

## Build Commands

### Building the mod
```bash
./gradlew build
```
The output JAR will be in `build/libs/`

### Running the game
```bash
# Client (for testing in-game)
./gradlew runClient

# Server
./gradlew runServer

# Data generation
./gradlew runData
```

### Cleaning build artifacts
```bash
./gradlew clean
```

## Code Architecture

### Package Structure
The main package is `com.github.alexthe666.alexsmobs` with the following key subpackages:

- **`entity/`** - All mob entities, spawn logic, AI behaviors
  - `AMEntityRegistry.java` - Central entity type registration using DeferredRegister
  - Individual entity classes (e.g., `EntityGrizzlyBear.java`, `EntityCrocodile.java`)
  - `ai/` subdirectory contains custom AI goals and pathfinding

- **`block/`** - Custom blocks and block entities
  - `AMBlockRegistry.java` - Block registration

- **`item/`** - Items, including spawn eggs, equipment, and consumables
  - `AMItemRegistry.java` - Item registration

- **`client/`** - Client-side only code (models, renderers, particles)
  - `model/` - Entity models
  - `render/` - Entity renderers
  - `particle/` - Particle effects
  - `gui/` - GUI screens

- **`config/`** - Configuration system
  - `AMConfig.java` - Config values (spawn weights, feature flags)
  - `BiomeConfig.java` - Biome-specific spawn configuration
  - `ConfigHolder.java` - Forge config spec holder

- **`world/`** - World generation features, biome modifiers
  - `AMFeatureRegistry.java` - Feature registration
  - `AMMobSpawnBiomeModifier.java` - Custom biome spawn modifier
  - `AMMobSpawnStructureModifier.java` - Structure-based spawn modifier

- **`message/`** - Network packets for client-server communication
  - Individual message classes for syncing entity states, mounting, etc.

- **`misc/`** - Registry classes for sounds, recipes, advancements, loot, etc.
  - `AMSoundRegistry.java`, `AMRecipeRegistry.java`, `AMLootRegistry.java`, etc.

- **`tileentity/`** - Block entities (e.g., hummingbird feeder, leafcutter ant chambers)
  - `AMTileEntityRegistry.java` - Tile entity registration

- **`effect/`** - Potion effects
  - `AMEffectRegistry.java` - Effect and potion registration

- **`enchantment/`** - Custom enchantments
  - `AMEnchantmentRegistry.java` - Enchantment registration

### Registration Pattern
This mod uses **Forge's DeferredRegister system**. All game content (entities, blocks, items, etc.) is registered through static `DeferredRegister` instances defined in registry classes:

- Each registry class has a `public static final DeferredRegister<T> DEF_REG` field
- Registrations are done with `DEF_REG.register(name, supplier)`
- These are registered to the mod event bus in the main mod class constructor

### Main Mod Class
`AlexsMobs.java` is the entry point:
- Registers all deferred registries to the mod event bus
- Sets up network channels for packet handling
- Handles config loading/baking
- Initializes client and server proxies via `DistExecutor`
- Contains special date-based features (April Fools, Halloween)

### Client/Server Separation
- Uses proxy pattern: `CommonProxy` and `ClientProxy` 
- `DistExecutor.runForDist()` ensures client-only code doesn't load on servers
- Client-side rendering code is isolated in the `client/` package

### Configuration
- Main config file: `alexsmobs.toml` (generated in config directory)
- Config is defined using Forge's config system
- Values are "baked" from ForgeConfigSpec into static fields in `AMConfig` for performance
- Each mob has configurable spawn weights and spawn rolls

### Entity Registration
Entities are registered in `AMEntityRegistry.java`:
- Uses `EntityType.Builder` with factory methods
- Defines hitbox sizes, tracking range, fire immunity, etc.
- Spawn placements registered in `initializeAttributes()` method
- Each entity has spawn rules (biome, height, light level requirements)

### Network Communication
- Uses `SimpleChannel` for custom packets
- Network wrapper: `AlexsMobs.NETWORK_WRAPPER`
- Packets registered in `setup()` method with sequential IDs
- Common packet types: mounting entities, syncing inventory, multipart entity interactions

### Mixins
This mod uses SpongePowered Mixins:
- Config: `citadel.mixins.json` 
- Mixin annotation processor is configured in dependencies
- Refmap: `citadel.refmap.json`

## Resource Files

### Assets (`src/main/resources/assets/alexsmobs/`)
- `blockstates/` - Block state JSON files
- `textures/` - Entity textures, item textures, block textures
- `models/` - Item and block models
- `sounds/` - Sound event definitions
- `lang/` - Translations

### Data (`src/main/resources/data/alexsmobs/`)
- `advancements/` - Achievement/advancement JSON files
- `loot_tables/` - Loot tables for entities and blocks
- `recipes/` - Crafting recipes
- `tags/` - Item, block, biome tags

### Access Transformer
- Location: `src/main/resources/META-INF/accesstransformer.cfg`
- Used to access normally private/protected Minecraft classes and fields

## Development Notes

### Adding a New Entity
1. Create entity class extending appropriate base class (e.g., `TamableAnimal`, `Animal`, `Monster`)
2. Register in `AMEntityRegistry.java` with appropriate attributes
3. Add spawn placement rules in `initializeAttributes()`
4. Create model class in `client/model/`
5. Create renderer class in `client/render/`
6. Register renderer in `ClientProxy.clientInit()`
7. Add spawn egg to `AMItemRegistry.java`
8. Configure spawn weights in `AMConfig.java` and biome spawns via data files
9. Add translations, textures, loot tables, and advancements

### Gradle Configuration
- **Memory:** JVM configured with `-Xmx3G` (see `gradle.properties`)
- **JEI (Just Enough Items):** Used for recipe/item viewing (compileOnly dependency)
- **Access Transformers:** Enabled in build.gradle
- **Source Sets:** Includes generated resources from data generators

### Special Features
- April Fools and Halloween mode (date-based feature flags in `AlexsMobs.java`)
- Super secret settings config option to enable holiday features year-round

## Important Files

- `build.gradle` - Build configuration, dependencies, run configurations
- `gradle.properties` - Memory settings, dependency versions
- `mods.toml` - Mod metadata (version, dependencies, authors)
- `accesstransformer.cfg` - Access transformations for Minecraft internals
- `citadel.mixins.json` - Mixin configuration
