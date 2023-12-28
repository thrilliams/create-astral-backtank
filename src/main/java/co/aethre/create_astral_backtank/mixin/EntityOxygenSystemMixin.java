package co.aethre.create_astral_backtank.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import co.aethre.create_astral_backtank.CreateAstralBacktank;

import earth.terrarium.ad_astra.common.entity.system.EntityOxygenSystem;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

@Mixin(EntityOxygenSystem.class)
public class EntityOxygenSystemMixin {
	@Inject(
			at = @At("HEAD"),
			method = "oxygenTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V",
			cancellable = true
	)
	private static void oxygenTickMixin(LivingEntity entity, ServerLevel level, CallbackInfo ci) {
		if (CreateAstralBacktank.hasWorkingDivingSuit(entity)) ci.cancel();
	}
}
