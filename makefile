build:
	@javac -d out/ *.java
	@echo "building unocli"

run:
	@java -cp out/ Main

clean:
	@rm -rf ./out/
	@echo "removeing contents of out/"
