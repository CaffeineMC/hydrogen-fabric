![Project icon](src/main/resources/assets/hydrogen/icon.png?raw=true)

# Hydrogen (for Fabric)
![GitHub license](https://img.shields.io/github/license/CaffeineMC/hydrogen-fabric.svg)
![GitHub issues](https://img.shields.io/github/issues/CaffeineMC/hydrogen-fabric.svg)
![GitHub tag](https://img.shields.io/github/tag/CaffeineMC/hydrogen-fabric.svg)

Hydrogen is a free and open-source mod designed to reduce the game's memory requirements by implementing more
memory-efficient data structures and logic. It's primarily designed for more heavily modded scenarios, but can
offer (small) improvements even in lightly modded or vanilla scenarios.

The mod works on both the **client and server**, and **doesn't require the mod to be installed
on both sides**. However, the benefits of running Hydrogen on the server are pretty small as of the moment.

#### Why are these patches not in [Lithium](https://github.com/CaffeineMC/lithium-fabric)?

Hydrogen relies on rather egregious hacks in order to load some code into the game, primarily because the Guava
library developers really do not want people re-implementing their interfaces. These hacks are "safe" in the sense
that the game will simply fail to start if a problem occurs, but are really rather not suitable for the standards
which Lithium intends to promote. In other words, this mod could be looked at as "things too dirty to put in Lithium."

---

## Installation

### Manual installation (recommended)

You will need Fabric Loader 0.10.x or newer installed in your game in order to load Hydrogen. If you haven't installed
Fabric mods before, you can find a variety of community guides for doing so [here](https://fabricmc.net/wiki/install).

#### Stable releases

![GitHub release](https://img.shields.io/github/release/CaffeineMC/hydrogen-fabric.svg)

The latest releases of Hydrogen are published to our [Modrinth](https://modrinth.com/mods/hydrogen) and
[GitHub release](https://github.com/CaffeineMC/hydrogen-fabric/releases) pages. Releases are considered by our team to be
**suitable for general use**, but they are not guaranteed to be free of bugs and other issues.

Usually, releases will be made available on GitHub slightly sooner than other locations.

#### Bleeding-edge builds (unstable)

[![GitHub build status](https://img.shields.io/github/workflow/status/CaffeineMC/hydrogen-fabric/gradle-ci/1.16.x/dev)](https://github.com/CaffeineMC/hydrogen-fabric/actions/workflows/gradle.yml)

If you are a player who is looking to get your hands on the latest **bleeding-edge changes for testing**, consider
taking a look at the automated builds produced through our [GitHub Actions workflow](https://github.com/CaffeineMC/hydrogen-fabric/actions/workflows/gradle.yml?query=event%3Apush).
This workflow automatically runs every time a change is pushed to the repository, and as such, the builds it produces
will generally reflect the latest snapshot of development.

Bleeding edge builds will often include unfinished code that hasn't been extensively tested. That code may introduce
incomplete features, bugs, crashes, and all other kinds of weird issues. You **should not use these bleeding edge builds**
unless you know what you are doing and are comfortable with software debugging. If you report issues using these builds,
we will expect that this is the case. Caveat emptor.

---

### Reporting Issues

You can report bugs and crashes by opening an issue on our [issue tracker](https://github.com/CaffeineMC/hydrogen-fabric/issues).
Before opening a new issue, use the search tool to make sure that your issue has not already been reported.

### Community
[![Discord chat](https://img.shields.io/badge/chat%20on-discord-7289DA?logo=discord&logoColor=white)](https://jellysquid.me/discord)

We have an [official Discord community](https://jellysquid.me/discord) for all of our projects. By joining, you can:
- Get installation help and technical support with all of our mods 
- Be notified of the latest developments as they happen
- Get involved and collaborate with the rest of our team
- ... and just hang out with the rest of our community.

---

### Building from sources

Support is not provided for setting up build environments or compiling the mod. We ask that
users who are looking to get their hands dirty with the code have a basic understanding of compiling Java/Gradle
projects. The basic overview is provided here for those familiar.

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

Hydrogen uses a typical Gradle project structure and can be built by simply running the default `build` task. After Gradle
finishes building the project, you can find the build artifacts (typical mod binaries, and their sources) in
`build/libs`.

**Tip:** If this is a one-off build, and you would prefer the Gradle daemon does not stick around in memory afterwards,
try adding the [`--no-daemon` flag](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:disabling_the_daemon)
to ensure that the daemon is torn down after the build is complete. However, subsequent builds of the project will
[start more slowly](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:why_the_daemon) if the Gradle
daemon is not available to be re-used.


Build artifacts ending in `dev` are outputs containing the sources and compiled classes
before they are remapped into stable intermediary names. If you are working in a developer environment and would
like to add the mod to your game, you should prefer to use the `modRuntime` or `modCompile` configurations provided by
Loom instead of these outputs.

---
### License

Hydrogen is licensed under GNU LGPLv3, a free and open-source license. For more information, please see the
[license file](https://github.com/CaffeineMC/hydrogen-fabric/blob/1.16.x/dev/LICENSE.txt).
