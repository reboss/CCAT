# Critical Care Audit Tool (CCAT)

## What is CCAT

CCAT is an auditing tool for nurses or other certified staff.

## Purpose

CCAT will help Nurses or other certified staff complete their daily audits more efficiently. The use of pen and paper to perform daily audits has become slow, inconsistent, and generally outdated. CCAT aims to solve the aforementioned problems by introducing a consistent structure and persistent records of past audits.

### Java IDE
	
The recommended Java IDE's you can use are NetBeans, Eclipse, and IntelliJ.
	
[NetBeans](https://netbeans.org/ "NetBeans"), [Eclipse](https://eclipse.org/ "Eclipse"), [IntelliJ](https://www.jetbrains.com/idea/ "IntelliJ")

The code is runnable inside Netbeans, Eclipse and IntelliJ. We are using the JavaFX framework for the UI. The latest JDK and JRE versions are recommended.

## Build

This program is built and tested on windows.

* Before building CCAT, be sure to create a folder called "CCAT-files" in "Program Files (x86)". In CCAT-files place the latest version of [Sqlite-JDBC] (https://bitbucket.org/xerial/sqlite-jdbc/downloads).

* To create the database, run SQLiteCreate.java as a script within your IDE.  Then run the file SQLiteSeeder (also from your IDE).  Both files are located in package, "database".  These files are not included in the build and should be only run once. 

* To build in Netbeans, select: "Run" > "Clean and Build Project" located in the top navigation bar.

* To build in Eclipse, you can either turn on "Build Automatically" (recommended) or select: "Project" > "Build Project" located in the top navigation bar. Clean before building is recommended.

* To build in IntelliJ, select: "Build" > "Rebuild Project" located in the top navigation bar.

## Run

You can run CCAT either from your IDE or if you have the .jar file you can run it from the command line, jar must be in project folder.

```
java -jar CCAT.jar
```

## Contribution

Anyone can contribute and work on any available issue.

Simply make sure you have an appropriate java doc above any methods you create or modify. The format used is the standard NetBeans formatting. After, simply make a pull request and have patience.
If you have any questions about an issue feel free and encouraged to ask.

------------------------------------------
* See spec documents for more details and visual aid.
