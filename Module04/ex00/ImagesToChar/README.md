
## Requirements

- Java JDK 8 or higher

---

## Compilation

Open a console in the project root folder (ImagesToChar/) and run:

    javac src/java/rabat/s1337/printer/*/*.java -d target

---

## Running

Still from the project root folder, run:

    java -cp target rabat.s1337.printer.app.Main <whiteChar> <blackChar> <imagePath>

  whiteChar  - single character to represent bright/white pixels
  blackChar  - single character to represent dark/black pixels
  imagePath  - path to the BMP image file

Example:

    java -cp target rabat.s1337.printer.app.Main '.' '0' src/resources/it_black.bmp