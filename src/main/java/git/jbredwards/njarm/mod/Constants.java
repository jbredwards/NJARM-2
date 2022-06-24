package git.jbredwards.njarm.mod;

import net.darkhax.bookshelf.lib.LoggingHelper;

import javax.annotation.Nonnull;

/**
 * Mod constants
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
            DEPENDENCIES = "required-after:fluidlogged_api@[1.8.1,);required-after:bookshelf;after:baubles;required-client:assetmover;after-client:jei;";

    @Nonnull
    public static final LoggingHelper LOGGER = new LoggingHelper(NAME);
}
