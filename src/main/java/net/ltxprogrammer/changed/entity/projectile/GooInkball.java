package net.ltxprogrammer.changed.entity.projectile;

import net.ltxprogrammer.changed.entity.TransfurCause;
import net.ltxprogrammer.changed.entity.TransfurContext;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedEntities;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GooInkball extends ThrowableItemProjectile {
    public GooInkball(EntityType<? extends GooInkball> entityType, Level level) {
        super(entityType, level);
    }

    public GooInkball(Level level, LivingEntity livingEntity) {
        super(ChangedEntities.GOO_INKBALL.get(), livingEntity, level);
    }

    public GooInkball(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
        super(ChangedEntities.GOO_INKBALL.get(), p_37395_, p_37396_, p_37397_, p_37394_);
    }

    protected Item getDefaultItem() {
        return ChangedItems.LATEX_INKBALL.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return new ItemParticleOption(ParticleTypes.ITEM, itemstack.isEmpty() ? ChangedItems.LATEX_INKBALL.get().getDefaultInstance() : itemstack);
    }

    public void handleEntityEvent(byte p_37402_) {
        if (p_37402_ == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!(hitResult.getEntity() instanceof LivingEntity livingEntity))
            return;

        ProcessTransfur.progressTransfur(livingEntity, 6.0f,
                TransfurVariant.LATEX_SQUID_DOG.randomGender(hitResult.getEntity().level.random), TransfurContext.hazard(TransfurCause.SQUID_DOG_INKBALL));
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}