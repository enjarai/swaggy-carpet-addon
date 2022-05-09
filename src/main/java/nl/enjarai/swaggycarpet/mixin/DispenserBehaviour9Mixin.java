package nl.enjarai.swaggycarpet.mixin;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import nl.enjarai.swaggycarpet.SwaggySettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(targets = "net.minecraft.block.dispenser.DispenserBehavior$9")
public abstract class DispenserBehaviour17Mixin {
	private BlockPointer pointer;

	@Inject(
			method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
			at = @At(value = "HEAD")
	)
	private void storeLocals(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		this.pointer = pointer;
	}

	@ModifyVariable(
			method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
			at = @At(value = "STORE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;")
	)
	private Item pickupFishIfPossible(Item original) {
		if (SwaggySettings.dispensersPickUpBothFishAndWater) {
			BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
			List<WaterCreatureEntity> list = pointer.getWorld().getEntitiesByClass(
					WaterCreatureEntity.class, new Box(blockPos), waterEntity -> waterEntity.isAlive() && waterEntity instanceof Bucketable);

			for (WaterCreatureEntity entity : list) {
				var bucketable = (Bucketable) entity;

				entity.playSound(bucketable.getBucketedSound(), 1.0f, 1.0f);
				ItemStack resultStack = bucketable.getBucketItem();
				entity.discard();

				return resultStack.getItem();
			}
		}
		return original;
	}
}
