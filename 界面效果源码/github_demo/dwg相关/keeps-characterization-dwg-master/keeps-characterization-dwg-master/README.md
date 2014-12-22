keeps-characterization-dwg
==========================

Characterization tool for dwg files, made by KEEP SOLUTIONS.


## Build & Use

To build the application simply clone the project and execute the following Maven command: `mvn clean package`  
The binary will appear at `target/dwg-characterization-tool-1.0-SNAPSHOT-jar-with-dependencies.jar`

To see the usage options execute the command:

```bash
$ java -jar target/dwg-characterization-tool-1.0-SNAPSHOT-jar-with-dependencies.jar -h
usage: java -jar [jarFile]
 -f <arg> file to analyze
 -h       print this message
 -v       print this tool version
```

## Tool Output Examples
```bash
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<dwgCharacterizationResult>
    <features>
        <item>
            <key>meta_last-author</key>
            <value>Brian</value>
        </item>
        <item>
            <key>Last-Author</key>
            <value>Brian</value>
        </item>
        <item>
            <key>Content-Type</key>
            <value>image/vnd.dwg</value>
        </item>
    </features>
    <validationInfo>
        <valid>true</valid>
    </validationInfo>
</dwgCharacterizationResult>



<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<dwgCharacterizationResult>
    <features/>
    <validationInfo>
        <valid>false</valid>
        <validationError>String index out of range: -1</validationError>
    </validationInfo>
</dwgCharacterizationResult>
```

## License

This software is available under the [LGPL version 3 license](LICENSE).

