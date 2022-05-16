package git.jbredwards.njarm.mod.common.config;

/**
 *
 * @author jbred
 *
 */
public interface IConfig
{
    default void onUpdate() {}
    //typically stuff that requires a restart will be initialized here
    default void onFMLInit() { onUpdate(); }
}
