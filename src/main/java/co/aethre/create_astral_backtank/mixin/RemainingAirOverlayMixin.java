// adapted from https://git.bitheaven.ru/BitHeaven/create-air-fabric/src/branch/main/src/main/java/ru/bitheaven/createairfabric/mixin/RemainingAirOverlayMixin.java

package co.aethre.create_astral_backtank.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;

import co.aethre.create_astral_backtank.CreateAstralBacktank;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

@Mixin(RemainingAirOverlay.class)
public class RemainingAirOverlayMixin {
	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/player/LocalPlayer;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"
			),
			method = "render(Lnet/minecraft/client/gui/GuiGraphics;II)V"
	)
	private static boolean redirectRender(LocalPlayer player, TagKey<Fluid> fluidTagKey) {
		// show backtank air overlay if the player is underwater or in a dimension without oxygen
		return CreateAstralBacktank.shouldUseOxygen(player, fluidTagKey);
	}
}
