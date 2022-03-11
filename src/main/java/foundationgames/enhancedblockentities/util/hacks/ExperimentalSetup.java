package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.DyeColor;

import java.io.IOException;

public enum ExperimentalSetup {;
    private static ResourceManager RESOURCE_MANAGER;

    public static void setup() {
        EBEConfig config = EnhancedBlockEntities.CONFIG;

        if (config.renderEnhancedChests && config.experimentalChests) {
            try {
                if (RESOURCE_MANAGER != null) setupChests(RESOURCE_MANAGER);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental chests!", e);
                config.experimentalChests = false;
                config.save();
            }
        }
        if (config.renderEnhancedBeds && config.experimentalBeds) {
            try {
                if (RESOURCE_MANAGER != null) setupBeds(RESOURCE_MANAGER);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental beds!", e);
                config.experimentalBeds = false;
                config.save();
            }
        }
        if (config.renderEnhancedSigns && config.experimentalSigns) {
            try {
                if (RESOURCE_MANAGER != null) setupSigns(RESOURCE_MANAGER);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental signs!", e);
                config.experimentalSigns = false;
                config.save();
            }
        }
    }

    public static void setupChests(ResourceManager resourceManager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        ResourceHacks.addChestParticleTexture("chest", "entity/chest/normal", resourceManager, p);
        ResourceHacks.addChestParticleTexture("trapped_chest", "entity/chest/trapped", resourceManager, p);
        ResourceHacks.addChestParticleTexture("ender_chest", "entity/chest/ender", resourceManager, p);
        ResourceHacks.addChestParticleTexture("christmas_chest", "entity/chest/christmas", resourceManager, p);
    }

    public static void setupBeds(ResourceManager resourceManager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        for (var color : DyeColor.values()) {
            ResourceHacks.addBedParticleTexture(color.getName(), "entity/bed/"+color.getName(), resourceManager, p);
        }
    }

    public static void setupSigns(ResourceManager resourceManager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        ResourceHacks.addSignParticleTexture("oak", "entity/signs/oak", resourceManager, p);
        ResourceHacks.addSignParticleTexture("birch", "entity/signs/birch", resourceManager, p);
        ResourceHacks.addSignParticleTexture("spruce", "entity/signs/spruce", resourceManager, p);
        ResourceHacks.addSignParticleTexture("jungle", "entity/signs/jungle", resourceManager, p);
        ResourceHacks.addSignParticleTexture("acacia", "entity/signs/acacia", resourceManager, p);
        ResourceHacks.addSignParticleTexture("dark_oak", "entity/signs/dark_oak", resourceManager, p);
        ResourceHacks.addSignParticleTexture("crimson", "entity/signs/crimson", resourceManager, p);
        ResourceHacks.addSignParticleTexture("warped", "entity/signs/warped", resourceManager, p);
    }

    public static void cacheResourceManager(ResourceManager resourceManager) {
        RESOURCE_MANAGER = resourceManager;
    }
}
