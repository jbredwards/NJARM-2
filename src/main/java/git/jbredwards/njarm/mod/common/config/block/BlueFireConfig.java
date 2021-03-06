package git.jbredwards.njarm.mod.common.config.block;

import git.jbredwards.njarm.mod.common.config.ConfigHandler;
import git.jbredwards.njarm.mod.common.config.IConfig;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.config.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author jbred
 *
 */
public final class BlueFireConfig implements IConfig
{
    @Nonnull private static final ScriptEngine engine = new ScriptEngineManager(null).getEngineByName("JavaScript");

    @Config.LangKey("config.njarm.block.blueFire.soulSandBlocks")
    @Nonnull public final String[] soulSandBlocks;
    @Nonnull public static final Object2BooleanMap<Block> SOUL_SAND = new Object2BooleanOpenHashMap<>();

    @Config.LangKey("config.njarm.block.blueFire.inBlueFireEntityDmg")
    @Nonnull public final String inBlueFireEntityDmg;

    @Config.LangKey("config.njarm.block.blueFire.inBlueFireBossDmg")
    @Nonnull public final String inBlueFireBossDmg;

    @Config.LangKey("config.njarm.block.blueFire.onBlueFireEntityDmg")
    @Nonnull public final String onBlueFireEntityDmg;

    @Config.LangKey("config.njarm.block.blueFire.onBlueFireBossDmg")
    @Nonnull public final String onBlueFireBossDmg;

    public static float getDamageDealt(@Nonnull Entity entity, boolean onBlueFire) throws ScriptException {
        engine.put("entity", entity); //allow players to use obfuscated entity methods, if they're up for it...
        engine.put("health", entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).getHealth() : 0);
        if(onBlueFire) return (float)(double)engine.eval(entity.isNonBoss() ? ConfigHandler.blockCfg.blueFireCfg.onBlueFireEntityDmg : ConfigHandler.blockCfg.blueFireCfg.onBlueFireBossDmg);
        else return (float)(double)engine.eval(entity.isNonBoss() ? ConfigHandler.blockCfg.blueFireCfg.inBlueFireEntityDmg : ConfigHandler.blockCfg.blueFireCfg.inBlueFireBossDmg);
    }

    @Config.LangKey("config.njarm.block.blueFire.quarkBlueFireRender")
    public final boolean quarkBlueFireRender;
    public static boolean quarkBlueFireRender() { return ConfigHandler.blockCfg.blueFireCfg.quarkBlueFireRender; }

    @Override
    public void onUpdate() {
        //generate the soul sand blocks
        SOUL_SAND.clear();
        for(String name : soulSandBlocks) {
            final @Nullable Block block = Block.getBlockFromName(name);
            if(block != null) SOUL_SAND.put(block, true);
        }
    }

    //needed for gson
    public BlueFireConfig(@Nonnull String[] soulSandBlocks, @Nonnull String inBlueFireEntityDmg, @Nonnull String inBlueFireBossDmg, @Nonnull String onBlueFireEntityDmg, @Nonnull String onBlueFireBossDmg, boolean quarkBlueFireRender) {
        this.soulSandBlocks = soulSandBlocks;
        this.inBlueFireEntityDmg = inBlueFireEntityDmg;
        this.inBlueFireBossDmg = inBlueFireBossDmg;
        this.onBlueFireEntityDmg = onBlueFireEntityDmg;
        this.onBlueFireBossDmg = onBlueFireBossDmg;
        this.quarkBlueFireRender = quarkBlueFireRender;
    }
}
