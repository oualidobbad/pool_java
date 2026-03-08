
# Set the destination directory for class files
```javac src/java/rabat/s1337/printer/*/*.java -d target```

# Specify where to find user class files
```java -cp target rabat.s1337.printer.app.Main '.' 'i' "src/resources/it_black.bmp"```

#rm -rf target