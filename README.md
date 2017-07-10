# HappyTrails

Welcome to **HappyTrails**, a trails plugin for Sponge!

Authors: gabizou

The intention of this plugin is pretty simple: Make fancy trails for 
players to use in game with permissions! The limit is only as far as your
imagination! There are a default set of trails provided, however, they
are not required to persist! You, as a server administrator, can customize
every single trail and even add or remove trails!

#### Current Features
- Set trails on yourself
- Remove a trail from yourself
- Trails persist between restarts/logins so you don't have to set it every time!
- Fallback trails to the default if your last used trail was removed or changed!
- Only uses SpongeAPI 5.x as a base, so compatible with Minecraft 1.10.2 servers and up!

#### Commands

Command | Description | Permission
--------| ------------| ----------
/trail set \<trailId> | Sets your current trail to the desired one | happytrails.command.set
/trail reset | Resets your trail, removes it from you as a player | happytrails.command.reset

#### Trail Config

The configuration file is fairly dynamic. It will generate a default set of
trails for you, as a server owner, to utilize and familiarize yourself with the
options. The trails themselves are fully serializable and deserializable
using Sponge's native systems. The current default configuration looks like so:
```hocon
defaultTrail="happytrails:hearts"
trails=[
    {
        ContentVersion=1
        id="happytrails:hearts"
        name=Hearts
        "particle_effect" {
            Options=[
                {
                    Option="minecraft:velocity"
                    Value {
                        x=0.5
                        y=1.0
                        z=0.4
                    }
                },
                {
                    Option="minecraft:quantity"
                    Value=7
                }
            ]
            Type="minecraft:heart"
        }
        period=10
        radius=30
    },
    {
        ContentVersion=1
        id="happytrails:villager_happy"
        name="Happy Villager"
        "particle_effect" {
            Options=[
                {
                    Option="minecraft:offset"
                    Value {
                        x=0.5
                        y=1.0
                        z=0.4
                    }
                },
                {
                    Option="minecraft:velocity"
                    Value {
                        x=0.5
                        y=1.0
                        z=0.4
                    }
                },
                {
                    Option="minecraft:quantity"
                    Value=13
                }
            ]
            Type="minecraft:happy_villager"
        }
        period=10
        radius=30
    },
    {
        ContentVersion=1
        id="happytrails:villager_storm"
        name="Stormy Villager"
        "particle_effect" {
            Options=[
                {
                    Option="minecraft:offset"
                    Value {
                        x=0.0
                        y=3.0
                        z=0.0
                    }
                },
                {
                    Option="minecraft:velocity"
                    Value {
                        x=0.0
                        y=0.1
                        z=0.0
                    }
                },
                {
                    Option="minecraft:quantity"
                    Value=5
                }
            ]
            Type="minecraft:angry_villager"
        }
        period=10
        radius=30
    },
    {
        ContentVersion=1
        id="happytrails:crit_strike"
        name="Critical Strike"
        "particle_effect" {
            Options=[
                {
                    Option="minecraft:color"
                    Value {
                        Blue=139
                        ContentVersion=1
                        Green=139
                        Red=0
                    }
                },
                {
                    Option="minecraft:offset"
                    Value {
                        x=10.0
                        y=3.0
                        z=10.0
                    }
                },
                {
                    Option="minecraft:quantity"
                    Value=10
                }
            ]
            Type="minecraft:critical_hit"
        }
        period=5
        radius=20
    },
    {
        ContentVersion=1
        id="happytrails:cloud"
        name=Clouds
        "particle_effect" {
            Options=[
                {
                    Option="minecraft:offset"
                    Value {
                        x=0.0
                        y=3.0
                        z=0.0
                    }
                },
                {
                    Option="minecraft:velocity"
                    Value {
                        x=0.01
                        y=0.01
                        z=0.01
                    }
                },
                {
                    Option="minecraft:quantity"
                    Value=2
                }
            ]
            Type="minecraft:cloud"
        }
        period=1
        radius=10
    }
]

```

#### Future Features
- Dynamic trails that change with each time they are spawned (think rainbows)
- Randomization of trails (using a trail that allows for random values to be set)
- Creation of trails through commands with an in game menu system
- Per trail permissions
- Maybe allowing multiple trails to be set at once

#### Version History


Version | Features / Changes | Date
--------|--------------------|-------
0.1.0 | <ul><li>Configurable Trails</li><li>Trails have period duration and player range</li></ul> | July 9th, 2017
0.1.1 | <ul><li>Per-trail permissions. The permission for the trail is based on the `id` of the trail *after* the plugin id. So, if the id is `happytrails:villager_storm`, the permission will be `happytrails.trail.villager_storm` | July 9th, 2017
