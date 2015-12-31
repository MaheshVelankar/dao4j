# Introduction #

Two maven project
  * the core of the library
  * the generator gui

https://dao4j.googlecode.com/svn/trunk/core

https://dao4j.googlecode.com/svn/trunk/generator

# Check out read only #

on terminal:

svn checkout http://dao4j.googlecode.com/svn/trunk/ dao4j-read-only

cd dao4j-read-only/core

mvn install

cd ../generator

mvn install

cd target

java -jar dao4jGenerator.jar

may be the gui shows up...

![http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-start.png](http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-start.png)

set up connection (only postgresql, firebird, mysql, for now, but you can add drivers as you like)

![http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-connection.png](http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-connection.png)

after a while, schema gets up

![http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-schema.png](http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-schema.png)

set up code options

![http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-code-options.png](http://dao4j.googlecode.com/svn/trunk/generator/wiki/img/gui-code-options.png)

and press generate code

the results may be like that on
[generated code example](http://code.google.com/p/dao4j/source/browse/trunk/exampleDaoPetstore/src/main/java)