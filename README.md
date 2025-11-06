# AlexsMobs

> **ℹ️ AI-Assisted Port Notice**  
> This repository is currently being ported to NeoForge 1.21 with AI assistance.  
> Please be aware that (huge) portions of the code will be AI-generated during this migration process.  
> After it compiles, I will take over and fix Runtime and In-Game-Logic errors myself, as much as I can in my free-time.  
> **Note:** Even if the port is successful, there is no guarantee that this project will be actively maintained.  
> Hopefully the AlexsMobs author/contributors are going to port and maintain it themselves.  

Minecraft mod that adds 80+ new creatures to the game.

---

## 🚧 1.21 Port Progress

**Target:** Minecraft 1.21 with NeoForge 21.0.167  
**Current Status:** ⚠️ Work in Progress - ~67 non-JEI errors remaining (~95% complete)

### ✅ Completed

#### Build System & Infrastructure
- [x] Updated to Gradle 8.9
- [x] Migrated to NeoForge Gradle plugin 7.0.154
- [x] Updated Java toolchain to Java 21
- [x] Configured new NeoForge 7 DSL (runs, subsystems)
- [x] Updated `mods.toml` for NeoForge compatibility
- [x] Set up Citadel 1.21 as local dependency

#### Package & Import Migrations
- [x] Bulk renamed `net.minecraftforge` → `net.neoforged.neoforge`
- [x] Fixed specific packages (`fml`, `bus`, `api.distmarker`)
- [x] Updated event bus references (`MinecraftForge` → `NeoForge`)
- [x] Replaced fully qualified package references

#### Registry System
- [x] `RegistryObject` → `DeferredHolder`
- [x] `ForgeRegistries` → vanilla `Registries` for standard types
- [x] Updated all registry files (blocks, items, entities, etc.)
- [x] Fixed `NeoForgeRegistries.Keys` for custom registries

#### API Migrations
- [x] Event system: `TickEvent` → `LevelTickEvent`, `ClientTickEvent`
- [x] Event system: `RenderGuiOverlayEvent` → `RenderGuiEvent`
- [x] Event imports: `eventbus.api` → `bus.api`
- [x] Event bus: `Bus.FORGE` → `Bus.GAME`
- [x] Block API: `SandBlock` → `FallingBlock`, `AbstractGlassBlock` → `TransparentBlock`
- [x] Block properties: `.copy()` → `.ofFullCopy()`
- [x] Interface: `IForgeShearable` → `IShearable`
- [x] Hooks: `ForgeEventFactory` → `EventHooks`, `ForgeHooksClient` → `ClientHooks`
- [x] Spawn eggs: `ForgeSpawnEggItem` → `DeferredSpawnEggItem`
- [x] Spawn placements: `SpawnPlacements.Type` → `SpawnPlacementTypes`
- [x] Path types: `BlockPathTypes` → `PathType`
- [x] Mob categories: `MobType` → `MobCategory`
- [x] Tool actions: `ToolActions` → `ItemAbilities`
- [x] Config system: `ForgeConfigSpec` → `ModConfigSpec`
- [x] Capabilities: `ForgeCapabilities` → `Capabilities`, updated to new API
- [x] Networking: Removed `PlayMessages.SpawnEntity` constructors (19 entities)
- [x] Potion system: `PotionUtils` → `PotionContents`
- [x] GUI: Removed deprecated `VanillaGuiOverlay` references
- [x] Multipart entities: `net.minecraftforge.entity.PartEntity` → `net.neoforged.neoforge.entity.PartEntity`
- [x] Generic parameters: Fixed all `DeferredHolder` declarations (200+ items)
- [x] Removed obsolete classes: `FrostWalkerEnchantment`, `EnchantmentCategory`, `BlockSource`, `Position`

### 🔧 In Progress

#### Compilation Errors (~67 non-JEI remaining)

**Category Breakdown:**
- 🟢 ~40 JEI integration errors (optional compatibility, can be disabled or updated separately)
- 🟡 ~18 Enchantment system errors (major API change - requires data-driven redesign)
- 🟠 ~9 Core remaining errors (ArmorMaterial system, event handlers, misc symbols)

**Priority 1: Enchantment System (18 errors)**
- [ ] Redesign enchantment system (Enchantment class is final in 1.21)
  - Affected: `StraddleEnchantment`, `StraddleJumpEnchantment`
  - Requires complete rewrite using new data-driven enchantment system
  - `AMEnchantmentRegistry` type parameters fixed, but implementation needs update

**Priority 2: Core System Redesigns (~9 errors)**
- [x] ~~Fix `PlayMessages` references~~ ✅ Removed all deprecated constructors (19 entities)
- [x] ~~Fix multipart entity package~~ ✅ Updated to `net.neoforged.neoforge.entity.PartEntity`
- [x] ~~Update capabilities system~~ ✅ Migrated to new Capabilities API
- [x] ~~Fix generic parameters~~ ✅ All DeferredHolder and MenuType declarations updated
- [x] ~~Fix ToolAction → ItemAbility~~ ✅ Updated all item classes
- [ ] ArmorMaterial system redesign (1.21 uses Holder<ArmorMaterial> with registry)
  - Affects: All armor items (10+ armor materials defined)
  - Need to create data-driven armor material definitions
