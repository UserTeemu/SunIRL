# SunIRL
 A 1.16 Fabric mod (made for ModFest 1.16) that allows you to see the sun and moon where they should be in real life.
 
 The mod requires [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api/files). SunIRL also uses [ClothConfig](https://github.com/shedaniel/cloth-config) and [AutoConfig](https://github.com/shedaniel/AutoConfig) for configs, which are included in the jar.
 
 Calculations made based on [commons-suncalc](https://github.com/shred/commons-suncalc).
 
 ## HELP! The sun doesn't go below the horizon, even though it's supposed to
It's because what you call the "horizon" isn't the same as 0 pitch.
What the player would call the horizon, depends their render distance.
If someone has a very pog idea how to make this look more pleasing, please PR it.
 
 ## Customization
 ### With ModMenu
 When in a world: Esc -> Mods -> SunIRL -> gear icon in top right
 ### Without ModMenu
 Sadly you'll need to change the settings from `my_game_folder/config/sunirl.json`
 
 ## License
 License: [GPLv3 license](https://www.gnu.org/licenses/gpl-3.0.html)
