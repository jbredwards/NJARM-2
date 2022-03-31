package git.jbredwards.njarm;

import javax.annotation.Nonnull;

/**
 * mod constants (exists so that I don't have to type them over and over)
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
            DEPENDENCIES = "required-after:fluidlogged_api;required-after:bookshelf;after:baubles;after:jei";
}
