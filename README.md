
# Information Theory Calculator
A JavaFX App for doing some stuff related to information theory.

### Author: 
Georgio Yammine

## Application Features:

  - #### Entropy Calculator:
    Calculates the entropy in bits for a String.

  - #### Huffman Encoding:
    Calculates the huffman encoding for a given string and compares it to the uniform encoding.
    It shows how many bits will be used in each and how much is saved by using huffman.

    The program also shows the Symbol frequency, the symbol encoding, and the message encoding.

  - #### Trifid Cipher:
    Encrypt and Decrypt a plaintext using the Trifid cipher with a key.
    The cipher uses the key to create a 3x3x3 table using the key and then uses it to encrypt and decrypt.

    The program shows the 3x3x3 generated table and the step by step to encrypt/decrypt.

    Trifid only supports alphabet characters [a-z] and the '.' character (3x3x3=27) while the spaces are ignored and added later.

  - #### Styling
    The application support both LIGHT and DARK mode. This can be changed by the user in the top right window using a switch button and this is remembered by the program.

## Releases
Application builds can be found under [releases](https://github.com/georgioyammine/Information-Theory-Calculator/releases) for both [Windows] and Mac devices.

You can run the release by opening  bin/Information Theory Calculator[.bat]

## Getting Started
- ### Required Software dependencies
    1. JDK 11+: OpenJDK 11 (LTS) - JVM: Hotspot, AdoptOpenJDK 11, available at https://adoptopenjdk.net/.
  
- ### Dependencies automatically installed by Maven
    1. org.openjfx JavaFX 11
    2. org.jfxtras jmetro
    3. org.controlsfx controlsfx

- ### Running Process
    1. Run via IDE launcher.Main
    2. Run via Maven: `mvn javafx:run` 
  
- ### Building Process
  `mvn clean javafx:jlink`
  
  - #### cross building
    To create a cross platform build (for a different OS):
    First, you will need the JDKs for those other platforms. Download them as archives (zip or tar.gz), not installers from https://adoptopenjdk.net/releases.html, and unpack them to directories of your choice.
    Then pass this to the jmodsPath tag in the pom.xml file as follows:
    ``` 
    <plugin>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-maven-plugin</artifactId>
            <version>0.0.6</version>
            <configuration>
                <launcher>Information Theory Calculator</launcher>
                <mainClass>launcher.Main</mainClass>
                <!--for building for cross platform include the platform jdk below (/Contents/Home/jmods)-->
                <jmodsPath>
                        <!--mac-->
                    C:/Program Files/AdoptOpenJDK/mac/OpenJDK11U-jdk_x64_mac_hotspot_11.0.11_9/jdk-11.0.11+9/Contents/Home/jmods
                </jmodsPath>
            </configuration>
        </plugin>
    ```
    
    After passing the target JDK, running `mvn clean javafx:jlink` will generate the image for the target OS.
    

  Generated Image can be found under target/image.

## License

The project is licensed under GPL 3. See [LICENSE](https://github.com/georgioyammine/Information-Theory-Calculator/blob/main/LICENSE) file for the full license.

## Preview

![i1](https://user-images.githubusercontent.com/61707078/118400842-103ba680-b66c-11eb-900b-ded125874cf6.PNG)
![i2](https://user-images.githubusercontent.com/61707078/118400843-116cd380-b66c-11eb-8599-83e001115232.PNG)
![i3](https://user-images.githubusercontent.com/61707078/118400846-129e0080-b66c-11eb-81f1-a3fde2aee278.PNG)
