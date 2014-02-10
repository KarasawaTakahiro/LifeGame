
name = LifeGame
doc = doc
sourcepath = .

jar :
	jar cvfm $(name).jar MANIFEST.MF *.java *.class

compile :
	javac *.java

run :
	java $(name)

doc :
	javadoc -d $(doc) -sourcepath $(sourcepath) *.java

