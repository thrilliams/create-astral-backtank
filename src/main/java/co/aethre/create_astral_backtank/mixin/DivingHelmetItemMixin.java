package co.aethre.create_astral_backtank.mixin;

import co.aethre.create_astral_backtank.CreateAstralBacktank;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;

@Mixin(DivingHelmetItem.class)
public class DivingHelmetItemMixin {
	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"
			),
			method = "breatheUnderwater(Lnet/minecraft/world/entity/LivingEntity;)V"
	)
	private static boolean redirectBreatheUnderwater(LivingEntity entity, TagKey<Fluid> fluidTagKey) {
		// use backtank air if the player is underwater or in a dimension without oxygen
		return CreateAstralBacktank.shouldUseOxygen(entity, fluidTagKey);
	}
}
