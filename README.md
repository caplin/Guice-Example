A Guice Dependency Injection Tutorial
=====================================

This Java application begins as a simple utility that retrieves FX quotes from Yahoo! and prints them to the console. By moving through the tags you can see the application evolve to use dependency injection, first manually and then using the Guice library. By the end of the project the application will become a Rates DataSource Adapter which can provide streaming rates to the Caplin FX Motif web application.

The application is used to illustrate the ideas discussed in the Powerpoint presentation at the root of the project.

Guice features covered:

* Standard binding
* The @Named annotation
* Assisted Inject

Plus some classes from Guava:

* Multimap
* EventBus

Usage
=====

Start by checking out tag "1.0". This gives you the starting point for the project - when it runs it will retrieve FX quotes for EURUSD once a second and print them to the console. At this point the application does not use the dependency injection pattern at all, each class instantiates its own dependencies.

Then you can move through the tags/branches to see the application develop!

<table>
  <tr>
    <td>1.0</td>
    <td>Starting point for the application.</td>
  </tr>
  <tr>
    <td>1.1</td>
    <td>Ready for the introduction of a dependency injection library. All dependency resolution is now in the main() method and all dependencies are declared in the constructors.</td>
  </tr>  
  <tr>
    <td>1.2</td>
    <td>Dependency injection is implemented using Guice.</td>
  </tr>
  <tr>
    <td>1.2a</td>
    <td>Feature branch showing how dependencies can be moved out of the module into their own classes, using the ScheduledExecutorService as an example.</td>
  </tr>
  <tr>
    <td>1.2b</td>
    <td>Feature branch showing how the @Named annotation can be used to inject different instances of classes. We now inject a String called "CurrencyPair" into the Application and a String called "LogFilePath" to the AuditLogger.</td>
  </tr>
  <tr>
    <td>1.2c</td>
    <td>Feature branch showing how the injector can be instantied with different modules based on a command line argument.</td>
  </tr> 
  <tr>
    <td>1.3</td>
    <td>Ready for the introduction of a factory. We have a new class QuoteBuilder, but how does it create new instances of the Quote class?</td>
  </tr>
  <tr>
    <td>1.4</td>
    <td>A hand-coded QuoteFactory has been implemented. This is ready to be replaced by Guice Assisted Inject.</td>
  </tr>
  <tr>
    <td>2.0</td>
    <td>The QuoteBuilder class is now a factory that works using Assisted Inject. Also, the project is now a Caplin DataSource Adapter which can provide streaming FX rates.</td>
  </tr>
  <tr>
    <td>2.1</td>
    <td>The FXQuoteProvider is slightly simplified by using the Multimap provided in Guava.</td>
  </tr>
  <tr>
    <td>2.2</td>
    <td>A Guava EventBus is now used for posting incoming Quote events.</td>
  </tr>  
</table>
