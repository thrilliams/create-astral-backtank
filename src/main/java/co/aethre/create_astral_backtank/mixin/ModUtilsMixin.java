package co.aethre.create_astral_backtank.mixin;

import co.aethre.create_astral_backtank.CreateAstralBacktank;

import com.simibubi.create.content.equipment.armor.DivingBootsItem;

import earth.terrarium.ad_astra.common.util.ModUtils;

import net.minecraft.world.entity.Entity;

import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModUtils.class)
public class ModUtilsMixin {
	@Inject(
			at = @At("HEAD"),
			method = "getEntityGravity(Lnet/minecraft/world/entity/Entity;)F",
			cancellable = true
	)
	private static void getEntityGravity(Entity entity, CallbackInfoReturnable<Float> ci) {
		boolean wearingBoots = DivingBootsItem.isWornBy(entity);
		Level level = entity.level();
		float levelGravity = ModUtils.getPlanetGravity(level);

		if (!wearingBoots || !ModUtils.isSpacelevel(level)) {
            ci.setReturnValue(levelGravity);
			return;
        }

		if (CreateAstralBacktank.CONFIG.divingBootsNormalizeGravity()) {
			ci.setReturnValue(1.0F);
			return;
		}

		float modifiedGravity = levelGravity * CreateAstralBacktank.CONFIG.divingBootsGravityMultiplier();

		if (modifiedGravity > 1.0F && !CreateAstralBacktank.CONFIG.divingBootsGravityPast1G()) {
			ci.setReturnValue(1.0F);
			return;
		}

		ci.setReturnValue(modifiedGravity);
	}
}
