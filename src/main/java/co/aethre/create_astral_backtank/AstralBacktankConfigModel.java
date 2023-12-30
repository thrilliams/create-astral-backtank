package co.aethre.create_astral_backtank;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Expanded;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

import java.util.List;

@Modmenu(modId = CreateAstralBacktank.ID)
@Config(name = "astral-backtank-config", wrapperName = "AstralBacktankConfig")
public class AstralBacktankConfigModel {
	public ThermalResistance freezeResistance = ThermalResistance.COPPER;
	public ThermalResistance heatResistance = ThermalResistance.NETHERITE_FULL_SET;

	public List<String> copperMaterials = List.of("create:copper");
	public List<String> netheriteMaterials = List.of("netherite");

	@RangeConstraint(min = 1.0F, max = 10.0F)
	// this default value adjusts mars to feel like earth gravity
	public float divingBootsGravityMultiplier = 9.80665F / 3.72076F;
	public boolean divingBootsGravityPast1G = false;
	public boolean divingBootsNormalizeGravity = false;
}
