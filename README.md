# kt-pbe

### Goal
Add PBE keys to a keystore in the jceks format.

This is an approach to storing secrets that can be accessed at runtime without
providing sensitive information in a properties file.

### How do I get set up?
./gradlew build
alternatively make

You can setup an eclipse project by executing the eclipse target.

### CLI
Untar the distribution and execute with the following options:
```
kt-pbe -keystore </path/to/store> -password <store password> -alias <identifier> -secret <pbe secret>
```
