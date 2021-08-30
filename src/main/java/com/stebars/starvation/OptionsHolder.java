package com.stebars.starvation;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class OptionsHolder {
	public static class Common {	

	    // For reference, exhaustion is full when the bar gets to 4.0.

	    public ConfigValue<Float> jumpWalkExtra;
	    public ConfigValue<Float> jumpSprintExtra;
	    // By default, jumping causes 0.05, or 0.2 if sprinting.
	    // I'll increase to 0.2, or 0.3 if sprinting.
	    // So I need to add 0.15, or 0.1 if sprinting.
	    
	    public ConfigValue<Float> breakBlockExtra;
	    // By default it causes 0.005; I'll increase to 0.15, so adding another 0.145.
	    
	    public ConfigValue<Float> attackExtra;
	    // By default, attacking causes 0.1; I'll increase to 0.3, so adding 0.2.
	    
	    public ConfigValue<Float> hurtExtraMultiplier;
	    // Causes a variable amount depending on damage source; I'll just quadruple it.
	    
	    public ConfigValue<Float> passengerTickExtra;
	    public ConfigValue<Float> swimmingTickExtra;
	    public ConfigValue<Float> walkUnderwaterTickExtra;
	    public ConfigValue<Float> walkOnWaterTickExtra;
	    public ConfigValue<Float> climbTickExtra;
	    public ConfigValue<Float> fallTickExtra;
	    public ConfigValue<Float> flyTickExtra;
	    public ConfigValue<Float> sprintTickExtra;
	    public ConfigValue<Float> crouchTickExtra;
	    // These are all per-tick, 20 ticks per second, so should probably be under 0.01.


		public Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Set the cost of actions. A half-shank is 4, so a full bar is 20*4 = 80. The values here are in addition to the values from vanilla.")
				.push("Actions");
			
	        jumpWalkExtra = builder.comment("Extra hunger from jumping while not sprinting. Vanilla is 0.05.")
	        		.define("jumpWalkExtra", 0.15f);
	        jumpSprintExtra = builder.comment("Hunger from jumping while sprinting. Vanilla is 0.2.")
	        		.define("jumpSprintExtra", 0.1f);
	        breakBlockExtra = builder.comment("Hunger from breaking a block. Vanilla is 0.005.")
	        		.define("breakBlockExtra", 0.145f);
	        attackExtra = builder.comment("Hunger from attacking. Vanilla is 0.1.")
	        		.define("attackExtra", 0.2f);
	        hurtExtraMultiplier = builder.comment("In vanilla, different damage sources deal different amounts of exhaustion. "
	        		+ "This is a multiplier on that exhaustion, added to vanilla, so setting this to 3 gives 4x as much exhaustion from all damage.")
	        		.define("hurtExtraMultiplier", 3f);
	        builder.pop();
	        builder.comment("These are exhaustion costs per tick. There are 20 ticks in a second, so something that costs 0.005 per tick will drain an "
	        		+ "entire bar of 20 shanks in 20 * 4 / (0.005 * 20) = 800 seconds = 13 minutes. One Minecraft day is 20 minutes.")
	        	.push("Per-tick");
	        passengerTickExtra = builder.define("passengerTickExtra", 0f);
	        swimmingTickExtra = builder.define("swimmingTickExtra", 0.003f);
	        walkUnderwaterTickExtra = builder.define("walkUnderwaterTickExtra", 0f);
	        walkOnWaterTickExtra = builder.define("walkOnWaterTickExtra", 0f);
	        climbTickExtra = builder.define("climbTickExtra", 0.003f);
	        fallTickExtra = builder.define("fallTickExtra", 0f);
	        flyTickExtra = builder.define("flyTickExtra", 0.0015f);
	        sprintTickExtra = builder.define("sprintTickExtra", 0.003f);
	        crouchTickExtra = builder.define("crouchTickExtra", 0.0015f);
			builder.pop();
		}
	}

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	static { //constructor
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}