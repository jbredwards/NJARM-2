package git.jbredwards.njarm.mod.common.config.entity.util;

import git.jbredwards.njarm.mod.common.config.IConfig;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public interface ISpawnableConfig extends IConfig
{
    @Nonnull
    String biomeData();

    int maxSpawnCount();
    int minSpawnCount();
    int spawnWeight();
}
