JAR=Java-Tiff2PDF.jar
VERSION=1.0.0
TESTS?=./...
CHS_ENV_HOME?=$(HOME)/.chs_env
CHS_ENVS=$(CHS_ENV_HOME)/global_env
SOURCE_ENV=for chs_env in $(CHS_ENVS); do test -f $$chs_env && . $$chs_env; done

all: build

test:
	mvn test

build:
	mvn clean install package

clean:
	mvn clean

dist: build
	-rm -rf ./.dist-build
	mkdir ./.dist-build
	cp ./target/$(JAR) ./.dist-build/$(JAR)
	cp ./start.sh ./.dist-build/start.sh
	cd ./.dist-build; zip $(JAR).zip $(JAR) start.sh
	mv ./.dist-build/$(JAR).zip $(JAR)-$(VERSION).zip
	rm -rf ./.dist-build

.PHONY: all build clean
