package co.aethre.create_astral_backtank;

import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CreateAstralBacktank implements ModInitializer {
	public static final String ID = "modid";
	public static final String NAME = "Example Mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	public static boolean hasWorkingDivingSuit(LivingEntity entity) {
		return DivingHelmetItem.isWornBy(entity) && !BacktankUtil.getAllWithAir(entity).isEmpty();
	}

	public static boolean shouldUseOxygen(LivingEntity entity, TagKey<Fluid> fluidTagKey) {
		return entity.isEyeInFluid(fluidTagKey) || !OxygenUtils.levelHasOxygen(entity.level());
	}
}
