# Hydrogen (for Fabric)
![GitHub license](https://img.shields.io/github/license/jellysquid3/hydrogen-fabric.svg)
![GitHub issues](https://img.shields.io/github/issues/jellysquid3/hydrogen-fabric.svg)
![GitHub tag](https://img.shields.io/github/tag/jellysquid3/hydrogen-fabric.svg)

All subjects intending to handle high-energy gamma leaking portal technology must be informed that they MAY
be informed of applicable regulatory compliance issues.

No further compliance information is required or will be provided, and you are an excellent test subject!

#### What?

I'm not sure yet. This mod works to reduce the memory usage of the game after startup by compacting various
data structures (primarily in block state and block model objects.)

:warning: Hydrogen hasn't received a super extensive amount of testing, but preliminary reports would suggest things are
fairly stable. That isn't a warranty however, and you should **make backups** of your worlds before installing it.

As with most things I publish, this repository's existence is not a guarantee that I'll maintain this mod. This mod is
an experiment. Please keep that in mind.

### Building from source

If you're hacking on the code or would like to compile a custom build of Lithium from the latest sources, you'll want
to start here.

#### Prerequisites

You will need to install JDK 8 in order to build Lithium. You can either install this through a package manager such as
[Chocolatey](https://chocolatey.org/) on Windows or [SDKMAN!](https://sdkman.io/) on other platforms. If you'd prefer to
not use a package manager, you can always grab the installers or packages directly from
[AdoptOpenJDK](https://adoptopenjdk.net/).

On Windows, the Oracle JDK/JRE builds should be avoided where possible due to their poor quality. Always prefer using
the open-source builds from AdoptOpenJDK when possible.

#### Compiling

Navigate to the directory you've cloned this repository and launch a build with Gradle using `gradlew build` (Windows)
or `./gradlew build` (macOS/Linux). If you are not using the Gradle wrapper, simply replace `gradlew` with `gradle`
or the path to it.

The initial setup may take a few minutes. After Gradle has finished building everything, you can find the resulting
artifacts in `build/libs`.

### License

Hydrogen is licensed under GNU LGPLv3, a free and open-source license. For more information, please see the
[license file](https://github.com/jellysquid3/hydrogen-fabric/blob/1.16.x/dev/LICENSE.txt).