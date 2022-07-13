package git.jbredwards.njarm.mod.common.tileentity;

import git.jbredwards.njarm.mod.client.particle.util.ParticleUtils;
import git.jbredwards.njarm.mod.common.block.BlockNetherCore;
import git.jbredwards.njarm.mod.common.config.block.NetherCoreConfig;
import git.jbredwards.njarm.mod.common.init.ModBlocks;
import git.jbredwards.njarm.mod.common.init.ModSounds;
import git.jbredwards.njarm.mod.common.util.ChatUtils;
import git.jbredwards.njarm.mod.common.util.ItemStackUtils;
import git.jbredwards.njarm.mod.common.util.SoundUtils;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jbred
 *
 */
public class TileEntityNetherCore extends TileEntityBasic implements ITickable
{
    @Nullable
    protected UUID activator = null;
    protected boolean activated, prevActivated;
    protected int time, duration = NetherCoreConfig.getDuration();

    @Override
    public void update() {
        prevActivated = activated;
        if(hasWorld()) {
            //pocket edition behavior
            if(NetherCoreConfig.getAltReactorBehavior()) {
                if(activated && !world.isRemote) {
                    //converts reactor to glowing obsidian at proper intervals
                    switch(time) {
                        case 20:
                            fill(pos.add(-1, -1, 0), pos.add(1, -1, 0), false, false, ModBlocks.GLOWING_OBSIDIAN);
                            fill(pos.add(0, -1, -1), pos.add(0, -1, 1), false, true, ModBlocks.GLOWING_OBSIDIAN);
                            break;
                        case 40:
                            setBlock(pos.add(1, 0, 1), ModBlocks.GLOWING_OBSIDIAN);
                            setBlock(pos.add(1, 0, -1), ModBlocks.GLOWING_OBSIDIAN);
                            setBlock(pos.add(-1, 0, 1), ModBlocks.GLOWING_OBSIDIAN);
                            setBlock(pos.add(-1, 0, -1), ModBlocks.GLOWING_OBSIDIAN);
                            break;
                        case 60:
                            fill(pos.add(-1, 1, 0), pos.add(1, 1, 0), false, false, ModBlocks.GLOWING_OBSIDIAN);
                            fill(pos.add(0, 1, -1), pos.add(0, 1, 1), false, true, ModBlocks.GLOWING_OBSIDIAN);
                            break;
                        case 80:
                            setBlock(pos.add(1, -1, 1), ModBlocks.GLOWING_OBSIDIAN);
                            setBlock(pos.add(1, -1, -1), ModBlocks.GLOWING_OBSIDIAN);
                            setBlock(pos.add(-1, -1, 1), ModBlocks.GLOWING_OBSIDIAN);
                            setBlock(pos.add(-1, -1, -1), ModBlocks.GLOWING_OBSIDIAN);
                            break;
                    }
                    //reactor is set
                    if(time > 80) {
                        //reactor is still running
                        if(time < duration) {
                            //try summon entity
                            if(canMakeWave(NetherCoreConfig.getPigmanCooldown())) {
                                final Entity entity = NetherCoreConfig.getRandomEntity(world);

                                final int x = world.rand.nextInt(15) - 7, z;
                                if(x > 1 || x < -1) z = world.rand.nextInt(15) - 7;
                                else z = world.rand.nextBoolean() ? world.rand.nextInt(6) + 2 : world.rand.nextInt(6) - 7;
                                entity.setPosition(pos.getX() + x + 0.5, pos.getY() - 1, pos.getZ() + z + 0.5);

                                if(activator != null) {
                                    @Nullable EntityPlayer player = world.getPlayerEntityByUUID(activator);
                                    if(player == null) {
                                        final List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class,
                                                new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(NetherCoreConfig.getRange()),
                                                EntitySelectors.CAN_AI_TARGET.and(Entity::isEntityAlive)::test);

                                        if(players.size() > 0) player = players.get(world.rand.nextInt(players.size()));
                                    }

                                    if(player != null) {
                                        if(entity instanceof EntityPigZombie) ((EntityPigZombie)entity).becomeAngryAt(player);
                                        else if(entity instanceof EntityLiving) ((EntityLiving)entity).setAttackTarget(player);
                                    }
                                }

                                if(entity instanceof EntityLiving) ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(pos), null);
                                world.spawnEntity(entity);
                            }
                            //try summon items
                            if(canMakeWave(NetherCoreConfig.getItemCooldown())) {
                                NetherCoreConfig.getRandomItems(world).forEach(stack -> {
                                    final int x = world.rand.nextInt(15) - 7, z;
                                    if(x > 1 || x < -1) z = world.rand.nextInt(15) - 7;
                                    else z = world.rand.nextBoolean() ? world.rand.nextInt(6) + 2 : world.rand.nextInt(6) - 7;

                                    final EntityItem item = new EntityItem(world, pos.getX() + x + 0.5, pos.getY() - 1, pos.getZ() + z + 0.5, stack);
                                    item.setDefaultPickupDelay();
                                    world.spawnEntity(item);
                                });
                            }
                        }
                        //reactor is finished
                        else switch(time - duration) {
                            case 0:
                                fill(pos.add(-1, 1, -1), pos.add(1, 1, 1), false, false, Blocks.OBSIDIAN);
                                break;
                            case 20:
                                fill(pos.add(-1, 0, -1), pos.add(1, 1, 1), false, true, Blocks.OBSIDIAN);
                                world.setBlockState(pos, getBlockType().getStateFromMeta(2), 2);
                                break;
                            case 40:
                                fill(pos.add(-1, -1, -1), pos.add(1, -1, 1), false, false, Blocks.OBSIDIAN);
                                break;
                            case 60:
                                createNetherSpire(true);
                                activated = false;
                                return;
                        }
                    }

                    time++;
                }
            }
            //legacy reactor behavior
            else if(NetherCoreConfig.getRange() != 0 && world.getTotalWorldTime() % 10 == 0) {
                activated = areBlocksSet();
                if(activated) {
                    if(!world.isRemote) applyEffect();
                    else spawnParticles();

                    //plays sound in a way that it doesn't overlap
                    if(world.getTotalWorldTime() % 70 == 0)
                        SoundUtils.playSound(this, ModSounds.NETHER_REACTOR_CORE_AMBIENT, 1, 1);
                }

                //plays active/deactivate sound
                if(prevActivated != activated)
                    SoundUtils.playSound(this, activated ? ModSounds.NETHER_REACTOR_CORE_AMBIENT : ModSounds.NETHER_REACTOR_CORE_DEACTIVATE, 1, 1);
            }
        }
    }

    @Override
    public void writeNBT(@Nonnull NBTTagCompound dataTag) {
        if(activator != null) dataTag.setString("Player", activator.toString());
        dataTag.setInteger("Time", time);
        dataTag.setInteger("Duration", duration);
    }

    @Override
    public void readNBT(@Nonnull NBTTagCompound dataTag) {
        if(dataTag.hasKey("Player", Constants.NBT.TAG_STRING)) {
            final String id = dataTag.getString("Player");
            if(id.split("-").length == 5) activator = UUID.fromString(id);
        }

        if(dataTag.hasKey("Time", Constants.NBT.TAG_INT)) {
            time = dataTag.getInteger("Time");
            activated = time > 0;
        }

        if(dataTag.hasKey("Duration", Constants.NBT.TAG_INT))
            duration = Math.max(dataTag.getInteger("Duration"), 100);
    }

    public boolean tryInitialize(@Nonnull EntityPlayer player) {
        if(ChatUtils.getOrError(player, !world.isOutsideBuildHeight(pos.up(32)), "tile.njarm.nether_reactor_core.buildLower")
        && ChatUtils.getOrError(player, player.posY + 1 >= pos.getY(), "tile.njarm.nether_reactor_core.needsBeCloser")
        && ChatUtils.getOrError(player, areBlocksSet(), "tile.njarm.nether_reactor_core.incorrectPattern")) {
            if(!world.isRemote) {
                activator = player.getUniqueID();
                activated = true;
                time = 1;

                createNetherSpire(false);
                world.setBlockState(pos, getBlockType().getDefaultState().withProperty(BlockNetherCore.TYPE, BlockNetherCore.Type.INITIALIZED));
            }

            else player.sendMessage(new TextComponentTranslation("tile.njarm.nether_reactor_core.activate"));
        }

        return true;
    }

    public void applyEffect() {
        world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(NetherCoreConfig.getRange())).forEach(entity -> {
            if(entity instanceof EntityPlayer) entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 11 * 20, 0, true, true));
            else if(entity instanceof IEntityOwnable && ((IEntityOwnable)entity).getOwner() instanceof EntityPlayer)
                entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 11 * 20, 0, true, true));
        });
    }

    public void spawnParticles() {
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                for(int k = -1; k < 2; k++) {
                    final double chance = world.rand.nextDouble();
                    if(i == -1 && chance >= 0.7) ParticleUtils.spawnRedstoneParticles(world, pos.add(j, i, k), (x, y, z) ->
                            world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0, 0, 0));
                    else if(k % 2 != 0 && i == 0 && j % 2 != 0 && chance >= 0.7) ParticleUtils.spawnRedstoneParticles(world, pos.add(j, i, k), (x, y, z) ->
                            world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0, 0, 0));
                    else if(i == 1 && (j % 2 == 0 || k % 2 == 0) && chance >= 0.7) ParticleUtils.spawnRedstoneParticles(world, pos.add(j, i, k), (x, y, z) ->
                            world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0, 0, 0));
                }
            }
        }
    }

    protected boolean canMakeWave(int cooldown) {
        return cooldown > 0 && (NetherCoreConfig.isDynamicDifficulty() && time % (cooldown /
                Math.max(world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(8),
                        player -> !player.isSpectator() && player.isEntityAlive() && !(player instanceof FakePlayer)).size(), 1)) == 0
                || !NetherCoreConfig.isDynamicDifficulty() && time % cooldown == 0);
    }

    public boolean areBlocksSet() {
        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2; y++) {
                for(int z = -1; z < 2; z++) {
                    final BlockPos offset = pos.add(x, y, z);
                    final IBlockState state = world.getBlockState(offset);

                    //bottom layer
                    if(y == -1) {
                        //check gold
                        if(x % 2 != 0 && z % 2 != 0) {
                            if(!ItemStackUtils.hasOreName(world, pos, state, "blockGold"))
                                return false;
                        }
                        //check cobble
                        else {
                            if(!ItemStackUtils.hasOreName(world, pos, state, "cobblestone"))
                                return false;
                        }
                    }

                    //middle layer
                    else if(y == 0) {
                        //check cobble
                        if(x % 2 != 0 && z % 2 != 0) {
                            if(!ItemStackUtils.hasOreName(world, pos, state, "cobblestone"))
                                return false;
                        }
                        //check air
                        else if(x != 0 || z != 0) {
                            if(!state.getBlock().isAir(state, world, offset))
                                return false;
                        }
                    }

                    //top layer
                    else {
                        //check air
                        if(x % 2 != 0 && z % 2 != 0) {
                            if(!state.getBlock().isAir(state, world, offset))
                                return false;
                        }
                        //check cobble
                        else {
                            if(!ItemStackUtils.hasOreName(world, pos, state, "cobblestone"))
                                return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    //==============
    //SPIRE HANDLING
    //==============

    public void createNetherSpire(boolean isDecay) {
        fill(pos.add(-8, -3, -8), pos.add(8, -2, 8), false, false, Blocks.NETHERRACK);
        fill(pos.add(-8, -1, -8), pos.add(8, 5, 8), isDecay, true, Blocks.NETHERRACK);
        fill(pos.add(-8, 3, -8), pos.add(8, 3, 8), isDecay, false, Blocks.NETHERRACK);
        fill(pos.add(-5, 5, -5), pos.add(5, 13, 5), isDecay, true, Blocks.NETHERRACK);
        fill(pos.add(-3, 11, -3), pos.add(3, 25, 3), isDecay, true, Blocks.NETHERRACK);

        createRoof(-8, 5, 8, 8, isDecay, true);
        createRoof(-5, 13, 4, 5, isDecay, false);
        createRoof(-3, 25, 3, 3, isDecay, true);

        setBlock(pos.add(-5, 23, 5), Blocks.AIR);

        if(!isDecay) {
            fill(pos.add(2, -1, 7), pos.add(7, 1, -7), false, false, Blocks.AIR);
            fill(pos.add(-2, -1, 7), pos.add(-7, 1, -7), false, false, Blocks.AIR);
            fill(pos.add(1, -1, 2), pos.add(-1, 1, 7), false, false, Blocks.AIR);
            fill(pos.add(1, -1, -2), pos.add(-1, 1, -7), false, false, Blocks.AIR);
            fill(pos.add(7, 2, 7), pos.add(-7, 2, -7), false, false, Blocks.AIR);
        }
    }

    protected void createRoof(int xIn, int yIn, int zIn, int size, boolean isDecay, boolean isNormal) {
        for(int i = 0; i <= size * 2; i++) {
            int s = isNormal ? 1 : -1;
            int x = (s * xIn) + (s * i);
            int y = yIn + i;
            int z = (s * zIn) - (s * i);

            fillDiagonal(x, y, z, size, i, isDecay);
            fillDiagonal(x, y, MathHelper.clamp(z - s, -size, size), size, i, isDecay);
            fillDiagonal(x, y, MathHelper.clamp(z + s, -size, size), size, i, isDecay);
        }
    }

    protected void fillDiagonal(int x, int y, int z, int size, int layer, boolean isDecay) {
        for(int i = 0; i <= size; i++) {
            final BlockPos pos1 = pos.add(MathHelper.clamp(x + i, -size, size), y, MathHelper.clamp(z + i, -size, size));
            final BlockPos pos2 = pos.add(MathHelper.clamp(x - i, -size, size), y, MathHelper.clamp(z - i, -size, size));

            if(x + i >= -size && x + i <= size && z + i >= -size && z + i <= size) {
                if(x + i + 1 < -size || x + i + 1 > size || z + i + 1 < -size || z + i + 1 > size)
                    fill(pos1, pos1.down(layer), isDecay, false, Blocks.NETHERRACK);
                if(!isDecay) setBlock(pos1, Blocks.NETHERRACK);
                else if(world.rand.nextDouble() < 0.3) setBlock(pos1, Blocks.AIR);
            }

            if(x - i >= -size && x - i <= size && z - i >= -size && z - i <= size) {
                if(x - i - 1 < -size || x - i - 1 > size || z - i - 1 < -size || z - i - 1 > size)
                    fill(pos2, pos2.down(layer), isDecay, false, Blocks.NETHERRACK);
                if(!isDecay) setBlock(pos2, Blocks.NETHERRACK);
                else if(world.rand.nextDouble() < 0.3) setBlock(pos2, Blocks.AIR);
            }
        }
    }

    protected void fill(@Nonnull BlockPos pos1, @Nonnull BlockPos pos2, boolean isDecay, boolean hollow, @Nonnull Block block) {
        final BlockPos start = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
        final BlockPos finish = new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));

        for(int y = start.getY(); y <= finish.getY(); ++y) {
            for(int x = start.getX(); x <= finish.getX(); ++x) {
                for(int z = start.getZ(); z <= finish.getZ(); ++z) {
                    if(x == start.getX() || x == finish.getX() || z == start.getZ() || z == finish.getZ() || !hollow) {
                        if(!isDecay) setBlock(new BlockPos(x, y, z), block);
                        else if(world.rand.nextDouble() < 0.3) setBlock(new BlockPos(x, y, z), Blocks.AIR);
                    }
                }
            }
        }
    }

    //checks if the pos is breakable, then if it is, places the given block
    protected void setBlock(@Nonnull BlockPos pos, @Nonnull Block block) {
        final IBlockState state = world.getBlockState(pos);
        if(state.getBlockHardness(world, pos) != -1)
            world.setBlockState(pos, block.getDefaultState(), Constants.BlockFlags.SEND_TO_CLIENTS);
    }
}
