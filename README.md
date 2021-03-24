![Project icon](src/main/resources/assets/hydrogen/icon.png?raw=true)

# Hydrogen (for Fabric)
![GitHub license](https://img.shields.io/github/license/jellysquid3/hydrogen-fabric.svg)
![GitHub issues](https://img.shields.io/github/issues/jellysquid3/hydrogen-fabric.svg)
![GitHub tag](https://img.shields.io/github/tag/jellysquid3/hydrogen-fabric.svg)

Hydrogen is a free and open-source mod designed to reduce the game's memory requirements by implementing more
memory-efficient data structures and logic. It's primarily designed for more heavily modded scenarios, but can
offer (small) improvements even in lightly modded or vanilla scenarios.

The mod works on both the **client and server**, and **doesn't require the mod to be installed
on both sides**. However, the benefits of running Hydrogen on the server are pretty small as of the moment.

#### Why are these patches not in [Lithium](https://github.com/jellysquid3/lithium-fabric)?

Hydrogen relies on rather egregious hacks in order to load some code into the game, primarily because the Guava
library developers really do not want people re-implementing their interfaces. These hacks are "safe" in the sense
that the game will simply fail to start if a problem occurs, but are really rather not suitable for the standards
which Lithium intends to promote. In other words, this mod could be looked at as "things too dirty to put in Lithium."
## Installation

### Stable releases

#### Manual Installation (recommended)

The latest releases of Sodium are published to our [official Modrinth page](https://modrinth.com/mod/hydrogen) and [GitHub releases page](https://github.com/jellysquid3/hydrogen-fabric/releases). Usually, builds will be
made available on GitHub slightly sooner than other locations.

You will need Fabric Loader 0.10.x or newer installed in your game in order to load Sodium. If you haven't installed Fabric
mods before, you can find a variety of community guides for doing so [here](https://fabricmc.net/wiki/install).


### Bleeding-edge builds

If you are a player who is looking to get your hands on the latest **bleeding-edge builds for testing**, consider
taking a look at the builds produced through our [GitHub Actions workflow](https://github.com/CaffeineMC/hydrogen-fabric/actions/workflows/gradle.yml). This
workflow automatically runs every time a change is pushed to the repository, and as such, they will reflect the latest
state of development.

Bleeding edge builds will often include unfinished code that hasn't been extensively tested. That code may introduce
incomplete features, bugs, crashes, and all other kinds of weird issues. You **should not use these bleeding edge builds**
unless you know what you are doing and are comfortable with software debugging. If you report issues using these builds,
we will expect that this is the case. Caveat emptor.

### Reporting Issues

You can report bugs and crashes by opening an issue on our [issue tracker](https://github.com/jellysquid3/hydrogen-fabric/issues).
Before opening a new issue, please check using the search tool that your issue has not already been created.

### Community
[![Discord chat](https://img.shields.io/badge/chat%20on-discord-7289DA)](https://jellysquid.me/discord)

We have an [official Discord community](https://jellysquid.me/discord) for all of our projects. By joining, you can:
- Get installation help and technical support with all of our mods 
- Be notified of the latest developments as they happen
- Get involved and collaborate with the rest of our team
- ... and just hang out with the rest of our community.

### Building from sources

#### Requirements

- JRE 8 or newer (for running Gradle)
- JDK 8 (optional)
  - If you neither have JDK 8 available on your shell's path or installed through a supported package manager (such as
[SDKMAN](https://sdkman.io)), Gradle will automatically download a suitable toolchain from the [AdoptOpenJDK project](https://adoptopenjdk.net/)
and use it to compile the project. For more information on what package managers are supported and how you can
customize this behavior on a system-wide level, please see [Gradle's Toolchain user guide](https://docs.gradle.org/current/userguide/toolchains.html).
- Gradle 6.7 or newer (optional)
  - The [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:using_wrapper) is provided in
    this repository can be used instead of installing a suitable version of Gradle yourself. However, if you are building
    many projects, you may prefer to install it yourself through a suitable package manager as to save disk space and to
    avoid many different Gradle daemons sitting around in memory.

#### Building with Gradle

Sodium uses a typical Gradle project structure and can be built by simply running the default `build` task.

**Tip:** If this is a one-off build, and you would prefer the Gradle daemon does not stick around in memory afterwards 
(often consuming upwards of 1 GiB), then you can use the [`--no-daemon` argument](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:disabling_the_daemon)
to ensure that the daemon is torn down after the build is complete. However, subsequent Gradle builds will
[start more slowly](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:why_the_daemon) if the Gradle
daemon is not sitting warm and loaded in memory.

After Gradle finishes building the project, the resulting build artifacts (your usual mod binaries, and
their sources) can be found in `build/libs`.

Build artifacts classified with `dev` are outputs containing the sources and compiled classes
before they are remapped into stable intermediary names. If you are working in a developer environment and would
like to add the mod to your game, you should prefer to use the `modRuntime` or `modCompile` configurations provided by
Loom instead of these outputs.

Please note that support is not provided for setting up build environments or compiling the mod. We ask that
users who are looking to get their hands dirty with the code have a basic understanding of compiling Java/Gradle
projects.

### License

Hydrogen is licensed under GNU LGPLv3, a free and open-source license. For more information, please see the
[license file](https://github.com/jellysquid3/hydrogen-fabric/blob/1.16.x/dev/LICENSE.txt).
