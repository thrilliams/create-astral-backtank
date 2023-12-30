package co.aethre.create_astral_backtank.mixin;

import co.aethre.create_astral_backtank.ThermalResistance;
import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import co.aethre.create_astral_backtank.CreateAstralBacktank;

import earth.terrarium.ad_astra.common.entity.system.EntityTemperatureSystem;
import earth.terrarium.ad_astra.common.util.ModUtils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import javax.annotation.Nullable;

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
		ThermalResistance heatResistanceOption = CreateAstralBacktank.CONFIG.heatResistance();
		return evaluateThermalResistance(entity, heatResistanceOption) || ModUtils.armourIsHeatResistant(entity);
	}

	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Learth/terrarium/ad_astra/common/util/ModUtils;armourIsFreezeResistant(Lnet/minecraft/world/entity/LivingEntity;)Z"
			),
			method = "temperatureTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V"
	)
	private static boolean armourIsFreezeResistantMixin(LivingEntity entity) {
		ThermalResistance freezeResistanceOption = CreateAstralBacktank.CONFIG.freezeResistance();
		return evaluateThermalResistance(entity, freezeResistanceOption) || ModUtils.armourIsFreezeResistant(entity);
	}

	private static boolean evaluateThermalResistance(LivingEntity entity, ThermalResistance thermalResistance) {
		// probably a cleaner way to do this
		switch (thermalResistance) {
			case NONE:
			default:
				return false;
			case NETHERITE_FULL_SET:
				return hasFullNetheriteSuit(entity, false);
			case NETHERITE:
				boolean hasPartialNetheriteSuit = CreateAstralBacktank.hasWorkingDivingSuit(entity);
				hasPartialNetheriteSuit &= slotIsNetherite(entity, EquipmentSlot.HEAD, false);
				hasPartialNetheriteSuit &= slotIsNetherite(entity, EquipmentSlot.CHEST, false);
				return hasPartialNetheriteSuit;
			case COPPER_FULL_SET:
				return hasFullNetheriteSuit(entity, true);
			case COPPER:
				return CreateAstralBacktank.hasWorkingDivingSuit(entity) || hasFullNetheriteSuit(entity, false);
			case ALWAYS:
				return true;
		}
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
		if (slotMaterial == null) return false;
		boolean valid = materialIsNetherite(slotMaterial);
		if (orCopper) valid |= materialIsCopper(slotMaterial);
		return valid;
	}

	@Nullable
	private static ArmorMaterial getMaterialBySlot(LivingEntity entity, EquipmentSlot slot) {
		Item slotItem = entity.getItemBySlot(slot).getItem();
		if (!(slotItem instanceof ArmorItem)) return null;
		return ((ArmorItem) slotItem).getMaterial();
	}

	private static boolean materialIsCopper(ArmorMaterial material) {
		return CreateAstralBacktank.CONFIG.copperMaterials().contains(material.getName());
	}

	private static boolean materialIsNetherite(ArmorMaterial material) {
		return CreateAstralBacktank.CONFIG.netheriteMaterials().contains(material.getName());
	}
}
