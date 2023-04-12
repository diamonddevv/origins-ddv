# (C) DiamondDev, 2023
# Licensed under CC0
#
# Description of File:
#   CLI Tool to convert Minecraft Commands to an instance of ParticlesData.java, using Yarn Mappings, 1.19.3 Minecraft, and FabricMC
#   ParticlesData Record located at:
#       net.diamonddev.ddvorigins.util.FXUtil.ParticlesData

## function definitions
def parseCoordinate(s, d):
    if (s == "~"): return "entity.get" + d + "()"
    return s


def parseOffsetCoordinate(s):
    if (s == "~"): return "0f"
    return s + "f"


def parseSpawnType(s):
    if (s == "force"): return "true"
    elif (s == "normal"): return "false"
    else: raise ValueError("Spawn type must be either \"normal\" or \"force\"")

## Cut Command String and get only the bits I need
commandStr = input("Enter Command: ")  # screw input validation i cant be bothered
if (commandStr.startswith("/")): commandStr = commandStr[1:]  # remove '/' at start if it exists

commandStr = commandStr[9:]  # remove "particle "
lumps = commandStr.split(" ")  # split at spaces into lumps

## Start Building the line of Java code
line = "FXUtil.spawnParticles(new FXUtil.ParticlesData<>(<INSERT PARTICLE TYPE>"  # start the line
line += ", "
line += parseSpawnType(lumps[9])  # add the force boolean
line += ", "
line += parseCoordinate(lumps[1], "X")  # coord x
line += ", "
line += parseCoordinate(lumps[2], "Y")  # coord y
line += ", "
line += parseCoordinate(lumps[3], "Z")  # coord z
line += ", "
line += parseOffsetCoordinate(lumps[4])  # offset x
line += ", "
line += parseOffsetCoordinate(lumps[5])  # offset y
line += ", "
line += parseOffsetCoordinate(lumps[6])  # offset z
line += ", "
line += lumps[7] + "f"  # speed
line += ", "
line += lumps[8]  # count
line += "), entity.world);"  # Finish line

## Printout phase
print()
print()
print("Generated Line of Code: ")
print()
print(line)
print()