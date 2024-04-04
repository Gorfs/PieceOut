# Piece out

## Testing with JUnit

### On the command line

when executing tests on this project you need to download the JUnit library from [https://junit.org/junit4/](https://junit.org/junit4/).

First step is to compile the project as usual
```sh
javac ./main/*.java
```

Then run the tests while using the path to your Junit library

```sh
javac -cp .:path/to/junit/junit-<version>.jar org.junit.runner.JUnitCore AllTests.java
```


### In VScode 


Install the java Test runner extension

click on the "testing" icon next to the extension, then click on enable java testing with JUnit, 
You can now run tests

Now on the testing icon you can run any test you want in that panel.