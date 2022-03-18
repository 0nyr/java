# WARN: running the project from the class files won't work since the res/ folder is outside the class/

getClass().getResourceAsStream("/res/pixel_art_onyr_120x51.png")
This properly works only if testpack/ and res/ are at same level which is not the case in normal 
directory but which becomes true when creating the .jar:

 ❮ onyr ★  kenzae❯ ❮ jar❯❯ jar tf Testpack.jar 
META-INF/
META-INF/MANIFEST.MF
testpack/
testpack/display/
testpack/display/LabelSprite.class
testpack/Main.class
res/
res/pixel_art_onyr_120x51.png
