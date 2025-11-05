# AlexsMobs

> **ℹ️ AI-Assisted Port Notice**  
> This repository is currently being ported to NeoForge 1.21 with AI assistance.  
> Please be aware that portions of the code will be AI-generated during this migration process.  
> **Note:** Even if the port is successful, there is no guarantee that this project will be actively maintained.

Minecraft mod that adds 80+ new creatures to the game.

---

## 🚧 1.21 Port Progress

**Target:** Minecraft 1.21 with NeoForge 21.0.167  
**Current Status:** ⚠️ Work in Progress - ~100 compilation errors remaining

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
- [x] Block API: `SandBlock` → `FallingBlock`
- [x] Block properties: `.copy()` → `.ofFullCopy()`
- [x] Interface: `IForgeShearable` → `IShearable`
- [x] Hooks: `ForgeEventFactory` → `EventHooks`
- [x] Hooks: `ForgeHooksClient` → `ClientHooks`
- [x] Spawn eggs: `ForgeSpawnEggItem` → `DeferredSpawnEggItem`

### 🔧 In Progress

#### Compilation Errors (~100 remaining)

**Priority 1: Networking System (4 errors)**
- [ ] Remove/replace `PlayMessages.SpawnEntity` constructors
  - Affected: `EntityCachalotEcho`, `EntityCockroachEgg`, `EntityEmuEgg`, `EntityStraddleboard`
  - NeoForge removed `PlayMessages`, needs custom entity spawning

**Priority 2: Entity API Changes (92 errors)**
- [ ] Fix "Symbol nicht gefunden" errors in entity classes
  - Most common in: `EntityLaviathan` (4), `EntityStraddleboard` (3), `EntityGust` (3)
  - Likely issues: attribute registration, AI goals, capabilities
- [ ] Update multipart entity system
  - Affected: `EntityBoneSerpent`, `EntityLaviathan`, `EntityCachalotWhale`
- [ ] Fix entity registration and spawn placement

**Priority 3: Remaining Package Updates (4 errors)**
- [ ] Fix `net.minecraftforge.entity` references
- [ ] Update capabilities system if used

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

- [ ] ~100 compilation errors remaining (mostly entity-related)
- [ ] Not yet tested in-game
- [ ] Citadel dependency requires manual build

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
