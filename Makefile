
name = LifeGame

jar :
	jar cvfm $(name).jar MANIFEST.MF *.java *.class

compile :
	javac *.java

run :
	java $(name)

