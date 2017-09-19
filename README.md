# datastore-client
Client Library for trevorism datastore.

Supports primitive types, Dates, Lists and Maps

Classes should have a `String` or `long` 'id' property

```
Repository<MyClass> repo = new PingingDatastoreRepository<>(MyClass.class);

MyClass created = repo.create(myClass);
MyClass retrieved = repo.get("id");
MyClass updated = repo.update("id", myClass);
MyClass deleted = repo.delete("id");

```