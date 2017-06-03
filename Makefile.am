#
# Custom makefile for gradle based java applications
#


epack:
	./gradlew epack --no-daemon --gradle-user-home="WORKDIR"

build:
	./gradlew build -x test

eclipse:
	./gradlew clean cleanEclipse eclipse

clean-local:
	./gradlew clean

appRun:
	exec ./gradlew run --no-daemon

appDebug:
	exec ./gradlew debug --no-daemon

install-exec-local:
	cat build/distributions/$(PACKAGE)-$(VERSION).tar | $(am__untar) -C $(DESTDIR)$(prefix:/%=%)/

if DEBUG
CONF_ENV = dev
all-local: build

else
CONF_ENV = prod
all-local: epack
endif

.PHONY: build eclipse appRun appDebug
