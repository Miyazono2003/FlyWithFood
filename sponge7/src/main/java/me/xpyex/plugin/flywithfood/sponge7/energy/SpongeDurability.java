package me.xpyex.plugin.flywithfood.sponge7.energy;

import me.xpyex.plugin.flywithfood.common.FlyWithFood;
import me.xpyex.plugin.flywithfood.common.flyenergy.energies.DurabilityEnergy;
import me.xpyex.plugin.flywithfood.common.implementation.FWFUser;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

public class SpongeDurability extends DurabilityEnergy {
    private static final ItemStack EMPTY_STACK = ItemStack.builder().itemType(ItemTypes.AIR).build();

    @Override
    public void cost(@NotNull FWFUser user, @NotNull Number value) {
        if (value.intValue() == 0) {
            return;
        }
        Player target = user.getPlayer();
        if (target.getChestplate().orElse(EMPTY_STACK).getType() != ItemTypes.ELYTRA) {
            return;
        }
        FlyWithFood.getInstance().getAPI().runTask(() ->
             target.getChestplate().orElse(EMPTY_STACK).offer(Keys.ITEM_DURABILITY, getNow(user).shortValue() - value.shortValue())
        );
    }

    @Override
    public @NotNull Number getNow(@NotNull FWFUser user) {
        if (user.<Player>getPlayer().getChestplate().orElse(EMPTY_STACK).getType() != ItemTypes.ELYTRA) {
            return -1;
        }
        return user.<Player>getPlayer().getChestplate().orElse(EMPTY_STACK).get(Keys.ITEM_DURABILITY).orElse(0);
    }
}
