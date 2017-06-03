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
	exec ./gradlew appRun --no-daemon

appDebug:
	exec ./gradlew appDebug --no-daemon

install-exec-local:
	cat build/distributions/$(PACKAGE)-$(VERSION).tar | $(am__untar) -C $(DESTDIR)$(prefix:/%=%)/
	$(SED) -i \
		-e "/^CLASSPATH/ s|\(\$$APP_HOME/lib\)\(/muk-microservices-[0-9\.]*\.jar\)|REVERT\2|" \
		-e "/^CLASSPATH/ s|\$$APP_HOME/lib|$(jettydir)|g" \
		-e "/^CLASSPATH/ s|REVERT|\$$APP_HOME/lib|" $(DESTDIR)$(prefix:/%=%)/$(PACKAGE)-$(VERSION)/bin/$(PACKAGE)

if DEBUG
CONF_ENV = dev
all-local: build

else
CONF_ENV = prod
all-local: epack
endif

.PHONY: build eclipse epack appRun appDebug
