# Simple dao on top of jdbc #
Bored in get confused by huge and hidden details of other ORM?

Try this.
Describe jdbc connection, the root folder and root package to generate code, the schema on the db and generate the code.

It will generate the code of dao and a minimal junit test.
The database connection configuration stuff are in a simple Spring context.

The rest?
The dao implementation get the relation config data.
Simple readable and quick code, the dao knows the bean and put and get data from db, just to have goog performance.

We don't need a truck to cross the street, just a good pair of shoes.

Take a look.

[howToUse](howToUse.md)

Remark: this project is young and I'm working on it, so do not trust on binaries, use the source code ;-)