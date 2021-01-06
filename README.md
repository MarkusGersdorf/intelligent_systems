# How to setup JavaFX

For this task Java 11 was used and accordingly JavaFX 11. JavaFX must be included in order to be able to run it via
Intellij, for example.

1. download [JavaFX](https://gluonhq.com/products/scene-builder/)

2. Then it must be added to the project directory

3. To do this, the unpacked folder is added to the 'lib' folder in the main directory

After JavaFX has been added, JavaFX must be added to the library. To do this, go to *File -> Project Structure* and
select *Libaries* on the left side. In order to add now something new, a new Java project library is added over the
plus. In the again opening window then in the JavaFX folder added before the lib folder is selected. Afterwards a query
opens, to which module the new library is to be added. There *ludo* is selected. After this step, the last change
follows. JavaFX must be added to the VM options. Todo this go to *Run->Edit Configurations...* .

The following code must be added inside the 'VM Options' field:

`--module-path "C:\Users\Marcel\intelligent_systems\lib\javafx-sdk-11.0.2\lib"`

`--add-modules javafx.controls,javafx.fxml`

Afterwards the configuration can be saved and executed