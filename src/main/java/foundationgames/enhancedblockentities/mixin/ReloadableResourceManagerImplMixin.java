package foundationgames.enhancedblockentities.mixin;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.resource.*;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {
    @Shadow
    LifecycledResourceManager activeManager;

    @ModifyVariable(method = "reload", at = @At("HEAD"))
    private List<ResourcePack> enhanced_bes$injectResourcePacks(List<ResourcePack> packs) {
        ResourceUtil.resetExperimentalPack();

        ImmutableList.Builder<ResourcePack> builder = ImmutableList.builder();
        builder.addAll(packs);
        builder.add(ResourceUtil.getPack(), ResourceUtil.getExperimentalPack());

        return builder.build();
    }

    @Inject(method = "reload", at = @At("TAIL"))
    private void enhanced_bes$injectAndSetupExperimentalPack(Executor prepareExecutor,
                                                             Executor applyExecutor,
                                                             CompletableFuture<Unit> initialStage,
                                                             List<ResourcePack> packs,
                                                             CallbackInfoReturnable<ResourceReload> callbackInfoReturnable) {
        ExperimentalSetup.cacheResourceManager(activeManager);
        ExperimentalSetup.setup();

        refreshNamespaces(activeManager);
    }

    private void refreshNamespaces(LifecycledResourceManager lifecycledResourceManager) {
        var lifecycledResourceManagerMixin = (LifecycledResourceManagerImplMixin)lifecycledResourceManager;

        for (var pack : lifecycledResourceManagerMixin.getPacks()) {
            for (var str : pack.getNamespaces(ResourceType.CLIENT_RESOURCES)) {
                var namespace = lifecycledResourceManagerMixin.getSubManagers()
                        .computeIfAbsent(str, ns -> new NamespaceResourceManager(ResourceType.CLIENT_RESOURCES, ns));

                if (namespace.streamResourcePacks().noneMatch(p -> p == pack)) {
                    namespace.addPack(pack);
                }
            }
        }
    }
}