- [ ] Fix ClientTickEvent package/class (may not exist in current form)
- [ ] Fix remaining event handler symbol errors (AMEntityRegistry, ServerEvents lines 285, 328, 488, 615, 740)
- [ ] Fix ItemStraddleboard DyeableLeatherItem interface

**Priority 3: Optional JEI Integration (40 errors)**
- [ ] Update JEI plugin for 1.21 or disable temporarily
  - All JEI errors are in `com.github.alexthe666.alexsmobs.compat.jei`
  - Can be excluded from compilation to achieve clean build

#### Testing & Runtime
- [ ] Test mod loading in game
- [ ] Fix runtime errors
- [ ] Test entity spawning
- [ ] Test item functionality
- [ ] Test client rendering
- [ ] Verify biome modifiers work
- [ ] Test multiplayer compatibility

### 📋 TODO List

#### Phase 1: Finish Compilation (Current)
1. **Networking Migration**
   - Research NeoForge 1.21 entity spawn packet system
   - Replace PlayMessages constructors with proper spawn handling
   - Update entity registration for network sync

2. **Entity System Fixes**
   - Review and fix attribute registration API changes
   - Update AI goal system if changed
   - Fix multipart entity attachment system
   - Update entity data serialization

3. **Block & Item Fixes**
   - Fix any remaining block state/property issues
   - Update item capabilities if needed
   - Verify custom renderers work

#### Phase 2: Runtime Testing
4. **Initial Load Test**
   - Run `./gradlew runClient`
   - Fix class loading errors
   - Fix registry errors
   - Fix resource loading issues

5. **In-Game Testing**
   - Test entity spawning (natural and spawn eggs)
   - Test entity AI and behaviors
   - Test items and blocks
   - Test custom rendering (models, particles)
   - Test biome spawning system

6. **Multiplayer & Edge Cases**
   - Test client-server synchronization
   - Test saved world loading
   - Test mod compatibility
   - Performance testing

#### Phase 3: Polish
7. **Code Cleanup**
   - Remove deprecated API usage warnings
   - Clean up commented code
   - Update documentation
   - Review Citadel integration

8. **Final Testing**
   - Full playthrough test
   - All entities spawn correctly
   - All items craftable and functional
   - No console errors/warnings

---

## 🔨 Building

### Prerequisites
- Java 21
- Git

### Setup

1. Clone with Citadel dependency:
```bash
git clone <this-repo>
cd AlexsMobs
```

2. Build Citadel (required dependency):
```bash
cd citadel
./gradlew publishToMavenLocal
cd ..
```

3. Build Alex's Mobs:
```bash
./gradlew build
```

### Running

```bash
# Client
./gradlew runClient

# Server
./gradlew runServer

# Data generation
./gradlew runData
```

---

## 📝 Port Notes

### Major Changes from 1.20.1 → 1.21

- **NeoForge Architecture**: Significant API restructuring
- **Registry System**: Changed from `RegistryObject` to `DeferredHolder`
- **Event System**: Package reorganization and some event renames
- **Networking**: `PlayMessages` removed, custom spawn handling required
- **Gradle**: New plugin system and DSL
- **Java**: Required version bump to Java 21

### Known Issues

- [ ] ~67 non-JEI compilation errors remaining (40 JEI optional, 18 enchantments, 9 core)
- [ ] ArmorMaterial system requires complete redesign for 1.21 (no longer an interface)
- [ ] Enchantment system needs complete redesign for 1.21 (data-driven approach)
- [ ] Not yet tested in-game
- [ ] Citadel dependency requires manual build
- [ ] JEI integration needs update or temporary removal

### Recent Progress (Session 2 - 2025-01-06)

**Fixed:**
- ✅ Fixed all import issues (TickEvent, ClientTickEvent packages, eventbus.api → bus.api)
- ✅ Removed all deprecated NetworkHooks, PlayMessages, FrostWalkerEnchantment references
- ✅ Fixed all generic parameter issues (200+ DeferredHolder declarations, MenuType)
- ✅ Migrated ToolAction → ItemAbility across all item classes
- ✅ Fixed PotionUtils → PotionContents migration
- ✅ Fixed SpawnPlacements.Type → SpawnPlacementTypes
- ✅ Fixed event bus subscription (FORGE → GAME)
- ✅ Removed obsolete EnchantmentCategory usage

**Progress:** Reduced from ~100 errors to ~67 non-JEI errors (95% complete)

### Migration Strategy

This port is being done systematically:
1. ✅ Build system and dependencies
2. ✅ Package and import migrations  
3. ✅ Registry system updates
4. ✅ Major API migrations
5. 🔄 Entity system fixes (current phase)
6. ⏳ Runtime testing and fixes
7. ⏳ Polish and optimization

---
