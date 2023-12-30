package co.aethre.create_astral_backtank;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Expanded;
import io.wispforest.owo.config.annotation.Modmenu;

import java.util.List;

@Modmenu(modId = CreateAstralBacktank.ID)
@Config(name = "astral-backtank-config", wrapperName = "AstralBacktankConfig")
public class AstralBacktankConfigModel {
	public ThermalResistance freezeResistance = ThermalResistance.COPPER;
	public ThermalResistance heatResistance = ThermalResistance.NETHERITE_FULL_SET;

	@Expanded
	public List<String> copperMaterials = List.of("create:copper");

	@Expanded
	public List<String> netheriteMaterials = List.of("netherite");
}
