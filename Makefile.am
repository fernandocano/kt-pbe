#
# Custom makefile for gradle based java applications
#


epack:
	./gradlew epack --no-daemon --gradle-user-home="WORKDIR" -Pv=$(VERSION)

build:
	./gradlew build -x test -Pv=$(VERSION)

eclipse:
	./gradlew clean cleanEclipse eclipse -Pv=$(VERSION)

clean-local:
	./gradlew clean -Pv=$(VERSION)

appRun:
	exec ./gradlew run --no-daemon -Pv=$(VERSION)

appDebug:
	exec ./gradlew debug --no-daemon -Pv=$(VERSION)

install-exec-local:
	cat build/distributions/$(PACKAGE)-$(VERSION).tar | $(am__untar) -C $(DESTDIR)$(prefix:/%=%)/

if DEBUG
CONF_ENV = dev
all-local: build

else
CONF_ENV = prod
all-local: epack
endif

.PHONY: build epack eclipse appRun appDebug
