# datastore-client
![Jenkins](https://img.shields.io/jenkins/build/http/trevorism-build.eastus.cloudapp.azure.com/datastore-client)
![Jenkins Coverage](https://img.shields.io/jenkins/coverage/jacoco/http/trevorism-build.eastus.cloudapp.azure.com/datastore-client)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/datastore-client)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/datastore-client)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/datastore-client)

Client Library for trevorism datastore.

Current Version: 2.2.0

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

