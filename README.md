# Create Astral Backtank

This mod is more or less a fork of [Create Air Fabric](https://modrinth.com/mod/create-air-fabric), with some minor changes and updates. Notably, Backtanks can no longer be refilled in enivorenments without oxygen, and copper diving gear is now cold resistant (at least in the context of space).

The mod is also mildly configurable. Options can be changed using [Mod Menu](https://modrinth.com/mod/modmenu) or by directly editing the config file. There are currently two options:
- Freeze Resistance. Possible values:
    - None: No Create equipment will grant freeze resistance.
    - Netherite: Wearing a Netherite diving suit and Netherite leggings and boots will grant freeze resistance.
    - Copper, full set: Wearing a copper or Netherite diving suit and copper or Netherite leggings and boots will grant freeze resistance.
    - Copper (default): Wearing any diving suit will grant freeze resistance.
- Copper Material IDs. By default, only Create's copper armor is treated as copper armor. If your mod/pack adds copper armor, put that material's ID here for the mod to register it as copper for the `Copper, full set` option above.
