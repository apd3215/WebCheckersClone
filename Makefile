# Written by Josh so we don't have to remember the command every time
# we want to execute this thing.
main:
	# this command is marginally faster than having main depend on compile and exec
	mvn compile exec:java
build:
	# codebase compilation only, does not execute
	mvn compile
exec:
	# executes latest compiled program
	mvn exec:java
