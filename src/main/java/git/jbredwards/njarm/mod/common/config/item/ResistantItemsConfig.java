package git.jbredwards.njarm.mod.common.config.item;

import git.jbredwards.njarm.mod.common.config.IConfig;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jbred
 *
 */
public final class ResistantItemsConfig implements IConfig
{
    @Config.LangKey("config.njarm.item.resistant.explodeImmune")
    @Nonnull public final String[] explodeImmune;
    @Nonnull public static final List<Item> EXPLODE = new ArrayList<>();

    @Config.LangKey("config.njarm.item.resistant.fireImmune")
    @Nonnull public final String[] fireImmune;
    @Nonnull public static final List<Item> FIRE = new ArrayList<>();

    @Config.LangKey("config.njarm.item.resistant.invulnerable")
    @Nonnull public final String[] invulnerable;
    @Nonnull public static final List<Item> INVULNERABLE = new ArrayList<>();

    @Override
    public void onUpdate() {
        EXPLODE.clear();
        final Set<Item> explode = new HashSet<>();
        for(String name : explodeImmune) {
            final @Nullable Item item = Item.getByNameOrId(name);
            if(ChatUtils.getOrError(item != null, "Could not resolve item in config: " + name)) explode.add(item);
        }

        FIRE.clear();
        final Set<Item> fire = new HashSet<>();
        for(String name : fireImmune) {
            final @Nullable Item item = Item.getByNameOrId(name);
            if(ChatUtils.getOrError(item != null, "Could not resolve item in config: " + name)) fire.add(item);
        }

        INVULNERABLE.clear();
        final Set<Item> inv = new HashSet<>();
        for(String name : invulnerable) {
            final @Nullable Item item = Item.getByNameOrId(name);
            if(ChatUtils.getOrError(item != null, "Could not resolve item in config: " + name)) inv.add(item);
        }

        EXPLODE.addAll(explode);
        FIRE.addAll(fire);
        INVULNERABLE.addAll(inv);
    }

    //needed for gson
    public ResistantItemsConfig(@Nonnull String[] explodeImmune, @Nonnull String[] fireImmune, @Nonnull String[] invulnerable) {
        this.explodeImmune = explodeImmune;
        this.fireImmune = fireImmune;
        this.invulnerable = invulnerable;
    }
}
