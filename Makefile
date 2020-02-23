# Written by Josh so we don't have to remember the command every time
# we want to execute this thing.
# Later modified to include testing and stuff.

.PHONY: main build exec test test-all test-ui test-appl test-model docs zip
#yeah, yeah, this is not how its supposed to be done, etc.

main:
	# this command is marginally faster than having main depend on compile and exec
	mvn compile exec:java
build:
	# codebase compilation only, does not execute
	mvn compile
exec:
	# executes latest compiled program
	mvn exec:java
test:
	# runs test on all tiers together
	mvn clean test jacoco:report
	@echo TEST REPORT LINK:
	@echo ALL: `pwd`/target/site/jacoco/index.html

test-all:
	# runs tests on tiers in isolation and provides links to reports
	mvn exec:exec@tests-and-coverage
	@echo TEST REPORT LINKS:
	@echo ======================================================
	@echo UI:    `pwd`/target/site/jacoco/ui/index.html
	@echo APPL:  `pwd`/target/site/jacoco/appl/index.html
	@echo MODEL: `pwd`/target/site/jacoco/model/index.html
	@echo ======================================================

test-ui:
	# running tests on UI tier
	mvn clean test-compile surefire:test@ui jacoco:report@ui
	@echo TEST REPORT LINK:
	@echo UI: `pwd`/target/site/jacoco/ui/index.html

test-appl:
	# running tests on APPL tier
	mvn clean test-compile surefire:test@appl jacoco:report@appl
	@echo TEST REPORT LINK:
	@echo APPL: `pwd`/target/site/jacoco/appl/index.html

test-model:
	# running tests on MODEL tier
	mvn clean test-compile surefire:test@model jacoco:report@model
	@echo TEST REPORT LINK:
	@echo MODEL: `pwd`/target/site/jacoco/appl/index.html

docs:
	# generates design documentation PDF
	mvn exec:exec@docs
	@echo PDF: `pwd`/target/DesignDoc.pdf

zip:
	# create a zipfile distribution of the source for the project
	mvn exec:exec@zip
	@echo DISTRIBUTION ZIPFILE:
	@echo ZIP LOCATION: `pwd`/target/WebCheckers.zip
