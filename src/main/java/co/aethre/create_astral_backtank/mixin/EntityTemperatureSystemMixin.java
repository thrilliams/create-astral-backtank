package co.aethre.create_astral_backtank.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import co.aethre.create_astral_backtank.CreateAstralBacktank;
import co.aethre.create_astral_backtank.FreezeResistance;

import earth.terrarium.ad_astra.common.entity.system.EntityTemperatureSystem;
import earth.terrarium.ad_astra.common.util.ModUtils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;

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
		boolean heatResistant = ModUtils.armourIsHeatResistant(entity);
		heatResistant |= hasFullNetheriteSuit(entity, false);
		return heatResistant;
	}

	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Learth/terrarium/ad_astra/common/util/ModUtils;armourIsFreezeResistant(Lnet/minecraft/world/entity/LivingEntity;)Z"
			),
			method = "temperatureTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V"
	)
	private static boolean armourIsFreezeResistantMixin(LivingEntity entity) {
		boolean freezeResistant = ModUtils.armourIsFreezeResistant(entity);
		FreezeResistance freezeResistanceOption = CreateAstralBacktank.CONFIG.freezeResistance();

		if (freezeResistanceOption.equals(FreezeResistance.COPPER)) {
			freezeResistant |= CreateAstralBacktank.hasWorkingDivingSuit(entity);
			freezeResistant |= hasFullNetheriteSuit(entity, false);
		} else if (freezeResistanceOption.equals(FreezeResistance.COPPER_FULL_SET)) {
			freezeResistant |= hasFullNetheriteSuit(entity, true);
		} else if (freezeResistanceOption.equals(FreezeResistance.NETHERITE)) {
			freezeResistant |= hasFullNetheriteSuit(entity, false);
		}

		return freezeResistant;
	}

	private static boolean hasFullNetheriteSuit(LivingEntity entity, boolean orCopper) {
		// a full copper suit must include a functional diving of any kind
		if (!CreateAstralBacktank.hasWorkingDivingSuit(entity)) return false;

		if (!slotIsNetherite(entity, EquipmentSlot.HEAD, orCopper)) return false;
		if (!slotIsNetherite(entity, EquipmentSlot.CHEST, orCopper)) return false;
		if (!slotIsNetherite(entity, EquipmentSlot.LEGS, orCopper)) return false;
		if (!slotIsNetherite(entity, EquipmentSlot.FEET, orCopper)) return false;

		return true;
	}

	private static boolean slotIsNetherite(LivingEntity entity, EquipmentSlot slot, boolean orCopper) {
		ArmorMaterial slotMaterial = getMaterialBySlot(entity, slot);
		CreateAstralBacktank.LOGGER.info(slotMaterial.getName());
		boolean valid = materialIsNetherite(slotMaterial);
		if (orCopper) valid |= materialIsCopper(slotMaterial);
		return valid;
	}

	private static ArmorMaterial getMaterialBySlot(LivingEntity entity, EquipmentSlot slot) {
		return ((ArmorItem) entity.getItemBySlot(slot).getItem()).getMaterial();
	}

	private static boolean materialIsCopper(ArmorMaterial material) {
		// if (material == null) return false;
		return CreateAstralBacktank.CONFIG.copperMaterials().contains(material.getName());
	}

	private static boolean materialIsNetherite(ArmorMaterial material) {
		// if (material == null) return false;
		return material.equals(ArmorMaterials.NETHERITE);
	}
}
