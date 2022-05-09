package nl.enjarai.swaggycarpet.mixin;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import nl.enjarai.swaggycarpet.SwaggySettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(targets = "net.minecraft.block.dispenser.DispenserBehavior$8")
public abstract class DispenserBehaviour16Mixin {
	@Inject(
			method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
			at = @At("HEAD"),
			cancellable = true
	)
	private void pickupFishIfPossible(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		System.out.println(SwaggySettings.dispensersPickUpFish);
		if (SwaggySettings.dispensersPickUpFish && stack.isOf(Items.WATER_BUCKET)) {
			BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
			List<WaterCreatureEntity> list = pointer.getWorld().getEntitiesByClass(
					WaterCreatureEntity.class, new Box(blockPos), waterEntity -> waterEntity.isAlive() && waterEntity instanceof Bucketable);

			for (WaterCreatureEntity entity : list) {
				var bucketable = (Bucketable) entity;

				entity.playSound(bucketable.getBucketedSound(), 1.0f, 1.0f);
				ItemStack resultStack = bucketable.getBucketItem();
				entity.discard();

				cir.setReturnValue(resultStack);
			}
		}
	}
}
