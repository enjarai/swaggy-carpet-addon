package nl.enjarai.swaggycarpet.mixin;

import carpet.CarpetServer;
import nl.enjarai.swaggycarpet.SwaggyAddon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarpetServer.class)
public abstract class CarpetServerMixin {
    @Inject(
            method = "onGameStarted",
            at = @At(value = "HEAD"),
            remap = false
    )
    private static void registerCarpetExtension(CallbackInfo ci)
    {
        SwaggyAddon.noop();
    }
}
