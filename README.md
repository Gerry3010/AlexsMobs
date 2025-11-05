# AlexsMobs

> **ℹ️ AI-Assisted Port Notice**  
> This repository is currently being ported to NeoForge 1.21 with AI assistance.  
> Please be aware that portions of the code will be AI-generated during this migration process.  
> **Note:** Even if the port is successful, there is no guarantee that this project will be actively maintained.

Minecraft mod that adds 80+ new creatures to the game.

---

## 🚧 1.21 Port Progress

**Target:** Minecraft 1.21 with NeoForge 21.0.167  
**Current Status:** ⚠️ Work in Progress - ~66 non-JEI errors remaining (~93% complete)

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
- [x] Event system: `TickEvent` → `LevelTickEvent`
- [x] Event system: `RenderGuiOverlayEvent` → `RenderGuiEvent`
- [x] Event imports: `eventbus.api` → `bus.api`
- [x] Block API: `SandBlock` → `FallingBlock`, `AbstractGlassBlock` → `TransparentBlock`
- [x] Block properties: `.copy()` → `.ofFullCopy()`
- [x] Interface: `IForgeShearable` → `IShearable`
- [x] Hooks: `ForgeEventFactory` → `EventHooks`, `ForgeHooksClient` → `ClientHooks`
- [x] Spawn eggs: `ForgeSpawnEggItem` → `DeferredSpawnEggItem`
- [x] Path types: `BlockPathTypes` → `PathType`
- [x] Mob categories: `MobType` → `MobCategory`
- [x] Tool actions: `ToolActions` → `ItemAbilities`
- [x] Config system: `ForgeConfigSpec` → `ModConfigSpec`
- [x] Capabilities: `ForgeCapabilities` → `Capabilities`, updated to new API
- [x] Networking: Removed `PlayMessages.SpawnEntity` constructors (19 entities)
- [x] GUI: Removed deprecated `VanillaGuiOverlay` references
- [x] Multipart entities: `net.minecraftforge.entity.PartEntity` → `net.neoforged.neoforge.entity.PartEntity`

### 🔧 In Progress

#### Compilation Errors (~66 remaining)

**Category Breakdown:**
- 🟢 ~40 JEI integration errors (optional compatibility, can be disabled)
- 🟡 ~18 Enchantment system errors (major API change - Enchantment class is now final)
- 🟠 ~8 Miscellaneous entity/client errors

**Priority 1: Enchantment System (18 errors)**
- [ ] Redesign enchantment system (Enchantment class is final in 1.21)
  - Affected: `StraddleEnchantment`, `StraddleJumpEnchantment`
  - Requires complete rewrite using new data-driven enchantment system
  - `AMEnchantmentRegistry` type parameters fixed, but implementation needs update

**Priority 2: Remaining Core Issues (~8 errors)**
- [x] ~~Fix `PlayMessages` references~~ ✅ Removed all deprecated constructors
- [x] ~~Fix multipart entity package~~ ✅ Updated to `net.neoforged.neoforge.entity.PartEntity`
- [x] ~~Update capabilities system~~ ✅ Migrated to new Capabilities API
- [ ] Fix remaining entity-specific symbol errors
  - ClientEvents line 425
  - EntityElephant, EntityFroststalker, EntityKangaroo, EntityMimicube, EntityRhinoceros, EntityStradpole

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

- [ ] ~66 compilation errors remaining (40 JEI, 18 enchantments, 8 misc)
- [ ] Enchantment system needs complete redesign for 1.21
- [ ] Not yet tested in-game
- [ ] Citadel dependency requires manual build
- [ ] JEI integration needs update or temporary removal

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
