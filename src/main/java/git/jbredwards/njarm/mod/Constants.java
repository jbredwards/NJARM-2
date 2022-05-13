package git.jbredwards.njarm.mod;

import javax.annotation.Nonnull;

/**
 * Mod constants (exists so that I don't have to type them over and over)
 * @author jbred
 *
 */
public final class Constants
{
    @Nonnull
    public static final String
            MODID = "njarm",
            NAME = "Not Just Another Ruby Mod 2",
            VERSION = "2.4",
            DEPENDENCIES = "required-after:fluidlogged_api@[1.8.0,);required-after:bookshelf;after:baubles;required-client:assetmover;after-client:jei;";
}
