# dUtils

dUtils is a lightweight Paper plugin written in Kotlin, designed to provide a small set of server utility commands, warp persistence, and configurable message handling.

## Highlights

- Kotlin + Paper 1.20.x
- Command handling via revxrsal Lamp
- Adventure-based chat components
- YAML-backed configuration and localization
- Persistent warp storage
- Centralized exception and permission feedback

## Provided Features

### Utility Commands
- Difficulty control for the main world
- Gamemode changes for players
- Warp creation, deletion, listing, and teleportation

### Runtime Support
- Reload of registered config/message files
- Consistent error handling for invalid players, permissions, and syntax
- Placeholder-based message templates

## Commands

- `/dutils reload`
- `/difficulty <mode>`
- `/gamemode <mode> [player]`
- `/warp <name>`
- `/setwarp <name>`
- `/delwarp <name>`
- `/warps`

## Permissions

- `dutils.admin`
- `dutils.admin.reload`
- `dutils.difficulty`
- `dutils.gamemode`
- `dutils.warp`
- `dutils.warps.set`
- `dutils.warps.delete`

## Configuration Files

- `config.yml` — general plugin configuration
- `lang.yml` — message and translation definitions
- `warps.yml` — saved warp locations