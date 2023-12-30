# Create Astral Backtank

This mod is more or less a fork of [Create Air Fabric](https://modrinth.com/mod/create-air-fabric), with some minor changes and updates. Notably, Backtanks can no longer be refilled in enivorenments without oxygen, and copper diving gear is now cold resistant (at least in the context of space).

The mod is also mildly configurable. Options can be changed using [Mod Menu](https://modrinth.com/mod/modmenu) or by directly editing the config file. There are currently two options:
- `Freeze resistance`, and
- `Heat resistance`. Both share the same possible values:
    - `Never`: No Create equipment will grant thermal resistance.
    - `Netherite, full set`: Wearing a Netherite diving suit and Netherite leggings and boots will grant thermal resistance.
    - `Netherite`: Wearing a Netherite diving suit will grant thermal resistance.
    - `Copper, full set`: Wearing a copper or Netherite diving suit and copper or Netherite leggings and boots will grant thermal resistance.
    - `Copper` (default): Wearing any diving suit will grant thermal resistance.
    - `Always`: Ad Astra's thermal damage will never be dealt.
- `Copper material IDs`. By default, only Create's copper armor is treated as copper armor. If your mod/pack adds copper armor, put that material's ID here for the mod to register it as copper for the `Copper, full set` option above.
- `Netherite material IDs`. Similarly, only vanilla Netherite is treated as such. You can add more Netherite or Netherite-tier armors as above.
- `Diving Boots gravity multiplier`. While in SPACE™ (that is, on a planet or in orbit) entities wearing Diving Boots will have their gravity multiplied by this number. Set this to 1.0 to disable this mechanic.
- `Diving Boots gravity can be stronger than Overworld`. By default, if `Diving Boots gravity multiplier` is set to a high enough value that adjusted gravity exceeds that of the Overworld, it will be capped. Enable this option to change that, and for wacky shenanigans.
- `Diving Boots normalize gravity`. If this is enabled, the previous two options will be ignored and gravity in space with boots on will be the same as the overworld.
