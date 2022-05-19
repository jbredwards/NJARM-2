package git.jbredwards.njarm.mod.common.block.util;

/**
 * When running through blocks, lily pads for example, the particles & sounds for the block below are used.
 * Vanilla has only corrected this behavior for the running sound snow layers use. This interface serves as a way to
 * specify whether ot not to apply the fix for this block or not (MC-1691)
 * @author jbred
 *
 */
public interface IHasRunningEffects {}
