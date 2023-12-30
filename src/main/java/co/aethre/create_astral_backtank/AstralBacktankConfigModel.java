package co.aethre.create_astral_backtank;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Expanded;
import io.wispforest.owo.config.annotation.Modmenu;

import java.util.List;

@Modmenu(modId = CreateAstralBacktank.ID)
@Config(name = "astral-backtank-config", wrapperName = "AstralBacktankConfig")
public class AstralBacktankConfigModel {
	public FreezeResistance freezeResistance = FreezeResistance.COPPER;
	@Expanded
	public List<String> copperMaterials = List.of("create:copper");
}
