package git.jbredwards.njarm.mod.common.config.entity.util;

import git.jbredwards.njarm.mod.common.config.IConfig;

/**
 *
 * @author jbred
 *
 */
public interface IEntityConfig extends IConfig
{
    int trackerRange();
    int trackerUpdateFrequency();
    boolean trackerSendVelocityUpdates();
}
