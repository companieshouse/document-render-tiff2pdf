all: build

clean:
	mvn clean

build:
	mvn compile

test: test-unit

test-unit: clean
	mvn test

package:
	$(eval commit := $(shell git rev-parse --short HEAD))
	$(eval tag := $(shell git tag -l 'release/*' -l 'hotfix/*' --points-at HEAD))
	$(eval VERSION := $(shell if [[ -n "$(tag)" ]]; then basename $(tag); else echo $(commit); fi))
	mvn versions:set -DnewVersion=$(VERSION) -DgenerateBackupPoms=false
	mvn package -DskipTests

dist: clean package

.PHONY: all clean build test test-unit package dist
