# RealmsHost

Providing foundational components and player social activities for LTSX

[简体中文](./README_zh_CN.md) | **English**

RealmsHost offers the following components:

- Player Profession System
- Town System
- Player Friends System
- Respawn Point Selector
- Modification of the original MinecraftServer.class to implement LTSX features
- Several GUI components
- Integration with REI
- A variety of utility classes including `ItemUtils`, `PlayerUtils`, `WorldUtils`, etc.
- A PlayerHUD interface that can be called by other mods
- Global `&` color identifier and chat formatting codes

## Player Profession System

The Player Profession System offers the following three professions:

- Farmer (Agriculture)
- Miner (Mining)
- Knight (Hunting)

`Farmer` provides players with the farmer profession. This profession allows players to tame all animals provided by LTSX, and some animals (such as cows and sheep) will be attracted to players who are farmers. Additionally, farmers accelerate the growth rate of nearby crops and can plant some rare or difficult-to-grow crops, yielding extra harvests.

`Miner` offers the mining profession to players. Miners inherently have an `Efficiency III` buff and can use advanced tools. Miners also have a 1.5 times higher chance of encountering rare minerals compared to other players, allowing them to mine these minerals. Moreover, the fossil industry tech tree requires the miner profession to be unlocked.

`Knight` provides players with the knight profession. Knights gain a `Speed` buff and receive an additional 30 seconds duration when consuming effect-laden food or potions. Knights can wield a variety of rare weapons and enchantments, with a chance to drop rare items when defeating enemies or monsters. These rare items are necessary resources for unlocking tech trees.

## Town System

The Town System provides gathering places for players. A town management system based on commands or blocks allows players to easily establish and organize towns. Upon joining the server, players will be prompted to choose to join an existing town or act independently.

Towns have the following elements:

- Town Main City
- Town Shop
- Team Land
- Town Task Center
- Town Hall
- Town Bank
- Town Industrial Base

`Town Main City` is the spawn location for all new players or those teleporting to the town. Anyone teleporting into the town will spawn here. Additionally, the Town Main City serves as a gathering and activity center for town residents, defined as an area `no smaller than 16x16`.

`Town Shop` is the town's official trading center. It not only caters to the trading needs of town residents but also handles inter-town trade activities. It is defined as having a `town shop center block, shop block, and trading station block`.

`Team Land` is the residential area for town residents. It provides a sufficiently large area for residents to build their own structures or offers building templates. Claimed Team Lands are protected, and residents from outside the town or unauthorized town residents cannot destroy blocks within.

`Town Task Center` is where residents receive tasks. The center offers different tasks for residents of different professions. Five new tasks are refreshed daily, with rare rewards for completing daily tasks. Doing tasks is a quick way to level up and unlock tech trees.

`Town Hall` houses the town management block. This block is accessible only to the town owner and authorized residents, allowing them to manage all aspects of the town.

`Town Bank` houses the town bank block, which stores the town's official savings. Currency earned from trading at the `Town Shop` is stored in the Town Bank and cannot be moved or used elsewhere.

`Town Industrial Base` is where the town's production takes place. This includes, but is not limited to, mines, farms, and industrial bases. These industrial facilities can significantly increase the town's productivity and resource reserves, with connections to the Town Shop for selling excess resources.

### Establishment and Assessment of Towns

The establishment and assessment of towns are defined through various town management blocks. These blocks can be obtained by applying at the server's main city and are NBT-bound to the town owner, preventing misuse by others.

Establishing a town also requires a certain financial reserve, as it involves purchasing land and other management blocks to expand the town's territory. Land prices vary depending on the location.

### Town Taxation

Due to the integration of the `Economics` system, the system adjusts the tax rate based on the size of the town. This tax rate is part of the funds that the town owner must regularly pay to the server. This funding can be provided by town residents (high resident tax) or borne by the town owner through the Town Bank (high resident welfare). The system categorizes towns based on their resident tax rate, visible in the GUI when new players choose a town to join.

### Town Mergers and Wars

LTSX allows towns to resolve disputes through reasonable battles. War declarations are initiated in the `Town Hall` through the `town management block`. Once war is declared, it can proceed without the consent of the opposing town. During the war, town protection mechanisms are disabled, and facilities within the town and Team Lands are unprotected. The tax system adjusts based on the duration of the war, with longer wars incurring higher taxes.

After 48 hours of real-time, a ceasefire agreement can be proposed to the opposing town. The terms of the ceasefire can be set by the dominant side. Once agreed upon by the opposing town, the town reverts to normal mode, with tax and protection systems restored. Ceasefire terms include:

- Ceding territory
- Indemnity

`Ceding territory` allows the dominant town to occupy part of the losing town's land. Once 100% of the territory is ceded, the town is absorbed, with the merged town's name and ownership decided by the dominant side.

`Indemnity` requires the losing town to pay a portion of money or resources to the dominant town, not exceeding 80% of the losing town's total resources.

## Player Friends System

RealmsHost provides a player friends system. Now, by crouching and right-clicking on another player, you can open their player information page. On this page, you can view basic player information and add them as a friend. Players added as friends will have a [F] tag in their title. Friends can engage in private chats and transfers.

## Respawn Point Selector

RealmsHost modifies the death screen, allowing players to choose to respawn nearby or at a selected location. Possible respawn locations include: `Historical death point`, `Bed`, `Town (if any)`, `Home`, `Server main city`.

Using the respawn point selector will deduct a teleportation totem.

## LTSX Features

As RealmsHost is designed for LTSX, it offers a series of fine-tuned features for LTSX needs, such as:

- /tick command
- /manage command
- Console Tab completion
- MySQL and Grafana based visual performance panel
- /player command
- /hotfix command
- /freeze command
- /crash command
- /toast command
- /bossbar command
- /chatmsg command
- /actionbar command

Additionally, RealmsHost has taken over the original MinecraftServer.class, now implemented by RealmsHost Extension (RHX). RHX offers optimization features similar to those of Paper.

## GUI Components

RealmsHost, while implementing many features, has also developed GUI components for these features, such as:

- ColorButton - A colored button
- AnnouncementComponent - Announcement board

## Integration with REI

RealmsHost integrates a modified version of REI.

## Utility Classes

RealmsHost provides utility classes for use by other mods.

- RawCommand - A command executor that returns results (can be executed by players or command blocks)
- SafeTeleport - Safe teleportation that does not randomly teleport to dangerous locations

## PlayerHUD

RealmsHost, inspired by Create's train HUD interface, now continuously displays essential information in PlayerHUD, such as player temperature, environmental temperature, announcements, death points, etc., with beautiful transition animations.

Other mods can call RealmsHost's Interface to send content to PlayerHUD for display.

## Global & Identifier

Players can now use `&` everywhere to replace `§`. Additionally, `&h` is provided to change the font background color, and `&+#+RRGGBB` to define custom colors.

`&` can also be followed by `[component]` to display graphics, such as `&[teleport_request] context` to display a teleport request style font in the chat bar, usually with a graphic background to highlight the text.
