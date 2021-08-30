A very simple mod that can be configured to give a specific amount of exhaustion (roughly equivalent to hunger) whenever the player does certain actions (jumps, breaks blocks, attacks, or gets hurt) or per-tick depending on the player's current activity (sprinting, climbing, and others).

Here's the default configuration, which summarizes what you can do with this mod:
```
#Set the cost of actions. A half-shank is 4, so a full bar is 20*4 = 80. The values here are in addition to the values from vanilla.
[Actions]
	#Hunger from jumping while sprinting. Vanilla is 0.2.
	jumpSprintExtra = 0.1
	#In vanilla, different damage sources deal different amounts of exhaustion. This is a multiplier on that exhaustion, added to vanilla, so setting this to 3 gives 4x as much exhaustion from all damage.
	hurtExtraMultiplier = 3.0
	#Extra hunger from jumping while not sprinting. Vanilla is 0.05.
	jumpWalkExtra = 0.15
	#Hunger from breaking a block. Vanilla is 0.005.
	breakBlockExtra = 0.145
	#Hunger from attacking. Vanilla is 0.1.
	attackExtra = 0.2

#These are exhaustion costs per tick. There are 20 ticks in a second, so something that costs 0.005 per tick will drain an entire bar of 20 shanks in 20 * 4 / (0.005 * 20) = 800 seconds = 13 minutes. One Minecraft day is 20 minutes.
[Per-tick]
	swimmingTickExtra = 0.003
	crouchTickExtra = 0.0015
	walkOnWaterTickExtra = 0.0
	climbTickExtra = 0.003
	fallTickExtra = 0.0
	sprintTickExtra = 0.003
	passengerTickExtra = 0.0
	flyTickExtra = 0.0015
	walkUnderwaterTickExtra = 0.0
```
