![Header](./header.png)
-----
[![GitHub](https://img.shields.io/github/license/ShatteredSuite/ShatteredScrolls?style=for-the-badge&logo=github)](https://github.com/ShatteredSuite/ShatteredScrolls/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/ShatteredSuite/ShatteredScrolls?style=for-the-badge&logo=github)](https://github.com/ShatteredSuite/ShatteredScrolls/issues)
[![GitHub Version](https://img.shields.io/github/release/ShatteredSuite/ShatteredScrolls?label=Github%20Version&style=for-the-badge&logo=github)](https://github.com/ShatteredSuite/ShatteredScrolls/releases)
![Spigot Version](https://img.shields.io/spiget/version/128937421?label=Spigot%20Version&style=for-the-badge)
[![Discord](https://img.shields.io/badge/Get%20Help-On%20Discord-%237289DA?style=for-the-badge&logo=discord)](https://discord.gg/zUbNX9t)
[![Ko-Fi](https://img.shields.io/badge/Support-on%20Ko--fi-%23F16061?style=for-the-badge&logo=ko-fi)](https://ko-fi.com/uberpilot#)
## For Server Owners

### Prerequisites
1. A variant of Bukkit running any version 1.12 or higher. We recommend using [Paper](https://papermc.io/), if you don't have any other reason to use something else.

That's it. Start your server, stop it, make any configuration adjustments, then start it again.

### Default Files

Copies of the latest default files can be found [here](/src/main/resources/).

### Permissions


## For Developers
Add the following to your build script:

**Gradle**
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ShatteredSuite:ShatteredUtilities:Tag'
    implementation 'com.github.ShatteredSuite:ShatteredScrolls:Tag'
}
```

**Maven**
```xml
    <repositories>
        <!-- Load dependencies from GitHub. -->
         <repository>
             <id>jitpack.io</id>
             <url>https://jitpack.io</url>
         </repository>
    </repositories>

    <dependencies>
        <!-- https://github.com/ShatteredSuite/ShatteredUtilities -->
        <dependency>
            <groupId>com.github.ShatteredSuite</groupId>
            <artifactId>ShatteredUtilities</artifactId>
            <version>Tag</version>
        </dependency>
        <!-- https://github.com/ShatteredSuite/ShatteredScrolls -->
        <dependency>
            <groupId>com.github.ShatteredSuite</groupId>
            <artifactId>ShatteredScrolls</artifactId>
            <version>Tag</version>
        </dependency>
    </dependencies>
```

Or build both jars and add them as dependencies for your build process.