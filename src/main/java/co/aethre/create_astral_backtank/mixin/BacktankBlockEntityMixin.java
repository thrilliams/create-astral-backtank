package co.aethre.create_astral_backtank.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.content.equipment.armor.BacktankBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import earth.terrarium.ad_astra.common.util.OxygenUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BacktankBlockEntity.class)
public class BacktankBlockEntityMixin extends KineticBlockEntity {
	public BacktankBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Inject(at = @At("HEAD"), method = "tick()V", remap = false, cancellable = true)
	private void tickMixin(CallbackInfo ci) {
		// if the backtank is in a dimension without oxygen,
		if (level != null && !OxygenUtils.levelHasOxygen(level)) {
			// do normal kinetic things
			super.tick();
			// then stop the method
			ci.cancel();
		}
	}
}
