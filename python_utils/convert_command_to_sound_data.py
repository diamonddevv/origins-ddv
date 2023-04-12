# (C) DiamondDev, 2023
# Licensed under CC0
#
# Description of File:
#   CLI Tool to convert Minecraft Commands to an instance of SoundsData.java, using Yarn Mappings, 1.19.3 Minecraft, and FabricMC
#   ParticlesData Record located at:
#       net.diamonddev.ddvorigins.util.FXUtil.SoundsData

## function definitions
def parseCoordinate(s, d):
    if (s == "~"): return "entity.get" + d + "()"
    return s

## Cut Command String and get only the bits I need
commandStr = input("Enter Command: ")  # screw input validation i cant be bothered
if (commandStr.startswith("/")): commandStr = commandStr[1:]  # remove '/' at start if it exists

commandStr = commandStr[10:]  # remove "playsound "
lumps = commandStr.split(" ")  # split at spaces into lumps

## Start Building the line of Java code
line = "FXUtil.playSounds(new FXUtil.SoundsData(Registries.SOUND_EVENT.get(new Identifier(\""  # start the line
line += lumps[0]
line += "\")), "
line += "SoundCategory.MASTER, "  # add the category, for simplicity its always master
line += parseCoordinate(lumps[3], "X")  # coord x
line += ", "
line += parseCoordinate(lumps[4], "Y")  # coord y
line += ", "
line += parseCoordinate(lumps[5], "Z")  # coord z
line += ", "
line += lumps[6] + "f"  # volume
line += ", "
line += lumps[7] + "f" # pitch
line += "), entity.world);"  # Finish line

## Printout phase
print()
print()
print("Generated Line of Code: ")
print()
print(line)
print()