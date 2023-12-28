package co.aethre.create_astral_backtank.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.equipment.armor.NetheriteDivingHandler;

import co.aethre.create_astral_backtank.CreateAstralBacktank;

import earth.terrarium.ad_astra.common.entity.system.EntityTemperatureSystem;
import earth.terrarium.ad_astra.common.util.ModUtils;

import net.minecraft.world.entity.LivingEntity;

@Mixin(EntityTemperatureSystem.class)
public class EntityTemperatureSystemMixin {
	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Learth/terrarium/ad_astra/common/util/ModUtils;armourIsHeatResistant(Lnet/minecraft/world/entity/LivingEntity;)Z"
			),
			method = "temperatureTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V"
	)
	private static boolean armourIsHeatResistantMixin(LivingEntity entity) {
		boolean heatResistant = entity.getCustomData().getBoolean(NetheriteDivingHandler.FIRE_IMMUNE_KEY);
		return heatResistant || ModUtils.armourIsHeatResistant(entity);
	}

	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Learth/terrarium/ad_astra/common/util/ModUtils;armourIsFreezeResistant(Lnet/minecraft/world/entity/LivingEntity;)Z"
			),
			method = "temperatureTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V"
	)
	private static boolean armourIsFreezeResistantMixin(LivingEntity entity) {
		boolean freezeResistant = CreateAstralBacktank.hasWorkingDivingSuit(entity);
		return freezeResistant || ModUtils.armourIsFreezeResistant(entity);
	}
}
