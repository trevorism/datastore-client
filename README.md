# datastore-client
Client Library for trevorism datastore.

Supports classes with primitive types, Dates, Lists and Maps. Classes should have a `String` or `long` 'id' property


## Pinging implementation
The pinging implementation will ping the datastore API prior to each call.
The datastore API gets torn down, so a ping 'wakes' it up
```
Repository<MyClass> repo = new PingingDatastoreRepository<>(MyClass.class);

MyClass created = repo.create(myClass);
MyClass retrieved = repo.get("id");
MyClass updated = repo.update("id", myClass);
MyClass deleted = repo.delete("id");

```

## Fast implementation
Alternatively, you can wake up the datastore API with a ping, then perform
lots of operations. See below.
```
Repository<MyClass> repo = new FastDatastoreRepository<>(MyClass.class);

repo.ping();
for(MyClass myClass : list){
    repo.create(myClass);
}

```

