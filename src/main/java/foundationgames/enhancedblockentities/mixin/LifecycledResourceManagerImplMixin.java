package foundationgames.enhancedblockentities.mixin;

import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(LifecycledResourceManagerImpl.class)
public interface LifecycledResourceManagerImplMixin {
    @Accessor
    @Final
    List<ResourcePack> getPacks();

    @Accessor
    Map<String, NamespaceResourceManager> getSubManagers();
}
