# theScoreAutomationRepository

# Overview - 
This is a test automation framework designed as an automation testing assignment for Score. 
This framework is a Mobile automation based Multi module project with Singleton & POM based design patterns.

# Software pre-requisites:
Java - 11.0.4
Node versions used : 20.11.1, NPM version: 10.2.4
Appium version - 2.5.1 
Android Studio - Iguana (Install OS 10.0 or 11.0 emulator for best performance).
IntelliJ Community/Ultimate

# Tech stack used (Candidate machine) -
Project - Multi-module project (for scalability)
Frameworks - Test Driven Framework (TestNG), Data driven
Design patterns - Singleton & POM
Test data management - Data driven with Excel sheet
Build tool used - Maven
Device support - Real device and Emulator both
Logger used - Log4J

# Framework highlights - 
1. Its a multi-module project that means - All our required tests are kept in Browse module and all shared utilities are kept in SharedUtil module. 
2. Each module will have its own pom.xml and there will be one common pom.xml for the entire framework.
3. Driver initialization is happening through Singleton pattern : Refer DriverFactory class to see the driver related code.   
4. Appium server is getting started and ended programmatically, no manual intervention is required.
5. Data driven approach is used to feed the data from Data provider-Excel sheets. This will help in executing multiple data sets with same function. 
6. Most of the locators will have resource-ids as these are the best ones to use. However, some dynamic and static xpaths are also used in the page classes. 
7. Multi-threading concepts like ThreadLocal and Java 8 features like Streams API are used. 
8. All test cases can be independently executed. 
9. AutoGrantPermissions capability is set to true which means there will be no android device/location popups in the app. 

# Project Pre-requisites:
1. Softwares - All the softwares mentioned above should be pre-installed.
2. Cloning - Clone this repository in IntelliJ - https://github.com/gurjotahuja92/theScoreAutomationRepository.git
3. Maven setup - Run maven commands like mvn clean, mvn install, mvn complile etc to install all the dependencies provided in main pom.xml file. 
4. No VPN -Make sure you are not on any VPN as it can block the dependencies installations. 
5. Preparing emulators - Start your android emulator with 10.0 or 11.0 OS (for better performance). 
   Go to testRunner file qaRunner.xml under Browse module -> Change the platformVersion to match the emulator OS version you have selected (if required).
6. Installing Android app - Score android APK file has been already kept in the framework folder - apps (under main Browse module) and path has been provided in testrunner xml file. 
So, you do not need to worry about that, it will pick it up automatically and install it on emulator. 

# Steps to execute - 
1. Go to Browse module -> testRunner -> Run qa_runner.xml file. See if appium server gets started first and then test cases. 

# Test case flow - 
1. Launch the app, select Team, League and go to homepage.
2. Validate homepage displayed. 
3. Go to team selected via team logo on the top. 
4. Validate correct team displayed and other details. 
5. Go to team stats tab and validate stats are displayed. 
6. Run Step 1-5 with different data set using data provider.
