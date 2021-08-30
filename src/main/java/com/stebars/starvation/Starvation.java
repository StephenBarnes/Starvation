package com.stebars.starvation;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;



@Mod("starvation")
public class Starvation
{
    
    public Starvation() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC); 
    }

    @SubscribeEvent
    public void jumpExtraHunger(LivingJumpEvent event) {
    	if (event.getEntity() instanceof PlayerEntity) {
    		// By default, jumping causes 0.05, or 0.2 if sprinting.
    		// I'll increase to 0.15, or 0.25 if sprinting.
    		// So I need to add 0.1, or 0.05 if sprinting.
    		PlayerEntity player = (PlayerEntity) event.getEntity();
    		player.causeFoodExhaustion(player.isSprinting() ? OptionsHolder.COMMON.jumpSprintExtra.get() : OptionsHolder.COMMON.jumpWalkExtra.get());
    	}
    }
    
    @SubscribeEvent
    public void breakBlockExtraHunger(BreakEvent event) {
    	event.getPlayer().causeFoodExhaustion(OptionsHolder.COMMON.breakBlockExtra.get());
    }
    
    @SubscribeEvent
    public void attackExtraHunger(LivingAttackEvent event) {
    	DamageSource source = event.getSource();
    	if (source instanceof EntityDamageSource) {
    		EntityDamageSource entityDamageSource = (EntityDamageSource) source;
    		if (entityDamageSource.msgId == "player") {
    			((PlayerEntity) entityDamageSource.getEntity()).causeFoodExhaustion(OptionsHolder.COMMON.attackExtra.get());
    		}
    	}
    }

    @SubscribeEvent
    public void hurtExtraHunger(LivingDamageEvent event) {
    	LivingEntity hurtEntity = event.getEntityLiving();
    	if (hurtEntity instanceof PlayerEntity) {
    		((PlayerEntity) hurtEntity).causeFoodExhaustion(event.getSource().getFoodExhaustion() * OptionsHolder.COMMON.hurtExtraMultiplier.get());
    	}
    }
    
    @SubscribeEvent
    public void travelExtraHunger(PlayerTickEvent event) {
    	// The actual causeFoodExhaustion from vanilla is in PlayerEntity.checkMovementStatistics
    	// but I don't want to replicate that, rather have per-tick costs to swimming, etc.
    	PlayerEntity player = event.player;
    	if (OptionsHolder.COMMON.passengerTickExtra.get() != 0f && player.isPassenger())
    		player.causeFoodExhaustion(OptionsHolder.COMMON.passengerTickExtra.get());
    	else if (OptionsHolder.COMMON.swimmingTickExtra.get() != 0f && player.isSwimming())
    		player.causeFoodExhaustion(OptionsHolder.COMMON.swimmingTickExtra.get());
    	else if (OptionsHolder.COMMON.walkUnderwaterTickExtra.get() != 0f && player.isEyeInFluid(FluidTags.WATER))
    		player.causeFoodExhaustion(OptionsHolder.COMMON.walkUnderwaterTickExtra.get());
    	else if (OptionsHolder.COMMON.walkOnWaterTickExtra.get() != 0f && player.isInWater())
    		player.causeFoodExhaustion(OptionsHolder.COMMON.walkOnWaterTickExtra.get());
    	else if (OptionsHolder.COMMON.climbTickExtra.get() != 0f && player.onClimbable())
    		player.causeFoodExhaustion(OptionsHolder.COMMON.climbTickExtra.get());
    	else if ((OptionsHolder.COMMON.fallTickExtra.get() != 0 || OptionsHolder.COMMON.flyTickExtra.get() != 0) && !player.isOnGround()) {
    		if (OptionsHolder.COMMON.fallTickExtra.get() != 0 && player.isFallFlying())
    			player.causeFoodExhaustion(OptionsHolder.COMMON.fallTickExtra.get());
    		else if (OptionsHolder.COMMON.flyTickExtra.get() != 0 && !player.isFallFlying())
    			player.causeFoodExhaustion(OptionsHolder.COMMON.flyTickExtra.get());
    	} else if (OptionsHolder.COMMON.sprintTickExtra.get() != 0 && player.isSprinting())
    		player.causeFoodExhaustion(OptionsHolder.COMMON.sprintTickExtra.get());
    	else if (OptionsHolder.COMMON.crouchTickExtra.get() != 0 && player.isCrouching())
    		player.causeFoodExhaustion(OptionsHolder.COMMON.crouchTickExtra.get());
    	// They could still be standing/walking; can't test for that easily without checking all the rest are false, which is
    	// slow, so I'll just force that to be 0 extra.
    }
    

    /* Not yet implemented: multipliers on the "Hunger" potion effect (e.g. from eating rotten flesh) - because there doesn't
     * seem to be a potion performEffect hook, only hooks for applying/removing effects. */
}
