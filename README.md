# datastore-client
![Build](https://github.com/trevorism/datastore-client/actions/workflows/build.yml/badge.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/datastore-client)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/datastore-client)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/datastore-client)

Client Library for trevorism datastore.

Current [Version](https://github.com/trevorism/datastore-client/releases/latest)

Supports classes with primitive types, Dates, Lists and Maps. Classes should have a `String` or `long` 'id' property


## Pinging implementation
The pinging implementation will ping the datastore API on instantiation.
The datastore API gets torn down, so a ping 'wakes' it up
```
Repository<MyClass> repo = new PingingDatastoreRepository<>(MyClass.class);

MyClass created = repo.create(myClass);
MyClass retrieved = repo.get("id");
MyClass updated = repo.update("id", myClass);
MyClass deleted = repo.delete("id");

```

## Fast implementation
If the datastore is awoken, just call it with the fast implementation.
```
Repository<MyClass> repo = new FastDatastoreRepository<>(MyClass.class);

repo.ping();
for(MyClass myClass : list){
    repo.create(myClass);
}

```

